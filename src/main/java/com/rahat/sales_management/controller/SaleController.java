package com.rahat.sales_management.controller;

import com.rahat.sales_management.model.Sale;
import com.rahat.sales_management.service.SaleService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/v1/sales")
@AllArgsConstructor
public class SaleController {

    private final SaleService saleService;

    @GetMapping
    public ResponseEntity<List<Sale>> getAllSale() {
        return ResponseEntity.ok(saleService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sale> getSaleById(@PathVariable Integer id) {
        return ResponseEntity.ok(saleService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Sale> createSale(@RequestBody Sale sale) {
        return ResponseEntity.ok(saleService.create(sale));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sale> updateSale(@PathVariable Integer id, @RequestBody Sale sale) {
        return ResponseEntity.ok(saleService.update(id, sale));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSale(@PathVariable Integer id) {
        saleService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/daily")
    public ResponseEntity<Resource> getDailySail() {
        LocalDate today = LocalDate.now();
        File generatedFile = saleService.generateDailySalesReport(today);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=daily_sales_report_" + today.format(DateTimeFormatter.ISO_LOCAL_DATE) + ".xlsx");
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);

        Resource resource = new FileSystemResource(generatedFile);
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(generatedFile.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
