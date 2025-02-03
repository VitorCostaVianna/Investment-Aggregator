package com.vitor.Investmentaggregator.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.vitor.Investmentaggregator.client.dto.BrapiResponseDto;

@FeignClient(
    name = "BrapiClient",
    url = "https://brapi.dev"
)
public interface BrapiClient {
    
    @GetMapping(value = "/api/quote/{stockId}")
    BrapiResponseDto getQuote(@RequestParam("token") String tokne,
                              @PathVariable("stockId") String stockId);
}
