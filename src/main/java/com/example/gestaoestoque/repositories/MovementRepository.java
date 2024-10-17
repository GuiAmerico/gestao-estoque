package com.example.gestaoestoque.repositories;

import com.example.gestaoestoque.entities.Movement;
import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MovementRepository extends JpaRepository<Movement, Long> {
    @Query("SELECT m FROM Movement m WHERE m.date BETWEEN :start AND :end")
    Page<Movement> findMovementInPeriod(LocalDateTime start, LocalDateTime end, Pageable pageable);


}
