package fr.dawan.evalnico.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fr.dawan.evalnico.dto.EvaluationDto;
import fr.dawan.evalnico.entities.Evaluation;
@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {

	//évaluations d'un étudiant donné
	@Query("FROM Evaluation e WHERE e.etudiant.id= :etudiantId")
	List<Evaluation> findAllByEtudiantId(@Param("etudiantId") long etudiantId);

	//moyenne d'un étudiant pour une promotion donnée
	@Query("SELECT AVG(e.note) FROM Evaluation e JOIN e.etudiant etu JOIN etu.promotions promo WHERE etu.id= :etudiantId AND promo.id= :promotionId")
	double getAvgByEtudiantIdAndPromotionId(@Param("etudiantId") long etudiantId, @Param("promotionId") long promotionId);


	//moyenne d'une promo
	@Query("SELECT AVG(e.note) FROM Evaluation e JOIN e.etudiant etu JOIN etu.promotions promo WHERE promo.id= :promotionId")
	double getAvgByPromotionId(@Param("promotionId") long promotionId);

	@Query("SELECT AVG(e.note) FROM Evaluation e JOIN e.etudiant etu WHERE etu.id= :etudiantId AND e.epreuve.blocCompetences.id = :blocCompetencesId")
	double getAvgByEtudiantIdAndBlocCompId(@Param("etudiantId") long etudiantId, @Param("blocCompetencesId") long blocCompetencesId);
	
	@Query("SELECT AVG(e.note) FROM Evaluation e JOIN e.etudiant etu JOIN etu.promotions promo "
			+ "JOIN e.epreuve epr JOIN epr.blocCompetences b WHERE :promotionId IN promo.id AND b.id= :blocCompId")
	double getAvgOfPromoByBlocCompId(@Param("promotionId") long promotionId,@Param("blocCompId") long blocCompId);

	@Query("FROM Evaluation e WHERE e.etudiant.id = :id")
	List<Evaluation> getByEtudiantId(@Param("id")long id);

	@Query("FROM Evaluation e JOIN e.etudiant etu WHERE e.etudiant.id = :etudiantId AND e.epreuve.blocCompetences.id = :blocCompId")
	List<Evaluation> getByEtudiantIdAndBlocCompId(long etudiantId, long blocCompId);
	
	@Modifying
	@Query(value="DELETE FROM Evaluation e WHERE e.etudiant.id = :id")
	void deleteByUserId(@Param("id")long id);

	@Modifying
	@Query("DELETE FROM Evaluation e WHERE e.epreuve.id = :id")
	void deleteByEpreuveId(long id);
	
	@Query("FROM Evaluation e WHERE e.epreuve.id = :id")
	List<Evaluation> findAllByEpreuveId(@Param("id")long id);
	
	
//	@Query("SELECT AVG(e.note) FROM Evaluation e JOIN e.etudiant etu JOIN etu.promotions promo"
//			+ " WHERE promo.id= :promotionId AND e.epreuve.blocCompetences.id = :blocCompetencesId")
//	double getAvgOfPromoByBlocCompId(@Param("promotionId") long promotionId,@Param("blocCompetencesId") long blocCompId);
//	
	
	
}
