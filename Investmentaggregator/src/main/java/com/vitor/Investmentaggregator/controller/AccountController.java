package com.vitor.Investmentaggregator.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vitor.Investmentaggregator.controller.dto.AccountStockResponseDto;
import com.vitor.Investmentaggregator.controller.dto.AssociateAccountStockDto;
import com.vitor.Investmentaggregator.services.AccountService;

@RestController
@RequestMapping("/v1/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/{accountId}/stocks")
    public ResponseEntity<Void> associateStock(@PathVariable("accountId") String accountId,
        @RequestBody AssociateAccountStockDto dto) {
        
        accountService.associateStock(accountId, dto);

        return ResponseEntity.ok().build();
    }

    
    @GetMapping("/{accountId}/stocks")
    public ResponseEntity<List<AccountStockResponseDto>> getAssociateStock(@PathVariable("accountId") String accountId) {
        
        var stocks = accountService.listStocks(accountId);

        return ResponseEntity.ok(stocks);
    }
}
