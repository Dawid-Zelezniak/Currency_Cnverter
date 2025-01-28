package org.converter.currency.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record RateDto(
        @JsonProperty("code")
        String code,
        @JsonProperty("bid")
        BigDecimal bid,
        @JsonProperty("ask")
        BigDecimal ask) {

}
