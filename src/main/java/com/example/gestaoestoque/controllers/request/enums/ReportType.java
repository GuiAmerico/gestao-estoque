package com.example.gestaoestoque.controllers.request.enums;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReportType {
  LOW_STOCK("Produtos com Estoque Baixo", List.of("Nome Produto", "Quantidade em Estoque")),
  BEST_SELLERS("Produtos Mais Vendidos", List.of("Nome Produto", "Quantidade Vendida")),
  MOVEMENTS("Relatório de Movimentações", List.of("Nome Produto", "Quantidade", "Data", "Tipo de Movimentação"));

  private final String description;
  private final List<String> cellValues;
}
