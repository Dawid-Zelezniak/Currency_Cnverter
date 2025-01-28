package org.converter.currency.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record CurrencyTableDto(

        @JsonProperty("table")
        String tableName,
        @JsonProperty("rates")
        List<RateDto> rates
) {
}
