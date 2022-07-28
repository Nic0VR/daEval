package fr.dawan.evalnico.services;

import java.util.List;

import fr.dawan.evalnico.dto.VilleDto;

public interface VilleService  {

	int updateFromDg2() throws Exception;
	VilleDto saveOrUpdate(VilleDto vDto);
	void delete(long id);
	List<VilleDto> getAll();
	VilleDto getById(long id);
}