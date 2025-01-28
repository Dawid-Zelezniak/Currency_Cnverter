package org.converter.currency.valueObject;

public record CurrencyCode(String code) {

    /**
     * PLN,USD,EUR etc...
     */
    private static final int ISO_4217_STANDARD = 3;

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