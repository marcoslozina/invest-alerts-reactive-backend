package com.marcoslozina.investalerts.adapters.out;

import com.marcoslozina.investalerts.domain.model.AssetPrice;
import com.marcoslozina.investalerts.domain.port.AssetPriceProviderPort;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Locale;
import java.util.Map;
@Component
public class PriceApiClient  implements AssetPriceProviderPort {

    private final WebClient webClient;

    public PriceApiClient(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public Mono<Double> getCurrentPrice(String symbol) {
        return webClient.get()
            .uri("/price?symbol=" + symbol)
            .retrieve()
            .bodyToMono(AssetPrice.class)
            .map(assetPrice -> assetPrice.getPrice().doubleValue());
    }
}
