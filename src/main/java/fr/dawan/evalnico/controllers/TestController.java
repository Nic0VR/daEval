package fr.dawan.evalnico.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.dawan.evalnico.dto.EtudiantDto;
import fr.dawan.evalnico.services.EtudiantService;

@RestController
@RequestMapping("/api/test")
public class TestController {

	@Autowired
	private EtudiantService etudiantService;
	
	public ResponseEntity<String> insertionJeuDeTest(){
		
		EtudiantDto etu1 = new EtudiantDto();
		etu1.setEmail("etudiant1@dawan.fr");
		etu1.setMotDePasse("password");
		etu1.setNom("VRAD");
		etu1.setPrenom("Nico");
		
		try {
			etudiantService.saveOrUpdate(etu1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		EtudiantDto etu2 = new EtudiantDto();
		etu2.setEmail("etudiant2@dawan.fr");
		etu2.setMotDePasse("password");
		etu2.setNom("VRAD2");
		etu2.setPrenom("Nico2");
		
		try {
			etudiantService.saveOrUpdate(etu2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		
		
		return null;
	}
	
}
