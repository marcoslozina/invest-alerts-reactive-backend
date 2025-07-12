package com.marcoslozina.investalerts.domain.service;

import com.marcoslozina.investalerts.domain.model.AlertPrice;
import com.marcoslozina.investalerts.domain.model.AlertResult;
import com.marcoslozina.investalerts.domain.port.AlertNotificationPort;
import com.marcoslozina.investalerts.domain.port.AlertStoragePort;
import com.marcoslozina.investalerts.domain.port.AssetPriceProviderPort;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class AlertService {

    private final AlertStoragePort alertStorage;
    private final AssetPriceProviderPort priceProvider;
    private final MeterRegistry meterRegistry;
    private final AlertNotificationPort alertNotifier;

    public AlertService(AlertStoragePort alertStorage,
                        AssetPriceProviderPort priceProvider,
                        MeterRegistry meterRegistry,
                        @Qualifier("telegramNotifier") AlertNotificationPort alertNotifier) {
        this.alertStorage = alertStorage;
        this.priceProvider = priceProvider;
        this.meterRegistry = meterRegistry;
        this.alertNotifier = alertNotifier;
    }

    public Mono<AlertResult> checkAlert(AlertPrice alert) {
        return priceProvider.getCurrentPrice(alert.getSymbol())
            .flatMap(currentPrice -> {
                boolean triggered = switch (alert.getType()) {
                    case GREATER_THAN -> currentPrice > alert.getThreshold();
                    case LESS_THAN -> currentPrice < alert.getThreshold();
                };

                AlertResult result = new AlertResult(
                    alert.getSymbol(),
                    currentPrice,
                    alert.getThreshold(),
                    triggered
                );

                if (triggered) {
                    log.info("🚨 Alerta activada para {}: {} {} {}",
                        alert.getSymbol(), currentPrice, alert.getType(), alert.getThreshold());
                    meterRegistry
                        .counter("alerts.activated", "symbol", alert.getSymbol())
                        .increment();

                    return alertNotifier.notify(result).thenReturn(result);
                } else {
                    log.debug("✅ Alerta no activada para {}: precio {} vs umbral {}",
                        alert.getSymbol(), currentPrice, alert.getThreshold());
                    return Mono.just(result);
                }
            })
            .doOnError(error -> log.error(
                "❌ Error verificando alerta {}: {}", alert.getSymbol(), error.getMessage()))
            .doFinally(signal -> log.debug(
                "✔️ Verificación completada para {} con signal: {}", alert.getSymbol(), signal))
            .defaultIfEmpty(new AlertResult(
                alert.getSymbol(), null, alert.getThreshold(), false));
    }
}
