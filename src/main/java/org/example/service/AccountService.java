package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.model.Account;
import org.example.model.AccountBalance;
import org.example.model.dto.AccountCreationRequest;
import org.example.model.dto.MoneyConversionRequest;
import org.example.valueObject.CurrencyCode;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService {

    private static final int START_VALUE = 0;

    private final MoneyConversionService conversionService;

   private final Map<Integer, Account> accounts = new HashMap<>();
   private int actualId = START_VALUE;

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

    public List<Account> findAllAccounts() {
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

    public void addNewCurrency(Integer accountId, CurrencyCode code) {
        Account account = accounts.get(accountId);
        AccountBalance balance = account.getBalance();
        balance.addCurrencyToAccount(code);
        accounts.replace(accountId,account);
    }
}
