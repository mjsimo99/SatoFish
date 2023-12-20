package com.satoru.satofish.repository;

import com.satoru.satofish.model.entity.Fish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface FishRepository extends JpaRepository<Fish, String> {

    @Query("SELECT f FROM Fish f WHERE f.name = :name")
    Optional<Fish> findByName(@Param("name") String name);
}
