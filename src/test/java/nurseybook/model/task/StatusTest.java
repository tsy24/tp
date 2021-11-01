package nurseybook.model.task;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static nurseybook.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import nurseybook.testutil.Assert;

class StatusTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        // null isDone
        Assert.assertThrows(NullPointerException.class, () -> new Status(null, "true"));

        // null isOverdue
        Assert.assertThrows(NullPointerException.class, () -> new Status("false", null));
    }

    @Test
    public void constructor_invalidStatus_throwsIllegalArgumentException() {
        String invalidStatus = "a";

        // invalid isDone
        Assert.assertThrows(IllegalArgumentException.class, () -> new Status(invalidStatus, "false"));

        // invalid isOverdue
        Assert.assertThrows(IllegalArgumentException.class, () -> new Status("false", invalidStatus));
    }

    @Test
    public void isValidStatus() {
        // null status
        Assert.assertThrows(NullPointerException.class, () -> Status.isValidStatus(null));

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
        Status completedStatus = new Status("true", "false");
        assertTrue(completedStatus.equals(new Status("true", "false")));

        // random upper cases
        assertTrue(completedStatus.equals(new Status("TruE", "faLse")));

        // different isDone value
        assertFalse(completedStatus.equals(new Status("false", "false")));

        // different isOverdue value
        assertFalse(completedStatus.equals(new Status("false", "true")));

    }
}
