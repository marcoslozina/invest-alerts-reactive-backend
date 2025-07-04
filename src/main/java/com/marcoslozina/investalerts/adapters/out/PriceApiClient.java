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
    public Mono<AssetPrice> getCurrentPrice(String symbol) {
        String normalizedSymbol = symbol.toUpperCase(Locale.ROOT);

        return webClient.get()
            .uri("/price") // esta ruta es ficticia y no importa en test porque se mockea
            .retrieve()
            .bodyToMono(Map.class)
            .map(response -> {
                Map<String, Object> coin = (Map<String, Object>) response.get(normalizedSymbol);

                if (coin == null) {
                    throw new IllegalArgumentException("Símbolo no soportado: " + normalizedSymbol);
                }

                Object usdValue = coin.get("usd");

                if (usdValue == null) {
                    throw new IllegalArgumentException("Falta campo 'usd' para el símbolo: " + normalizedSymbol);
                }

                return new AssetPrice(
                    normalizedSymbol,
                    new BigDecimal(usdValue.toString()),
                    Instant.now()
                );
            });
    }
}
