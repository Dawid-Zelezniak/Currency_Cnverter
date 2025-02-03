package org.converter.currency.service.strategy.conversion;

import lombok.RequiredArgsConstructor;
import org.converter.currency.dto.MoneyConversionRequest;
import org.converter.currency.dto.RateDto;
import org.converter.currency.service.strategy.rates.RatesDownloader;
import org.converter.currency.valueObject.Currency;
import org.converter.currency.valueObject.CurrencyCode;
import org.converter.util.Money;

@RequiredArgsConstructor
public class XToPlnStrategy implements ConversionStrategy {

    private final RatesDownloader ratesDownloader;

    public Currency convert(MoneyConversionRequest request) {
        Currency baseCurrency = request.baseCurrency();
        String table = request.table();
        CurrencyCode baseCurrencyCode = baseCurrency.code();
        RateDto currencyCourse = ratesDownloader.getCurrencyRate(table, baseCurrencyCode);

        Money amount = baseCurrency.amount();
        Money multiplied = amount.multiply(new Money(currencyCourse.bid()));
        return new Currency(multiplied, request.targetCurrencyCode());
    }
}
