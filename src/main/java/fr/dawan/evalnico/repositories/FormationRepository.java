package fr.dawan.evalnico.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fr.dawan.evalnico.entities.Formation;
@Repository
public interface FormationRepository extends JpaRepository<Formation,Long> {

	@Query("FROM Formation f WHERE f.slug = :slug")
	Optional<Formation> findBySlug(@Param("slug")String slug);

	Page<Formation> findAllByTitreContaining(String search, Pageable pageable);
	
	long countByTitreContaining(String search);
}
