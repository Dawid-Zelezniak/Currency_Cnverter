package org.example.service.strategy;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.service.RatesDownloader;
import org.example.util.CurrencyCodesLoader;
import org.springframework.stereotype.Component;

import java.util.Set;

import static org.example.util.CurrencyCodes.PLN;

@Component
@RequiredArgsConstructor
@Slf4j
public class ConversionStrategyProvider {

    private static final Set<String> codes = CurrencyCodesLoader.readCodes();

    private final RatesDownloader ratesDownloader;

    public ConversionStrategy getStrategy(String base) {
        ConversionStrategy strategy;
        if (PLN.equalsIgnoreCase(base)) {
            strategy = new PlnToXStrategy(ratesDownloader);
        } else if (codes.contains(base.toUpperCase())) {
            strategy = new XToPlnStrategy(ratesDownloader);
        } else throw new IllegalArgumentException("Unsupported currency " + base);
        return strategy;
    }
}
