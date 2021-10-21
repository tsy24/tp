package seedu.address.model.task;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

/**
 * Represents a task's date and time in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)} and {@link #isValidTime(String)}
 */
public class DateTime implements Comparable<DateTime> {

    public static final String MESSAGE_DATE_CONSTRAINTS = "Date must be in the form yyyy-mm-dd";
    public static final String MESSAGE_TIME_CONSTRAINTS = "Time must be in the form HH:mm";

    public final LocalDate date;
    public final LocalTime time;

    /**
     * Constructs an {@code DateTime}.
     *
     * @param date A valid date.
     * @param time A valid time.
     */
    public DateTime(String date, String time) {
        requireAllNonNull(date, time);
        try {
            this.date = LocalDate.parse(date);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(MESSAGE_DATE_CONSTRAINTS);
        }

        try {
            this.time = LocalTime.parse(time);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(MESSAGE_TIME_CONSTRAINTS);
        }
    }

    /**
     * Constructs an {@code DateTime}.
     *
     * @param date A valid date.
     * @param time A valid time.
     */
    public DateTime(LocalDate date, LocalTime time) {
        this.date = date;
        this.time = time;
    }

    /**
     * Returns true if a given string is a valid date.
     */
    public static boolean isValidDate(String test) {
        requireNonNull(test);
        try {
            LocalDate.parse(test);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Returns true if a given string is a valid time.
     */
    public static boolean isValidTime(String test) {
        requireNonNull(test);
        try {
            LocalTime.parse(test);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Returns true if this instance is before the given date and time.
     */
    public boolean isBefore(DateTime dt) {
        return this.compareTo(dt) < 0;
    }

    /**
     * Returns true if this instance is after the given date and time.
     */
    public boolean isAfter(DateTime dt) {
        return this.compareTo(dt) > 0;
    }

    @Override
    public String toString() {
        return date.format(DateTimeFormatter.ISO_LOCAL_DATE)
                + " " + time.format(DateTimeFormatter.ISO_LOCAL_TIME);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DateTime // instanceof handles nulls
                && date.equals(((DateTime) other).date))
                && time.equals(((DateTime) other).time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, time);
    }

    /**
     * Returns {@code date} in ISO_LOCAL_DATE format
     */
    public String getStringDate() {
        return date.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    /**
     * Returns {@code time} in ISO_LOCAL_TIME format
     */
    public String getStringTime() {
        return time.format(DateTimeFormatter.ISO_LOCAL_TIME);
    }

    @Override
    public int compareTo(DateTime o) {
        int dateComparison = this.date.compareTo(o.date);

        if (dateComparison == 0) {
            return this.time.compareTo(o.time);
        }

        return dateComparison;
    }
}
