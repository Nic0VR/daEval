package fr.dawan.evalnico.services;

import java.util.List;

import fr.dawan.evalnico.dto.CountDto;
import fr.dawan.evalnico.dto.FormationDto;

public interface FormationService {
	
	int importFromDG2() throws Exception;
	FormationDto saveOrUpdate(FormationDto f);
	List<FormationDto> getAll();
	FormationDto getById(long id);
	void deleteById(long id);
	FormationDto findBySlug(String slug);
	List<FormationDto> getPageByTitre(int i, int max, String string);
	CountDto count(String string);
}
