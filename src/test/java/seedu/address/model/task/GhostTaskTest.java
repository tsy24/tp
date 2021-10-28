package seedu.address.model.task;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class GhostTaskTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new GhostTask(null));
    }

    @Test
    public void constructor_invalidGhostTask_throwsIllegalArgumentException() {
        String invalidGhostTask = "a";
        assertThrows(IllegalArgumentException.class, () -> new GhostTask(invalidGhostTask));
    }

    @Test
    public void isValidGhostTask() {
        // null ghostTask
        assertThrows(NullPointerException.class, () -> GhostTask.isValidGhostTask(null));

        // invalid ghostTasks
        assertFalse(GhostTask.isValidGhostTask("abc"));
        assertFalse(GhostTask.isValidGhostTask("2"));

        // valid ghostTasks
        assertTrue(GhostTask.isValidGhostTask("true"));
        assertTrue(GhostTask.isValidGhostTask("TrUe")); // random upper cases
        assertTrue(GhostTask.isValidGhostTask("false"));
    }

    @Test
    public void checkIfGhostTask() {
        // ghostTask
        GhostTask ghostTask = new GhostTask("true");
        assertTrue(ghostTask.checkIfGhostTask());

        // realTask
        GhostTask realTask = new GhostTask("false");
        assertFalse(realTask.checkIfGhostTask());
    }

    @Test
    public void equals() {
        GhostTask ghostTask = new GhostTask("true");
        assertTrue(ghostTask.equals(new GhostTask("true")));
        assertTrue(ghostTask.equals(new GhostTask("TruE"))); // random upper cases

        assertFalse(ghostTask.equals(new GhostTask("false")));
    }
}
