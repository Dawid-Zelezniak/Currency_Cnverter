package org.converter.holidays;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CalendarificResponse(
        @JsonProperty("response")
        Response response
) {
}

