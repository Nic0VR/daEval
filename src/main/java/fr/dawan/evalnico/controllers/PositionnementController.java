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
import fr.dawan.evalnico.dto.PositionnementDto;
import fr.dawan.evalnico.services.PositionnementService;

@RestController
@RequestMapping("/api/positionnement")
public class PositionnementController {

	
	@Autowired
	private PositionnementService positionnementService;
	
	@GetMapping(produces="application/json")
	public ResponseEntity<List<PositionnementDto>> getAll(){
		List<PositionnementDto> result = positionnementService.getAll();
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	
	
	@GetMapping(value="/{etudiantId}/grille",produces="text/html")
	public ResponseEntity<String> generatePdfGrillePosEtudiant(@PathVariable("etudiantId")long etudiantId){
		String html="";
		try {
			html = positionnementService.generatePdfGrillePositionnementEtudiant(etudiantId);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		
			return ResponseEntity.status(HttpStatus.OK).body(html);
		
	}
	@GetMapping(value="/promo/{promotionId}/grille",produces="text/html")
	public ResponseEntity<String> generatePdfGrillePosPromo(@PathVariable("promotionId")long promotionId){
		String html="";
		try {
			html = positionnementService.generatePdfGrillePositionnementPromotion(promotionId);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		
			return ResponseEntity.status(HttpStatus.OK).body(html);
		
	}
	@PostMapping(consumes="application/json",produces="application/json")
	public ResponseEntity<PositionnementDto> save(@RequestBody PositionnementDto p){
		PositionnementDto result=null;
		try {
			result = positionnementService.saveOrUpdate(p);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(result);
	}
	
	@PutMapping(consumes="application/json",produces="application/json")
	public ResponseEntity<PositionnementDto> update(@RequestBody PositionnementDto p){
		PositionnementDto result=null;
		try {
			result = positionnementService.saveOrUpdate(p);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(result);
	}
	
	@DeleteMapping(value="/{id}")
	public ResponseEntity<Long> delete(@PathVariable(name="id",required=true) long id){
		positionnementService.delete(id);
		return ResponseEntity.status(HttpStatus.OK).body(id);
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<PositionnementDto> getById(@PathVariable(name="id",required=true)long id){
		PositionnementDto result = positionnementService.getById(id);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	
	@GetMapping(value = { "/{page}/{size}", "/{page}/{size}/{search}" }, produces = "application/json")
	public List<PositionnementDto> getAllByPage(@PathVariable("page") int page, @PathVariable("size") int max,
			@PathVariable(value = "search", required = false) Optional<String> search) throws Exception {
		if (search.isPresent())
			return positionnementService.getAll(page - 1, max);
		else
			return positionnementService.getAll(page - 1, max);
	}
	
	
	// GET /count/{search}
	@GetMapping(value= {"/count","/count/{search}"}, produces = "application/json")
	public CountDto countBy(@PathVariable(value = "search",required = false) Optional<String> search) {
		CountDto result = null;
		if(search.isPresent())
			result = positionnementService.count();
		else
			result = positionnementService.count();

		return result;
	}
	
	@GetMapping(value="/ByInterv={id}",produces="application/json")
	public List<PositionnementDto> getAllByInterventionId(@PathVariable("id") long id){
		
		
		List<PositionnementDto> result = positionnementService.getAllByInterventionId(id);
		
		return result;
		
	}
}
