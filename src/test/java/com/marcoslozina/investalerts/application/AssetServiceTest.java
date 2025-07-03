package com.marcoslozina.investalerts.application;

import com.marcoslozina.investalerts.domain.model.AssetPrice;
import com.marcoslozina.investalerts.domain.port.AssetPriceProviderPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.Instant;

import static org.mockito.Mockito.*;

import com.marcoslozina.investalerts.domain.model.AssetPrice;
import com.marcoslozina.investalerts.domain.port.AssetPriceHistoryPort;
import com.marcoslozina.investalerts.domain.port.AssetPriceProviderPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static org.mockito.Mockito.*;

class AssetServiceTest {

    private AssetPriceProviderPort provider;
    private AssetPriceHistoryPort history;
    private AssetService service;

    @BeforeEach
    void setUp() {
        provider = mock(AssetPriceProviderPort.class);
        history = mock(AssetPriceHistoryPort.class);
        service = new AssetService(provider, history);
    }

    @Test
    void shouldSaveAndReturnPrice() {
        AssetPrice price = new AssetPrice("BTC", BigDecimal.valueOf(60000), Instant.now());

        when(provider.getCurrentPrice("BTC")).thenReturn(Mono.just(price));
        when(history.savePrice(price)).thenReturn(Mono.empty());

        StepVerifier.create(service.getPrice("BTC"))
            .expectNext(price)
            .verifyComplete();

        verify(history).savePrice(price);
    }

    @Test
    void shouldReturnHistory() {
        AssetPrice p1 = new AssetPrice("BTC", BigDecimal.valueOf(1), Instant.now());
        AssetPrice p2 = new AssetPrice("BTC", BigDecimal.valueOf(2), Instant.now());

        when(history.getHistory("BTC")).thenReturn(Flux.fromIterable(List.of(p1, p2)));

        StepVerifier.create(service.getHistory("BTC"))
            .expectNext(p1)
            .expectNext(p2)
            .verifyComplete();
    }
}
