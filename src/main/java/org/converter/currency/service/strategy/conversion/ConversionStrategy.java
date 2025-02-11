package org.converter.currency.service.strategy.conversion;

import org.converter.currency.dto.MoneyConversionRequest;
import org.converter.currency.valueObject.Currency;

public interface ConversionStrategy {

    Currency convert(MoneyConversionRequest request);
}
