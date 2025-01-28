package org.example.service.strategy;

import org.example.model.dto.MoneyConversionRequest;
import org.example.valueObject.Currency;

public interface ConversionStrategy {

    Currency convert(MoneyConversionRequest request);
}
