package com.marcoslozina.investalerts.adapters.in.rest;

import com.marcoslozina.investalerts.adapters.in.web.dto.PriceAlertRequest;
import com.marcoslozina.investalerts.adapters.in.web.dto.PriceAlertResponse;
import com.marcoslozina.investalerts.application.RegisterAlertService;
import com.marcoslozina.investalerts.domain.model.PriceAlert;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/alerts")
public class AlertController {

    private final RegisterAlertService registerAlertService;

    public AlertController(RegisterAlertService registerAlertService) {
        this.registerAlertService = registerAlertService;
    }

    @PostMapping
    public ResponseEntity<PriceAlertResponse> createAlert(@RequestBody PriceAlertRequest request) {
        PriceAlert alert = registerAlertService.register(
            request.symbol,
            request.threshold,
            request.type
        );
        return ResponseEntity.ok(new PriceAlertResponse("Alerta registrada correctamente", alert));
    }
}
