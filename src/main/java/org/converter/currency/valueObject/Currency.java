package org.converter.valueObject;

import jakarta.validation.Valid;

public record Currency(
        @Valid
        Money amount,
        @Valid
        CurrencyCode code
) {


}
