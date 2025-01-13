package com.vitor.Investmentaggregator.controller.dto;

public record AccountResponseDto(String accountId,
                                 String description,
                                 String street,
                                 Integer number) {
    
}
