package org.example.service;


import lombok.RequiredArgsConstructor;
import org.example.cache.CurrencyRatesCache;
import org.example.model.dto.CurrencyTableDto;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class NbpTableCRates {

    private static final String TABLE_C_URL = "https://api.nbp.pl/api/exchangerates/rates/c/";

    private final CurrencyRatesCache ratesCache;

    public void updateRates() {
        WebClient webClient = WebClient.create();
        try {
            Mono<CurrencyTableDto[]> monoTable = webClient.get()
                    .uri(TABLE_C_URL)
                    .retrieve()
                    .bodyToMono(CurrencyTableDto[].class);
            CurrencyTableDto currencyTableDto = getValueFromMono(monoTable);
            ratesCache.put("tableC", currencyTableDto);
        } catch (Exception e) {
            throw new UnsupportedOperationException("Unknown exception while downloading table with rates.");
        }
    }

    private CurrencyTableDto getValueFromMono(Mono<CurrencyTableDto[]> monoTable) {
        try {
            return Objects.requireNonNull(monoTable.block())[0];
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("Currency courses are unavailable. Try again later.");
        }
    }
}
