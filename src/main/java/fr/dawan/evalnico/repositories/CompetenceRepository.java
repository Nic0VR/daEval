package fr.dawan.evalnico.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fr.dawan.evalnico.entities.Competence;

@Repository
public interface CompetenceRepository extends JpaRepository<Competence,Long>{

	@Query("FROM Competence c WHERE c.titre LIKE %:search% OR c.description LIKE %:search%")
	List<Competence> findByTitreOrDescription(@Param("search") String search);

	@Query("FROM Competence c WHERE c.blocCompetences.id =:id")
	List<Competence> findByBlocCompetenceId(@Param("id") long id);

	@Query("FROM Competence c JOIN FETCH c.blocCompetences b WHERE  b.titreProfessionnel.id =:id  ")
	List<Competence> findByTitreProfessionelId(@Param("id") long id);

	@Modifying
	@Query("DELETE FROM Competence c WHERE c.blocCompetences.id = :id ")
	void deleteByBlocId(@Param("id") long id);

}
