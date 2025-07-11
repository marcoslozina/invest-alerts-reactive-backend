package com.marcoslozina.investalerts.domain.port;


import com.marcoslozina.investalerts.domain.model.AlertPrice;

import java.util.List;

public interface AlertStoragePort {
    void save(AlertPrice alert);
    List<AlertPrice> findBySymbol(String symbol);
    List<AlertPrice> findAll();
}
