package fr.dawan.evalnico.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.dawan.evalnico.dto.CountDto;
import fr.dawan.evalnico.dto.TitreProfessionnelDto;
import fr.dawan.evalnico.dto.VilleDto;
import fr.dawan.evalnico.services.VilleService;

@RestController
@RequestMapping("/api/ville")
public class VilleController {

	@Autowired
	private VilleService villeService;
	
	@GetMapping(value="/update-from-dg2", produces = "application/json")
	public CountDto updateFromDG2() throws Exception {
		 int nb = villeService.updateFromDg2();
		 CountDto result = new CountDto();
		 result.setNb(nb);
		 return result;
	}

	@GetMapping(produces="application/json")
	public ResponseEntity<List<VilleDto>> getAll(){
		List<VilleDto> result= villeService.getAll();
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<VilleDto> getById(@PathVariable(name="id",required=true) long id){
		VilleDto vInDb = villeService.getById(id);
		if(vInDb!=null) {
			return ResponseEntity.status(HttpStatus.OK).body(vInDb);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}
}
