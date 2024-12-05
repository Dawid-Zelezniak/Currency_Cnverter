package org.example.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record CurrencyCourseDto(
        @JsonProperty("currency")
        String currency,
        @JsonProperty("rates")
        List<RateDto> rates
) {

    public RateDto getRate() {
        return rates.get(0);
    }
}
