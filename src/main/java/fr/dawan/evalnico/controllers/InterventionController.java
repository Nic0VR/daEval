package fr.dawan.evalnico.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.dawan.evalnico.dto.InterventionDto;
import fr.dawan.evalnico.services.InterventionService;

@RestController
@RequestMapping(value="/api/interv")
public class InterventionController {

	@Autowired
	private InterventionService interventionService;
	

	@PostMapping(consumes="application/json", produces = "application/json")
	public ResponseEntity<InterventionDto> save(@RequestBody InterventionDto interventionDto){
		
		InterventionDto result = interventionService.saveOrUpdate(interventionDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(result);
	}
	
	@PutMapping(consumes="application/json", produces = "application/json")
	public ResponseEntity<InterventionDto> update(@RequestBody InterventionDto interventionDto ){
		InterventionDto result = interventionService.saveOrUpdate(interventionDto);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	
	@DeleteMapping(value= "/{id}") 
	public ResponseEntity<Long> Delete(@PathVariable(name="id",required=true) long id){
		interventionService.delete(id);
		return ResponseEntity.status(HttpStatus.OK).body(id);
	}
	
	@GetMapping(produces="application/json")
	public ResponseEntity<List<InterventionDto>> getAll(){
		List<InterventionDto> result = interventionService.getAll();
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
}
