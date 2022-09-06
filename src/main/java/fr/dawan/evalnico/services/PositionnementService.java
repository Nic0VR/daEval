package fr.dawan.evalnico.services;

import java.util.List;

import fr.dawan.evalnico.dto.CountDto;
import fr.dawan.evalnico.dto.PositionnementDto;
import fr.dawan.evalnico.exceptions.IllegalCreateException;

public interface PositionnementService {
	List<PositionnementDto> getAll();

	List<PositionnementDto> getAll(int start, int max) throws Exception;

	PositionnementDto getById(long id);

	PositionnementDto saveOrUpdate(PositionnementDto pDto) throws IllegalCreateException;

	void delete(long id);

	List<PositionnementDto> getAllByEtudiantId(long etudiantId);

	List<PositionnementDto> getAllByInterventionId(long interventionId);
	
	PositionnementDto getByEtudiantIdAndInterventionId(long etudiantId, long interventionId);

	String generatePdfGrillePositionnementEtudiant(long etudiantId) throws Exception;

	String generatePdfGrillePositionnementPromotion(long promotionId) throws Exception;


	List<PositionnementDto> getAllByEtudiantAndPromo(long etudiantId, long promotionId) throws Exception;


	void deleteByEtudiantId(long id);

	CountDto count();
}
