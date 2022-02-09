package codurance;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeProviderShould {
    @Test
    void return_current_time_as_a_string() {
        var timeProvider = new TimeProvider();
        var result = timeProvider.now();
        var expected = LocalDate.now().format(DateTimeFormatter.ISO_DATE);
        Assertions.assertEquals(expected, result);
    }
}
