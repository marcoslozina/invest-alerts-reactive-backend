package com.marcoslozina.investalerts.infrastructure;

import com.marcoslozina.investalerts.domain.port.AssetPriceProviderPort;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Random;

@Component
@Primary
public class SimulatedAssetPriceProvider implements AssetPriceProviderPort {

    private final Random random = new Random();

    @Override
    public Mono<Double> getCurrentPrice(String symbol) {
        // Simula precio entre 30.000 y 80.000
        double simulatedPrice = 30000 + (random.nextDouble() * 50000);
        return Mono.just(simulatedPrice);
    }
}
