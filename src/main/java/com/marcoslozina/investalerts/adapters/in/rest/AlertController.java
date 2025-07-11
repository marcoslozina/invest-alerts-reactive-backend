package com.marcoslozina.investalerts.adapters.in.rest;

import com.marcoslozina.investalerts.adapters.in.web.dto.PriceAlertRequest;
import com.marcoslozina.investalerts.adapters.in.web.dto.PriceAlertResponse;
import com.marcoslozina.investalerts.application.RegisterAlertService;
import com.marcoslozina.investalerts.domain.model.AlertPrice;
import com.marcoslozina.investalerts.domain.port.AlertNotifierPort;
import com.marcoslozina.investalerts.domain.repository.AlertRepository;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/alerts")
public class AlertController {

    private final RegisterAlertService registerAlertService;
    private final AlertNotifierPort notifier;
    private final AlertRepository repository;

    public AlertController(
        RegisterAlertService registerAlertService,
        AlertNotifierPort notifier,
        AlertRepository repository
    ) {
        this.registerAlertService = registerAlertService;
        this.notifier = notifier;
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity<PriceAlertResponse> createAlert(@RequestBody PriceAlertRequest request) {
        AlertPrice alert = registerAlertService.register(
            request.symbol,
            request.threshold,
            request.type
        );
        return ResponseEntity.ok(new PriceAlertResponse("Alerta registrada correctamente", alert));
    }

    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<AlertPrice> streamAlerts() {
        return notifier.streamTriggeredAlerts();
    }

   /* @PostMapping
    public Mono<Void> createAlert(@RequestBody AlertPrice alert) {
        return repository.save(alert);
    } */
}
