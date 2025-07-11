package com.marcoslozina.investalerts.infrastructure.notifier;
import com.marcoslozina.investalerts.domain.model.AlertPrice;
import com.marcoslozina.investalerts.domain.port.AlertNotifierPort;
import com.marcoslozina.investalerts.domain.port.AlertStoragePort;
import com.marcoslozina.investalerts.domain.port.AssetPriceProvider;
import com.marcoslozina.investalerts.domain.repository.AlertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Component
@Profile("local")
public class SimulatedNotifier implements AlertNotifierPort {

    private final AlertRepository repository;
    private final AssetPriceProvider priceProvider;

    public SimulatedNotifier(AlertRepository repository, AssetPriceProvider priceProvider) {
        this.repository = repository;
        this.priceProvider = priceProvider;
    }

    @Override
    public Flux<AlertPrice> streamTriggeredAlerts() {
        return Flux.interval(Duration.ofSeconds(5))
            .flatMap(tick -> repository.findAll()) // obtiene todas las alertas
            .flatMap(alert ->
                priceProvider.getCurrentPrice(alert.getSymbol())
                    .filter(price -> alert.isTriggeredBy(price))
                    .map(price -> alert)
            );
    }
}
