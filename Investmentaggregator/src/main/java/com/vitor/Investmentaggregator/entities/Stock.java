package com.vitor.Investmentaggregator.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = Stock.TABLE_NAME)
public class Stock {
    
    private static final String TABLE_NAME = "tb_stocks";

    @Id
    @Column(name = "stock_id")
    private String stockId; // PETR4 , MGLU4

    @Column(name = "description")
    private String description;

    public Stock() {
    }

    public Stock(String stockId, String description) {
        this.setStockId(stockId);
        this.setDescription(description);
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    

}
