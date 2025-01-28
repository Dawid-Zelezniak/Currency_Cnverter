package org.example.model;

import lombok.*;
import org.example.valueObject.Currency;
import org.example.valueObject.CurrencyCode;
import org.example.valueObject.Money;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.example.util.CurrencyCodes.*;

@Getter
@NoArgsConstructor
@ToString
@Builder
@AllArgsConstructor
public class AccountBalance {

    private Map<CurrencyCode, Currency> balances = new HashMap<>();

    public AccountBalance(Money money) {
        balances.put(PLN_CODE, new Currency(money, PLN_CODE));
        balances.put(USD_CODE, new Currency(new Money(BigDecimal.ZERO), USD_CODE));
    }

    public void addCurrencyToAccount(CurrencyCode code) {
        addCurrencyIfAbsent(code);
    }

    public Money getBalanceByCurrencyCode(CurrencyCode code) {
        addCurrencyIfAbsent(code);
        Currency currency = balances.get(code);
        return currency.amount();
    }

    public void subtractCurrency(Currency currency) {
        CurrencyCode code = currency.code();
        Currency actual = balances.get(code);
        Money actualAmount = actual.amount();
        Money toSubtract = currency.amount();
        validateNewBalance(actualAmount, toSubtract);
        Money subtracted = actualAmount.subtract(toSubtract);
        balances.put(code, new Currency(subtracted, code));
    }

    public void sumCurrencyBalance(Currency currency) {
        CurrencyCode code = currency.code();
        addCurrencyIfAbsent(code);
        Currency actual = balances.get(code);
        Money actualAmount = actual.amount();
        Money updatedBalance = actualAmount.add(currency.amount());
        balances.put(code, new Currency(updatedBalance, code));
    }

    private void addCurrencyIfAbsent(CurrencyCode code) {
        balances.computeIfAbsent(code, c -> {
            Money initialBalance = new Money(BigDecimal.ZERO);
            return new Currency(initialBalance, code);
        });
    }

    private void validateNewBalance(Money actual, Money toSubtract) {
        if (actual.subtract(toSubtract).isLowerThanZero()) {
            throw new IllegalArgumentException("Not enough founds to process this operation.");
        }
    }
}
