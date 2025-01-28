package org.converter.holidays;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Getter
public class HolidaysDownloader {

    @Value("${calendar.base.holidays.request}")
    String holidaysRequest;

    @Value("${calendar.api.key}")
    String apiKey;

    public Set<Holiday> getHolidaysInPoland() {
        int actualYear = LocalDate.now().getYear();
        String url = holidaysRequest + "?api_key=" + apiKey + "&country=pl&year=" + actualYear;

        try {
            CalendarificResponse allHolidays = getAllHolidays(url);
            return fetchNationalHolidays(allHolidays);
        } catch (Exception e) {
            throw new UnsupportedOperationException("Unknown exception while downloading holidays.");
        }
    }

    private CalendarificResponse getAllHolidays(String url) {
        return WebClient.create()
                .method(HttpMethod.GET)
                .uri(url)
                .retrieve()
                .bodyToMono(CalendarificResponse.class)
                .block();
    }

    private Set<Holiday> fetchNationalHolidays(CalendarificResponse allHolidays) {
        Response response = allHolidays.response();
        List<Holiday> holidays = response.holidays();
        return holidays.stream()
                .filter(holiday -> {
                    List<String> type = holiday.type();
                    String holidayType = type.get(0);
                    return holidayType.equalsIgnoreCase("National holiday");
                })
                .sorted(Comparator.comparing(o -> o.date().toLocalDate()))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }
}
