package com.vitor.Investmentaggregator.services;

import org.springframework.stereotype.Service;

import com.vitor.Investmentaggregator.controller.dto.CreateStockDto;
import com.vitor.Investmentaggregator.entities.Stock;
import com.vitor.Investmentaggregator.repository.StockRepository;

@Service
public class StockService {
    
    private final StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public void createStock(CreateStockDto dto) {
       var stock = new Stock(dto.stockId(), 
                            dto.description()
      );
       stockRepository.save(stock);
    }
}
