package com.vitor.Investmentaggregator.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.vitor.Investmentaggregator.client.BrapiClient;
import com.vitor.Investmentaggregator.controller.dto.AccountStockResponseDto;
import com.vitor.Investmentaggregator.controller.dto.AssociateAccountStockDto;
import com.vitor.Investmentaggregator.entities.AccountStock;
import com.vitor.Investmentaggregator.entities.AccountStockId;
import com.vitor.Investmentaggregator.repository.AccountRepository;
import com.vitor.Investmentaggregator.repository.AccountStockRepository;
import com.vitor.Investmentaggregator.repository.StockRepository;

@Service
public class AccountService {
    @Value("#{environment.TOKEN}")
    private String TOKEN;
    
    private final AccountRepository accountRepository;
    
    private final StockRepository stockRepository;

    private final AccountStockRepository accountStockRepository;

    private final BrapiClient brapiClient;

    public AccountService(AccountRepository accountRepository, 
                          StockRepository stockRepository,
                          AccountStockRepository accountStockRepository,
                          BrapiClient brapiClient) {
        this.accountRepository = accountRepository;
        this.stockRepository = stockRepository;
        this.accountStockRepository = accountStockRepository;
        this.brapiClient = brapiClient;
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
                                                            getTotalPrice(a.getStock().getStockId(), a.getQuantity())
                                                        ))
                    .toList();
    
    }

    private double getTotalPrice(String stockId , int quantity) {
        var response = brapiClient.getQuote(TOKEN, stockId);

        var price = response.results().getFirst().regularMarketPrice();

        return price * quantity;
    }
    
}
