package com.marcoslozina.investalerts.adapters.in.rest;

import com.marcoslozina.investalerts.application.AssetService;
import com.marcoslozina.investalerts.domain.model.AssetPrice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class AssetController {

    private final AssetService assetService;

    public AssetController(AssetService assetService) {
        this.assetService = assetService;
    }

    @GetMapping("/assets/price")
    public Mono<AssetPrice> getPrice(@RequestParam String symbol) {
        return assetService.getPrice(symbol);
    }

    @GetMapping("/assets/history")
    public Flux<AssetPrice> getHistory(@RequestParam String symbol) {
        return assetService.getHistory(symbol);
    }
}
