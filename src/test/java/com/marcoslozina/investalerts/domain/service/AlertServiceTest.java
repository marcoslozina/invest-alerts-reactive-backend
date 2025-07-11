package com.marcoslozina.investalerts.domain.service;

import com.marcoslozina.investalerts.domain.model.AlertPrice;
import com.marcoslozina.investalerts.domain.model.AlertType;
import com.marcoslozina.investalerts.domain.port.AlertStoragePort;
import com.marcoslozina.investalerts.domain.port.AssetPriceProviderPort;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AlertServiceTest {

    @Mock
    private AlertStoragePort alertStorage;

    @Mock
    private AssetPriceProviderPort priceProvider;

    private AlertService alertService;

    @BeforeEach
    void setup() {
        alertService = new AlertService(alertStorage, priceProvider, new SimpleMeterRegistry());
    }

    @Test
    void shouldTriggerGreaterThanAlert() {
        AlertPrice alert = new AlertPrice("BTC", 20000.0, AlertType.GREATER_THAN);
        when(priceProvider.getCurrentPrice("BTC")).thenReturn(Mono.just(25000.0));

        StepVerifier.create(alertService.checkAlert(alert))
            .expectNextMatches(result -> result.isTriggered())
            .verifyComplete();
    }

    @Test
    void shouldNotTriggerWhenBelowThreshold() {
        AlertPrice alert = new AlertPrice("BTC", 30000.0, AlertType.GREATER_THAN);
        when(priceProvider.getCurrentPrice("BTC")).thenReturn(Mono.just(25000.0));

        StepVerifier.create(alertService.checkAlert(alert))
            .expectNextMatches(result -> !result.isTriggered())
            .verifyComplete();
    }

    @Test
    void shouldReturnNotTriggeredIfPriceUnavailable() {
        AlertPrice alert = new AlertPrice("BTC", 30000.0, AlertType.GREATER_THAN);
        when(priceProvider.getCurrentPrice("BTC")).thenReturn(Mono.empty());

        StepVerifier.create(alertService.checkAlert(alert))
            .expectNextMatches(result -> !result.isTriggered())
            .verifyComplete();
    }
}
