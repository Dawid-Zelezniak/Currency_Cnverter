package org.example.valueObject;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class CurrencyCode {

    /**
     * PLN,USD,EUR etc...
     */
    private static final int ISO_4217_STANDARD = 3;

    private String code;

    public CurrencyCode(String code) {
        validLength(code);
        this.code = code.toLowerCase();
    }

    private void validLength(String code) {
        if (code.length() != ISO_4217_STANDARD) {
            throw new IllegalArgumentException("Incorrect currency code.");
        }
    }
}