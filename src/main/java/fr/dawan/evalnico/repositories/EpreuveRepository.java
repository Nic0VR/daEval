package fr.dawan.evalnico.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fr.dawan.evalnico.entities.Epreuve;
@Repository
public interface EpreuveRepository extends JpaRepository<Epreuve,Long> {

	@Modifying
	@Query("DELETE FROM Epreuve e WHERE e.blocCompetences.id = :id")
	void deleteByBlocCompId(@Param("id")long id);

	@Query("FROM Epreuve e WHERE e.blocCompetences.id = :id")
	List<Epreuve> findByBlocCompId(@Param("id") long id);

	Page<Epreuve> findAllByTitreContainingOrDescriptionContaining(String titre,String description, Pageable pageable);

	long countByTitreContainingOrDescriptionContaining(String titre,String description);

}
