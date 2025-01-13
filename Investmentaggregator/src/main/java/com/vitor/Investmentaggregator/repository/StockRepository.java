package com.vitor.Investmentaggregator.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vitor.Investmentaggregator.entities.Stock;

public interface StockRepository extends JpaRepository<Stock, String> {
    
}
