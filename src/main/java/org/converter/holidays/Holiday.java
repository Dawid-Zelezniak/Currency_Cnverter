package org.converter.holidays;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record Holiday(
        @JsonProperty("date")
        HolidayDate date,
        @JsonProperty("type")
        List<String> type
) { }
