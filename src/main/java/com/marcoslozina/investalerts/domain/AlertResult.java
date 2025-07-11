package com.marcoslozina.investalerts.domain;

public record AlertResult(
    String symbol,
    Double currentPrice,
    Double threshold,
    boolean isTriggered
) {}
