package com.marcoslozina.investalerts.application;

import com.marcoslozina.investalerts.domain.model.AssetPrice;
import com.marcoslozina.investalerts.domain.port.AssetPriceProviderPort;
import com.marcoslozina.investalerts.domain.port.AssetPriceHistoryPort;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.Instant;

@Slf4j
@Service
public class AssetService {

    private final AssetPriceProviderPort priceProvider;
    private final AssetPriceHistoryPort priceHistory;
    private final MeterRegistry meterRegistry;

    public AssetService(AssetPriceProviderPort priceProvider,
                        AssetPriceHistoryPort priceHistory,
                        MeterRegistry meterRegistry) {
        this.priceProvider = priceProvider;
        this.priceHistory = priceHistory;
        this.meterRegistry = meterRegistry;
    }

    public Mono<AssetPrice> getPrice(String symbol) {
        return priceProvider.getCurrentPrice(symbol)
            .map(price -> new AssetPrice(symbol, BigDecimal.valueOf(price), Instant.now()))
            .doOnNext(assetPrice -> {
                log.info("📈 Precio obtenido: {} = {}", symbol, assetPrice.getPrice());
                meterRegistry.counter("asset.price.requests", "symbol", symbol).increment();
            })
            .doOnError(error -> log.error("❌ Error al obtener precio de {}: {}", symbol, error.getMessage()))
            .doFinally(signal -> log.debug("✔️ Flujo completado para {} con señal: {}", symbol, signal))
            .flatMap(assetPrice -> priceHistory.savePrice(assetPrice).thenReturn(assetPrice));
    }

    public Flux<AssetPrice> getHistory(String symbol) {
        return priceHistory.getHistory(symbol)
            .doOnSubscribe(sub -> log.debug("🔍 Consultando historial de {}", symbol))
            .doOnComplete(() -> log.debug("📜 Historial completado para {}", symbol));
    }
}
