package org.converter.currency.service.strategy.rates;

import lombok.extern.slf4j.Slf4j;
import org.converter.cache.CurrencyRatesCache;
import org.converter.currency.dto.CurrencyTableDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class NbpTableCRates {

    private static final int FIRST_ELEMENT_FROM_MONO = 0;

    private final CurrencyRatesCache ratesCache;
    private final WebClient webClient;

    public NbpTableCRates(CurrencyRatesCache ratesCache,
                          @Qualifier("nbpTableCWebClient") WebClient webClient) {
        this.ratesCache = ratesCache;
        this.webClient = webClient;
    }

    public void updateRates() {
        try {
            Mono<CurrencyTableDto[]> monoTable = webClient.get()
                    .retrieve()
                    .bodyToMono(CurrencyTableDto[].class);
            CurrencyTableDto currencyTableDto = getValueFromMono(monoTable);
            ratesCache.put("tableC", currencyTableDto);
        } catch (Exception e) {
            log.error("Exception type: {}, message: {}", e.getClass(), e.getMessage());
            throw new UnsupportedOperationException("Unknown exception while downloading table with rates.");
        }
    }

    private CurrencyTableDto getValueFromMono(Mono<CurrencyTableDto[]> monoTable) {
        CurrencyTableDto[] blocked = monoTable.block();
        validateTableAndValueExist(blocked);
        return blocked[FIRST_ELEMENT_FROM_MONO];
    }

    private void validateTableAndValueExist(CurrencyTableDto[] blocked) {
        if (blocked == null || blocked.length == 0 || blocked[FIRST_ELEMENT_FROM_MONO] == null) {
            log.debug("Currency table unavailable");
            throw new IllegalArgumentException("Currency courses are unavailable. Try again later.");
        }
    }
}

