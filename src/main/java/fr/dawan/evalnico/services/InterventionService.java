package fr.dawan.evalnico.services;

import java.util.List;

import fr.dawan.evalnico.dto.InterventionDto;

public interface InterventionService {
	List<InterventionDto> getAll();
	InterventionDto getById(long id);
	List<InterventionDto> getAllByPromotionId(long promotionId);
	List<InterventionDto> getAllByEtudiantId(long etudiantId);
	void delete(long id);
	InterventionDto saveOrUpdate(InterventionDto idto);
	
	
}
	