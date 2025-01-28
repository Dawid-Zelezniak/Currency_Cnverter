package org.example.service.strategy;

import lombok.RequiredArgsConstructor;
import org.example.model.dto.MoneyConversionRequest;
import org.example.model.dto.RateDto;
import org.example.service.RatesDownloader;
import org.example.valueObject.Currency;
import org.example.valueObject.CurrencyCode;
import org.example.valueObject.Money;

@RequiredArgsConstructor
public class PlnToXStrategy implements ConversionStrategy {

    private final RatesDownloader ratesDownloader;

    @Override
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
