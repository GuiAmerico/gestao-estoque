package com.example.gestaoestoque.controllers;

import com.example.gestaoestoque.controllers.request.MovementRequest;
import com.example.gestaoestoque.controllers.response.MovementResponse;
import com.example.gestaoestoque.services.MovementService;
import jakarta.validation.Valid;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/movements")
public class MovementController {

  private final MovementService movementService;

  @PostMapping("/products/{productId}")
  public ResponseEntity<MovementResponse> registerMovement(
    @RequestBody @Valid MovementRequest movement,
    @PathVariable Long productId
  ) {
    MovementResponse newMovement = movementService.registerMovement(movement, productId);
    return ResponseEntity.status(HttpStatus.CREATED).body(newMovement);
  }

  @GetMapping("/reports/movements")
  public ResponseEntity<Page<MovementResponse>> getMovementsReport(
    @RequestParam LocalDate start,
    @RequestParam LocalDate end,
    @PageableDefault(size = 30, sort = "date", direction = Direction.DESC) Pageable pageable
  ) {
    Page<MovementResponse> movements = movementService.getMovementsInPeriod(
      start,
      end,
      pageable
    );
    return ResponseEntity.ok(movements);
  }
}
