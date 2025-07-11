package com.marcoslozina.investalerts.adapters.out;

import com.marcoslozina.investalerts.domain.model.AssetPrice;
import com.marcoslozina.investalerts.domain.port.AssetPriceHistoryPort;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemoryAssetPriceHistoryAdapter implements AssetPriceHistoryPort {

    private final Map<String, Deque<AssetPrice>> historyMap = new ConcurrentHashMap<>();
    private final int maxSize = 20;

    @Override
    public Mono<Void> savePrice(AssetPrice assetPrice) {
        historyMap.compute(assetPrice.getSymbol(), (symbol, deque) -> {
            if (deque == null) {
                deque = new ArrayDeque<>();
            }
            if (deque.size() >= maxSize) {
                deque.removeFirst();
            }
            deque.addLast(assetPrice);
            return deque;
        });
        return Mono.empty();
    }

    @Override
    public Flux<AssetPrice> getHistory(String symbol) {
        return Flux.fromIterable(historyMap.getOrDefault(symbol, new ArrayDeque<>()));
    }
}
