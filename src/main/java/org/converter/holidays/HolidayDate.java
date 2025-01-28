package org.converter.holidays;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public record HolidayDate(
        @JsonProperty("iso")
        String iso
) {
    public LocalDate toLocalDate() {
        return LocalDate.parse(iso);
    }
}
