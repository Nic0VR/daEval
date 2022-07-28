package fr.dawan.evalnico.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.dawan.evalnico.dto.CompetenceDto;
import fr.dawan.evalnico.dto.UtilisateurDto;
import fr.dawan.evalnico.dto.CompetenceDto;
import fr.dawan.evalnico.entities.Competence;
import fr.dawan.evalnico.entities.Utilisateur;
import fr.dawan.evalnico.entities.Competence;
import fr.dawan.evalnico.repositories.CompetenceRepository;
import fr.dawan.evalnico.tools.DtoTools;

@Service
@Transactional
public class CompetenceServiceImpl implements CompetenceService{

	@Autowired
	private CompetenceRepository competenceRepository;

	@Override
	public CompetenceDto saveOrUpdate(CompetenceDto cDto) {
		Competence c  = DtoTools.convert(cDto, Competence.class);
		c = competenceRepository.saveAndFlush(c);
		return DtoTools.convert(c, CompetenceDto.class);
	}

	@Override
	public List<CompetenceDto> findByTitreOrDescription(String search) {

		List<Competence> listeC= competenceRepository.findByTitreOrDescription(search);
		List<CompetenceDto> result = new ArrayList<CompetenceDto>();
		for (Competence competence : listeC) {
			result.add(DtoTools.convert(competence, CompetenceDto.class));
		}
		return result;
	}

	@Override
	public List<CompetenceDto> findAllByBlocCompetenceId(long id) {
		List<Competence> listeC= competenceRepository.findByBlocCompetenceId(id);
		List<CompetenceDto> result = new ArrayList<CompetenceDto>();

		for (Competence competence : listeC) {
			result.add(DtoTools.convert(competence, CompetenceDto.class));
		}
		return result;
	}

	@Override
	public List<CompetenceDto> findAllByTitreProfessionnelId(long id) {
		List<Competence> listeC= competenceRepository.findByTitreProfessionelId(id);
		List<CompetenceDto> result = new ArrayList<CompetenceDto>();

		for (Competence competence : listeC) {
			result.add(DtoTools.convert(competence, CompetenceDto.class));
		}
		return result;
	}

	@Override
	public void delete(long id) {
		competenceRepository.deleteById(id);		


	}

	@Override
	public List<CompetenceDto> findAll() {
		List<Competence> resultInDb = competenceRepository.findAll();
		List<CompetenceDto> result = new ArrayList<CompetenceDto>();
		for (Competence e : resultInDb) {
			result.add(DtoTools.convert(e, CompetenceDto.class));
		}
		return result;
	}
	
	@Override
	public CompetenceDto getById(long id) {
		Optional<Competence> u = competenceRepository.findById(id);
		if (u.isPresent())
			return DtoTools.convert(u.get(), CompetenceDto.class);

		return null;
	}
}