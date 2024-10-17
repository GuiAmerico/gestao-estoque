package com.example.gestaoestoque.services;

import com.example.gestaoestoque.controllers.request.MovementRequest;
import com.example.gestaoestoque.controllers.response.MovementResponse;
import java.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MovementService {

  MovementResponse registerMovement(MovementRequest movementRequest, Long productId);

  Page<MovementResponse> getMovementsInPeriod(LocalDate start, LocalDate end, Pageable pageable);
}
