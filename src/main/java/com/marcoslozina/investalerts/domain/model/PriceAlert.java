package com.marcoslozina.investalerts.domain.model;

public class PriceAlert {
    private final String symbol;
    private final double threshold;
    private final AlertType type;

    public PriceAlert(String symbol, double threshold, AlertType type) {
        this.symbol = symbol;
        this.threshold = threshold;
        this.type = type;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getThreshold() {
        return threshold;
    }

    public AlertType getType() {
        return type;
    }

    public enum AlertType {
        GREATER_THAN, LESS_THAN
    }
}
