package org.example.service.validation;

import lombok.extern.slf4j.Slf4j;
import org.example.model.AccountBalance;
import org.example.model.dto.MoneyConversionRequest;
import org.example.valueObject.Currency;
import org.example.valueObject.Money;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CurrencyConversionValidator {

    public void validateBalance(AccountBalance balance, MoneyConversionRequest request) {
        Currency requestBaseCurrency = request.baseCurrency();
        Money moneyToConvert = requestBaseCurrency.amount();
        log.info("Amount in request:{}", moneyToConvert);

        Money actualCurrencyAmountOnAccount = balance.getBalanceByCurrencyCode(requestBaseCurrency.code());
        log.info("Amount on account:{}", actualCurrencyAmountOnAccount.getValue());

        Money subtracted = actualCurrencyAmountOnAccount.subtract(moneyToConvert);
        if (subtracted.isLowerThanZero()) {
            log.error("Not enough funds for conversion. Missing: {}", Math.abs(subtracted.getDoubleValue()));
            throw new IllegalArgumentException("You don't have enough founds to process this operation.Missing founds:"
                            + Math.abs(subtracted.getDoubleValue()));
        }
    }
}
