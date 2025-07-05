package com.marcoslozina.investalerts.adapters.in.web.dto;

import com.marcoslozina.investalerts.domain.model.PriceAlert;

public class PriceAlertResponse {
    public String message;
    public PriceAlert alert;

    public PriceAlertResponse(String message, PriceAlert alert) {
        this.message = message;
        this.alert = alert;
    }
}
