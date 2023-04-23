package chapter12;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;

public class SourceCode121 {
    public static void main(String[] args) {
        //날짜와 시간 조합
        localdateTimeExample();
        //instant
        instantExample();

        //DateTimeFormatter
        dateTimeFormatterExample();

    }

    private static void localdateTimeExample() {
        LocalDate date = LocalDate.of(2014, 3, 18);
        LocalTime time = LocalTime.of(13, 45, 20); // 13:45:20

        LocalDateTime dt1 = LocalDateTime.of(2014, Month.MARCH, 18, 13, 45, 20); // 2014-03-18T13:45
        LocalDateTime dt2 = LocalDateTime.of(date, time);
        LocalDateTime dt3 = date.atTime(13, 45, 20);
        LocalDateTime dt4 = date.atTime(time);
        LocalDateTime dt5 = time.atDate(date);
    }

    private static void instantExample() {
        Instant.ofEpochSecond(3);
        Instant.ofEpochSecond(3,0);
        Instant.ofEpochSecond(2,1_000_000_000); // 2초 이후의 1억 나노초(1초)
        Instant.ofEpochSecond(4,-1_000_000_000); //4초 이전의 1억 나노초(1초)

//        int day = Instant.now().get(ChronoField.DAY_OF_MONTH);
//         위코드는 예외를 발생시킨다. Instant는 사람이 읽을 수 있는 시간 정보를 제공하지않는다.
    }

    private static void dateTimeFormatterExample() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate localDate = LocalDate.of(2014, 3, 18);
        String formattedDate = localDate.format(formatter);
        LocalDate parse1 = LocalDate.parse(formattedDate, formatter);
    }
}
