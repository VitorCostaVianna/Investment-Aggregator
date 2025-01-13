package com.vitor.Investmentaggregator.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vitor.Investmentaggregator.controller.dto.CreateStockDto;
import com.vitor.Investmentaggregator.services.StockService;

@RestController 
@RequestMapping("/v1/stocks")
public class StockController {
    
    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @PostMapping
    public ResponseEntity<Void> createStock(@RequestBody CreateStockDto dto) {
        
        stockService.createStock(dto);

        return ResponseEntity.ok().build();
    }
}
