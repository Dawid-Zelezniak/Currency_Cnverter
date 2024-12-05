package org.example.model;

import jakarta.validation.Valid;
import lombok.*;
import org.example.valueObject.Currency;
import org.example.valueObject.CurrencyCode;
import org.example.valueObject.Money;

import java.math.BigDecimal;

import static org.example.util.CurrencyCodes.*;

@Getter
@NoArgsConstructor
@ToString
public class AccountBalance {

    //dodać mapę w której podstawowowymi walutami będzie pln i usd a jak kolejnej waluty nie będzie w tej mapie to zostanie dodana

    @Valid
    private Currency plnBalance;
    @Valid
    private Currency usdBalance = new Currency(new Money(BigDecimal.ZERO), USD_CODE);
    @Valid
    private Currency chfBalance = new Currency(new Money(BigDecimal.ZERO), CHF_CODE);

    public AccountBalance(Money money){
        this.plnBalance = new Currency(money,PLN_CODE);
    }

    public Money getBalanceByCurrencyCode(CurrencyCode code) {
        Money balance = null;
        String currencyCode = code.getCode();
        switch (currencyCode) {
            case PLN -> balance = plnBalance.amount();
            case USD -> balance = usdBalance.amount();
            case CHF -> balance = chfBalance.amount();
            default -> throwException(currencyCode);
        }
        return balance;
    }

    public void subtractCurrency(Currency c) {
        CurrencyCode code = c.code();
        Money amount = c.amount();
        String currencyCode = code.getCode();

        switch (currencyCode) {
            case PLN -> plnBalance = new Currency(plnBalance.amount().subtract(amount), PLN_CODE);
            case USD -> usdBalance = new Currency(usdBalance.amount().subtract(amount), USD_CODE);
            case CHF -> chfBalance = new Currency(chfBalance.amount().subtract(amount), CHF_CODE);
            default -> throwException(currencyCode);
        }
    }

    public void addCurrency(Currency c) {
        CurrencyCode code = c.code();
        Money amount = c.amount();
        String currencyCode = code.getCode();

        switch (currencyCode) {
            case PLN -> plnBalance = new Currency(plnBalance.amount().add(amount), PLN_CODE);
            case USD -> usdBalance = new Currency(usdBalance.amount().add(amount), USD_CODE);
            case CHF -> chfBalance = new Currency(chfBalance.amount().add(amount), CHF_CODE);
            default -> throwException(currencyCode);
        }
    }

    private void throwException(String code) {
        throw new IllegalArgumentException("Unsupported currency " + code);
    }
}
