package org.converter.currency.service;

import lombok.RequiredArgsConstructor;
import org.converter.account.model.AccountBalance;
import org.converter.currency.dto.MoneyConversionRequest;
import org.converter.currency.service.strategy.ConversionStrategy;
import org.converter.currency.service.strategy.ConversionStrategyProvider;
import org.converter.currency.service.validation.CurrencyConversionValidator;
import org.converter.currency.valueObject.Currency;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MoneyConversionService {

    private final CurrencyConversionValidator validator;
    private final ConversionStrategyProvider strategyProvider;

    public AccountBalance convertMoney(AccountBalance balance, MoneyConversionRequest request) {
        validator.validateBalance(balance, request);
        ConversionStrategy strategy = strategyProvider.getStrategy(request.getBaseCurrencyCode());
        Currency converted = strategy.convert(request);
        balance.subtractCurrency(request.baseCurrency());
        balance.sumCurrencyBalance(converted);
        return balance;
    }
}
