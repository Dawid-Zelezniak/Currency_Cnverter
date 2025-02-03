import org.converter.currency.service.strategy.rates.NbpTableCRates;
import org.converter.holidays.Holiday;
import org.converter.holidays.HolidayDate;
import org.converter.holidays.HolidaysDownloader;
import org.converter.scheduler.NbpRatesScheduler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith({MockitoExtension.class})
class NbpRatesSchedulerTest {

    @Mock
    private NbpTableCRates tableCRates;
    @Mock
    private HolidaysDownloader holidaysDownloader;
    @InjectMocks
    private NbpRatesScheduler scheduler;

    @Test
    void shouldUpdateRatesWhenNotHoliday() {
        when(holidaysDownloader.getHolidaysInPoland()).thenReturn(Set.of());

        scheduler.updateTableCRates();

        verify(tableCRates).updateRates();
    }

    @Test
    void shouldThrowExceptionWhenHoliday() {
        Holiday nationalHoliday = new Holiday(new HolidayDate(LocalDate.now().toString()), List.of("National holiday"));
        when(holidaysDownloader.getHolidaysInPoland()).thenReturn(Set.of(nationalHoliday));

        assertThrows(DateTimeException.class, () -> scheduler.updateTableCRates());
        verifyNoInteractions(tableCRates);
    }
}
