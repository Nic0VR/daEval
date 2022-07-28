package fr.dawan.evalnico.controllers;

import java.util.List;
import java.util.Optional;

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

import fr.dawan.evalnico.dto.CountDto;
import fr.dawan.evalnico.dto.FormationDto;
import fr.dawan.evalnico.services.FormationService;

@RestController
@RequestMapping(value = "/api/formation")
public class FormationController {

	@Autowired
	private FormationService formationService;
	
	@GetMapping(value = "/update-from-dg2", produces = "application/json")
    public CountDto updateFromDG2() throws Exception {
        int nb = formationService.importFromDG2();
        CountDto result = new CountDto();
        result.setNb(nb);
        return result;
    }
	
	@GetMapping(produces="application/json")
	public ResponseEntity<List<FormationDto>> getAll(){
		List<FormationDto> result = formationService.getAll();
		return ResponseEntity.status(HttpStatus.OK).body(result);	
	}

	@PostMapping(consumes="application/json", produces = "application/json")
	public ResponseEntity<FormationDto> save(@RequestBody FormationDto promoDto){

		FormationDto result = null;
		try {
			result = formationService.saveOrUpdate(promoDto);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(result != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(result);

		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	}

	@PutMapping(consumes="application/json", produces = "application/json")
	public ResponseEntity<FormationDto> update(@RequestBody FormationDto promoDto ){
		FormationDto result = null;
		try {
			result = formationService.saveOrUpdate(promoDto);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(result != null) {
			return ResponseEntity.status(HttpStatus.OK).body(result);

		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	}

	@DeleteMapping(value= "/{id}") 
	public ResponseEntity<Long> Delete(@PathVariable(name="id",required=true) long id){
		formationService.deleteById(id);
		return ResponseEntity.status(HttpStatus.OK).body(id);
	}


	@GetMapping(value= {"/page/{page}/{size}", "/page/{page}/{size}/{search}"}, produces = "application/json")
	public  ResponseEntity<List<FormationDto>> getAllByPage(
			@PathVariable("page") int page, 
			@PathVariable("size") int max, 
			@PathVariable(value="search", required = false) Optional<String> search){
		if(search.isPresent())
			return ResponseEntity.status(HttpStatus.OK).body(formationService.getPageByTitre(page-1, max, search.get())) ;
		else
			return ResponseEntity.status(HttpStatus.OK).body(formationService.getPageByTitre(page-1, max, ""));
	}
	
	// GET /count/{search}
	@GetMapping(value= {"/count","/count/{search}"}, produces = "application/json")
	public CountDto countBy(@PathVariable(value = "search",required = false) Optional<String> search) {
		CountDto result = null;
		if(search.isPresent())
			result = formationService.count(search.get());
		else
			result = formationService.count("");

		return result;
	}
	
	@GetMapping(value="/{id}",produces="application/json")
	public ResponseEntity<FormationDto> getById(@PathVariable(value="id")long id){
		return ResponseEntity.status(HttpStatus.OK).body(formationService.getById(id)) ;
	}
}
