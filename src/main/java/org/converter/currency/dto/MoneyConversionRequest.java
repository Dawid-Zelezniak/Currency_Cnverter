package org.converter.currency.dto;

import org.converter.currency.valueObject.Currency;
import org.converter.currency.valueObject.CurrencyCode;

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
