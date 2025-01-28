package org.converter.currency.service;

import org.converter.currency.dto.RateDto;
import org.converter.currency.valueObject.CurrencyCode;

public interface RatesDownloader {

    RateDto getCurrencyCourse(String tableName, CurrencyCode currencyCode);
}
