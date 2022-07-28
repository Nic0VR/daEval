package fr.dawan.evalnico.services;

import java.util.List;

import fr.dawan.evalnico.dto.CountDto;
import fr.dawan.evalnico.dto.EtudiantDto;
import fr.dawan.evalnico.dto.LoginDto;
import fr.dawan.evalnico.dto.LoginResponseDto;

public interface EtudiantService {
	
	EtudiantDto saveOrUpdate(EtudiantDto eDto) throws Exception;
	
	List<EtudiantDto> getAll();
	
	void delete(long id);
	
	EtudiantDto findByEmail(String email) throws Exception;

	EtudiantDto getById(long id);
	
//	LoginResponseDto checkLogin(LoginDto loginDto) throws Exception;

	List<EtudiantDto> getAll(int page, int max, String search);

	CountDto count(String search);

}
