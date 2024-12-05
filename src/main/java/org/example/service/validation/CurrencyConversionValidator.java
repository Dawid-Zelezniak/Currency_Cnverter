package org.example.service.validation;

import lombok.extern.slf4j.Slf4j;
import org.example.model.AccountBalance;
import org.example.model.dto.MoneyConversionRequest;
import org.example.valueObject.Currency;
import org.example.valueObject.Money;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Slf4j
public class CurrencyConversionValidator {

    public void validateBalance(AccountBalance balance, MoneyConversionRequest request) {
        Currency currency = request.baseCurrency();
        Money amountInRequest = currency.amount();
        log.info("Amount in request:{}", amountInRequest);

        Money balanceByCurrencyCode = balance.getBalanceByCurrencyCode(currency.code());
        BigDecimal accountCurrencyBalance = balanceByCurrencyCode.getValue();
        log.info("Amount on account:{}", accountCurrencyBalance);

        Money subtracted = balanceByCurrencyCode.subtract(amountInRequest);
        if (!subtracted.isGreaterThanOrEqualZero()) {
            throw new IllegalArgumentException(
                    "You don't have enough founds to process this operation.Missing founds:"
                            + Math.abs(subtracted.getDoubleValue()));
        }
    }
}
