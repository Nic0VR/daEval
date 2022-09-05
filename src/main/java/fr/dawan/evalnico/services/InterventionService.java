package fr.dawan.evalnico.services;

import java.util.List;

import fr.dawan.evalnico.dto.CountDto;
import fr.dawan.evalnico.dto.InterventionDto;
import fr.dawan.evalnico.dto.PositionnementDto;

public interface InterventionService {
	List<InterventionDto> getAll();
	InterventionDto getById(long id);
	List<InterventionDto> getAllByPromotionId(long promotionId);
	List<InterventionDto> getAllByEtudiantId(long etudiantId);
	void delete(long id);
	InterventionDto saveOrUpdate(InterventionDto idto);
	List<InterventionDto> getAll(int page, int max, String search) throws Exception;
	CountDto count();
	List<InterventionDto> getAllByFormateurId(long id);
	
}
	