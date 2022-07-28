package fr.dawan.evalnico.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.dawan.evalnico.entities.TitreProfessionnel;
@Repository
public interface TitreProfessionnelRepository extends JpaRepository<TitreProfessionnel,Long> {

	//@Query("FROM TitreProfessionnel t WHERE t.")
	//List<TitreProfessionnel> findAllByTitre(String titre);

	List<TitreProfessionnel> findAllByTitreContaining(String titre);

	Page<TitreProfessionnel> findAllByTitreContainingOrSlugContaining(String titre,String slug, Pageable pageable);

	TitreProfessionnel findBySlug(String slug);

	int countByTitreContainingOrSlugContaining(String search, String search2);



}
