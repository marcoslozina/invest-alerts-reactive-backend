package com.marcoslozina.investalerts.domain.port;

import com.marcoslozina.investalerts.domain.model.AssetPrice;
import reactor.core.publisher.Mono;

public interface AssetPriceProviderPort {
    Mono<AssetPrice> getCurrentPrice(String symbol);
}
