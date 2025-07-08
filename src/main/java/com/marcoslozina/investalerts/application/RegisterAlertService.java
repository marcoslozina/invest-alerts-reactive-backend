package com.marcoslozina.investalerts.application;

import com.marcoslozina.investalerts.domain.model.AlertPrice;
import com.marcoslozina.investalerts.domain.model.AlertType; // ✅ import correcto
import com.marcoslozina.investalerts.domain.port.AlertStoragePort;
import org.springframework.stereotype.Service;

@Service
public class RegisterAlertService {

    private final AlertStoragePort storage;

    public RegisterAlertService(AlertStoragePort storage) {
        this.storage = storage;
    }

    public AlertPrice register(String symbol, double threshold, AlertType type) {
        if (symbol == null || symbol.isBlank()) {
            throw new IllegalArgumentException("Symbol must not be empty");
        }
        if (threshold <= 0) {
            throw new IllegalArgumentException("Threshold must be greater than zero");
        }

        AlertPrice alert = new AlertPrice(symbol.toUpperCase(), threshold, type);
        storage.save(alert);
        return alert;
    }
}
