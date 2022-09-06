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
	
	@Query(nativeQuery = true, value = "SELECT etu.id, util.active, util.email, util.image_path, util.mot_de_passe, util.nom, util.prenom, util.role, util.version "
			+ "FROM etudiant etu JOIN utilisateur util ON util.id=etu.id WHERE etu.id IN"
			+ " (SELECT evaluation.etudiant_id FROM evaluation WHERE evaluation.epreuve_id=:epreuveId)")
	List<Etudiant> getEtudiantAyantPasseEpreuve(@Param("epreuveId")long epreuveId);

	@Query(nativeQuery=true,value="SELECT etu.id, util.active, util.email, util.image_path, util.mot_de_passe, util.nom, util.prenom, util.role, util.version FROM etudiant etu JOIN utilisateur util ON util.id=etu.id WHERE etu.id IN (\r\n"
			+ "(SELECT etudiants_id FROM etudiant_promotions ep WHERE ep.promotions_id = (SELECT i.promotion_id FROM intervention i WHERE i.id = :intervId)))")
	List<Etudiant> getEtudiantsByInterventionId(@Param("intervId")long intervId);
}
