package org.converter.currency.valueObject;

import jakarta.validation.Valid;
import org.converter.util.Money;

public record Currency(
        @Valid
        Money amount,
        @Valid
        CurrencyCode code
) {


}
