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
public class XToPlnStrategy implements ConversionStrategy {

    private final CurrencyCourseService courseService;

    @Override
    public Currency convert(MoneyConversionRequest request) {
        CurrencyCode baseCurrencyCode = request.baseCurrency().code();
        CurrencyCourseDto currencyCourse = courseService.getCurrencyCourse(baseCurrencyCode);
        Currency currency = request.baseCurrency();
        Money amount = currency.amount();

        RateDto rate = currencyCourse.getRate();
        BigDecimal bid = rate.bid();

        Money multiplied = amount.multiply(new Money(bid));
        return new Currency(multiplied, request.targetCurrencyCode());
    }
}
