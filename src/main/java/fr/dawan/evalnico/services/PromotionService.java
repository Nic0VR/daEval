package fr.dawan.evalnico.services;

import java.util.List;

import fr.dawan.evalnico.dto.CountDto;
import fr.dawan.evalnico.dto.EtudiantDto;
import fr.dawan.evalnico.dto.PromotionDto;

public interface PromotionService {

	PromotionDto saveOrUpdate(PromotionDto pDto) throws Exception;
	void delete(long id);
	List<PromotionDto> findAll();
	PromotionDto findById(long id);
	List<EtudiantDto> getEtudiantsFromPromo(long promoId);
	List<PromotionDto> findByTitreProId(long titreProId);
	List<PromotionDto> getAll(int i, int max, String string);
	CountDto count(String string);
	List<PromotionDto> getAllPromosContainingEtudiant(long etudiantId);
}
