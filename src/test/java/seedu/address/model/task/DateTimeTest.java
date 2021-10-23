package seedu.address.model.task;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class DateTimeTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DateTime(null, null));
    }

    @Test
    public void constructor_invalidDateTime_throwsIllegalArgumentException() {
        String invalidDate = "8 july";
        String invalidTime = "12.12am";
        assertThrows(IllegalArgumentException.class, () -> new DateTime(invalidDate, invalidTime));
    }

    @Test
    public void isValidDateTime() {
        // null date, time
        assertThrows(NullPointerException.class, () -> DateTime.isValidDate(null));
        assertThrows(NullPointerException.class, () -> DateTime.isValidTime(null));

        // invalid dates
        assertFalse(DateTime.isValidDate("bamboo")); // nonsense
        assertFalse(DateTime.isValidDate("8 july")); // wrong format
        assertFalse(DateTime.isValidDate("2010-02-31")); // invalid value

        // invalid times
        assertFalse(DateTime.isValidTime("edward cullen"));
        assertFalse(DateTime.isValidTime("12.12am")); // wrong format
        assertFalse(DateTime.isValidTime("70:99")); // correct format, invalid value

        // valid dates
        assertTrue(DateTime.isValidDate("2021-12-11"));
        assertTrue(DateTime.isValidDate("2000-01-01")); //time in the past

        // valid time
        assertTrue(DateTime.isValidTime("23:00")); // 24 hours time
        assertTrue(DateTime.isValidTime("08:32:30")); // with seconds
    }

    @Test
    public void isAfter() {
        DateTime now = new DateTime("2021-10-31", "10:25");

        // before the DateTime to test against -> returns true
        DateTime beforeDate = new DateTime("2021-09-15", "13:00"); // different date
        DateTime beforeTime = new DateTime("2021-10-31", "09:40"); // different time
        assertTrue(now.isAfter(beforeDate));
        assertTrue(now.isAfter(beforeTime));

        // after the DateTime to test against -> returns false
        DateTime afterDate = new DateTime("2021-11-19", "13:20"); // different date
        DateTime afterTime = new DateTime("2021-10-31", "18:25"); // different time
        assertFalse(now.isAfter(afterDate));
        assertFalse(now.isAfter(afterTime));
    }

    @Test
    public void isBefore() {
        DateTime now = new DateTime("2021-10-31", "10:25");

        // before the DateTime to test against -> returns false
        DateTime beforeDate = new DateTime("2021-09-15", "13:00"); // different date
        DateTime beforeTime = new DateTime("2021-10-31", "09:40"); // different time
        assertFalse(now.isBefore(beforeDate));
        assertFalse(now.isBefore(beforeTime));

        // after the DateTime to test against -> returns true
        DateTime afterDate = new DateTime("2021-11-19", "13:20"); // different date
        DateTime afterTime = new DateTime("2021-10-31", "18:25"); // different time
        assertTrue(now.isBefore(afterDate));
        assertTrue(now.isBefore(afterTime));
    }

    @Test
    public void isOverdue() {
        DateTime before = new DateTime("2021-10-20", "12:30");
        DateTime after = new DateTime("2025-10-01", "18:45");

        // day over -> returns true
        assertTrue(DateTime.isOverdue(before));

        // day in the future -> returns false
        assertFalse(DateTime.isOverdue(after));
    }

    @Test
    public void compareTo() {
        DateTime dt0 = new DateTime("2021-12-25", "12:30"); // main DateTest item
        DateTime dt1 = new DateTime("2021-12-31", "23:59"); // compare with dt0 by time
        DateTime dt2 = new DateTime("2021-12-25", "12:00"); // compare with dt1 by date

        // dt0 is before dt1 -> returns negative value
        assertTrue(dt0.compareTo(dt1) < 0);

        // dt0 is after dt2 -> returns positive value
        assertTrue(dt0.compareTo(dt2) > 0);

        // happens at the same time -> returns 0
        assertFalse(dt0.compareTo(dt0) == -1);
    }
}
