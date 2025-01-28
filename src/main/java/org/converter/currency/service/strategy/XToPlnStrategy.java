package org.example.service.strategy;

import lombok.RequiredArgsConstructor;
import org.example.model.dto.MoneyConversionRequest;
import org.example.model.dto.RateDto;
import org.example.service.CourseDownloader;
import org.example.service.RatesDownloader;
import org.example.valueObject.Currency;
import org.example.valueObject.CurrencyCode;
import org.example.valueObject.Money;

@RequiredArgsConstructor
public class XToPlnStrategy implements ConversionStrategy {

    private final RatesDownloader ratesDownloader;

    @Override
    public Currency convert(MoneyConversionRequest request) {
        Currency baseCurrency = request.baseCurrency();
        String table = request.table();
        CurrencyCode baseCurrencyCode = baseCurrency.code();
        RateDto currencyCourse = ratesDownloader.getCurrencyCourse(table, baseCurrencyCode);

        Money amount = baseCurrency.amount();
        Money multiplied = amount.multiply(new Money(currencyCourse.bid()));
        return new Currency(multiplied, request.targetCurrencyCode());
    }
}
