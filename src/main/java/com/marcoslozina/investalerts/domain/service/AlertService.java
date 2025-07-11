package com.marcoslozina.investalerts.domain.service;

import com.marcoslozina.investalerts.domain.model.AlertPrice;
import com.marcoslozina.investalerts.domain.AlertResult;
import com.marcoslozina.investalerts.domain.model.AlertType;
import com.marcoslozina.investalerts.domain.port.AlertStoragePort;
import com.marcoslozina.investalerts.domain.port.AssetPriceProviderPort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Service
public class AlertService {

    private final AlertStoragePort alertStorage;
    private final AssetPriceProviderPort priceProvider;

    public AlertService(AlertStoragePort alertStorage, AssetPriceProviderPort priceProvider) {
        this.alertStorage = alertStorage;
        this.priceProvider = priceProvider;
    }

    public Mono<AlertResult> checkAlert(AlertPrice alert) {
        return priceProvider.getCurrentPrice(alert.getSymbol())
            .map(currentPrice -> {
                boolean triggered = switch (alert.getType()) {
                    case GREATER_THAN -> currentPrice > alert.getThreshold();
                    case LESS_THAN -> currentPrice < alert.getThreshold();
                };
                return new AlertResult(alert.getSymbol(), currentPrice, alert.getThreshold(), triggered);
            })
            .defaultIfEmpty(new AlertResult(alert.getSymbol(), null, alert.getThreshold(), false));
    }
}
