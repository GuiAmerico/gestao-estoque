package com.example.gestaoestoque.services.imp;

import static com.example.gestaoestoque.controllers.request.enums.ReportType.BEST_SELLERS;
import static com.example.gestaoestoque.controllers.request.enums.ReportType.LOW_STOCK;
import static com.example.gestaoestoque.controllers.request.enums.ReportType.MOVEMENTS;

import com.example.gestaoestoque.controllers.request.enums.ReportType;
import com.example.gestaoestoque.controllers.response.ProductResponse;
import com.example.gestaoestoque.entities.enums.MovementType;
import com.example.gestaoestoque.services.MovementService;
import com.example.gestaoestoque.services.ProductService;
import com.example.gestaoestoque.services.ReportService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ReportServiceImp implements ReportService {

  private final ProductService productService;
  private final MovementService movementService;

  @Override
  public void generateExcel(HttpServletResponse response, ReportType type) throws IOException {
    HSSFWorkbook workbook = new HSSFWorkbook();
    HSSFSheet sheet = switch (type) {
      case LOW_STOCK -> generateLowStockReport(workbook);
      case BEST_SELLERS -> generateBestSellersReport(workbook);
      case MOVEMENTS -> generateMovementsReport(workbook);
    };

    for (int i = 0; i < 4; i++) {
      sheet.autoSizeColumn(i);
    }

    ServletOutputStream ops = response.getOutputStream();
    workbook.write(ops);
    workbook.close();
    ops.close();
  }

  private HSSFSheet generateMovementsReport(
    HSSFWorkbook workbook
  ) {
    HSSFSheet sheet = workbook.createSheet(MOVEMENTS.getDescription());
    HSSFRow row = sheet.createRow(0);

    List<String> cellValues = MOVEMENTS.getCellValues();
    cellValues
      .forEach(cellValue -> row.createCell(cellValues.indexOf(cellValue)).setCellValue(cellValue));

    HSSFCellStyle dateCellStyle = workbook.createCellStyle();
    HSSFDataFormat dateFormat = workbook.createDataFormat();
    dateCellStyle.setDataFormat(dateFormat.getFormat("dd-mm-yyyy"));

    Pageable pageable = PageRequest.ofSize(100);
    movementService.getMovementsInPeriod(
        LocalDate.now().minusDays(30),
        LocalDate.now(),
        pageable
      )
      .forEach(movement -> {
        HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
        dataRow.createCell(0).setCellValue(movement.getProduct().getName());
        dataRow.createCell(1).setCellValue(movement.getQuantity());
        dataRow.createCell(2).setCellValue(movement.getDate().toLocalDate().toString());
        dataRow.createCell(3)
          .setCellValue(movement.getType().equals(MovementType.INPUT) ? "Entrada" : "Saída");
      });

    row.cellIterator().forEachRemaining(cell -> sheet.autoSizeColumn(cell.getColumnIndex()));
    return sheet;
  }

  private HSSFSheet generateBestSellersReport(
    HSSFWorkbook workbook
  ) {
    HSSFSheet sheet = workbook.createSheet(BEST_SELLERS.getDescription());
    HSSFRow row = sheet.createRow(0);

    List<String> cellValues = BEST_SELLERS.getCellValues();
    cellValues
      .forEach(cellValue -> row.createCell(cellValues.indexOf(cellValue)).setCellValue(cellValue));

    HSSFCellStyle dateCellStyle = workbook.createCellStyle();
    HSSFDataFormat dateFormat = workbook.createDataFormat();
    dateCellStyle.setDataFormat(dateFormat.getFormat("dd-mm-yyyy"));

    productService.findBestSellingProduct()
      .forEach(bestSellingProductResponse -> {
        HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
        ProductResponse product = bestSellingProductResponse.getProduct();
        int totalSold = bestSellingProductResponse.getTotalSold();
        dataRow.createCell(0).setCellValue(product.getName());
        dataRow.createCell(1).setCellValue(totalSold);
      });

    row.cellIterator().forEachRemaining(cell -> sheet.autoSizeColumn(cell.getColumnIndex()));
    return sheet;
  }

  private HSSFSheet generateLowStockReport(HSSFWorkbook workbook) {
    HSSFSheet sheet = workbook.createSheet(ReportType.LOW_STOCK.getDescription());
    HSSFRow row = sheet.createRow(0);

    List<String> cellValues = LOW_STOCK.getCellValues();
    cellValues
      .forEach(cellValue -> row.createCell(cellValues.indexOf(cellValue)).setCellValue(cellValue));

    Pageable pageable = PageRequest.ofSize(100);
    productService.findLowStockProduct(pageable)
      .forEach(product -> {
        HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
        dataRow.createCell(0).setCellValue(product.getName());
        dataRow.createCell(1).setCellValue(product.getStock());
      });

    row.cellIterator().forEachRemaining(cell -> sheet.autoSizeColumn(cell.getColumnIndex()));
    return sheet;

  }
}
