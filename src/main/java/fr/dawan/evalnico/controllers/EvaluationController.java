package fr.dawan.evalnico.controllers;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

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
import fr.dawan.evalnico.dto.EvaluationDto;
import fr.dawan.evalnico.services.EvaluationService;

@RestController
@RequestMapping("/api/eval")
public class EvaluationController {

	@Autowired
	private EvaluationService evaluationService;
	


	@PostMapping(consumes="application/json", produces = "application/json")
	public ResponseEntity<EvaluationDto> save(@RequestBody EvaluationDto promoDto){

		EvaluationDto result = null;
		
			result = evaluationService.saveOrUpdate(promoDto);
		
		if(result != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(result);

		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	}

	@PutMapping(consumes="application/json", produces = "application/json")
	public ResponseEntity<EvaluationDto> update(@RequestBody EvaluationDto promoDto ){
		EvaluationDto result = null;
		try {
			result = evaluationService.saveOrUpdate(promoDto);
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
		evaluationService.delete(id);
		return ResponseEntity.status(HttpStatus.OK).body(id);
	}

	@GetMapping(produces="application/json")
	public ResponseEntity<List<EvaluationDto>> getAll(){
		List<EvaluationDto> result= evaluationService.findAll();
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	@GetMapping(produces="application/json", value="/avg/blocId={blocCompId}/etudiantId={etudiantId}")
	public ResponseEntity<CountDto> getMoyenneEtudiantInBlocComp(@PathVariable(name="etudiantId",required=true) long etudiantId,@PathVariable(name="blocCompId",required=true) long blocCompId){
		CountDto result = evaluationService.moyenneEtudiantInBlocComp(etudiantId, blocCompId);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	
	
	@GetMapping(produces="application/json", value="/avg/promoId={promoId}/etudiantId={etudiantId}")
	public ResponseEntity<CountDto> getMoyenneEtudiantPromo(@PathVariable(name="etudiantId",required=true) long etudiantId,@PathVariable(name="promoId",required=true) long promoId){
		CountDto result = evaluationService.moyenneGeneraleEtudiant(etudiantId, promoId);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	
	@GetMapping(produces="application/json", value="/avg/promoId={promoId}")
	public ResponseEntity<CountDto> getMoyennePromo(@PathVariable(name="promoId",required=true) long promoId){
		CountDto result = evaluationService.moyenneGeneralePromotion(promoId);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	
	@GetMapping(produces="application/json", value="/avg/promoId={promoId}/blocId={blocId}")
	public ResponseEntity<CountDto> getMoyennePromoInBlocComp(@PathVariable(name="promoId",required=true) long promoId,@PathVariable(name="blocId",required=true) long blocId){
		CountDto result = evaluationService.moyennePromotionBlocCompetence(promoId, blocId);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	@GetMapping(value="/etud/id={id}",produces="application/json")
	public ResponseEntity<List<EvaluationDto>> getAllEvalByUserId(@PathVariable(name="id")long id){

		List<EvaluationDto> result = evaluationService.getAllEvalByEtudiantId(id);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	@GetMapping(value="/etudiantId={etudiantId}/blocId={blocCompId}")
	public ResponseEntity<List<EvaluationDto>> getAllEvalByUserIdAndBlocId(@PathVariable(name="etudiantId")long etudiantId, @PathVariable(name="blocCompId")long blocCompId){
		
		List<EvaluationDto> result = evaluationService.getAllEvalByEtudiantIdAndBlocId(etudiantId, blocCompId);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

	@GetMapping(value = "/bulletin-etudiant/{etudiantId}/{promotionId}", produces = "application/octet-stream")
	public ResponseEntity<Resource> generateBulletinByStudentAndPromo(@PathVariable("etudiantId") long etudiantId,
			@PathVariable("promotionId") long promotionId) throws Exception {

		String outputPdfPath = evaluationService.generateBulletinPdfByStudentAndPromo(etudiantId, promotionId);

		File f = new File(outputPdfPath);
		Path path = Paths.get(f.getAbsolutePath());
		ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=bulletinEtudiant"+etudiantId+"-promo"+promotionId+".pdf");
		headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
		headers.add("Pragma", "no-cache");
		headers.add("Expires", "0");

		return ResponseEntity.ok().headers(headers).contentLength(f.length())
				.contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource);
	}

	
	@GetMapping(value = "/bulletin-promotion/{promotionId}", produces = "application/octet-stream")
	public ResponseEntity<Resource> generateBulletinsByPromo(@PathVariable("promotionId") long promotionId) throws Exception {

		String outputZipPath = evaluationService.generateBulletinPdfForPromo(promotionId);

		File f = new File(outputZipPath);
		Path path = Paths.get(f.getAbsolutePath());
		ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=bulletins-promo-"+promotionId+".zip");
		headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
		headers.add("Pragma", "no-cache");
		headers.add("Expires", "0");

		return ResponseEntity.ok().headers(headers).contentLength(f.length())
				.contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource);
		
	}
	
	@GetMapping(value="/epreuveId={epreuveId}")
	public ResponseEntity<List<EvaluationDto>> getByEpreuveId(@PathVariable("epreuveId")long epreuveId){
		List<EvaluationDto> resultInDb=evaluationService.getAllEvalByEpreuveId(epreuveId);
		
		return ResponseEntity.status(HttpStatus.OK).body(resultInDb);
		
	}
}