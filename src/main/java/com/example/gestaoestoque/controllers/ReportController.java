package com.example.gestaoestoque.controllers;

import com.example.gestaoestoque.controllers.request.MovementRequest;
import com.example.gestaoestoque.controllers.request.enums.ReportType;
import com.example.gestaoestoque.controllers.response.MovementResponse;
import com.example.gestaoestoque.services.MovementService;
import com.example.gestaoestoque.services.ReportService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/reports")
public class ReportController {

  private final ReportService reportService;

  @GetMapping
  public void generateReport(
    HttpServletResponse response,
    @RequestParam ReportType type
  ) throws IOException {

    response.setContentType("application/octet-stream");


    String headerKey = "Content-Disposition";
    String headerValue = "attachment;filename=".concat(type.name()).concat(".xls");

    response.setHeader(headerKey, headerValue);

    reportService.generateExcel(response, type);

    response.flushBuffer();
  }

}
