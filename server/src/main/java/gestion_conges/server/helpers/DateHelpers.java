package gestion_conges.server.helpers;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.stream.Stream;

public class DateHelpers
{
    public static boolean isWeekEnd(LocalDate date)
    {
        return Stream.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY).anyMatch(d -> d == date.getDayOfWeek());
    }
}
