package com.marcoslozina.investalerts.domain.port;


import com.marcoslozina.investalerts.domain.model.PriceAlert;

import java.util.List;

public interface AlertStoragePort {
    void save(PriceAlert alert);
    List<PriceAlert> findBySymbol(String symbol);
}
