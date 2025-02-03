package org.converter.currency.service.strategy.rates;

import org.converter.currency.dto.RateDto;
import org.converter.currency.valueObject.CurrencyCode;

public interface RatesDownloader {

    RateDto getCurrencyRate(String tableName, CurrencyCode currencyCode);
}
