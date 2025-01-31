package com.vitor.Investmentaggregator.services;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.vitor.Investmentaggregator.controller.dto.AccountStockResponseDto;
import com.vitor.Investmentaggregator.controller.dto.AssociateAccountStockDto;
import com.vitor.Investmentaggregator.entities.AccountStock;
import com.vitor.Investmentaggregator.entities.AccountStockId;
import com.vitor.Investmentaggregator.repository.AccountRepository;
import com.vitor.Investmentaggregator.repository.AccountStockRepository;
import com.vitor.Investmentaggregator.repository.StockRepository;

@Service
public class AccountService {
    
    private final AccountRepository accountRepository;
    
    private final StockRepository stockRepository;

    private final AccountStockRepository accountStockRepository;

    public AccountService(AccountRepository accountRepository, 
                          StockRepository stockRepository,
                          AccountStockRepository accountStockRepository) {
        this.accountRepository = accountRepository;
        this.stockRepository = stockRepository;
        this.accountStockRepository = accountStockRepository;
    }

    public void associateStock(String accountId, AssociateAccountStockDto dto) {
        var account = accountRepository.findById(UUID.fromString(accountId))
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Account not found"));
    
        var stock = stockRepository.findById(dto.stockId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Stock not found"));

        var id = new AccountStockId(account.getAccountId(), stock.getStockId());
        var accountStock = new AccountStock(id,
                                            account,
                                            stock,
                                            dto.quantity());
    
        accountStockRepository.save(accountStock);
       }

    public List<AccountStockResponseDto> listStocks(String accountId) {
        var account = accountRepository.findById(UUID.fromString(accountId))
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Account not found"));
    
        return account.getAccountStocks().stream()
                    .map(a -> new AccountStockResponseDto(a.getStock().getStockId().toString(),
                                                          a.getQuantity(),
                                                          0.0))
                    .toList();
    
    }
    
}
