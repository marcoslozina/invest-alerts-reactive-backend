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
    public Mono<Void> savePrice(AssetPrice price) {
        historyMap.compute(price.getSymbol(), (symbol, deque) -> {
            if (deque == null) {
                deque = new ArrayDeque<>();
            }
            if (deque.size() >= maxSize) {
                deque.removeFirst();
            }
            deque.addLast(price);
            return deque;
        });
        return Mono.empty();
    }

    @Override
    public Flux<AssetPrice> getHistory(String symbol) {
        Deque<AssetPrice> deque = historyMap.get(symbol);
        if (deque == null) return Flux.empty();
        return Flux.fromIterable(new ArrayList<>(deque));
    }
}
