package fr.dawan.evalnico.services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import fr.dawan.evalnico.dto.BlocCompetencesDto;
import fr.dawan.evalnico.dto.CompetenceDto;
import fr.dawan.evalnico.dto.CountDto;
import fr.dawan.evalnico.exceptions.NoDataException;

public interface BlocCompetenceService {

	BlocCompetencesDto saveOrUpdate(BlocCompetencesDto blocDto);
	
	void delete(long id);
	
	List<BlocCompetencesDto> findAllByTitreOrDescription(String search);
	
	BlocCompetencesDto findById(long id) throws NoDataException;

	List<BlocCompetencesDto> findAllByTitreProId(long id) throws NoDataException ;
	
	List<BlocCompetencesDto> findAll();

	void deleteByTitreProId(long id) throws NoDataException, Exception;

	List<BlocCompetencesDto> getPageBySearch(int i, int max, String string);

	CountDto count(String search);
	
	List<BlocCompetencesDto> findPagedByTitreProIdAndByTitreContainingOrDescriptionContaining(long id, String search,int i, int max) throws NoDataException;
}
