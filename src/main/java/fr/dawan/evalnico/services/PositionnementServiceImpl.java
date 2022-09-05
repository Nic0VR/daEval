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
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import fr.dawan.evalnico.dto.CountDto;
import fr.dawan.evalnico.dto.EtudiantDto;
import fr.dawan.evalnico.dto.FormationDto;
import fr.dawan.evalnico.dto.InterventionDto;
import fr.dawan.evalnico.dto.ModelGrilleEtudiant;
import fr.dawan.evalnico.dto.PositionnementDto;
import fr.dawan.evalnico.dto.UtilisateurDto;
import fr.dawan.evalnico.entities.Intervention;
import fr.dawan.evalnico.entities.Positionnement;
import fr.dawan.evalnico.entities.Promotion;
import fr.dawan.evalnico.repositories.PositionnementRepository;
import fr.dawan.evalnico.repositories.PromotionRepository;
import fr.dawan.evalnico.tools.DtoTools;
import fr.dawan.evalnico.tools.PdfTools;
import freemarker.template.Configuration;
import freemarker.template.Template;

@Service
public class PositionnementServiceImpl implements PositionnementService {

	@Autowired
	private Configuration freemarkerConfig;
	@Value(value = "${backend.url}")
	private String backEndUrl;
	@Value("${app.storagefolder}")
	private String storageFolder;
	@Autowired
	private EtudiantService etudiantService;
	@Autowired
	private InterventionService interventionService;
	@Autowired
	private PositionnementRepository positionnementRepository;
	@Autowired
	private PromotionService promotionService;
	@Autowired
	private PromotionRepository promotionRepository;
	@Autowired
	private FormationService formationService;
	@Autowired
	private UtilisateurService utilisateurService;
	
	@Override
	public List<PositionnementDto> getAll() {
		List<Positionnement> resultInDb = positionnementRepository.findAll();
		List<PositionnementDto> result = new ArrayList<PositionnementDto>();
		for (Positionnement positionnement : resultInDb) {
			result.add(DtoTools.convert(positionnement, PositionnementDto.class));
		}
		return result;
	}


	@Override
	public PositionnementDto getById(long id) {
		Optional<Positionnement> resultInDb = positionnementRepository.findById(id);
		if (resultInDb.isPresent()) {
			return DtoTools.convert(resultInDb, PositionnementDto.class);
		}
		return null;
	}

	@Override
	public PositionnementDto saveOrUpdate(PositionnementDto uDto) throws Exception {
		Positionnement u = DtoTools.convert(uDto, Positionnement.class);
		if (u.getId() == 0) { // insertion = on vérifie qu'il n'est pas déjà noté
			Positionnement p = positionnementRepository.findByEtudiantIdAndInterventionId(uDto.getEtudiantId(),
					uDto.getInterventionId());
			if (p != null)
				throw new Exception("An other evaluation exist for this student/intervention, update the first one !");
		}
		u = positionnementRepository.saveAndFlush(u);
		return DtoTools.convert(u, PositionnementDto.class);
	}

	@Override
	public CountDto count() {
		CountDto result = new CountDto();
		result.setNb(positionnementRepository.count());
		return result;
	}

	@Override
	public void delete(long id) {
		positionnementRepository.deleteById(id);

	}

	@Override
	public void deleteByEtudiantId(long id) {
		List<PositionnementDto> positionnements = this.getAllByEtudiantId(id);
		positionnements.forEach( p -> delete(p.getId()));
	}
	
	@Override
	public List<PositionnementDto> getAllByEtudiantAndPromo(long etudiantId, long promotionId) throws Exception {
		List<Positionnement> positionnements = positionnementRepository.getAllByEtudiantAndPromo(etudiantId,
				promotionId);

		// on transforme le résultat en liste de DTO
		List<PositionnementDto> result = new ArrayList<PositionnementDto>();
		for (Positionnement u : positionnements) {
			result.add(DtoTools.convert(u, PositionnementDto.class));
		}
		return result;
	}

	@Override
	public List<PositionnementDto> getAllByEtudiantId(long etudiantId) {
		List<Positionnement> resultInDb = positionnementRepository.findAllByEtudiantId(etudiantId);
		List<PositionnementDto> result = new ArrayList<PositionnementDto>();
		for (Positionnement positionnement : resultInDb) {
			result.add(DtoTools.convert(positionnement, PositionnementDto.class));
		}
		return result;
	}

	@Override
	public List<PositionnementDto> getAllByInterventionId(long interventionId) {
		List<Positionnement> resultInDb = positionnementRepository.findAllByInterventionId(interventionId);
		List<PositionnementDto> result = new ArrayList<PositionnementDto>();
		for (Positionnement positionnement : resultInDb) {
			result.add(DtoTools.convert(positionnement, PositionnementDto.class));
		}
		return result;
	}

	@Override
	public String generatePdfGrillePositionnementEtudiant(long etudiantId) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();
		EtudiantDto etudiant = etudiantService.getById(etudiantId);
		model.put("etudiant", etudiant);
//		Map<InterventionDto, PositionnementDto> positionnements = new HashMap<InterventionDto, PositionnementDto>();
//		List<InterventionDto> interventions = interventionService.getAllByEtudiantId(etudiantId);
////
//		for (InterventionDto interventionDto : interventions) {
//			PositionnementDto positionnementsOfIntervention = this.getByEtudiantIdAndInterventionId(etudiantId,
//					interventionDto.getId());
//			positionnements.put(interventionDto, positionnementsOfIntervention);
//		}
//		
		// Récupération des positionnements de l'etudiant
		List<PositionnementDto> positionnementsOfStudent = this.getAllByEtudiantId(etudiantId);
		List<ModelGrilleEtudiant> data = new ArrayList<ModelGrilleEtudiant>();
		
		for (PositionnementDto positionnementDto : positionnementsOfStudent) {
			// récupération de l'intervention concernant le positionnement
			InterventionDto inter = interventionService.getById( positionnementDto.getInterventionId());
			FormationDto formation = formationService.getById(inter.getFormationId());
			UtilisateurDto formateur = utilisateurService.getById(inter.getFormateurId());
			String nomFormateur = formateur.getNom() + " " + formateur.getPrenom();
			
			ModelGrilleEtudiant mge = new ModelGrilleEtudiant();
			mge.setFormation(formation);
			mge.setIntervention(inter);
			mge.setPositionnement(positionnementDto);
			mge.setNomCompletFormateur(nomFormateur);
			
			data.add(mge);
		}
		
		model.put("data", data);
		
		
//		model.put("positionnements", positionnements);

		
		// on définit ici le chemin où il va chercher les fichiers de templates
		freemarkerConfig.setClassForTemplateLoading(this.getClass(), "/templates");
		// charger le template titrepro.ftl
		Template template = freemarkerConfig.getTemplate("grillepositionnement.ftl");

		// on lui demande d'appliquer le template pour l'objet model
		String htmlContent = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

		String outputPdf = storageFolder + "/grilleposition-" + etudiant.getNom()+etudiant.getPrenom() + ".pdf";
		PdfTools.generatePdfFromHtml(outputPdf, htmlContent);
		return outputPdf;
	}

	@Override
	public PositionnementDto getByEtudiantIdAndInterventionId(long etudiantId, long interventionId) {
		Positionnement result = positionnementRepository.findByEtudiantIdAndInterventionId(etudiantId, interventionId);
		if (result != null) {
			return DtoTools.convert(result, PositionnementDto.class);
		}
		return null;
	}

	@Override
	public String generatePdfGrillePositionnementPromotion(long promotionId) throws Exception {

		//

		Optional<Promotion> promotionOpt = promotionRepository.findById(promotionId);
		Promotion promo = null;
		if (promotionOpt.isPresent()) {
			promo = promotionOpt.get();
			// on définit ici le chemin où il va chercher les fichiers de templates
			freemarkerConfig.setClassForTemplateLoading(this.getClass(), "/templates");
			// charger le template titrepro.ftl
			Template template = freemarkerConfig.getTemplate("grille-promo.ftl");
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("promotion", promo);
			model.put("backEndUrl", backEndUrl);
			List<Positionnement> positionnementsByPromo = positionnementRepository.getAllByPromoId(promotionId);
			Map<Intervention, List<Positionnement>> posiByPromoMap = positionnementsByPromo.stream()
					.collect(Collectors.groupingBy(Positionnement::getIntervention));
			model.put("posiByPromoEntries", posiByPromoMap.entrySet());

//			for (Intervention interv : posiByPromoMap.keySet()) {
//				System.out.println("formation : " + interv.getFormation().getTitre());
//				for (Positionnement pos : posiByPromoMap.get(interv)) {
//					System.out.println(pos.getEtudiant().getNom() + " - NivDeb : " + pos.getNiveauDebut().getValeur()
//							+ " - NivFin : " + pos.getNiveauFin().getValeur());
//				}
//			}

			// on lui demande d'appliquer le template pour l'objet t (titreProfessionnel)
			String htmlContent = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

			// CONVERSION HTML ================> PDF
			String outputPdf = storageFolder + "/grille-promo-" + promo.getId() + ".pdf";
			PdfTools.generatePdfFromHtml(outputPdf, htmlContent);

			return outputPdf;
		}
		return null;
	}

	@Override
	public List<PositionnementDto> getAll(int page, int max) throws Exception {
		// on requête la bdd
		List<Positionnement> titres = positionnementRepository.findAll(PageRequest.of(page, max)).get()
				.collect(Collectors.toList());

		// on transforme le résultat en liste de DTO
		List<PositionnementDto> result = new ArrayList<PositionnementDto>();
		for (Positionnement u : titres) {
			result.add(DtoTools.convert(u, PositionnementDto.class));
		}
		return result;
	}




}
