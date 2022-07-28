package fr.dawan.evalnico.services;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import fr.dawan.evalnico.dto.BlocCompetencesDto;
import fr.dawan.evalnico.dto.CompetenceDto;
import fr.dawan.evalnico.dto.CountDto;
import fr.dawan.evalnico.dto.EtudiantDto;
import fr.dawan.evalnico.dto.EvalByBlocDto;
import fr.dawan.evalnico.dto.EvaluationDto;
import fr.dawan.evalnico.dto.PromotionDto;
import fr.dawan.evalnico.dto.TitreProfessionnelDto;
import fr.dawan.evalnico.entities.BlocCompetences;
import fr.dawan.evalnico.entities.Etudiant;
import fr.dawan.evalnico.entities.Evaluation;
import fr.dawan.evalnico.entities.Promotion;
import fr.dawan.evalnico.repositories.BlocCompetencesRepository;
import fr.dawan.evalnico.repositories.EtudiantRepository;
import fr.dawan.evalnico.repositories.EvaluationRepository;
import fr.dawan.evalnico.repositories.PromotionRepository;
import fr.dawan.evalnico.tools.DtoTools;
import fr.dawan.evalnico.tools.PdfTools;
import freemarker.template.Configuration;
import freemarker.template.Template;

@Service
@Transactional
public class EvaluationServiceImpl implements EvaluationService {

	@Autowired
	private EvaluationRepository evaluationRepository;
	@Autowired
	private Configuration freemarkerConfig;
	@Autowired
	private TitreProfessionnelService titreProfessionnelService;
	@Autowired
	private EtudiantService etudiantService;
	@Autowired
	private PromotionService promotionService;
	@Autowired
	private BlocCompetenceService blocCompetencesService;
	@Autowired
	private CompetenceService competenceService;
	@Autowired
	private EtudiantRepository etudiantRepository;
	@Autowired
	private PromotionRepository promotionRepository;
	@Autowired
	private BlocCompetencesRepository blocCompetencesRepository;
	
	@Value(value = "${backend.url}")
	private String backEndUrl;
	@Value("${app.storagefolder}")
	private String storageFolder;
	
	@Override
	public EvaluationDto saveOrUpdate(EvaluationDto pDto) {
		
		Evaluation promo = DtoTools.convert(pDto, Evaluation.class);
		promo = evaluationRepository.saveAndFlush(promo);
		return DtoTools.convert(promo, EvaluationDto.class);
	}

	@Override
	public void delete(long id) {
//		Optional<Evaluation> evalToDelete = evaluationRepository.findById(id);
		evaluationRepository.deleteById(id);
	}
	
	@Override
	public void deleteByUserId(long id) {
		
		List<EvaluationDto> evals = getAllEvalByEtudiantId(id);
		evals.forEach( eval->{this.delete(eval.getId());});
		
	}
	
	
	@Override
	public List<EvaluationDto> findAll() {
		List<Evaluation> resultInDb = evaluationRepository.findAll();
		List<EvaluationDto> result = new ArrayList<EvaluationDto>();
		for (Evaluation e : resultInDb) {
			result.add(DtoTools.convert(e, EvaluationDto.class));
		}
		return result;
	}

	@Override
	public CountDto moyenneEtudiantInBlocComp(long etudiantId, long blocCompId) {
		CountDto result = new CountDto();
		try {
			result.setNb(evaluationRepository.getAvgByEtudiantIdAndBlocCompId(etudiantId, blocCompId));
		} catch (Exception e) {
			// TODO: handle exception
		}
	
		return result;
	}

	@Override
	public CountDto moyenneGeneraleEtudiant(long etudiantId, long promotionId) {
		CountDto result = new CountDto();
		try {
			result.setNb(evaluationRepository.getAvgByEtudiantIdAndPromotionId(etudiantId, promotionId));

		} catch (Exception e) {
			// TODO: handle exception
		}
		return result;
	}

	@Override
	public CountDto moyenneGeneralePromotion(long promotionId) {
		CountDto result = new CountDto();
		try {
			result.setNb(evaluationRepository.getAvgByPromotionId(promotionId));

		} catch (Exception e) {
			// TODO: handle exception
		}
		return result;
	}

	@Override
	public CountDto moyennePromotionBlocCompetence(long promotionId, long blocCompId) {
		CountDto result = new CountDto();
		try {
			result.setNb(evaluationRepository.getAvgOfPromoByBlocCompId(promotionId, blocCompId));

		} catch (Exception e) {
			// TODO: handle exception
		}
		return result;
	}

	@Override
	public List<EvaluationDto> getAllEvalByEtudiantId(long id) {
		List<Evaluation> resultInDb = evaluationRepository.getByEtudiantId(id);
		List<EvaluationDto> result = new ArrayList<EvaluationDto>();
		for (Evaluation evaluation : resultInDb) {
			result.add(DtoTools.convert(evaluation, EvaluationDto.class));
		}
		return result;
	}

	@Override
	public EvaluationDto getById(long id) {
		Optional<Evaluation> result = evaluationRepository.findById(id);
		if(result.isPresent()) {
			return DtoTools.convert(result.get(), EvaluationDto.class);
		}
		return null;
	}

	@Override
	public List<EvaluationDto> getAllEvalByEtudiantIdAndBlocId(long etudiantId, long blocCompId) {
		List<Evaluation> resultInDb = evaluationRepository.getByEtudiantIdAndBlocCompId(etudiantId,blocCompId);
		
		List<EvaluationDto> result = new ArrayList<EvaluationDto>();
		
		for (Evaluation evaluation : resultInDb) {
			result.add(DtoTools.convert(evaluation, EvaluationDto.class));
		}
		return result;
	}

	@Override
	public String GeneratePDFBulletin(long titreProId, long etudiantId, long promotionId) throws Exception {
		TitreProfessionnelDto t = titreProfessionnelService.findById(titreProId);
		EtudiantDto e = etudiantService.getById(etudiantId);
		PromotionDto p = promotionService.findById(promotionId);
		List<BlocCompetencesDto> blocs = blocCompetencesService.findAllByTitreProId(t.getId());
		List<CompetenceDto> competences = competenceService.findAllByTitreProfessionnelId(titreProId);
		
		
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("titrePro", t);
		model.put("etudiant",e);
		model.put("promo", p);
		model.put("competences", competences);
		model.put("blocs", blocs);
		// besoin de passer les moyennes générale de l'etudiant et de la promo pour chaque bloc
		Map<String,CountDto> moyennesEtudiant = new HashMap<String,CountDto>();
		Map<String,CountDto> moyennesPromotion = new HashMap<String,CountDto>();

		for (BlocCompetencesDto blocCompetencesDto : blocs) {
			moyennesEtudiant.put(String.valueOf(blocCompetencesDto.getId()) ,  moyenneEtudiantInBlocComp(etudiantId,blocCompetencesDto.getId()));
			moyennesPromotion.put(String.valueOf(blocCompetencesDto.getId()), moyennePromotionBlocCompetence(p.getId(),blocCompetencesDto.getId()));
		}
		model.put("moyennesEtudiant", moyennesEtudiant);
		model.put("moyennesPromotion", moyennesPromotion);
		// on fait passer la moyenne générale de l'etudiant et de la promo
		Map<String,CountDto> moyennesGenerale = new HashMap<String,CountDto>();
		moyennesGenerale.put("etudiant",moyenneGeneraleEtudiant(e.getId(),p.getId()) );
		moyennesGenerale.put("promotion", moyenneGeneralePromotion(p.getId()));
		
		model.put("moyennesGenerale", moyennesGenerale);
		//on définit ici le chemin où il va chercher les fichiers de templates
		freemarkerConfig.setClassForTemplateLoading(this.getClass(), "/templates");
		
		//charger le template titrepro.ftl 
		Template template = freemarkerConfig.getTemplate("titrepro.ftl");
		
		//on lui demande d'appliquer le template pour l'objet model
		String result = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
					
		String outputPdf = storageFolder + "/titrepro-" + t.getId() + ".pdf";
		PdfTools.generatePdfFromHtml(outputPdf, result);
		return result;
	}
	


	@Override
	public String generateBulletinPdfByStudentAndPromo(long etudiantId, long promotionId) throws Exception {
		Optional<Etudiant> etuOpt = etudiantRepository.findById(etudiantId);
		if (etuOpt.isPresent()) {
			freemarkerConfig.setClassForTemplateLoading(this.getClass(), "/templates");
			Template template = freemarkerConfig.getTemplate("bulletin-eval.ftl");

			Map<String, Object> model = new HashMap<String, Object>();
			model.put("backEndUrl", backEndUrl);

			Etudiant etu = etuOpt.get();
			model.put("etudiant", etu);

			Optional<Promotion> promoOpt = promotionRepository.findById(promotionId);
			if (promoOpt.isPresent()) {
				Promotion promo = promoOpt.get();
				model.put("promo", promo);
				model.put("promoAnnee", promo.getDateDebut().getYear());
				model.put("titrePro", promo.getTitreProfessionnel().getTitre());

				List<BlocCompetences> blocs = blocCompetencesRepository
						.findAllByTitreProId(promo.getTitreProfessionnel().getId());

				List<EvalByBlocDto> evalList = new ArrayList<EvalByBlocDto>();
				double s = 0;
				for (BlocCompetences bc : blocs) {
					EvalByBlocDto evalByBlocDto = new EvalByBlocDto();
					evalByBlocDto.setBlocCompetences(bc);
					try {
						System.out.println("etuId = "+etudiantId);
						System.out.println("blocId = "+ bc.getId());
//						double moyB = getAvgByEtudiantIdAndBlocCompId(etudiantId, bc.getId()).getResult();
						double moyB  =moyenneEtudiantInBlocComp(etudiantId, bc.getId()).getNb();
						evalByBlocDto.setMoyenne(moyB);
						s += moyB;
					} catch (Exception e) {
						//TODO 0 par défaut ou afficher un message
						e.printStackTrace();
					}
					
					try {
						evalByBlocDto.setMoyennePromo(moyennePromotionBlocCompetence(promotionId, bc.getId()).getNb());	
					} catch (Exception e) {
						// 0 par défaut //TODO ajouter un message pour dire note manquante
						e.printStackTrace();
					}
					evalList.add(evalByBlocDto);
				}
				model.put("evalList", evalList);
				model.put("moyEtudiant", (s/blocs.size()));
				model.put("moyPromo",moyenneGeneralePromotion(promotionId).getNb());
			}
			String htmlContent = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
			String outputPdf = storageFolder + "/bulletin-" + etudiantId + "-promo-" + promotionId + ".pdf";
			PdfTools.generatePdfFromHtml(outputPdf, htmlContent);

			return outputPdf;
		}

		return null;
	}
	@Override
	public String generateBulletinPdfForPromo(long promotionId) throws Exception{
		// Récupérer les étudiants de la promo
		List<Etudiant> etudiants = etudiantRepository.findByPromotionId(promotionId);

		String zipFile = storageFolder + "/bulletins-promo-" + promotionId + ".zip";
		try {
			File f = new File(zipFile);
			if (f.exists())
				f.delete();
			
			Path zipFilePath = Files.createFile(Paths.get(zipFile));

			// ZipOutputStream <= 1 à n ZipEntry
			try (ZipOutputStream zos = new ZipOutputStream(Files.newOutputStream(zipFilePath))) {
				for (Etudiant etu : etudiants) {
					String outputPdf = generateBulletinPdfByStudentAndPromo(etu.getId(), promotionId);
					ZipEntry zipEntry = new ZipEntry("bulletin-etu-"+etu.getId()+".pdf");
					zos.putNextEntry(zipEntry);
					zos.write(Files.readAllBytes(Paths.get(outputPdf)));
					zos.flush();
					zos.closeEntry();

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return zipFile;
	}

	@Override
	public List<EvaluationDto> getAllEvalByEpreuveId(long id) {
		List<Evaluation> resultInDb = evaluationRepository.findAllByEpreuveId(id);
		List<EvaluationDto> result = new ArrayList<EvaluationDto>();
		
		for (Evaluation evaluation : resultInDb) {
			result.add(DtoTools.convert(evaluation, EvaluationDto.class));
		}
		return result;
	}


	
	
}
