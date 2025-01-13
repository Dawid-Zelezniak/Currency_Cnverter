package org.example.service.strategy;

import lombok.RequiredArgsConstructor;
import org.example.model.dto.CurrencyCourseDto;
import org.example.model.dto.MoneyConversionRequest;
import org.example.model.dto.RateDto;
import org.example.service.CurrencyRatesDownloader;
import org.example.valueObject.Currency;
import org.example.valueObject.CurrencyCode;
import org.example.valueObject.Money;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class XToPlnStrategy implements ConversionStrategy {

    private final CurrencyRatesDownloader ratesDownloader;

    @Override
    public Currency convert(MoneyConversionRequest request) {
        Currency baseCurrency = request.baseCurrency();
        CurrencyCode baseCurrencyCode = baseCurrency.code();
        CurrencyCourseDto currencyCourse = ratesDownloader.getCurrencyCourse(baseCurrencyCode);

        RateDto rate = currencyCourse.getRate();
        BigDecimal bid = rate.bid();

        Money amount = baseCurrency.amount();
        Money multiplied = amount.multiply(new Money(bid));
        return new Currency(multiplied, request.targetCurrencyCode());
    }
}
