package com.marcoslozina.investalerts.domain.port;


import com.marcoslozina.investalerts.domain.model.AssetPrice;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AssetPriceHistoryPort {
    Mono<Void> savePrice(AssetPrice price);
    Flux<AssetPrice> getHistory(String symbol);
}
