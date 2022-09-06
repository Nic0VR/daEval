package fr.dawan.evalnico.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.dawan.evalnico.dto.CountDto;
import fr.dawan.evalnico.dto.FormateurDto;
import fr.dawan.evalnico.dto.LoginDto;
import fr.dawan.evalnico.dto.LoginResponseDto;
import fr.dawan.evalnico.dto.UtilisateurDto;
import fr.dawan.evalnico.entities.Utilisateur;
import fr.dawan.evalnico.exceptions.InvalidDataException;
import fr.dawan.evalnico.exceptions.NoDataException;
import fr.dawan.evalnico.exceptions.TokenException;
import fr.dawan.evalnico.repositories.UtilisateurRepository;
import fr.dawan.evalnico.tools.DtoTools;
import fr.dawan.evalnico.tools.HashTools;
import fr.dawan.evalnico.tools.JwtTokenUtil;
import fr.dawan.evalnico.tools.TokenSaver;

@Service
@Transactional
public class UtilisateurServiceImpl implements UtilisateurService {

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	private UtilisateurRepository utilisateurRepository;

	@Override
	public UtilisateurDto saveOrUpdate(UtilisateurDto uDto) throws Exception  {
		Utilisateur u = DtoTools.convert(uDto, Utilisateur.class);

		try {
			UtilisateurDto uInDb = this.findByEmail(uDto.getEmail());
			if (uInDb != null) {
				throw new InvalidDataException("Email déjà pris");
			}
		} catch (NoDataException e) {

		}
		if (u.getId() == 0) { // insertion

			u.setMotDePasse(HashTools.hashSHA512(u.getMotDePasse()));
		} else { // modif

			UtilisateurDto userInDb;
			try {
				userInDb = getById(u.getId());
				if (u.getMotDePasse().length() > 0
						&& !userInDb.getMotDePasse().contentEquals(HashTools.hashSHA512(u.getMotDePasse()))) {

					u.setMotDePasse(HashTools.hashSHA512(u.getMotDePasse()));
				}
				// si le mot de passe est vide, on ne l'update pas
				if (u.getMotDePasse().length() == 0) {
					u.setMotDePasse(userInDb.getMotDePasse());
				}
				
			} catch (IllegalArgumentException e) {
				throw new IllegalArgumentException(e);
				
			} catch (NoDataException e) {
				u.setMotDePasse(HashTools.hashSHA512(u.getMotDePasse()));
			}
		}

		u = utilisateurRepository.saveAndFlush(u);
		return DtoTools.convert(u, UtilisateurDto.class);
	}

	@Override
	public List<UtilisateurDto> getAll() {

		List<Utilisateur> ListU = utilisateurRepository.findAll();
		List<UtilisateurDto> result = new ArrayList<UtilisateurDto>();

		for (Utilisateur utilisateur : ListU) {
			result.add(DtoTools.convert(utilisateur, UtilisateurDto.class));
		}
		return result;
	}

	@Override
	public List<UtilisateurDto> getAll(int page, int max, String search) throws IllegalArgumentException {
		// on requête la bdd
//		System.out.println("search = "+search);
		if (page < 0 || max < 0) {
			throw new IllegalArgumentException("page and size must be greater than 0");
		}
		List<Utilisateur> users = utilisateurRepository
				.findAllByNomContainingOrPrenomContainingOrEmailContaining(search, search, search,
						PageRequest.of(page, max))
				.get().collect(Collectors.toList());

		// on transforme le résultat en liste de DTO
		List<UtilisateurDto> result = new ArrayList<UtilisateurDto>();
		for (Utilisateur u : users) {
			result.add(DtoTools.convert(u, UtilisateurDto.class));
		}
		return result;
	}

	@Override
	public CountDto count(String search) {
		CountDto result = new CountDto();
		result.setNb(
				utilisateurRepository.countByNomContainingOrPrenomContainingOrEmailContaining(search, search, search));

		return result;
	}

	@Override
	public void delete(long id) throws IllegalArgumentException, NoDataException {

		UtilisateurDto userToDelete = this.getById(id);

		utilisateurRepository.deleteById(userToDelete.getId());

	}

	@Override
	public UtilisateurDto findByEmail(String email) throws NoDataException {
		Utilisateur u = utilisateurRepository.findByEmail(email);
		if (u != null)
			return DtoTools.convert(u, UtilisateurDto.class);
		else
			throw new NoDataException("User not found !");
	}

	@Override
	public UtilisateurDto getById(long id) throws NoDataException, IllegalArgumentException {
		if (id <= 0) {
			throw new IllegalArgumentException("Id must be greater than 0");
		}
		Optional<Utilisateur> u = utilisateurRepository.findById(id);
		if (u.isPresent())
			return DtoTools.convert(u.get(), UtilisateurDto.class);

		throw new NoDataException("User not found");
	}

	@Override
	public LoginResponseDto checkLogin(LoginDto loginDto) throws Exception {
		Utilisateur u = utilisateurRepository.findByEmail(loginDto.getEmail());
		if (u != null && u.getMotDePasse().equals(HashTools.hashSHA512(loginDto.getMotDePasse())) && u.isActive()) {
			LoginResponseDto result = DtoTools.convert(u, LoginResponseDto.class);
			// generate JWT TOKEN
			Map<String, Object> claims = new HashMap<String, Object>();
			claims.put("user_id", u.getId());
			claims.put("user_fullName", u.getNom() + " " + u.getPrenom());
			claims.put("user_role", u.getRole().toString());

			String token = jwtTokenUtil.doGenerateToken(claims, loginDto.getEmail());
			TokenSaver.tokensByEmail.put(u.getEmail(), token);
			// générer le token
			// le sauvegarder côté service pour pouvoir le vérifier lors des prochaines
			// requêtes
			result.setToken(token);
			result.setNom(u.getNom());
			result.setPrenom(u.getPrenom());
			result.setTypeUser(u.getRole().toString());
			return result;
		} else
			throw new TokenException("Error : invalid credentials !");
	}

	@Override
	public List<FormateurDto> getAllFormateurs() throws NoDataException {

		List<Utilisateur> resultInDb = utilisateurRepository.getAllFormateurs();
		if (resultInDb.isEmpty()) {
			throw new NoDataException("No trainers found");
		}
		List<FormateurDto> result = new ArrayList<FormateurDto>();
		for (Utilisateur utilisateur : resultInDb) {
			FormateurDto f = new FormateurDto();
			f.setId(utilisateur.getId());
			f.setNom(utilisateur.getNom());
			f.setPrenom(utilisateur.getPrenom());
			result.add(f);
		}
		return result;
	}
}
