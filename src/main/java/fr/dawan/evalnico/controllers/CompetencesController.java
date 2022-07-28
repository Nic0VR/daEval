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

import fr.dawan.evalnico.dto.BlocCompetencesDto;
import fr.dawan.evalnico.dto.CompetenceDto;
import fr.dawan.evalnico.services.CompetenceService;

@RestController
@RequestMapping("/api/competences")
public class CompetencesController {

	@Autowired
	private CompetenceService competenceService;
	

	@PostMapping(consumes="application/json", produces = "application/json")
	public ResponseEntity<CompetenceDto> save(@RequestBody CompetenceDto compDto){
		
		CompetenceDto result = competenceService.saveOrUpdate(compDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(result);
	}
	
	@PutMapping(consumes="application/json", produces = "application/json")
	public ResponseEntity<CompetenceDto> update(@RequestBody CompetenceDto compDto ){
		CompetenceDto result = competenceService.saveOrUpdate(compDto);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	
	@DeleteMapping(value= "/{id}") 
	public ResponseEntity<Long> Delete(@PathVariable(name="id",required=true) long id){
		competenceService.delete(id);
		return ResponseEntity.status(HttpStatus.OK).body(id);
	}
	
	@GetMapping(value="/search={search}",produces = "application/json")
	public ResponseEntity<List<CompetenceDto>> findByTitreOrDescription(@PathVariable(name="search",required=true) String search){
		List<CompetenceDto> result = competenceService.findByTitreOrDescription(search);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	
	@GetMapping(value="/blocCompId={id}",produces = "application/json")
	public ResponseEntity<List<CompetenceDto>> findByBlocCompId(@PathVariable(name="id",required=true) long id){
		List<CompetenceDto> result = competenceService.findAllByBlocCompetenceId(id);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	
	@GetMapping(value="/titreProId={id}",produces = "application/json")
	public ResponseEntity<List<CompetenceDto>> findByTitreProId(@PathVariable(name="id",required=true) long id){
		List<CompetenceDto> result = competenceService.findAllByTitreProfessionnelId(id);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	
	@GetMapping(produces="application/json")
	public ResponseEntity<List<CompetenceDto>> findAll(){
		List<CompetenceDto> result = competenceService.findAll();
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	
	
}
