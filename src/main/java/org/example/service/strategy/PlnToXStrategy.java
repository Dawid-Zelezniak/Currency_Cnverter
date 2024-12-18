package org.example.service.strategy;

import lombok.RequiredArgsConstructor;
import org.example.model.dto.CurrencyCourseDto;
import org.example.model.dto.MoneyConversionRequest;
import org.example.model.dto.RateDto;
import org.example.service.CurrencyCourseService;
import org.example.valueObject.Currency;
import org.example.valueObject.CurrencyCode;
import org.example.valueObject.Money;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class PlnToXStrategy implements ConversionStrategy {

    private final CurrencyCourseService courseService;

    @Override
    public Currency convert(MoneyConversionRequest request) {
        CurrencyCode targetCurrencyCode = request.targetCurrencyCode();
        CurrencyCourseDto currencyCourse = courseService.getCurrencyCourse(targetCurrencyCode);
        Currency currency = request.baseCurrency();
        Money amount = currency.amount();

        RateDto rate = currencyCourse.getRate();
        BigDecimal ask = rate.ask();

        Money divided = amount.divide(new Money(ask));
        return new Currency(divided, targetCurrencyCode);
    }
}
