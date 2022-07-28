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
import fr.dawan.evalnico.repositories.EtudiantRepository;
import fr.dawan.evalnico.services.EtudiantService;

@RestController
@RequestMapping("/api/etudiant")
public class EtudiantController {

	@Autowired
	private EtudiantService etudiantService;

	//	@Value("${app.storagefolder}")
	//	private String storageFolder;

	//	@Autowired
	//	private ObjectMapper objectMapper;

	@GetMapping(produces = "application/json")
	public List<EtudiantDto> getAll(){
		return etudiantService.getAll();
	}

	// /api/users/{id} <= PathVariable (param dans l'URL)
	//api/users?email=xxxx&p2=AAAA (Request Param)

	// /api/users/{id}
	@GetMapping(value="/{id}", produces = "application/json")
	public EtudiantDto findById(@PathVariable("id") long id){
		return etudiantService.getById(id);
	}

	@PostMapping(consumes="application/json", produces = "application/json")
	public ResponseEntity<EtudiantDto> save(@RequestBody EtudiantDto uDto){
		EtudiantDto result=null;
		try {
			result = etudiantService.saveOrUpdate(uDto);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(result);
	}

	@PutMapping(consumes="application/json", produces = "application/json")
	public ResponseEntity<EtudiantDto> update(@RequestBody EtudiantDto uDto){
		EtudiantDto r = etudiantService.getById(uDto.getId());
		if(r==null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(uDto);
		}
		try {
			return ResponseEntity.status(HttpStatus.OK).body( etudiantService.saveOrUpdate(uDto));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(uDto);

	}

	//suppression
	@DeleteMapping(value="/{id}") //dans PathVariable, tout param est obligatoire,
	//mettre required à false sinon
	public ResponseEntity<Long> delete(@PathVariable(name = "id")long id){
		etudiantService.delete(id);
		return ResponseEntity.status(HttpStatus.OK).body(id);
	}


	//Utilisation de RequestParam (paramètre nommé optionel)
	// GET /{page}/{max}?search=xxxxx
	//	@GetMapping(value="/{page}/{size}", produces = "application/json")
	//	public List<EtudiantDto> getAllByPage(
	//							@PathVariable("page") int page, 
	//							@PathVariable("size") int max,
	//							@RequestParam(required = false, name = "search") String search){
	//		return etudiantService.getAll(page-1, max, "");
	//	}

	//Solution 2 avec PathVariable et dupliquer les URI
	@GetMapping(value= {"/page/{page}/{size}", "/page/{page}/{size}/{search}"}, produces = "application/json")
	public List<EtudiantDto> getAllByPage(
			@PathVariable("page") int page, 
			@PathVariable("size") int max, 
			@PathVariable(value="search", required = false) Optional<String> search){
		if(search.isPresent())
			return etudiantService.getAll(page-1, max, search.get());
		else
			return etudiantService.getAll(page-1, max, "");
	}


	// GET /count/{search}
	@GetMapping(value= {"/count","/count/{search}"}, produces = "application/json")
	public CountDto countBy(@PathVariable(value = "search",required = false) Optional<String> search) {
		CountDto result = null;
		if(search.isPresent())
			result = etudiantService.count(search.get());
		else
			result = etudiantService.count("");

		return result;
	}

	//	//mise à jour d'un utilisateur avec une image
	//	@PostMapping(value="/save-image/{id}", consumes="multipart/form-data", produces="text/plain")
	//	public ResponseEntity<String> uploadImage(@PathVariable("id") long userId, @RequestParam("file") MultipartFile file) throws Exception{
	//		//stocker le fichier dans le répertoire de stockage
	//		File f = new File(storageFolder + "/" + file.getOriginalFilename());
	//		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(f));
	//		bos.write(file.getBytes());
	//		bos.close();
	//		//dans la table user, stocker le chemin vers le fichier
	//		EtudiantDto uDto = etudiantService.getById(userId);
	//		uDto.setImagePath(file.getOriginalFilename());
	//		etudiantService.saveOrUpdate(uDto);
	//		return ResponseEntity.ok("Upload done !");
	//	}

	@GetMapping(value="/{id}")
	public ResponseEntity<EtudiantDto> getById(@PathVariable(name="id",required=true) long id){
		EtudiantDto result = etudiantService.getById(id);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
}