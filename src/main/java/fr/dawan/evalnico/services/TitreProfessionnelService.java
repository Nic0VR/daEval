package fr.dawan.evalnico.services;

import java.util.List;

import fr.dawan.evalnico.dto.CountDto;
import fr.dawan.evalnico.dto.TitreProfessionnelDto;
import fr.dawan.evalnico.exceptions.NoDataException;

public interface TitreProfessionnelService {

	TitreProfessionnelDto saveOrUpdate(TitreProfessionnelDto tDto) throws Exception;
	void delete(long id) throws Exception;
	List<TitreProfessionnelDto> findByTitre(String titre);
	List<TitreProfessionnelDto> findAll();
	TitreProfessionnelDto findById(long id);
	String generatePdf(long id) throws Exception;
	int importFromDG2() throws Exception;
	List<TitreProfessionnelDto> findByPage(int page,int max, String search);
	CountDto count(String search);
}
