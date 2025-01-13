package com.vitor.Investmentaggregator.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class AccountStockId {
    
    @Column(name = "account_id")
    private String accountId;

    @Column(name = "stock_id")
    private String stockId;

    public AccountStockId() {
    }

    public AccountStockId(String accountId, String stockId) {
        this.setAccountId(accountId);
        this.setStockId(stockId);
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }
}
