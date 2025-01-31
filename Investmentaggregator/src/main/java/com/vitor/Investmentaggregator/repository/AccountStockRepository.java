package com.vitor.Investmentaggregator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vitor.Investmentaggregator.entities.AccountStock;
import com.vitor.Investmentaggregator.entities.AccountStockId;

@Repository
public interface AccountStockRepository extends JpaRepository<AccountStock, AccountStockId> {
    
}
