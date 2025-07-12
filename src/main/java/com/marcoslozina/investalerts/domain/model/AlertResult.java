package com.marcoslozina.investalerts.domain.model;

public record AlertResult(String symbol, Double price, Double threshold, boolean triggered) {}
