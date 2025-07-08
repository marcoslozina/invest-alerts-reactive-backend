package com.marcoslozina.investalerts.adapters.in.web.dto;

import com.marcoslozina.investalerts.domain.model.AlertPrice;

public class PriceAlertResponse {
    public String message;
    public AlertPrice alert;

    public PriceAlertResponse(String message, AlertPrice alert) {
        this.message = message;
        this.alert = alert;
    }
}
