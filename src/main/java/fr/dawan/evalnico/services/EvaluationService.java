package fr.dawan.evalnico.services;

import java.io.IOException;
import java.util.List;

import fr.dawan.evalnico.dto.CountDto;
import fr.dawan.evalnico.dto.EvaluationDto;
import freemarker.core.ParseException;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;

public interface EvaluationService {


	EvaluationDto saveOrUpdate(EvaluationDto eDto);
	
	void delete(long id);
	
	List<EvaluationDto> findAll();
	
	List<EvaluationDto> getAllEvalByEtudiantId(long id);
	
	CountDto moyenneEtudiantInBlocComp(long etudiantId, long blocCompId);
	
	CountDto moyenneGeneraleEtudiant(long etudiantId, long promotionId);
	
	CountDto moyenneGeneralePromotion(long promotionId);
	
	CountDto moyennePromotionBlocCompetence(long promotionId, long blocCompId);
	
	EvaluationDto getById(long id);

	List<EvaluationDto> getAllEvalByEpreuveId(long id);
	
	List<EvaluationDto> getAllEvalByEtudiantIdAndBlocId(long etudiantId, long blocCompId);
	
	String GeneratePDFBulletin(long titreProId, long etudiantId, long promotionId) throws Exception;

	String generateBulletinPdfByStudentAndPromo(long etudiantId, long promotionId) throws Exception;

	String generateBulletinPdfForPromo(long promotionId) throws Exception;

	void deleteByUserId(long id);
}
