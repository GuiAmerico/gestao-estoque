package com.example.gestaoestoque.controllers.request;

import com.example.gestaoestoque.entities.enums.MovementType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MovementRequest {

  private int quantity;
  private MovementType type;
}
