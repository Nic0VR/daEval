package fr.dawan.evalnico.services;

import java.util.List;
import java.util.Optional;

import fr.dawan.evalnico.dto.CountDto;
import fr.dawan.evalnico.dto.EpreuveDto;

public interface EpreuveService {
	
	EpreuveDto saveOrUpdate(EpreuveDto e);
	EpreuveDto getById(long id);
	void delete(long id);
	List<EpreuveDto> findByBlocCompId(long id);
	List<EpreuveDto> findAll();
	List<EpreuveDto> findAllPaged(int page, int max, String search);
	void deleteByBlocCompId(long id) ;
	
	CountDto count(String search);
}
