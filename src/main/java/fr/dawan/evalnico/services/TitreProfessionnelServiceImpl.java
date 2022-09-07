package fr.dawan.evalnico.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.dawan.evalnico.dto.BlocCompEnrichiDto;
import fr.dawan.evalnico.dto.BlocCompetencesDto;
import fr.dawan.evalnico.dto.CompetenceDto;
import fr.dawan.evalnico.dto.CountDto;
import fr.dawan.evalnico.dto.DG2TitreProDto;
import fr.dawan.evalnico.dto.DG2TrainingDto;
import fr.dawan.evalnico.dto.EtudiantDto;
import fr.dawan.evalnico.dto.FormationDto;
import fr.dawan.evalnico.dto.PromotionDto;
import fr.dawan.evalnico.dto.TitreProfessionnelDto;
import fr.dawan.evalnico.dto.UtilisateurDto;
import fr.dawan.evalnico.entities.Formation;
import fr.dawan.evalnico.entities.TitreProfessionnel;
import fr.dawan.evalnico.entities.Utilisateur;
import fr.dawan.evalnico.exceptions.NoDataException;
import fr.dawan.evalnico.repositories.TitreProfessionnelRepository;
import fr.dawan.evalnico.tools.DtoTools;
import fr.dawan.evalnico.tools.PdfTools;
import freemarker.template.Configuration;
import freemarker.template.Template;

@Service
@Transactional
public class TitreProfessionnelServiceImpl implements TitreProfessionnelService {

	@Autowired
	private TitreProfessionnelRepository titreProfessionnelRepository;
	@Autowired
	private CompetenceService competenceService;
	@Autowired
	private BlocCompetenceService blocCompetenceService;
	@Autowired
	private PromotionService promotionService;
	@Value("${app.storagefolder}")
	private String storageFolder;
	@Autowired
	private Configuration freemarkerConfig;
	@Autowired
	private FormationService formationService;

	public String getStorageFolder() {
		return storageFolder;
	}

	public void setStorageFolder(String storageFolder) {
		this.storageFolder = storageFolder;
	}

	@Override
	public TitreProfessionnelDto saveOrUpdate(TitreProfessionnelDto tDto) throws Exception {

		TitreProfessionnel titre = DtoTools.convert(tDto, TitreProfessionnel.class);
		titre = titreProfessionnelRepository.saveAndFlush(titre);
		return DtoTools.convert(titre, TitreProfessionnelDto.class);
	}

	@Override
	public void delete(long id) throws Exception {
		// suppression des blocs de compétences qui font référence a ce titre :
		blocCompetenceService.deleteByTitreProId(id);
		// suppression des promos qui font reference a ce titre
		List<PromotionDto> promoToDelete = promotionService.findByTitreProId(id);
		for (PromotionDto promotionDto : promoToDelete) {
			promotionService.delete(promotionDto.getId());
		}

		titreProfessionnelRepository.deleteById(id);
	}

	@Override
	public List<TitreProfessionnelDto> findByTitre(String titre) {

		List<TitreProfessionnel> resultInDb = titreProfessionnelRepository.findAllByTitreContaining(titre);
		List<TitreProfessionnelDto> result = new ArrayList<TitreProfessionnelDto>();

		for (TitreProfessionnel titreProfessionnel : resultInDb) {
			result.add(DtoTools.convert(titreProfessionnel, TitreProfessionnelDto.class));
		}
		return result;
	}

	@Override
	public List<TitreProfessionnelDto> findAll() {
		List<TitreProfessionnel> resultInDb = titreProfessionnelRepository.findAll();
		List<TitreProfessionnelDto> result = new ArrayList<TitreProfessionnelDto>();
		for (TitreProfessionnel titreProfessionnel : resultInDb) {
			result.add(DtoTools.convert(titreProfessionnel, TitreProfessionnelDto.class));
		}
		return result;
	}

	@Override
	public TitreProfessionnelDto findById(long id) {
		Optional<TitreProfessionnel> t = titreProfessionnelRepository.findById(id);
		if (t.isPresent()) {
			return DtoTools.convert(t.get(), TitreProfessionnelDto.class);
		}
		return null;
	}

	@Override
	public int importFromDG2() throws Exception {
		RestTemplate restTemplate = new RestTemplate();// objet permettant de faire des requêtes HTTP

		ObjectMapper mapper = new ObjectMapper(); // objet de la librairie Jackson permettant de convertir de json>objet
		mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);

		ResponseEntity<String> rep = restTemplate
				.getForEntity("https://dawan.org/public/training/search?keywords=titre-professionnel", String.class);// req

		// GET
		int nbt = 0;
		int nbf = 0;
		if (rep.getStatusCode() == HttpStatus.OK) {

			TypeReference<Map<String, DG2TitreProDto>> typeRef = new TypeReference<Map<String, DG2TitreProDto>>() {
			};

			Map<String, DG2TitreProDto> results = mapper.readValue(rep.getBody(), typeRef);
			for (String key : results.keySet()) {
				DG2TitreProDto titreProResult = results.get(key);
				DG2TrainingDto trainingObj = titreProResult.getTraining();

				TitreProfessionnel tp = new TitreProfessionnel();
				tp.setTitre(trainingObj.getTitle());
				tp.setSlug(trainingObj.getSlug());

				TitreProfessionnel v = null;

				try {
					v = titreProfessionnelRepository.findBySlug(tp.getSlug());

				} catch (Exception e) {
					e.printStackTrace();
				}
				if (v == null) {
					titreProfessionnelRepository.saveAndFlush(tp);
					nbt++;
				}

			}
		}
		return nbt;
	}
//
	@Override
	public String generatePdf(long id) throws Exception {

		TitreProfessionnelDto t = findById(id);
		
		List<BlocCompetencesDto> blocs = blocCompetenceService.findAllByTitreProId(id);
		List<BlocCompEnrichiDto> data = new ArrayList<BlocCompEnrichiDto>();
		//pour chaque bloc on récup ses competences
		for (BlocCompetencesDto blocCompetencesDto : blocs) {
			BlocCompEnrichiDto bce = new BlocCompEnrichiDto();
			bce.setBloc(blocCompetencesDto);
			List<CompetenceDto> comp = competenceService.findAllByBlocCompetenceId(blocCompetencesDto.getId());
			bce.setComps(comp);
			data.add(bce);
		}
		
		// on définit ici le chemin où il va chercher les fichiers de templates
		freemarkerConfig.setClassForTemplateLoading(this.getClass(), "/templates");

		// charger le template titrepro.ftl et lui envoyer l'objet t
		Template template = freemarkerConfig.getTemplate("titrepro.ftl");

		// Une map pour envoyer plusieurs objets au freemarker template
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("blocCompsEnrichi", data);
		model.put("titrePro",t);
		// on lui demande d'appliquer le template pour l'objet t (titreProfessionnel)
		String htmlContent = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

		// CONVERSION HTML ================> PDF
		String outputPdf = storageFolder + "/titrepro-" + t.getId() + ".pdf";
		PdfTools.generatePdfFromHtml(outputPdf, htmlContent);

		return outputPdf;
	}

	@Override
	public List<TitreProfessionnelDto> findByPage(int page, int max, String search) {
		// on requête la bdd
		System.out.println("search = " + search);
		List<TitreProfessionnel> tps = titreProfessionnelRepository
				.findAllByTitreContainingOrSlugContaining(search, search,
						PageRequest.of(page, max))
				.get().collect(Collectors.toList());

		// on transforme le résultat en liste de DTO
		List<TitreProfessionnelDto> result = new ArrayList<TitreProfessionnelDto>();
		for (TitreProfessionnel t : tps) {
			result.add(DtoTools.convert(t, TitreProfessionnelDto.class));
		}
		return result;
	}
	
	@Override
	public CountDto count(String search) {
		CountDto result = new CountDto();
		result.setNb(titreProfessionnelRepository.countByTitreContainingOrSlugContaining(search,search));
		return result;
	}
}
