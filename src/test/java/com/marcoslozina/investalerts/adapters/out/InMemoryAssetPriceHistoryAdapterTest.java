package com.marcoslozina.investalerts.adapters.out;

import com.marcoslozina.investalerts.domain.model.AssetPrice;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.Instant;

class InMemoryAssetPriceHistoryAdapterTest {

    private final InMemoryAssetPriceHistoryAdapter adapter = new InMemoryAssetPriceHistoryAdapter();

    @Test
    void shouldStoreAndRetrieveHistory() {
        AssetPrice price1 = new AssetPrice("BTC", BigDecimal.valueOf(60000), Instant.now());
        AssetPrice price2 = new AssetPrice("BTC", BigDecimal.valueOf(61000), Instant.now());

        StepVerifier.create(adapter.savePrice(price1)).verifyComplete();
        StepVerifier.create(adapter.savePrice(price2)).verifyComplete();

        StepVerifier.create(adapter.getHistory("BTC"))
            .expectNext(price1)
            .expectNext(price2)
            .verifyComplete();
    }

    @Test
    void shouldReturnEmptyHistoryForUnknownSymbol() {
        StepVerifier.create(adapter.getHistory("UNKNOWN"))
            .verifyComplete();
    }
}
