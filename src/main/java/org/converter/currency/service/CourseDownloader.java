package org.converter.currency.service;

import lombok.RequiredArgsConstructor;
import org.converter.cache.CurrencyRatesCache;
import org.converter.currency.dto.CurrencyTableDto;
import org.converter.currency.dto.RateDto;
import org.converter.currency.valueObject.CurrencyCode;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CourseDownloader implements RatesDownloader {

    private final CurrencyRatesCache cache;
    private final NbpTableCRates tableCRates;

    public RateDto getCurrencyCourse(String tableName, CurrencyCode currencyCode) {
        CurrencyTableDto currencyTableDto = cache.get(tableName);
        if (currencyTableDto == null) {
            tryUpdateCurrencyTable(tableName);
            currencyTableDto = cache.get(tableName);
            Objects.requireNonNull(currencyTableDto, "Currency table '" + tableName + "' is unavailable. Try again later.");
        }
        return currencyTableDto.rates().stream()
                .filter(rate -> rate.code().equalsIgnoreCase(currencyCode.code()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Rate for currency " + currencyCode.code() + " not found."));
    }

    private void tryUpdateCurrencyTable(String tableName) {
        if (tableName.equals("tableC")) {
            tableCRates.updateRates();
        } else {
            throw new IllegalArgumentException("Unsupported table: " + tableName);
        }
    }
}
