package fr.dawan.evalnico.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import fr.dawan.evalnico.dto.CountDto;
import fr.dawan.evalnico.dto.InterventionDto;
import fr.dawan.evalnico.dto.PositionnementDto;
import fr.dawan.evalnico.entities.Intervention;
import fr.dawan.evalnico.entities.Positionnement;
import fr.dawan.evalnico.repositories.InterventionRepository;
import fr.dawan.evalnico.tools.DtoTools;

@Service
public class InterventionServiceImpl implements InterventionService {

	@Autowired
	private InterventionRepository interventionRepository;

	@Override
	public List<InterventionDto> getAll() {
		List<Intervention> resultInDb = interventionRepository.findAll();
		List<InterventionDto> result = new ArrayList<InterventionDto>();
		for (Intervention interv : resultInDb) {
			result.add(DtoTools.convert(interv, InterventionDto.class));
		}
		return result;
	}

	@Override
	public InterventionDto getById(long id) {
		Optional<Intervention> resultInDb = interventionRepository.findById(id);
		if (resultInDb.isPresent()) {
			return DtoTools.convert(resultInDb.get(), InterventionDto.class);
		}
		return null;
	}

	@Override
	public List<InterventionDto> getAllByPromotionId(long promotionId) {
		List<Intervention> resultInDb = interventionRepository.getAllByPromotionId(promotionId);
		List<InterventionDto> result = new ArrayList<InterventionDto>();
		for (Intervention interv : resultInDb) {
			result.add(DtoTools.convert(interv, InterventionDto.class));
		}
		return result;
	}

	@Override
	public List<InterventionDto> getAllByEtudiantId(long etudiantId) {
		List<Intervention> resultInDb = interventionRepository.getAllByEtudiantId(etudiantId);
		List<InterventionDto> result = new ArrayList<InterventionDto>();
		for (Intervention interv : resultInDb) {
			result.add(DtoTools.convert(interv, InterventionDto.class));
		}
		return result;
	}

	@Override
	public InterventionDto saveOrUpdate(InterventionDto idto) {
		Intervention i = DtoTools.convert(idto, Intervention.class);
		try {
			i = interventionRepository.saveAndFlush(i);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return DtoTools.convert(i, InterventionDto.class);
	}

	@Override
	public void delete(long id) {
		interventionRepository.deleteById(id);
	}
	
	
	@Override
	public List<InterventionDto> getAll(int page, int max, String search) throws Exception {
		// on requête la bdd
		List<Intervention> intervs = interventionRepository.findAll(PageRequest.of(page, max)).get()
				.collect(Collectors.toList());

		// on transforme le résultat en liste de DTO
		List<InterventionDto> result = new ArrayList<InterventionDto>();
		for (Intervention interv : intervs) {
			result.add(DtoTools.convert(interv, InterventionDto.class));
		}
		return result;
	}

	@Override
	public CountDto count() {
		long result = interventionRepository.count();
		CountDto d = new CountDto();
		d.setNb(result);
		return d;
	}

	@Override
	public List<InterventionDto> getAllByFormateurId(long id) {
		List<Intervention> resultInDb = interventionRepository.findAllByFormateurId(id);
		
		List<InterventionDto> result = new ArrayList<InterventionDto>();
		for (Intervention interv : resultInDb) {
			result.add(DtoTools.convert(interv, InterventionDto.class));
		}
		return result;
	}

}
