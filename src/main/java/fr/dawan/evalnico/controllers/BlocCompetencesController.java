package fr.dawan.evalnico.controllers;

import java.security.Provider.Service;
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

import fr.dawan.evalnico.dto.BlocCompetencesDto;
import fr.dawan.evalnico.dto.CountDto;
import fr.dawan.evalnico.services.BlocCompetenceService;

@RestController
@RequestMapping("/api/blocComp")
public class BlocCompetencesController {

	@Autowired
	private BlocCompetenceService blocCompetenceService;

	@PostMapping(consumes = "application/json", produces = "application/json")
	public ResponseEntity<BlocCompetencesDto> save(@RequestBody BlocCompetencesDto blocCompDto) {

		BlocCompetencesDto result = blocCompetenceService.saveOrUpdate(blocCompDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(result);
	}

	@PutMapping(consumes = "application/json", produces = "application/json")
	public ResponseEntity<BlocCompetencesDto> update(@RequestBody BlocCompetencesDto blocCompDto) {
		BlocCompetencesDto result = blocCompetenceService.saveOrUpdate(blocCompDto);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Long> Delete(@PathVariable(name = "id", required = true) long id) {
		blocCompetenceService.delete(id);
		return ResponseEntity.status(HttpStatus.OK).body(id);
	}

	@GetMapping(value = "/search={search}", produces = "application/json")
	public ResponseEntity<List<BlocCompetencesDto>> findByTitreOrDescription(
			@PathVariable(name = "search", required = true) String search) {
		List<BlocCompetencesDto> result = blocCompetenceService.findAllByTitreOrDescription(search);

		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

	@GetMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<BlocCompetencesDto> findById(@PathVariable(name = "id", required = true) long id) {
		BlocCompetencesDto result = blocCompetenceService.findById(id);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

	@GetMapping(value = "/tpId={id}", produces = "application/json")
	public ResponseEntity<List<BlocCompetencesDto>> findByTitreProId(
			@PathVariable(name = "id", required = true) long id) {
		List<BlocCompetencesDto> result = blocCompetenceService.findAllByTitreProId(id);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

	@GetMapping(produces = "application/json")
	public ResponseEntity<List<BlocCompetencesDto>> findAll() {
		List<BlocCompetencesDto> result = blocCompetenceService.findAll();
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

	@GetMapping(value = { "/page/{page}/{size}", "/page/{page}/{size}/{search}" }, produces = "application/json")
	public ResponseEntity<List<BlocCompetencesDto>> getPageBySearch(@PathVariable("page") int page,
			@PathVariable("size") int max, @PathVariable(value = "search", required = false) Optional<String> search) {
		List<BlocCompetencesDto> result;
		if (search.isPresent()) {
			result = blocCompetenceService.getPageBySearch(page - 1, max, search.get());
		} else {
			result = blocCompetenceService.getPageBySearch(page - 1, max, "");
		}
		return ResponseEntity.status(HttpStatus.OK).body(result);

	}
	
	
	@GetMapping(value = { "/page/tpId={id}/{page}/{size}", "/page/tpId={id}/{page}/{size}/{search}" }, produces = "application/json")
	public ResponseEntity<List<BlocCompetencesDto>> getPageTitreProIdAndBySearch(@PathVariable("id") long id,@PathVariable("page") int page,
			@PathVariable("size") int max, @PathVariable(value = "search", required = false) Optional<String> search) {
		
		List<BlocCompetencesDto> result;
		
		if (search.isPresent()) {
			result = blocCompetenceService.findPagedByTitreProIdAndByTitreContainingOrDescriptionContaining(id, search.get(), page-1, max);
		} else {
			result = blocCompetenceService.findPagedByTitreProIdAndByTitreContainingOrDescriptionContaining(id, "", page-1, max);
		}
		return ResponseEntity.status(HttpStatus.OK).body(result);

	}
	

	@GetMapping(value = { "/count", "/count/{search}" }, produces = "application/json")
	public ResponseEntity<CountDto> countBy(@PathVariable(value = "search", required = false) Optional<String> search) {
		CountDto result = null;
		if (search.isPresent())
			result = blocCompetenceService.count(search.get());
		else
			result = blocCompetenceService.count("");

		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	
	
	
}
