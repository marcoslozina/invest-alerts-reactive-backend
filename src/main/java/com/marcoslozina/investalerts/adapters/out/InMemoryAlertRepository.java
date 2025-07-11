package com.marcoslozina.investalerts.adapters.out;

import com.marcoslozina.investalerts.domain.model.AlertPrice;
import com.marcoslozina.investalerts.domain.repository.AlertRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class InMemoryAlertRepository implements AlertRepository {

    private final List<AlertPrice> alerts = new CopyOnWriteArrayList<>();

    @Override
    public Mono<Void> save(AlertPrice alert) {
        alerts.add(alert);
        return Mono.empty();
    }

    @Override
    public Flux<AlertPrice> findAll() {
        return Flux.fromIterable(alerts);
    }
}
