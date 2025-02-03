package org.converter.currency.service.strategy.rates;

import lombok.RequiredArgsConstructor;
import org.converter.cache.CurrencyRatesCache;
import org.converter.currency.dto.CurrencyTableDto;
import org.converter.currency.dto.RateDto;
import org.converter.currency.valueObject.CurrencyCode;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CurrencyRatesDownloader implements RatesDownloader {

    private final CurrencyRatesCache cache;
    private final NbpTableCRates tableCRates;

    @Override
    public RateDto getCurrencyRate(String tableName, CurrencyCode currencyCode) {
        CurrencyTableDto currencyTable = getTable(tableName);
        return currencyTable.rates().stream()
                .filter(rate -> rate.code().equalsIgnoreCase(currencyCode.code()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Rate for currency " + currencyCode.code() + " not found."));
    }

    private CurrencyTableDto getTable(String tableName) {
        CurrencyTableDto currencies = cache.get(tableName);
        if (currencies == null) {
            tryUpdateTable(tableName);
            currencies = cache.get(tableName);
            Objects.requireNonNull(currencies, "Currency table '" + tableName + "' is unavailable. Try again later.");
        }
        return currencies;
    }

    private void tryUpdateTable(String tableName) {
        if ("tableC".equals(tableName)) {
            tableCRates.updateRates();
        } else {
            throw new IllegalArgumentException("Unsupported table: " + tableName);
        }
    }
}
