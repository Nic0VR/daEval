package fr.dawan.evalnico.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import fr.dawan.evalnico.dto.BlocCompetencesDto;
import fr.dawan.evalnico.dto.NiveauDto;
import fr.dawan.evalnico.entities.BlocCompetences;
import fr.dawan.evalnico.entities.Niveau;
import fr.dawan.evalnico.repositories.NiveauRepository;
import fr.dawan.evalnico.tools.DtoTools;

public class NiveauServiceImpl implements NiveauService {
	
	@Autowired
	private NiveauRepository niveauRepository;
	@Override
	public NiveauDto saveOrUpdate(NiveauDto niveau) {
		Niveau n = DtoTools.convert(niveau, Niveau.class);
		n=niveauRepository.saveAndFlush(n);
		return DtoTools.convert(n, NiveauDto.class);
	}

	@Override
	public void delete(long id) {
		niveauRepository.deleteById(id);
	}

	@Override
	public List<NiveauDto> getAll() {
		List<Niveau> resultInDb= niveauRepository.findAll();
		List<NiveauDto> result = new ArrayList<NiveauDto>();
		for (Niveau n : resultInDb) {
			result.add(DtoTools.convert(n, NiveauDto.class));
		}
		return result;
	}

	@Override
	public NiveauDto findById(long id) {
		Optional<Niveau> resultInDb = niveauRepository.findById(id);
		if(resultInDb.isPresent()) {
			return DtoTools.convert(resultInDb.get(), NiveauDto.class);
		}
		return null;
	}

}
