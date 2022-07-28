package fr.dawan.evalnico.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import fr.dawan.evalnico.dto.BlocCompetencesDto;
import fr.dawan.evalnico.dto.CountDto;
import fr.dawan.evalnico.dto.PromotionDto;
import fr.dawan.evalnico.entities.BlocCompetences;
import fr.dawan.evalnico.entities.Promotion;
import fr.dawan.evalnico.repositories.BlocCompetencesRepository;
import fr.dawan.evalnico.repositories.CompetenceRepository;
import fr.dawan.evalnico.repositories.EpreuveRepository;
import fr.dawan.evalnico.tools.DtoTools;

@Service
@Transactional
public class BlocCompetenceServiceImpl  implements BlocCompetenceService{

	@Autowired
	private BlocCompetencesRepository blocCompetenceRepository;

	@Autowired
	private CompetenceRepository competenceRepository;
	@Autowired
	private EpreuveRepository epreuveRepository;
	@Autowired
	private EpreuveService epreuveService;
	@Override
	public BlocCompetencesDto saveOrUpdate(BlocCompetencesDto bDto) {
		BlocCompetences b = DtoTools.convert(bDto, BlocCompetences.class);
		b = blocCompetenceRepository.saveAndFlush(b);
		return DtoTools.convert(b, BlocCompetencesDto.class);
	}


	@Override
	public void delete(long id) {
		// suppression des compétences du bloc
		competenceRepository.deleteByBlocId(id);
		// suppression des epreuves qui font réference au bloc
		epreuveService.deleteByBlocCompId(id);
		
		blocCompetenceRepository.deleteById(id);		
	}

	@Override
	public List<BlocCompetencesDto> findAllByTitreOrDescription(String search) {

		List<BlocCompetences> listeComp= blocCompetenceRepository.findAllByTitreContainingOrDescriptionContaining(search);
		List<BlocCompetencesDto> result = new ArrayList<BlocCompetencesDto>();
		for (BlocCompetences blocCompetences : listeComp) {
			result.add(DtoTools.convert(blocCompetences, BlocCompetencesDto.class));
		}
		return result;
	}

	@Override
	public BlocCompetencesDto findById(long id) {
		Optional<BlocCompetences> b = blocCompetenceRepository.findById(id);
		if(b.isPresent()) {
			return DtoTools.convert(b.get(), BlocCompetencesDto.class);
		}
		return null;
	}

	@Override
	public List<BlocCompetencesDto> findAllByTitreProId(long id) {
		List<BlocCompetences> listeB = blocCompetenceRepository.findAllByTitreProId(id);
		List<BlocCompetencesDto> result = new ArrayList<BlocCompetencesDto>();

		for (BlocCompetences blocCompetences : listeB) {
			result.add(DtoTools.convert(blocCompetences, BlocCompetencesDto.class));
		}
		return result;
	}


	@Override
	public List<BlocCompetencesDto> findAll() {
		List<BlocCompetences> resultInDb = blocCompetenceRepository.findAll();
		List<BlocCompetencesDto> result = new ArrayList<BlocCompetencesDto>();
		for (BlocCompetences titreProfessionnel : resultInDb) {
			result.add(DtoTools.convert(titreProfessionnel, BlocCompetencesDto.class));
		}
		return result;
	}


	@Override
	public void deleteByTitreProId(long id) {
		
		List<BlocCompetences> result = blocCompetenceRepository.findAllByTitreProId(id);
		for (BlocCompetences blocCompetences : result) {
			delete(blocCompetences.getId());
		}
		
	}


	@Override
	public List<BlocCompetencesDto> getPageBySearch(int page, int max, String search) {
		
		List<BlocCompetences> resultInDb = blocCompetenceRepository.findPagedByTitreContainingOrDescriptionContaining(
				 search, PageRequest.of(page, max)).get().collect(Collectors.toList());
		List<BlocCompetencesDto> result = new ArrayList<BlocCompetencesDto>();
		for (BlocCompetences promo : resultInDb) {
			result.add(DtoTools.convert(promo, BlocCompetencesDto.class));
		}
		return result;
	}

	@Override
	public CountDto count(String search) {
		long result = blocCompetenceRepository.countByTitreContainingOrDescriptionContaining(search);
		CountDto d = new CountDto();
		d.setNb(result);
		return d;
	}


	@Override
	public List<BlocCompetencesDto> findPagedByTitreProIdAndByTitreContainingOrDescriptionContaining(long id,
			String search, int page, int max) {
		
		List<BlocCompetences> resultInDb = blocCompetenceRepository.findPagedByTitreProIdAndByTitreContainingOrDescriptionContaining(id, search, PageRequest.of(page, max))
				.get().collect(Collectors.toList());
		List<BlocCompetencesDto> result = new ArrayList<BlocCompetencesDto>();
		for (BlocCompetences promo : resultInDb) {
			result.add(DtoTools.convert(promo, BlocCompetencesDto.class));
		}
		return result;
	}
	
}
