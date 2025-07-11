package com.marcoslozina.investalerts.domain.model;

public class AlertPrice {

    private final String symbol;
    private final double threshold;
    private final AlertType type; // puede ser ABOVE o BELOW

    public AlertPrice(String symbol, double threshold, AlertType type) {
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

    public boolean isTriggeredBy(double currentPrice) {
        return switch (type) {
            case GREATER_THAN -> currentPrice >= threshold;
            case LESS_THAN -> currentPrice <= threshold;
        };
    }
}
