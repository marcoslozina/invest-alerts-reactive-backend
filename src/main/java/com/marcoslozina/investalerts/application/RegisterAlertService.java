package com.marcoslozina.investalerts.application;


import com.marcoslozina.investalerts.domain.model.PriceAlert;
import com.marcoslozina.investalerts.domain.port.AlertStoragePort;
import org.springframework.stereotype.Service;

@Service
public class RegisterAlertService {

    private final AlertStoragePort storage;

    public RegisterAlertService(AlertStoragePort storage) {
        this.storage = storage;
    }

    public PriceAlert register(String symbol, double threshold, PriceAlert.AlertType type) {
        if (symbol == null || symbol.isBlank()) {
            throw new IllegalArgumentException("Symbol must not be empty");
        }
        if (threshold <= 0) {
            throw new IllegalArgumentException("Threshold must be greater than zero");
        }
        PriceAlert alert = new PriceAlert(symbol.toUpperCase(), threshold, type);
        storage.save(alert);
        return alert;
    }
}
