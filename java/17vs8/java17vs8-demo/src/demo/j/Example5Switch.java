package demo.j;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class Example5Switch {
    public static void main(String[] args) {
        DayOfWeek dayOfWeek = LocalDate.now().getDayOfWeek();
        boolean freeDay = switch (dayOfWeek) {
            case MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY -> false;
            case SATURDAY, SUNDAY -> true;
        };

        System.out.println(freeDay);
    }
}
