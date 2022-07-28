package fr.dawan.evalnico.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.dawan.evalnico.dto.DG2LocationDto;
import fr.dawan.evalnico.dto.VilleDto;
import fr.dawan.evalnico.entities.Ville;
import fr.dawan.evalnico.repositories.VilleRepository;
import fr.dawan.evalnico.tools.DtoTools;

@Service
@Transactional
public class VilleServiceImpl implements VilleService {

	@Autowired
	private VilleRepository villeRepository;
	
	@Override
	public int updateFromDg2() throws Exception {
		RestTemplate restTemplate = new RestTemplate();// objet permettant de faire des requêtes HTTP

		ObjectMapper mapper = new ObjectMapper(); // objet de la librairie Jackson permettant de convertir de json>objet
		mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);

		ResponseEntity<String> rep = restTemplate.getForEntity("https://dawan.org/public/location/", String.class);// req
				
		// GET
		int nb = 0;
		if (rep.getStatusCode() == HttpStatus.OK) {

			DG2LocationDto[] villes = mapper.readValue(rep.getBody(), DG2LocationDto[].class);
			// traitement à faire avec les localisations récupérées
			for (DG2LocationDto locDto : villes) {
				VilleDto vDto = DtoTools.convert(locDto, VilleDto.class);
				// vérifier qu'elles n'existent pas en bdd (comparaison par rapport au slug)
				// puis insertion s'il n'existe pas
				Ville v = null;
				try {
					v = villeRepository.findBySlug(vDto.getSlug());

				} catch (Exception e) {
					e.printStackTrace();
				}
				if (v == null) {
					saveOrUpdate(vDto);
					nb++;
				}
			}
		}
		return nb;
	}

	@Override
	public VilleDto saveOrUpdate(VilleDto vDto) {
		Ville v = DtoTools.convert(vDto, Ville.class);
		v=villeRepository.saveAndFlush(v);
		return DtoTools.convert(v, VilleDto.class);
	}
	
	@Override
	public void delete(long id) {
		villeRepository.deleteById(id);		
	}
	
	public List<VilleDto> getAll(){
		List<Ville> resultInDb = villeRepository.findAll();
		List<VilleDto> result = new ArrayList<VilleDto>();
		for (Ville ville : resultInDb) {
			result.add(DtoTools.convert(ville, VilleDto.class));	
		}
		return result;
	}

	@Override
	public VilleDto getById(long id) {
		Optional<Ville> v= villeRepository.findById(id);
		if(v.isPresent()) {
			return DtoTools.convert(v.get(), VilleDto.class);
		}
		return null;
	}
}
