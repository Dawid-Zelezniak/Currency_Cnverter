package org.converter.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.converter.currency.service.NbpTableCRates;
import org.converter.holidays.Holiday;
import org.converter.holidays.HolidaysDownloader;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class NbpRatesScheduler {

    private final NbpTableCRates tableCRates;
    private final HolidaysDownloader holidaysDownloader;

    @Scheduled(cron = "0 20 8 * * MON-FRI", zone = "Europe/Warsaw")
    public void updateTableCRates() {
        log.debug("Scheduler before update table C");
        LocalDate now = LocalDate.now();
        Set<Holiday> holidaysInPoland = holidaysDownloader.getHolidaysInPoland();
        boolean isHolidayToday = holidaysInPoland.stream()
                .anyMatch(holiday -> holiday.date().toLocalDate().isEqual(now));
        if (isHolidayToday) {
            throw new DateTimeException("NBP does not update exchange rates on this day.");
        }
        tableCRates.updateRates();
        log.debug("Table C updated by scheduler");
    }
}
