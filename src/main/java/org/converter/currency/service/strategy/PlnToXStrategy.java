package org.converter.currency.service.strategy;

import lombok.RequiredArgsConstructor;
import org.converter.currency.dto.MoneyConversionRequest;
import org.converter.currency.dto.RateDto;
import org.converter.currency.service.RatesDownloader;
import org.converter.currency.valueObject.Currency;
import org.converter.currency.valueObject.CurrencyCode;
import org.converter.util.Money;

@RequiredArgsConstructor
public class PlnToXStrategy implements ConversionStrategy {

    private final RatesDownloader ratesDownloader;

    public Currency convert(MoneyConversionRequest request) {
        CurrencyCode targetCurrencyCode = request.targetCurrencyCode();
        String table = request.table();
        RateDto currencyCourse = ratesDownloader.getCurrencyCourse(table, targetCurrencyCode);

        Currency currency = request.baseCurrency();
        Money amount = currency.amount();

        Money divided = amount.divide(new Money(currencyCourse.ask()));
        return new Currency(divided, targetCurrencyCode);
    }
}
