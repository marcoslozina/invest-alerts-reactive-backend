package com.marcoslozina.investalerts.adapters.out;

import com.marcoslozina.investalerts.domain.model.AssetPrice;
import com.marcoslozina.investalerts.domain.port.AssetPriceProviderPort;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;

@Component
public class PriceApiClient implements AssetPriceProviderPort {

    private final WebClient webClient;

    public PriceApiClient(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public Mono<AssetPrice> getCurrentPrice(String symbol) {
        String id = mapToApiId(symbol);
        return webClient.get()
            .uri("/simple/price?ids={id}&vs_currencies=usd", id)
            .retrieve()
            .bodyToMono(Map.class)
            .map(body -> {
                Map<String, Object> coin = (Map<String, Object>) body.get(id);
                BigDecimal price = new BigDecimal(coin.get("usd").toString());
                return new AssetPrice(symbol.toUpperCase(), price, Instant.now());
            });
    }

    private String mapToApiId(String symbol) {
        return switch (symbol.toUpperCase()) {
            case "BTC" -> "bitcoin";
            case "ETH" -> "ethereum";
            case "SOL" -> "solana";
            default -> throw new IllegalArgumentException("Símbolo no soportado: " + symbol);
        };
    }
}
