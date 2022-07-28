package fr.dawan.evalnico.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fr.dawan.evalnico.entities.Etudiant;
import fr.dawan.evalnico.entities.Promotion;


@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Long>{

	@Query("FROM Etudiant etu JOIN etu.promotions p WHERE :promoId IN p")
	List<Etudiant> findAllEtudiantsByPromoId(@Param("promoId")long promoId);

	@Query(value="DELETE FROM etudiant_promotions  WHERE promotions_id = :id ",nativeQuery=true)
	void deleteRefToPromId(@Param("id") long id);

	@Query("FROM Promotion p WHERE p.titreProfessionnel.id = :titreProId")
	List<Promotion> findByTitreProId(@Param("titreProId")long titreProId);
	
	@Query("FROM Promotion p WHERE p.titreProfessionnel.titre LIKE %:search% OR p.ville.nom LIKE %:search2%" )
	Page<Promotion> findAllByTitreProContainingOrVilleContaining(String search, String search2,
			Pageable pageable);
	
	@Query("SELECT COUNT(*) FROM Promotion p WHERE p.titreProfessionnel.titre LIKE %:search% OR p.ville.nom LIKE %:search2%"
		)
	long countByTitreContainingOrVilleContaining(String search, String search2);
	
	@Query(value="DELETE FROM etudiant_promotions ep WHERE ep.etudiants_id=:etudiantId AND ep.promotions_id=:promotionId",nativeQuery = true)
	void deleteEtudiantPromotion(@Param("etudiantId")long etudiantId,@Param("promotionId") long promotionId);
	
	@Query(value="SELECT p.* FROM promotion p JOIN etudiant_promotions ep ON ep.promotions_id = p.id WHERE ep.etudiants_id =:id ",nativeQuery=true)
	List<Promotion> getPromoContainingEtudiant(@Param("id")long id);
	
}
