package fr.dawan.evalnico.services;

import java.util.List;

import fr.dawan.evalnico.dto.CountDto;
import fr.dawan.evalnico.dto.LoginDto;
import fr.dawan.evalnico.dto.LoginResponseDto;
import fr.dawan.evalnico.dto.UtilisateurDto;


public interface UtilisateurService {

	UtilisateurDto saveOrUpdate(UtilisateurDto uDto);
	
	List<UtilisateurDto> getAll();
	
	void delete(long id);
	
	UtilisateurDto findByEmail(String email) throws Exception;

	UtilisateurDto getById(long id);

	LoginResponseDto checkLogin(LoginDto loginDto) throws Exception;

	List<UtilisateurDto> getAll(int page, int max, String search);


	CountDto count(String search);
}
