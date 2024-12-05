package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.model.Account;
import org.example.model.AccountBalance;
import org.example.model.dto.AccountCreationRequest;
import org.example.model.dto.MoneyConversionRequest;
import org.example.valueObject.Currency;
import org.example.valueObject.Money;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import static org.example.util.CurrencyCodes.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService {

    private static final int START_VALUE = 0;

    private final MoneyConversionService conversionService;

    Map<Integer, Account> accounts = new LinkedHashMap<>();
    int actualId = START_VALUE;

    public Account createUserAccount(AccountCreationRequest request) {
        Account account = Account.builder()
                .id(++actualId)
                .firstName(request.firstName())
                .lastName(request.lastName())
                .balance(new AccountBalance(request.startBalance()))
                .build();
        accounts.put(actualId, account);
        return account;
    }

    public Account findAccountById(Integer id) {
        Account account = accounts.get(id);
        if (account == null) {
            throw new NoSuchElementException("Account with id: " + id + " not found.");
        }
        return account;
    }

    public List<Account> getAllAccounts() {
        return (List<Account>) accounts.values();
    }

    public AccountBalance convert(MoneyConversionRequest request) {
        Account account = accounts.get(request.accountId());
        AccountBalance balance = account.getBalance();
        AccountBalance accountBalance = conversionService.convertMoney(balance, request);
        account.setBalance(accountBalance);
        accounts.replace(request.accountId(), account);
        return accountBalance;
    }
}
