package com.marcoslozina.investalerts.adapters.out.dto;

import java.math.BigDecimal;
import java.time.Instant;

public class AssetPriceDto {
    public String symbol;
    public BigDecimal price;
    public Instant timestamp;

    // Constructor vacío requerido por Jackson
    public AssetPriceDto() {}

    // Constructor opcional por conveniencia
    public AssetPriceDto(String symbol, BigDecimal price, Instant timestamp) {
        this.symbol = symbol;
        this.price = price;
        this.timestamp = timestamp;
    }
}
