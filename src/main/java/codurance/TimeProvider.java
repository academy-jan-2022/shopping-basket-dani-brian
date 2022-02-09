package codurance;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TimeProvider {
    public String now() {
        return LocalDate.now().format(DateTimeFormatter.ISO_DATE);
    }
}
