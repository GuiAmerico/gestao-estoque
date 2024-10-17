package com.example.gestaoestoque.services;

import com.example.gestaoestoque.controllers.request.enums.ReportType;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface ReportService {
  void generateExcel(HttpServletResponse response, ReportType type) throws IOException;
}
