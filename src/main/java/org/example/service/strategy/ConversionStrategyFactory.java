package org.example.service.strategy;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.service.CurrencyCourseService;
import org.example.util.CurrencyCodesReader;
import org.springframework.stereotype.Component;

import java.util.Set;

import static org.example.util.CurrencyCodes.PLN;

@Component
@RequiredArgsConstructor
@Slf4j
public class ConversionStrategyFactory {

    private static final Set<String> codes = CurrencyCodesReader.readCodes();

    private final CurrencyCourseService courseService;


    public ConversionStrategy getStrategy(String base) {
        ConversionStrategy strategy;
        if (PLN.equalsIgnoreCase(base)) {
            strategy = new PlnToXStrategy(courseService);
        } else if (codes.contains(base.toUpperCase())) {
            strategy = new XToPlnStrategy(courseService);
        } else throw new IllegalArgumentException("Unsupported currency " + base);
        return strategy;
    }
}
