package fr.dawan.evalnico.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fr.dawan.evalnico.entities.Positionnement;

public interface PositionnementRepository extends JpaRepository<Positionnement,Long> {

	@Query("FROM Positionnement p WHERE p.intervention.id = :interventionId")
	List<Positionnement> findAllByInterventionId(@Param("interventionId")long interventionId);
	
	@Query("FROM Positionnement p WHERE p.etudiant.id = :etudiantId")
	List<Positionnement> findAllByEtudiantId(@Param("etudiantId")long etudiantId);
	
	@Query("FROM Positionnement p WHERE p.etudiant.id = :etudiantId AND p.intervention.id = :interventionId")
	Positionnement findByEtudiantIdAndInterventionId(@Param("etudiantId")long etudiantId,@Param("interventionId")long interventionId);
	
	@Query("FROM Positionnement p JOIN p.etudiant etu JOIN FETCH etu.promotions promo WHERE promo.id= :promotionId")
	List<Positionnement> getAllByPromoId(@Param("promotionId")long promotionId);
	
	@Query("FROM Positionnement p JOIN p.etudiant etu JOIN FETCH etu.promotions promo WHERE etu.id=:etudiantId AND promo.id= :promotionId")
	List<Positionnement> getAllByEtudiantAndPromo(long etudiantId, long promotionId);

	
}
