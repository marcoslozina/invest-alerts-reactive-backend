package com.marcoslozina.investalerts.infrastructure;

import com.marcoslozina.investalerts.domain.port.AssetPriceProvider;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Profile("local")
public class DummyAssetPriceProvider implements AssetPriceProvider {

    @Override
    public Mono<Double> getCurrentPrice(String symbol) {
        // Devuelve un valor fijo o simulado
        return Mono.just(123.45); // o cualquier lógica simulada
    }
}
