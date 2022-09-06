package fr.dawan.evalnico.controllers;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import fr.dawan.evalnico.dto.TitreProfessionnelDto;
import fr.dawan.evalnico.exceptions.NoDataException;
import fr.dawan.evalnico.services.TitreProfessionnelService;


@RestController
@RequestMapping("/api/titrePro")
public class TitreProfesionnelController {

	@Autowired
	private TitreProfessionnelService titreProfessionnelService;
	
	@PostMapping(consumes="application/json", produces = "application/json")
	public ResponseEntity<TitreProfessionnelDto> save(@RequestBody TitreProfessionnelDto titreProDto){
		
		TitreProfessionnelDto result=null;
		try {
			result = titreProfessionnelService.saveOrUpdate(titreProDto);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(result);
	}
	
	
	@PutMapping(consumes="application/json", produces = "application/json")
	public ResponseEntity<TitreProfessionnelDto> update(@RequestBody TitreProfessionnelDto titreProDto ){
		TitreProfessionnelDto result=null;
		try {
			result = titreProfessionnelService.saveOrUpdate(titreProDto);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	
	@DeleteMapping(value= "/{id}") 
	public ResponseEntity<Long> Delete(@PathVariable(name="id",required=true) long id) throws Exception{
		titreProfessionnelService.delete(id);
		return ResponseEntity.status(HttpStatus.OK).body(id);
	}
	
	@GetMapping(produces="application/json",value="/titre={titre}")
	public ResponseEntity<List<TitreProfessionnelDto>> findByTitre(@PathVariable(name="titre",required=true) String titre){
		List<TitreProfessionnelDto> result = titreProfessionnelService.findByTitre(titre);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	
	@GetMapping(produces="application/json")
	public ResponseEntity<List<TitreProfessionnelDto>> findAll( ){
		List<TitreProfessionnelDto> result = titreProfessionnelService.findAll();
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	
	@GetMapping(value={"/count","/count/{search}"},produces="application/json")
	public ResponseEntity<CountDto> count(@PathVariable(name ="search", required=false)Optional<String> search){
		CountDto result = null;
		if(search.isPresent()) {
			result = titreProfessionnelService.count(search.get());
		}else {
			result = titreProfessionnelService.count("");
		}
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	
	@GetMapping(value={"/page/{page}/{max}","/page/{page}/{max}/{search}"},produces="application/json")
	public ResponseEntity<List<TitreProfessionnelDto>> getPage(@PathVariable(name ="page")int page,
			@PathVariable(name ="max")int max,
			@PathVariable(name ="search", required = false)Optional<String> search){
		List<TitreProfessionnelDto> result=null;
		
		if(search.isPresent()) {
			result = titreProfessionnelService.findByPage(page-1, max, search.get());
		}else {
			result = titreProfessionnelService.findByPage(page-1, max, "");
		}
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	
	
	
	
	@GetMapping(produces="application/json",value="/{id}")
	public ResponseEntity<TitreProfessionnelDto> findById(@PathVariable(name="id",required=true)long id){
		
		TitreProfessionnelDto tInDb = titreProfessionnelService.findById(id);
		if(tInDb!=null) {
			return ResponseEntity.status(HttpStatus.OK).body(tInDb);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}
	
	@GetMapping(value = "/{id}/fiche", produces = "application/octet-stream")
	public ResponseEntity<Resource> generateDocById(@PathVariable("id") long id) throws Exception {
		
		String outputPdfPath = titreProfessionnelService.generatePdf(id);
		
		File f = new File(outputPdfPath);
		Path path = Paths.get(f.getAbsolutePath());
		ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=fiche.pdf");
		headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
		headers.add("Pragma", "no-cache");
		headers.add("Expires", "0");
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=fiche.pdf");
		
		
		return ResponseEntity.ok()
				.headers(headers).contentLength(f.length()).contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource);
	}
	
	@GetMapping(value = "/update-from-dg2", produces = "application/json")
	public CountDto updateFromDG2() throws Exception {
		int nb = titreProfessionnelService.importFromDG2();
		CountDto result = new CountDto();
		result.setNb(nb);
		return result;
	}
}
