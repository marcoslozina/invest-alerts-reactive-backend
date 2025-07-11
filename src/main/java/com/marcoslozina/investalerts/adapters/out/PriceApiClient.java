package com.marcoslozina.investalerts.adapters.out;

import com.marcoslozina.investalerts.adapters.out.dto.AssetPriceDto;
import com.marcoslozina.investalerts.domain.model.AssetPrice;
import com.marcoslozina.investalerts.domain.port.AssetPriceProviderPort;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class PriceApiClient implements AssetPriceProviderPort {

    private final WebClient webClient;

    public PriceApiClient(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public Mono<Double> getCurrentPrice(String symbol) {
        return webClient.get()
            .uri("/price?symbol=" + symbol)
            .retrieve()
            .bodyToMono(AssetPriceDto.class)
            .map(dto -> {
                if (dto == null || dto.price == null) {
                    throw new IllegalStateException("API devolvió un AssetPrice sin 'price'");
                }
                return dto.price.doubleValue();
            });
    }

    public Mono<AssetPrice> getCurrentAssetPrice(String symbol) {
        return webClient.get()
            .uri("/price?symbol=" + symbol)
            .retrieve()
            .bodyToMono(AssetPriceDto.class)
            .map(dto -> {
                if (dto == null || dto.symbol == null || dto.price == null || dto.timestamp == null) {
                    throw new IllegalStateException("API devolvió un AssetPrice incompleto");
                }
                return new AssetPrice(dto.symbol, dto.price, dto.timestamp);
            });
    }
}
