package com.marcoslozina.investalerts.domain.model;

import java.math.BigDecimal;
import java.time.Instant;

public class AssetPrice {
    private final String symbol;
    private final BigDecimal price;
    private final Instant timestamp;

    public AssetPrice(String symbol, BigDecimal price, Instant timestamp) {
        this.symbol = symbol;
        this.price = price;
        this.timestamp = timestamp;
    }

    public String getSymbol() { return symbol; }
    public BigDecimal getPrice() { return price; }
    public Instant getTimestamp() { return timestamp; }
}
