package com.marcoslozina.investalerts.adapters.in.web.dto;


import com.marcoslozina.investalerts.domain.model.AlertType;

public class PriceAlertRequest {
    public String symbol;
    public double threshold;
    public AlertType type;}
