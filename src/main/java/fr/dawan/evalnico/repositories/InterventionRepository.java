package fr.dawan.evalnico.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fr.dawan.evalnico.entities.Intervention;

@Repository
public interface InterventionRepository extends JpaRepository<Intervention,Long>{

	@Query("FROM Intervention i WHERE i.promotion.id = :promotionId")
	List<Intervention> getAllByPromotionId(@Param("promotionId")long promotionId);
	@Query("FROM Intervention i JOIN i.promotion p JOIN FETCH p.etudiants etu WHERE etu.id = :etudiantId ")
	List<Intervention> getAllByEtudiantId(@Param("etudiantId")long etudiantId);
	@Query("FROM Intervention i WHERE i.formateur.id =:id")
	List<Intervention> findAllByFormateurId(@Param("id")long id);

}
