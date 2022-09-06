package fr.dawan.evalnico.services;

import java.util.List;

import fr.dawan.evalnico.dto.CountDto;
import fr.dawan.evalnico.dto.FormateurDto;
import fr.dawan.evalnico.dto.LoginDto;
import fr.dawan.evalnico.dto.LoginResponseDto;
import fr.dawan.evalnico.dto.UtilisateurDto;
import fr.dawan.evalnico.exceptions.InvalidDataException;
import fr.dawan.evalnico.exceptions.NoDataException;


public interface UtilisateurService {

	UtilisateurDto saveOrUpdate(UtilisateurDto uDto) throws InvalidDataException, Exception;
	
	List<UtilisateurDto> getAll();
	
	void delete(long id) throws IllegalArgumentException, NoDataException;
	
	UtilisateurDto findByEmail(String email) throws NoDataException;

	UtilisateurDto getById(long id) throws IllegalArgumentException, NoDataException;

	LoginResponseDto checkLogin(LoginDto loginDto) throws Exception;

	List<UtilisateurDto> getAll(int page, int max, String search) throws IllegalArgumentException;

	CountDto count(String search);

	List<FormateurDto> getAllFormateurs() throws NoDataException;
}
