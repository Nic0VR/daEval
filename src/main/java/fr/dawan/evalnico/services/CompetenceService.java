package fr.dawan.evalnico.services;

import java.util.List;

import fr.dawan.evalnico.dto.CompetenceDto;
import fr.dawan.evalnico.dto.EpreuveDto;

public interface CompetenceService {

	CompetenceDto saveOrUpdate(CompetenceDto c);
	
	void delete(long id);
	
	List<CompetenceDto> findByTitreOrDescription(String search);
	
	List<CompetenceDto> findAllByBlocCompetenceId(long id);
	
	List<CompetenceDto> findAllByTitreProfessionnelId(long id);
	
	List<CompetenceDto> findAll();
	
	CompetenceDto getById(long id);

}
