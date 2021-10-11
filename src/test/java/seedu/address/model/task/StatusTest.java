package seedu.address.model.task;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class StatusTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Status(null));
    }

    @Test
    public void constructor_invalidStatus_throwsIllegalArgumentException() {
        String invalidStatus = "a";
        assertThrows(IllegalArgumentException.class, () -> new Status(invalidStatus));
    }

    @Test
    public void isValidStatus() {
        // null status
        assertThrows(NullPointerException.class, () -> Status.isValidStatus(null));

        // invalid statuses
        assertFalse(Status.isValidStatus("abc"));
        assertFalse(Status.isValidStatus("2"));

        // valid statuses
        assertTrue(Status.isValidStatus("true"));
        assertTrue(Status.isValidStatus("TrUe")); // random upper cases
        assertTrue(Status.isValidStatus("false"));
    }

    @Test
    public void equals() {
        Status completedStatus = new Status("true");
        assertTrue(completedStatus.equals(new Status("true")));
        assertTrue(completedStatus.equals(new Status("TruE"))); // random upper cases

        assertFalse(completedStatus.equals(new Status("false")));
    }
}
