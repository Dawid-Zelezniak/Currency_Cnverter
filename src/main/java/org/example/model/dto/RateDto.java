package org.example.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record RateDto(
        @JsonProperty("bid")
        BigDecimal bid,
        @JsonProperty("ask")
        BigDecimal ask
) {}
