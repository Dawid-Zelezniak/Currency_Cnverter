package org.example.service;

import org.example.model.dto.CurrencyCourseDto;
import org.example.valueObject.CurrencyCode;

public interface CurrencyRatesDownloader {

    CurrencyCourseDto getCurrencyCourse(CurrencyCode code);
}
