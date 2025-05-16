package com.supron.tourfound.repository;

import com.supron.tourfound.entity.TourEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface TourRepository extends JpaRepository<TourEntity, Long>, JpaSpecificationExecutor<TourEntity> {

    List<TourEntity> findByNameContaining(String name);

    //ToDO: Maybe delete later? :D
   /* @Query(value = "SELECT startCountry FROM TourEntity GROUP BY startCountry ORDER BY COUNT(*) DESC")
    Set<String> findToursCountryOrderedByCount();

    @Query(value = "SELECT duration FROM TourEntity GROUP BY duration ORDER BY COUNT(*) DESC")
    Set<String> findToursDurationOrderedByCount();*/

    List<TourEntity> findByStartCountry(String startCountry);

    List<TourEntity> findByNameContainingAndStartCountry(String name, String startCountry);

}
