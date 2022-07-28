package fr.dawan.evalnico.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.dawan.evalnico.dto.EpreuveDto;
import fr.dawan.evalnico.entities.Epreuve;
import fr.dawan.evalnico.repositories.EpreuveRepository;
import fr.dawan.evalnico.repositories.EvaluationRepository;
import fr.dawan.evalnico.tools.DtoTools;

@Service
@Transactional
public class EpreuveServiceImpl implements EpreuveService{

	@Autowired
	private EpreuveRepository epreuveRepository;

	@Autowired
	private EvaluationRepository evaluationRepository;
	
	@Override
	public EpreuveDto saveOrUpdate(EpreuveDto bDto) {
		Epreuve b = DtoTools.convert(bDto, Epreuve.class);
		b = epreuveRepository.saveAndFlush(b);
		return DtoTools.convert(b, EpreuveDto.class);
	}

	@Override
	public void delete(long id) {
		//supprimer les evals associées
		evaluationRepository.deleteByEpreuveId(id);
		epreuveRepository.deleteById(id);		
	}
	/**
	 * Supprime les epreuves et leurs évaluations associées pour un bloc de compétence donné
	 */
	public void deleteByBlocCompId(long id) {
		List<EpreuveDto> resultInDb = findByBlocCompId(id);
		for (EpreuveDto epreuveDto : resultInDb) {
			delete(epreuveDto.getId());
		}
	}
	
	@Override
	public List<EpreuveDto> findAll() {
		List<Epreuve> resultInDb = epreuveRepository.findAll();
		List<EpreuveDto> result = new ArrayList<EpreuveDto>();
		for (Epreuve e : resultInDb) {
			result.add(DtoTools.convert(e, EpreuveDto.class));
		}
		return result;
	}
	
	@Override
	public EpreuveDto getById(long id) {
		Optional<Epreuve> u = epreuveRepository.findById(id);
		if (u.isPresent())
			return DtoTools.convert(u.get(), EpreuveDto.class);

		return null;
	}
	
	public List<EpreuveDto> findByBlocCompId(long id){
		List<Epreuve> resultInDb = epreuveRepository.findByBlocCompId(id);
		List<EpreuveDto> result = new ArrayList<EpreuveDto>();
		for (Epreuve e : resultInDb) {
			result.add(DtoTools.convert(e, EpreuveDto.class));
		}
		return result;
	}
}
