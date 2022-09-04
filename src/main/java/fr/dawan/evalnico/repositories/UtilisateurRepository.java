package fr.dawan.evalnico.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fr.dawan.evalnico.dto.FormateurDto;
import fr.dawan.evalnico.entities.Utilisateur;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur,Long>{

	@Query("FROM Utilisateur u WHERE u.email= :email") 
	Utilisateur findByEmail(@Param("email") String email);

	Page<Utilisateur> findAllByNomContainingOrPrenomContainingOrEmailContaining(
			String nom, String prenom, String email, Pageable pageable);

	int countByNomContainingOrPrenomContainingOrEmailContaining(String nom, String prenom, String email);

	@Query("FROM Utilisateur u WHERE u.role = 'FORMATEUR'")
	List<Utilisateur> getAllFormateurs();
	
}
