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

class AssetServiceTest {

    private AssetPriceProviderPort providerPort;
    private AssetService assetService;

    @BeforeEach
    void setUp() {
        providerPort = mock(AssetPriceProviderPort.class);
        assetService = new AssetService(providerPort);
    }

    @Test
    void getPrice_returnsExpectedAssetPrice() {
        String symbol = "BTC";
        AssetPrice mockPrice = new AssetPrice(symbol, new BigDecimal("65000.00"), Instant.now());

        when(providerPort.getCurrentPrice(symbol)).thenReturn(Mono.just(mockPrice));

        StepVerifier.create(assetService.getPrice(symbol))
            .expectNextMatches(assetPrice ->
                assetPrice.getSymbol().equals("BTC") &&
                    assetPrice.getPrice().compareTo(new BigDecimal("65000.00")) == 0)
            .verifyComplete();

        verify(providerPort).getCurrentPrice(symbol);
    }
}
