package com.marcoslozina.investalerts.application;



import com.marcoslozina.investalerts.domain.model.AssetPrice;
import com.marcoslozina.investalerts.domain.port.AssetPriceProviderPort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class AssetService {

    private final AssetPriceProviderPort priceProvider;

    public AssetService(AssetPriceProviderPort priceProvider) {
        this.priceProvider = priceProvider;
    }

    public Mono<AssetPrice> getPrice(String symbol) {
        return priceProvider.getCurrentPrice(symbol);
    }
}
