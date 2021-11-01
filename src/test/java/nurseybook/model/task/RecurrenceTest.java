package nurseybook.model.task;

import static nurseybook.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class RecurrenceTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Recurrence(null));
    }

    @Test
    public void constructor_invalidRecurrence_throwsIllegalArgumentException() {
        String invalidStatus = "a";
        assertThrows(IllegalArgumentException.class, () -> new Recurrence(invalidStatus));
    }

    @Test
    public void isValidRecurrence() {
        // null status
        assertThrows(NullPointerException.class, () -> Recurrence.isValidRecurrence(null));

        // invalid statuses
        assertFalse(Recurrence.isValidRecurrence("d1y")); // non-matching words
        assertFalse(Recurrence.isValidRecurrence("days")); // extra letters
        assertFalse(Recurrence.isValidRecurrence("  d a ys  ")); // extra spaces

        // valid statuses
        assertTrue(Recurrence.isValidRecurrence("NONE"));
        assertTrue(Recurrence.isValidRecurrence("MONTH"));
    }

    @Test
    public void equals() {
        Recurrence completedRecurrence = new Recurrence("DAY");
        assertTrue(completedRecurrence.equals(new Recurrence("day"))); // all lower case
        assertTrue(completedRecurrence.equals(new Recurrence("dAy"))); // mix of lower and upper cases

        assertFalse(completedRecurrence.equals(new Recurrence("WEEK")));
    }
}
