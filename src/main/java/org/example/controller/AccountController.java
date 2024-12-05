package org.example.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.model.Account;
import org.example.model.AccountBalance;
import org.example.model.dto.AccountCreationRequest;
import org.example.model.dto.MoneyConversionRequest;
import org.example.service.AccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService service;

    @PostMapping("/create")
    public Account createAccount(@RequestBody @Valid AccountCreationRequest request) {
        return service.createUserAccount(request);
    }

    @GetMapping("/{id}")
    public Account findById(@PathVariable Integer id) {
        return service.findAccountById(id);
    }

    @GetMapping("/")
    public List<Account> getAccounts() {
        return service.findAllAccounts();
    }

    @PostMapping("/money/convert")
    public AccountBalance convert(@RequestBody @Valid MoneyConversionRequest request) {
        return service.convert(request);
    }
}
