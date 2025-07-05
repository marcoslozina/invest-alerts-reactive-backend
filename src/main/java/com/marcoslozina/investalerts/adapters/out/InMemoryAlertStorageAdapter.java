package com.marcoslozina.investalerts.adapters.out;

import com.marcoslozina.investalerts.domain.model.PriceAlert;
import com.marcoslozina.investalerts.domain.port.AlertStoragePort;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemoryAlertStorageAdapter implements AlertStoragePort {

    private final Map<String, List<PriceAlert>> alertsBySymbol = new ConcurrentHashMap<>();

    @Override
    public void save(PriceAlert alert) {
        alertsBySymbol
            .computeIfAbsent(alert.getSymbol(), k -> new ArrayList<>())
            .add(alert);
    }

    @Override
    public List<PriceAlert> findBySymbol(String symbol) {
        return alertsBySymbol.getOrDefault(symbol, List.of());
    }
}
