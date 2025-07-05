package com.marcoslozina.investalerts.adapters.in.web.dto;


import com.marcoslozina.investalerts.domain.model.PriceAlert;

public class PriceAlertRequest {
    public String symbol;
    public double threshold;
    public PriceAlert.AlertType type;
}
