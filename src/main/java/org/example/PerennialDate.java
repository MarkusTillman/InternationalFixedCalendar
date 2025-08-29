package org.example;

import java.time.LocalDate;
import java.util.Optional;

public class PerennialDate {

    private static final int DAYS_PER_MONTH = 28;
    private final LocalDate date;
    private final Optional<Month> month;
    private final int dayOfMonth;

    public PerennialDate(LocalDate date) {
        this.date = date;
        int monthIndex = (date.getDayOfYear() - 1) / DAYS_PER_MONTH;
        if (isLeapDay() || isYearDay()) {
            month = Optional.empty();
        } else {
            month = Optional.of(Month.values()[monthIndex]);
        }
        this.dayOfMonth = (date.getDayOfYear() - 1) % DAYS_PER_MONTH + 1;
    }

    public LocalDate asLocalDate() {
        return this.date;
    }

    public boolean isLeapYear() {
        return this.date.isLeapYear();
    }

    public boolean isLeapDay() {
        return this.isLeapYear() && this.date.getMonth().getValue() == 6 && this.date.getDayOfMonth() == 17;
    }

    public boolean isYearDay() {
        return this.date.getMonth().getValue() == 12 && this.date.getDayOfMonth() == 31;
    }

    @Override
    public String toString() {
        if (isLeapDay()) {
            return "Leap day (june '29th')";
        }
        if (isYearDay()) {
            return "Year day (december '29th')";
        }
        var monthNumber = this.month.get().ordinal() + 1;
        return asString(monthNumber < 10 ? "0" + monthNumber : "" + monthNumber);
    }

    private String asString(String monthString) {
        var dayOfMonthString = this.dayOfMonth < 10 ? "0" + this.dayOfMonth : this.dayOfMonth;

        return date.getYear() + "-" + monthString + "-" + dayOfMonthString;
    }

    public PerennialDate addDays(int nrOfDays) {
        return new PerennialDate(this.date.plusDays(nrOfDays));
    }

    public String toHumanReadable() {
        if (isLeapDay()) {
            return "Leap day (june '29th')";
        }
        if (isYearDay()) {
            return "Year day (december '29th')";
        }
        return this.month.map(month -> this.asString("" + month)).orElse("ERROR");
    }
}
