package com.rahat.sales_management.service;

import com.rahat.sales_management.model.Sale;
import com.rahat.sales_management.repository.SaleRepository;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class SaleService {

    private SaleRepository saleRepository;

    public List<Sale> findAll() {
        return  saleRepository.findAll();
    }

    public Sale findById(Integer id) {
        return saleRepository.findById(id).orElseThrow();
    }

    public Sale create(Sale sale) {
        return saleRepository.save(sale);
    }

    public Sale update(Integer id, Sale sale) {
        findById(id);
        sale.setId(id);
        return saleRepository.save(sale);
    }

    public void delete(Integer id) {
        findById(id);
        saleRepository.deleteById(id);
    }

    public List<Sale> findAllByDate(LocalDate date) {
        return saleRepository.findBySaleDate(date);
    }

    public File generateDailySalesReport(LocalDate localDate) {
        List<Sale> salesList = saleRepository.findBySaleDate(localDate);

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Daily Sales Report");

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Sales ID");
        headerRow.createCell(1).setCellValue("Product Name");
        headerRow.createCell(2).setCellValue("Customer Name");
        headerRow.createCell(3).setCellValue("Quantity Sold");

        int rowNum = 1;
        for (Sale sales : salesList) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(sales.getId());
            row.createCell(1).setCellValue(sales.getProduct().getName());
            row.createCell(2).setCellValue(sales.getCustomer().getName());
            row.createCell(3).setCellValue(sales.getQuantity());
        }

        for (int i = 0; i < 5; i++) {
            sheet.autoSizeColumn(i);
        }

        File tempFile = null;
        try {
            tempFile = File.createTempFile("daily_sales_report_", ".xlsx");
            try (FileOutputStream outputStream = new FileOutputStream(tempFile)) {
                workbook.write(outputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exception appropriately
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return tempFile;
    }
}
