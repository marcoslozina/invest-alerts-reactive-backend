package com.marcoslozina.investalerts.domain.model;

public class AlertResult {

    private final String symbol;
    private final Double currentPrice;
    private final double threshold;
    private final boolean triggered;

    public AlertResult(String symbol, Double currentPrice, double threshold, boolean triggered) {
        this.symbol = symbol;
        this.currentPrice = currentPrice;
        this.threshold = threshold;
        this.triggered = triggered;
    }

    public String getSymbol() {
        return symbol;
    }

    public Double getCurrentPrice() {
        return currentPrice;
    }

    public double getThreshold() {
        return threshold;
    }

    public boolean isTriggered() {
        return triggered;
    }

    @Override
    public String toString() {
        return "AlertResult{" +
            "symbol='" + symbol + '\'' +
            ", currentPrice=" + currentPrice +
            ", threshold=" + threshold +
            ", triggered=" + triggered +
            '}';
    }
}
