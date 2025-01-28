import org.converter.account.model.Account;
import org.converter.account.model.AccountBalance;
import org.converter.currency.dto.MoneyConversionRequest;
import org.converter.currency.dto.RateDto;
import org.converter.currency.service.MoneyConversionService;
import org.converter.currency.service.RatesDownloader;
import org.converter.currency.service.strategy.ConversionStrategyProvider;
import org.converter.currency.service.strategy.PlnToXStrategy;
import org.converter.currency.service.strategy.XToPlnStrategy;
import org.converter.currency.service.validation.CurrencyConversionValidator;
import org.converter.currency.valueObject.Currency;
import org.converter.currency.valueObject.CurrencyCode;
import org.converter.util.Money;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.converter.util.CurrencyCodes.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MoneyConversionServiceTest {

    private static RateDto usdRates;
    private static RateDto chfRates;

    @Mock
    private CurrencyConversionValidator validator;
    @Mock
    private ConversionStrategyProvider strategyFactory;
    @Mock
    private RatesDownloader ratesDownloader;
    @InjectMocks
    private MoneyConversionService conversionService;

    private Account testAccount;
    private Map<CurrencyCode, Currency> balances;

    @BeforeAll
    public static void initUsdRates() {
        usdRates = new RateDto("usd", createMoney(4.3).getValue(), createMoney(4.4).getValue());
        chfRates = new RateDto("chf", createMoney(4.9).getValue(), createMoney(5.0).getValue());
    }

    @BeforeEach
    public void initTestData() {
        testAccount = createTestAccount();
        balances = new HashMap<>();
    }

    @Test
    void shouldConvertPlnToUsd() {
        MoneyConversionRequest conversionRequest = createConversionRequest(testAccount.getId(), PLN_CODE, USD_CODE, 50);

        when(ratesDownloader.getCurrencyCourse("tableC", USD_CODE)).thenReturn(usdRates);
        when(strategyFactory.getStrategy(PLN_CODE.code())).thenReturn(new PlnToXStrategy(ratesDownloader));

        AccountBalance initialBalance = testAccount.getBalance();
        AccountBalance expectedBalance = setExpectedPlnAndUsdBalance(50, 11.36);

        AccountBalance actualBalance = conversionService.convertMoney(initialBalance, conversionRequest);
        verify(validator).validateBalance(initialBalance, conversionRequest);

        Map<CurrencyCode, Currency> expectedBalances = expectedBalance.getBalances();
        Map<CurrencyCode, Currency> actualBalances = actualBalance.getBalances();

        assertEquals(getMoneyAsDoubleFromCurrency(expectedBalances.get(PLN_CODE)), getMoneyAsDoubleFromCurrency(actualBalances.get(PLN_CODE)));
        assertEquals(getMoneyAsDoubleFromCurrency(expectedBalances.get(USD_CODE)), getMoneyAsDoubleFromCurrency(actualBalances.get(USD_CODE)));
    }

    @Test
    void shouldConvertUsdToPln() {
        AccountBalance balance = testAccount.getBalance();
        Map<CurrencyCode, Currency> currencyMap = balance.getBalances();
        currencyMap.put(USD_CODE, new Currency(createMoney(25), USD_CODE));

        MoneyConversionRequest conversionRequest = createConversionRequest(testAccount.getId(), USD_CODE, PLN_CODE, 15);

        when(ratesDownloader.getCurrencyCourse("tableC", USD_CODE)).thenReturn(usdRates);
        when(strategyFactory.getStrategy(USD_CODE.code())).thenReturn(new XToPlnStrategy(ratesDownloader));

        AccountBalance initialBalance = testAccount.getBalance();
        AccountBalance expectedBalance = setExpectedPlnAndUsdBalance(164.5, 10);

        AccountBalance actualBalance = conversionService.convertMoney(initialBalance, conversionRequest);
        verify(validator).validateBalance(initialBalance, conversionRequest);

        Map<CurrencyCode, Currency> expectedBalances = expectedBalance.getBalances();
        Map<CurrencyCode, Currency> actualBalances = actualBalance.getBalances();

        assertEquals(getMoneyAsDoubleFromCurrency(expectedBalances.get(PLN_CODE)), getMoneyAsDoubleFromCurrency(actualBalances.get(PLN_CODE)));
        assertEquals(getMoneyAsDoubleFromCurrency(expectedBalances.get(USD_CODE)), getMoneyAsDoubleFromCurrency(actualBalances.get(USD_CODE)));
    }

    @Test
    void shouldConvertPlnToChf() {
        MoneyConversionRequest conversionRequest = createConversionRequest(testAccount.getId(), PLN_CODE, CHF_CODE, 50);

        when(ratesDownloader.getCurrencyCourse("tableC", CHF_CODE)).thenReturn(chfRates);
        when(strategyFactory.getStrategy(PLN_CODE.code())).thenReturn(new PlnToXStrategy(ratesDownloader));

        AccountBalance initialBalance = testAccount.getBalance();
        AccountBalance expectedBalance = setExpectedPlnAndChfBalance(50, 10);

        AccountBalance actualBalance = conversionService.convertMoney(initialBalance, conversionRequest);
        verify(validator).validateBalance(initialBalance, conversionRequest);

        Map<CurrencyCode, Currency> expectedBalances = expectedBalance.getBalances();
        Map<CurrencyCode, Currency> actualBalances = actualBalance.getBalances();

        assertEquals(getMoneyAsDoubleFromCurrency(expectedBalances.get(PLN_CODE)), getMoneyAsDoubleFromCurrency(actualBalances.get(PLN_CODE)));
        assertEquals(getMoneyAsDoubleFromCurrency(expectedBalances.get(CHF_CODE)), getMoneyAsDoubleFromCurrency(actualBalances.get(CHF_CODE)));
    }

    private static Money createMoney(double value) {
        return new Money(BigDecimal.valueOf(value));
    }

    private Account createTestAccount() {
        return Account.builder()
                .id(1)
                .firstName("John")
                .lastName("Doe")
                .balance(new AccountBalance(createMoney(100)))
                .build();
    }

    private AccountBalance setExpectedPlnAndUsdBalance(double pln, double usd) {
        balances.put(PLN_CODE, new Currency(createMoney(pln), PLN_CODE));
        balances.put(USD_CODE, new Currency(createMoney(usd), USD_CODE));

        return AccountBalance.builder()
                .balances(balances)
                .build();
    }

    private AccountBalance setExpectedPlnAndChfBalance(double pln, double chf) {
        balances.put(PLN_CODE, new Currency(createMoney(pln), PLN_CODE));
        balances.put(CHF_CODE, new Currency(createMoney(chf), USD_CODE));

        return AccountBalance.builder()
                .balances(balances)
                .build();
    }

    private Double getMoneyAsDoubleFromCurrency(Currency currency) {
        Money amount = currency.amount();
        return amount.getDoubleValue();
    }

    private MoneyConversionRequest createConversionRequest(Integer id, CurrencyCode baseCurrency, CurrencyCode targetCurrency, int toConvert) {
        return new MoneyConversionRequest(id, "tableC",
                new Currency(createMoney(toConvert), baseCurrency), targetCurrency);
    }
}
