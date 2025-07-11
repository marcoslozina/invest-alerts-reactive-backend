package com.marcoslozina.investalerts.adapters.out;

import com.marcoslozina.investalerts.domain.model.AlertPrice;
import com.marcoslozina.investalerts.domain.port.AlertStoragePort;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemoryAlertStorageAdapter implements AlertStoragePort {

    private final Map<String, List<AlertPrice>> alertsBySymbol = new ConcurrentHashMap<>();

    @Override
    public void save(AlertPrice alert) {
        alertsBySymbol
            .computeIfAbsent(alert.getSymbol(), k -> new ArrayList<>())
            .add(alert);
    }

    @Override
    public List<AlertPrice> findBySymbol(String symbol) {
        return alertsBySymbol.getOrDefault(symbol, List.of());
    }

    @Override
    public List<AlertPrice> findAll() {
        return alertsBySymbol.values().stream()
            .flatMap(List::stream)
            .toList();
    }
}
