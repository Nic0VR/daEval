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
import fr.dawan.evalnico.dto.EtudiantDto;
import fr.dawan.evalnico.dto.PromotionDto;
import fr.dawan.evalnico.services.PromotionService;

@RestController
@RequestMapping("/api/promotion")
public class PromotionController {

	@Autowired
	private PromotionService promotionService;


	@PostMapping(consumes="application/json", produces = "application/json")
	public ResponseEntity<PromotionDto> save(@RequestBody PromotionDto promoDto){

		PromotionDto result = null;
		try {
			result = promotionService.saveOrUpdate(promoDto);
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
	public ResponseEntity<PromotionDto> update(@RequestBody PromotionDto promoDto ){
		PromotionDto result = null;
		try {
			result = promotionService.saveOrUpdate(promoDto);
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
		promotionService.delete(id);
		return ResponseEntity.status(HttpStatus.OK).body(id);
	}

	@GetMapping(produces="application/json")
	public ResponseEntity<List<PromotionDto>> getAll(){
		List<PromotionDto> result= promotionService.findAll();
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

	@GetMapping(produces="application/json", value="/id={id}/etudiants")
	public ResponseEntity<List<EtudiantDto>> getAllEtudiantsFromPromo(@PathVariable(name="id") long id){
		List<EtudiantDto> result= promotionService.getEtudiantsFromPromo(id);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	
	@GetMapping(value= {"/page/{page}/{size}", "/page/{page}/{size}/{search}"}, produces = "application/json")
	public  ResponseEntity<List<PromotionDto>> getAllByPage(
			@PathVariable("page") int page, 
			@PathVariable("size") int max, 
			@PathVariable(value="search", required = false) Optional<String> search){
		if(search.isPresent())
			return ResponseEntity.status(HttpStatus.OK).body(promotionService.getAll(page-1, max, search.get())) ;
		else
			return ResponseEntity.status(HttpStatus.OK).body(promotionService.getAll(page-1, max, ""));
	}
	
	// GET /count/{search}
	@GetMapping(value= {"/count","/count/{search}"}, produces = "application/json")
	public CountDto countBy(@PathVariable(value = "search",required = false) Optional<String> search) {
		CountDto result = null;
		if(search.isPresent())
			result = promotionService.count(search.get());
		else
			result = promotionService.count("");

		return result;
	}
	
	@GetMapping(value="/{id}",produces="application/json")
	public ResponseEntity<PromotionDto> getById(@PathVariable(value="id")long id){
		return ResponseEntity.status(HttpStatus.OK).body(promotionService.findById(id)) ;
	}
	
	@GetMapping(value="/ctn/{id}",produces="application/json")
	public ResponseEntity<List<PromotionDto>> getPromoContainingEtudiant(@PathVariable(value="id")long id){
		return ResponseEntity.status(HttpStatus.OK).body(promotionService.getAllPromosContainingEtudiant(id));
	}
}
