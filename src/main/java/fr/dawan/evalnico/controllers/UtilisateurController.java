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
import fr.dawan.evalnico.dto.FormateurDto;
import fr.dawan.evalnico.dto.UtilisateurDto;
import fr.dawan.evalnico.exceptions.InvalidDataException;
import fr.dawan.evalnico.exceptions.NoDataException;
import fr.dawan.evalnico.services.UtilisateurService;

@RestController
@RequestMapping("/api/utilisateur")
public class UtilisateurController {

	@Autowired
	private UtilisateurService utilisateurService;

	@GetMapping(produces = "application/json")
	public List<UtilisateurDto> getAll() {
		return utilisateurService.getAll();
	}

	@GetMapping(value = { "/count", "/count/{search}" }, produces = "application/json")
	public ResponseEntity<CountDto> count(@PathVariable(name = "search", required = false) Optional<String> search) {
		System.out.println("search=" + search);
		if (search.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(utilisateurService.count(search.get()));
		}
		return ResponseEntity.status(HttpStatus.OK).body(utilisateurService.count(""));
	}

	@GetMapping(value = { "/page/{page}/{max}/", "/page/{page}/{max}/{search}" }, produces = "application/json")
	public ResponseEntity<List<UtilisateurDto>> getPage(@PathVariable(name = "page") int page,
			@PathVariable(name = "max") int max,
			@PathVariable(name = "search", required = false) Optional<String> search) {
		List<UtilisateurDto> result = null;
		if (search.isPresent()) {
			result = utilisateurService.getAll(page - 1, max, search.get());
		} else {
			result = utilisateurService.getAll(page - 1, max, "");
		}

		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
//	// /api/users/{id}
//	@GetMapping(value="/{id}", produces = "application/json")
//	public UtilisateurDto findById(@PathVariable("id") long id){
//		return utilisateurService.getById(id);
//	}

	@PostMapping(consumes = "application/json", produces = "application/json")
	public ResponseEntity<UtilisateurDto> save(@RequestBody UtilisateurDto uDto) throws Exception {
		UtilisateurDto result = null;
		
		result = utilisateurService.saveOrUpdate(uDto);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(result);
	}

	@PutMapping(consumes = "application/json", produces = "application/json")
	public ResponseEntity<UtilisateurDto> update(@RequestBody UtilisateurDto uDto) throws Exception {
		UtilisateurDto r = utilisateurService.getById(uDto.getId());

		return ResponseEntity.status(HttpStatus.OK).body(utilisateurService.saveOrUpdate(uDto));

	}

	// suppression
	@DeleteMapping(value = "/{id}", produces = "application/json") // dans PathVariable, tout param est obligatoire,
	public ResponseEntity<Long> delete(@PathVariable(name = "id") long id)throws NoDataException, IllegalArgumentException {
		utilisateurService.delete(id);
		return ResponseEntity.status(HttpStatus.OK).body(id);
	}

	@GetMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<UtilisateurDto> getById(@PathVariable(name = "id", required = true) long id) throws NoDataException, IllegalArgumentException {
		UtilisateurDto result = utilisateurService.getById(id);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

	@GetMapping(value = "/formateurs", produces = "application/json")
	public ResponseEntity<List<FormateurDto>> getAllFormateurs() throws NoDataException {
		List<FormateurDto> result = utilisateurService.getAllFormateurs();
		return ResponseEntity.status(HttpStatus.OK).body(result);

	}
}
