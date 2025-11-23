package com.vidhan.FarmchainX.repository;

import com.vidhan.FarmchainX.entity.Advisory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdvisoryRepository extends JpaRepository<Advisory, String> {

    @Query("SELECT a FROM Advisory a WHERE a.farmer.id = :farmerId ORDER BY a.lastUpdated DESC")
    List<Advisory> findByFarmerIdOrderByLastUpdatedDesc(@Param("farmerId") String farmerId);

    List<Advisory> findByCropType(String cropType);

    List<Advisory> findByPriority(String priority);
}
