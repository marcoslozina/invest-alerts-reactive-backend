package com.marcoslozina.investalerts.application;

import com.marcoslozina.investalerts.domain.model.AssetPrice;
import com.marcoslozina.investalerts.domain.port.AssetPriceProviderPort;
import com.marcoslozina.investalerts.domain.port.AssetPriceHistoryPort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AssetService {

    private final AssetPriceProviderPort priceProvider;
    private final AssetPriceHistoryPort priceHistory;

    public AssetService(AssetPriceProviderPort priceProvider,
                        AssetPriceHistoryPort priceHistory) {
        this.priceProvider = priceProvider;
        this.priceHistory = priceHistory;
    }

    public Mono<AssetPrice> getPrice(String symbol) {
        return priceProvider.getCurrentPrice(symbol)
            .flatMap(price -> priceHistory.savePrice(price).thenReturn(price));
    }

    public Flux<AssetPrice> getHistory(String symbol) {
        return priceHistory.getHistory(symbol);
    }
}
