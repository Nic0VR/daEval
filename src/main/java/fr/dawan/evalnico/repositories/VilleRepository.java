package fr.dawan.evalnico.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fr.dawan.evalnico.entities.Ville;
@Repository
public interface VilleRepository extends JpaRepository<Ville,Long>{
	
	Page<Ville> findAllByNomContaining(String nom, Pageable pageable);

	long countByNomContaining(String nom);
	
	@Query("FROM Ville v WHERE v.slug = :slug")
	Ville findBySlug(@Param("slug") String slug);
}
