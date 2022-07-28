package fr.dawan.evalnico.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fr.dawan.evalnico.entities.BlocCompetences;

@Repository
public interface BlocCompetencesRepository extends JpaRepository<BlocCompetences,Long>{

	@Query("FROM BlocCompetences b WHERE b.titre LIKE %:search% OR b.description LIKE %:search%")
	List<BlocCompetences> findAllByTitreContainingOrDescriptionContaining(@Param("search")String search);

	@Query("FROM BlocCompetences b WHERE b.titreProfessionnel.id =:id")
	List<BlocCompetences> findAllByTitreProId(@Param("id") long id);

	@Query("FROM BlocCompetences b WHERE b.titre LIKE %:search% OR b.description LIKE %:search%")
	Page<BlocCompetences> findPagedByTitreContainingOrDescriptionContaining(@Param("search")String search, Pageable pageable);
	
	@Query("SELECT COUNT(b) FROM BlocCompetences b WHERE b.titre LIKE %:search% OR b.description LIKE %:search%")
	long countByTitreContainingOrDescriptionContaining(@Param("search")String search);
	
	@Query("FROM BlocCompetences b WHERE b.titreProfessionnel.id=:id AND ( b.titre LIKE %:search% OR b.description LIKE %:search% )")
	Page<BlocCompetences> findPagedByTitreProIdAndByTitreContainingOrDescriptionContaining(@Param("id")long id,@Param("search")String search, Pageable pageable);
	
}
