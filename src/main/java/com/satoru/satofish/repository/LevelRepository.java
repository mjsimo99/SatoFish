package com.satoru.satofish.repository;

import com.satoru.satofish.model.entity.Level;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository

public interface LevelRepository extends JpaRepository<Level, Long> {
    @Query("SELECT MAX(l.points) FROM Level l")
    Integer findHighestPoints();
}
