package com.marcoslozina.investalerts.domain.port;

import reactor.core.publisher.Mono;

public interface AssetPriceProvider {
    Mono<Double> getCurrentPrice(String symbol);
}
