package fr.dawan.evalnico.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.dawan.evalnico.dto.LoginDto;
import fr.dawan.evalnico.dto.LoginResponseDto;
import fr.dawan.evalnico.services.UtilisateurService;

@RestController
@RequestMapping( )
public class LoginController {

	@Autowired
	private UtilisateurService userService;
	
	 @PostMapping(value="/login", consumes="application/json", produces="application/json")
	 public LoginResponseDto checkLogin(@RequestBody LoginDto loginDto) throws Exception {
	    	//appel à la méthode du service
	        return userService.checkLogin(loginDto);
	 }
	       
}
