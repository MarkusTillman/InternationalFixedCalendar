package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.example.Month.*;

class PerennialDateTest {

    @Test
    void asJavaDate() {
        var january1st2000 = LocalDate.parse("2000-01-01");
        assertThat(new PerennialDate(january1st2000).asLocalDate()).isEqualTo(january1st2000);
    }

    @Test
    void year2000IsLeapYear() {
        var january1st2000 = LocalDate.parse("2000-01-01");
        assertThat(new PerennialDate(january1st2000).isLeapYear()).isEqualTo(true);
    }

    @Test
    void year2004IsLeapYear() {
        var january1st2004 = LocalDate.parse("2004-01-01");
        assertThat(new PerennialDate(january1st2004).isLeapYear()).isEqualTo(true);
    }

    @Test
    void year2100IsNotLeapYear() {
        var january1st2000 = LocalDate.parse("2100-01-01");
        assertThat(new PerennialDate(january1st2000).isLeapYear()).isEqualTo(false);
    }

    @Test
    void gregorianJune17th2000IsLeapDay() {
        var june12th2000 = LocalDate.parse("2000-06-17");
        assertThat(new PerennialDate(june12th2000).isLeapDay()).isEqualTo(true);
    }

    @Test
    void gregorianJune17th2001IsNotLeapDay() {
        var june12th2001 = LocalDate.parse("2001-06-17");
        assertThat(new PerennialDate(june12th2001).isLeapDay()).isEqualTo(false);
    }

    @Test
    void leapDayToString() {
        var june12th2000 = LocalDate.parse("2000-06-17");
        assertThat(new PerennialDate(june12th2000).toString()).isEqualTo("Leap day (june '29th')");
    }

    @Test
    void yearDay() {
        var gregorianNewYearsEve = LocalDate.parse("2000-12-31");
        assertThat(new PerennialDate(gregorianNewYearsEve).isYearDay()).isEqualTo(true);
    }

    @Test
    void yearDayToString() {
        var gregorianNewYearsEve = LocalDate.parse("2000-12-31");
        assertThat(new PerennialDate(gregorianNewYearsEve).toString()).isEqualTo("Year day (december '29th')");
    }

    @Test
    void leapDayToHumanReadableString() {
        var june12th2000 = LocalDate.parse("2000-06-17");
        assertThat(new PerennialDate(june12th2000).toHumanReadable()).isEqualTo("Leap day (june '29th')");
    }

    @Test
    void yearDayToHumanReadableString() {
        var gregorianNewYearsEve = LocalDate.parse("2000-12-31");
        assertThat(new PerennialDate(gregorianNewYearsEve).toHumanReadable()).isEqualTo("Year day (december '29th')");
    }

    @Test
    void gregorianDateToPerennialDate() {
        var january1st2000 = LocalDate.parse("2000-01-01");
        assertThat(new PerennialDate(january1st2000).toString()).isEqualTo("2000-01-01");
    }

    @ParameterizedTest
    @MethodSource("monthTestCases")
    void perennialDateToString(LocalDate gregorianStartDate, String expectedMonth, int nrOfDays) {
        var perennialDate = new PerennialDate(gregorianStartDate).addDays(nrOfDays);
        var expectedDay = nrOfDays + 1 < 10 ? "0" + (nrOfDays + 1) : nrOfDays + 1;
        if (!perennialDate.isLeapDay()) {
            assertThat(perennialDate.toString()).isEqualTo("2000-" + expectedMonth + "-" + expectedDay);
        }
    }

    @ParameterizedTest
    @MethodSource("monthTestCasesHumanReadable")
    void perennialDateToHumanReadableString(LocalDate gregorianStartDate, Month expectedMonth, int nrOfDays) {
        var perennialDate = new PerennialDate(gregorianStartDate).addDays(nrOfDays);
        var expectedDay = nrOfDays + 1 < 10 ? "0" + (nrOfDays + 1) : nrOfDays + 1;
        if (!perennialDate.isLeapDay()) {
            assertThat(perennialDate.toHumanReadable()).isEqualTo("2000-" + expectedMonth + "-" + expectedDay);
        }
    }

    private static Stream<Arguments> monthTestCases() {
        return Stream.of(
                IntStream.range(0, 27).mapToObj(dayOfMonth -> Arguments.of(LocalDate.parse("2000-01-01"), "01", dayOfMonth)),
                IntStream.range(0, 27).mapToObj(dayOfMonth -> Arguments.of(LocalDate.parse("2000-01-29"), "02", dayOfMonth)),
                IntStream.range(0, 27).mapToObj(dayOfMonth -> Arguments.of(LocalDate.parse("2000-02-26"), "03", dayOfMonth)),
                IntStream.range(0, 27).mapToObj(dayOfMonth -> Arguments.of(LocalDate.parse("2000-03-25"), "04", dayOfMonth)),
                IntStream.range(0, 27).mapToObj(dayOfMonth -> Arguments.of(LocalDate.parse("2000-04-22"), "05", dayOfMonth)),
                IntStream.range(0, 27).mapToObj(dayOfMonth -> Arguments.of(LocalDate.parse("2000-05-20"), "06", dayOfMonth)),
                IntStream.range(0, 27).mapToObj(dayOfMonth -> Arguments.of(LocalDate.parse("2000-06-17"), "07", dayOfMonth)),
                IntStream.range(0, 27).mapToObj(dayOfMonth -> Arguments.of(LocalDate.parse("2000-07-15"), "08", dayOfMonth)),
                IntStream.range(0, 27).mapToObj(dayOfMonth -> Arguments.of(LocalDate.parse("2000-08-12"), "09", dayOfMonth)),
                IntStream.range(0, 27).mapToObj(dayOfMonth -> Arguments.of(LocalDate.parse("2000-09-09"), "10", dayOfMonth)),
                IntStream.range(0, 27).mapToObj(dayOfMonth -> Arguments.of(LocalDate.parse("2000-10-07"), "11", dayOfMonth)),
                IntStream.range(0, 27).mapToObj(dayOfMonth -> Arguments.of(LocalDate.parse("2000-11-04"), "12", dayOfMonth)),
                IntStream.range(0, 27).mapToObj(dayOfMonth -> Arguments.of(LocalDate.parse("2000-12-02"), "13", dayOfMonth))
        ).flatMap(stream -> stream);
    }

    private static Stream<Arguments> monthTestCasesHumanReadable() {
        return Stream.of(
                IntStream.range(0, 27).mapToObj(dayOfMonth -> Arguments.of(LocalDate.parse("2000-01-01"), JANUARY, dayOfMonth)),
                IntStream.range(0, 27).mapToObj(dayOfMonth -> Arguments.of(LocalDate.parse("2000-01-29"), FEBRUARY, dayOfMonth)),
                IntStream.range(0, 27).mapToObj(dayOfMonth -> Arguments.of(LocalDate.parse("2000-02-26"), MARCH, dayOfMonth)),
                IntStream.range(0, 27).mapToObj(dayOfMonth -> Arguments.of(LocalDate.parse("2000-03-25"), APRIL, dayOfMonth)),
                IntStream.range(0, 27).mapToObj(dayOfMonth -> Arguments.of(LocalDate.parse("2000-04-22"), MAY, dayOfMonth)),
                IntStream.range(0, 27).mapToObj(dayOfMonth -> Arguments.of(LocalDate.parse("2000-05-20"), JUNE, dayOfMonth)),
                IntStream.range(0, 27).mapToObj(dayOfMonth -> Arguments.of(LocalDate.parse("2000-06-17"), SOL, dayOfMonth)),
                IntStream.range(0, 27).mapToObj(dayOfMonth -> Arguments.of(LocalDate.parse("2000-07-15"), JULY, dayOfMonth)),
                IntStream.range(0, 27).mapToObj(dayOfMonth -> Arguments.of(LocalDate.parse("2000-08-12"), AUGUST, dayOfMonth)),
                IntStream.range(0, 27).mapToObj(dayOfMonth -> Arguments.of(LocalDate.parse("2000-09-09"), SEPTEMBER, dayOfMonth)),
                IntStream.range(0, 27).mapToObj(dayOfMonth -> Arguments.of(LocalDate.parse("2000-10-07"), OCTOBER, dayOfMonth)),
                IntStream.range(0, 27).mapToObj(dayOfMonth -> Arguments.of(LocalDate.parse("2000-11-04"), NOVEMBER, dayOfMonth)),
                IntStream.range(0, 27).mapToObj(dayOfMonth -> Arguments.of(LocalDate.parse("2000-12-02"), DECEMBER, dayOfMonth))
        ).flatMap(stream -> stream);
    }
}