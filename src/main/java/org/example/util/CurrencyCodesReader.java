package org.example.util;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public class CurrencyCodesReader {

    private static final String FILE_NAME = "codes.txt";

    private CurrencyCodesReader(){}

    @PostConstruct
    public static Set<String> readCodes() {
        Set<String> codes = new HashSet<>();

        try (var br = new BufferedReader(new FileReader(FILE_NAME))) {
            String code;
            int codesCounter = 0;
            while ((code = br.readLine()) != null) {
                codesCounter++;
                codes.add(code);
            }
            log.info("Codes loaded : {}", codesCounter);
        } catch (IOException e) {
            throw new IllegalArgumentException("File " + FILE_NAME + " not found.");
        }
        return codes;
    }
}
