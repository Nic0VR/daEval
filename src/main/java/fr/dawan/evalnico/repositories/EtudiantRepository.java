package fr.dawan.evalnico.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fr.dawan.evalnico.entities.Etudiant;

@Repository
public interface EtudiantRepository extends JpaRepository<Etudiant, Long>{

	@Query("FROM Etudiant u WHERE u.email= :email") 
	Etudiant findByEmail(@Param("email") String email);

	Page<Etudiant> findAllByNomContainingOrPrenomContainingOrEmailContaining(
			String nom, String prenom, String email, Pageable pageable);

	long countByNomContainingOrPrenomContainingOrEmailContaining(String nom, String prenom, String email);
	
	@Query("FROM Etudiant etu JOIN etu.promotions promo WHERE promo.id = :promotionId")
	List<Etudiant> findByPromotionId(@Param("promotionId")long promotionId);

}
