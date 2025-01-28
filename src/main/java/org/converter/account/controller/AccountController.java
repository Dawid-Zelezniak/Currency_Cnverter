package org.example.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.model.Account;
import org.example.model.AccountBalance;
import org.example.model.dto.AccountCreationRequest;
import org.example.model.dto.MoneyConversionRequest;
import org.example.service.AccountService;
import org.example.valueObject.CurrencyCode;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService service;

    @PostMapping
    public Account createAccount(@RequestBody @Valid AccountCreationRequest request) {
        return service.createUserAccount(request);
    }

    @GetMapping
    public List<Account> getAccounts() {
        return service.findAllAccounts();
    }

    @GetMapping("/{id}")
    public Account findById(@PathVariable Integer id) {
        return service.findAccountById(id);
    }

    @PutMapping("/{accountId}/")
    public void addCurrency(@PathVariable Integer accountId,@RequestParam @Valid CurrencyCode code){
        service.addNewCurrency(accountId,code);
    }

    @PostMapping("/money/convert")
    public AccountBalance convert(@RequestBody @Valid MoneyConversionRequest request) {
        return service.convert(request);
    }
}
