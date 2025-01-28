package org.converter.holidays;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record Response(
        @JsonProperty("holidays")
        List<Holiday> holidays
) { }
