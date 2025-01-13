package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.model.AccountBalance;
import org.example.model.dto.MoneyConversionRequest;
import org.example.service.strategy.ConversionStrategy;
import org.example.service.strategy.ConversionStrategyFactory;
import org.example.service.validation.CurrencyConversionValidator;
import org.example.valueObject.Currency;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MoneyConversionService {

    private final CurrencyConversionValidator validator;
    private final ConversionStrategyFactory strategyFactory;

    public AccountBalance convertMoney(AccountBalance balance, MoneyConversionRequest request) {
        validator.validateBalance(balance, request);
        ConversionStrategy strategy = strategyFactory.getStrategy(request.getBaseCurrencyCode());
        Currency converted = strategy.convert(request);
        balance.subtractCurrency(request.baseCurrency());
        balance.sumCurrencyBalance(converted);
        return balance;
    }
}
