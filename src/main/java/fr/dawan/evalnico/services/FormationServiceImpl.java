package fr.dawan.evalnico.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.dawan.evalnico.dto.CountDto;
import fr.dawan.evalnico.dto.DG2TitreProDto;
import fr.dawan.evalnico.dto.DG2TrainingDto;
import fr.dawan.evalnico.dto.FormationDto;
import fr.dawan.evalnico.dto.PromotionDto;
import fr.dawan.evalnico.entities.Formation;
import fr.dawan.evalnico.entities.Promotion;
import fr.dawan.evalnico.repositories.FormationRepository;
import fr.dawan.evalnico.tools.DtoTools;

@Service
public class FormationServiceImpl implements FormationService {

	@Autowired
	private FormationRepository formationRepository;
	
	@Override
	public int importFromDG2() throws Exception {
		RestTemplate restTemplate = new RestTemplate();// objet permettant de faire des requêtes HTTP

		ObjectMapper mapper = new ObjectMapper(); // objet de la librairie Jackson permettant de convertir de json>objet
		mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);

		ResponseEntity<String> rep = restTemplate
				.getForEntity("https://dawan.org/public/training/search?keywords=titre-professionnel", String.class);// req

		// GET

		int nbf = 0;
		if (rep.getStatusCode() == HttpStatus.OK) {

			TypeReference<Map<String,DG2TitreProDto>> typeRef = new TypeReference<Map<String,DG2TitreProDto>>(){}; 

			Map<String,DG2TitreProDto> results = mapper.readValue(rep.getBody(), typeRef);
			for(String key : results.keySet()) {
				DG2TitreProDto titreProResult = results.get(key);
				DG2TrainingDto trainingObj = titreProResult.getTraining();

				Formation f = new Formation();
				f.setSlug(trainingObj.getSlug());
				f.setObjectifsPedagogiques(trainingObj.getObjectives());
				f.setTitre(trainingObj.getTitle());
				f.setDuree(Integer.parseInt( trainingObj.getDuration()));

				FormationDto w = null;

				try {

					w = findBySlug(f.getSlug());

				} catch (Exception e) {
					e.printStackTrace();
				}

				if(w==null) {
					formationRepository.saveAndFlush(f);
					nbf++;
				}
			}
		}
		return nbf;
	}

	@Override
	public FormationDto saveOrUpdate(FormationDto fdto) {
		Formation f = DtoTools.convert(fdto, Formation.class);
		f = formationRepository.saveAndFlush(f);
		return DtoTools.convert(f, FormationDto.class);
	}

	@Override
	public List<FormationDto> getAll() {
		List<Formation> resultInDb = formationRepository.findAll();
		List<FormationDto> result = new ArrayList<FormationDto>();
		for (Formation f : resultInDb) {
			result.add(DtoTools.convert(f, FormationDto.class));
		}
		return result;
	}

	@Override
	public FormationDto getById(long id) {
		Optional<Formation> f = formationRepository.findById(id);
		if(f.isPresent()) {
			return DtoTools.convert(f.get(), FormationDto.class);
		}
		return null;
	}

	@Override
	public void deleteById(long id) {
		formationRepository.deleteById(id);

	}

	@Override
	public FormationDto findBySlug(String slug) {
		
		Optional<Formation> f = formationRepository.findBySlug(slug);
		if(f.isPresent()) {
			return DtoTools.convert(f.get(), FormationDto.class);
		}
		return null;
	}

	@Override
	public List<FormationDto> getPageByTitre(int page, int max, String search) {
		List<Formation> resultInDb = formationRepository.findAllByTitreContaining(search, PageRequest.of(page, max))
				.get().collect(Collectors.toList());
		List<FormationDto> result = new ArrayList<FormationDto>();
		for (Formation formation : resultInDb) {
			result.add(DtoTools.convert(formation, FormationDto.class));
		}
		return result;
	}

	@Override
	public CountDto count(String string) {
		long result = formationRepository.countByTitreContaining(string);
		CountDto d = new CountDto();
		d.setNb(result);
		return d;
	}

}
