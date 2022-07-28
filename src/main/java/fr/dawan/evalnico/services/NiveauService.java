package fr.dawan.evalnico.services;

import java.util.List;

import fr.dawan.evalnico.dto.NiveauDto;

public interface NiveauService {

	NiveauDto saveOrUpdate(NiveauDto niveau);
	void delete(long id);
	List<NiveauDto> getAll();
	NiveauDto findById(long id);
	
}
