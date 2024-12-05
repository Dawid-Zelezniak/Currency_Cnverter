package org.example.service.strategy;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.service.CurrencyCourseService;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static org.example.util.CurrencyCodes.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class ConversionStrategyFactory {

    private static final Set<String> codes = new HashSet<>();
    private static final String FILE_NAME = "codes.txt";

    private final CurrencyCourseService courseService;

    @PostConstruct
    private void readCodes() {
        try (var br = new BufferedReader(new FileReader(FILE_NAME))) {
            String code;
            int codesCounter = 0;
            while ((code = br.readLine()) != null) {
                codesCounter++;
                codes.add(code);
            }
            log.info("Codes loaded : {}", codesCounter);
        } catch (IOException e) {
            throw new RuntimeException("File " + FILE_NAME + " not found.");
        }
    }

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
