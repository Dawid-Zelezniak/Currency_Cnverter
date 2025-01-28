package org.example.model.dto;

import org.example.valueObject.Currency;
import org.example.valueObject.CurrencyCode;

public record MoneyConversionRequest(
        Integer accountId,
        String table,
        Currency baseCurrency,
        CurrencyCode targetCurrencyCode
) {

    public String getBaseCurrencyCode() {
        return baseCurrency.code().code();
    }
}
