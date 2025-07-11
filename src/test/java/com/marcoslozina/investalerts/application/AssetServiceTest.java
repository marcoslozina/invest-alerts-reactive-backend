package com.marcoslozina.investalerts.application;

import com.marcoslozina.investalerts.domain.model.AssetPrice;
import com.marcoslozina.investalerts.domain.port.AssetPriceProviderPort;
import com.marcoslozina.investalerts.domain.port.AssetPriceHistoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AssetServiceTest {

    @Mock
    private AssetPriceProviderPort provider;

    @Mock
    private AssetPriceHistoryPort history;

    @InjectMocks
    private AssetService service;

    @Test
    void shouldReturnAssetPriceWhenProviderReturnsValue() {
        String symbol = "BTC";
        double price = 60000.0;
        Instant now = Instant.now();
        AssetPrice expected = new AssetPrice(symbol, BigDecimal.valueOf(price), now);

        when(provider.getCurrentPrice(symbol)).thenReturn(Mono.just(price));
        when(history.savePrice(any(AssetPrice.class))).thenReturn(Mono.empty());

        StepVerifier.create(service.getPrice(symbol))
            .assertNext(actual -> {
                assertEquals(symbol, actual.getSymbol());
                assertEquals(0, BigDecimal.valueOf(price).compareTo(actual.getPrice()));
            })
            .verifyComplete();

        verify(provider).getCurrentPrice(symbol);
        verify(history).savePrice(any(AssetPrice.class));
    }

    @Test
    void shouldReturnEmptyWhenProviderFails() {
        String symbol = "ETH";
        when(provider.getCurrentPrice(symbol)).thenReturn(Mono.empty());

        StepVerifier.create(service.getPrice(symbol))
            .verifyComplete();

        verify(provider).getCurrentPrice(symbol);
        verify(history, never()).savePrice(any());
    }
}
