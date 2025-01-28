package org.converter.cache;

import lombok.RequiredArgsConstructor;
import org.converter.currency.dto.CurrencyTableDto;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class CurrencyRatesCache {

    private final Map<String, CurrencyTableDto> cachedRates = new ConcurrentHashMap<>();

    public void put(String key, CurrencyTableDto value) {
        cachedRates.put(key, value);
    }

    public CurrencyTableDto get(String key) {
        return cachedRates.get(key);
    }
}
