package com.marcoslozina.investalerts.adapters.out;

import com.marcoslozina.investalerts.adapters.out.dto.AssetPriceDto;
import com.marcoslozina.investalerts.domain.model.AssetPrice;
import com.marcoslozina.investalerts.domain.port.AssetPriceProviderPort;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class PriceApiClient implements AssetPriceProviderPort {

    private final WebClient webClient;
    private final MeterRegistry meterRegistry;

    public PriceApiClient(WebClient webClient, MeterRegistry meterRegistry) {
        this.webClient = webClient;
        this.meterRegistry = meterRegistry;
    }

    @Override
    public Mono<Double> getCurrentPrice(String symbol) {
        log.debug("🌐 Solicitando precio para {}", symbol);

        meterRegistry.counter("external.api.calls", "endpoint", "/price", "symbol", symbol).increment();

        return webClient.get()
            .uri("/price?symbol=" + symbol)
            .retrieve()
            .bodyToMono(AssetPriceDto.class)
            .doOnNext(dto -> log.debug("✅ Respuesta recibida para {}: {}", symbol, dto.price))
            .doOnError(error -> {
                log.warn("⚠️ Error al obtener precio de {}: {}", symbol, error.getMessage());
                meterRegistry.counter("external.api.errors", "endpoint", "/price", "symbol", symbol).increment();
            })
            .doFinally(signal -> log.debug("📡 Llamada a API externa finalizada para {} con señal {}", symbol, signal))
            .map(dto -> {
                if (dto == null || dto.price == null) {
                    throw new IllegalStateException("API devolvió un AssetPrice sin 'price'");
                }
                return dto.price.doubleValue();
            });
    }

    public Mono<AssetPrice> getCurrentAssetPrice(String symbol) {
        log.debug("🌐 Solicitando AssetPrice completo para {}", symbol);

        meterRegistry.counter("external.api.calls", "endpoint", "/price", "symbol", symbol).increment();

        return webClient.get()
            .uri("/price?symbol=" + symbol)
            .retrieve()
            .bodyToMono(AssetPriceDto.class)
            .doOnNext(dto -> log.debug("✅ AssetPrice recibido para {}: {}", symbol, dto))
            .doOnError(error -> {
                log.warn("⚠️ Error al obtener AssetPrice completo para {}: {}", symbol, error.getMessage());
                meterRegistry.counter("external.api.errors", "endpoint", "/price", "symbol", symbol).increment();
            })
            .doFinally(signal -> log.debug("📡 Llamada completa finalizada para {} con señal {}", symbol, signal))
            .map(dto -> {
                if (dto == null || dto.symbol == null || dto.price == null || dto.timestamp == null) {
                    throw new IllegalStateException("API devolvió un AssetPrice incompleto");
                }
                return new AssetPrice(dto.symbol, dto.price, dto.timestamp);
            });
    }
}
