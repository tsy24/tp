package nurseybook.model.task;

import static nurseybook.logic.commands.TaskCommandTestUtil.VALID_DATE_JAN;
import static nurseybook.logic.commands.TaskCommandTestUtil.VALID_DATE_NOV;
import static nurseybook.logic.commands.TaskCommandTestUtil.VALID_NAME_ALEX;
import static nurseybook.logic.commands.TaskCommandTestUtil.VALID_NAME_KEITH;
import static nurseybook.logic.commands.TaskCommandTestUtil.VALID_TIME_SEVENPM;
import static nurseybook.logic.commands.TaskCommandTestUtil.VALID_TIME_TENAM;
import static nurseybook.testutil.TypicalTasks.APPLY_LEAVE_DAY_NEXT_RECURRENCE_GHOST;
import static nurseybook.testutil.TypicalTasks.APPLY_LEAVE_NEXT_DAY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import nurseybook.testutil.TaskBuilder;

public class GhostTaskTest {

    private final Task applyLeaveNextDayGhost = new TaskBuilder(APPLY_LEAVE_DAY_NEXT_RECURRENCE_GHOST).build();

    @Test
    void copyToGhostTask() {
        RealTask realTask = (RealTask) APPLY_LEAVE_NEXT_DAY;
        GhostTask ghostTask = realTask.copyToGhostTask();
        assertEquals(new TaskBuilder(APPLY_LEAVE_NEXT_DAY, false).build(), ghostTask);
    }

    @Test
    public void equals() {
        // same values -> returns true
        assertTrue(applyLeaveNextDayGhost.equals(APPLY_LEAVE_DAY_NEXT_RECURRENCE_GHOST));

        // same object -> returns true
        assertTrue(applyLeaveNextDayGhost.equals(applyLeaveNextDayGhost));

        // null -> returns false
        assertFalse(applyLeaveNextDayGhost.equals(null));

        // different type -> returns false
        assertFalse(applyLeaveNextDayGhost.equals(5));

        // different name -> returns false
        Task editedTask = new TaskBuilder(applyLeaveNextDayGhost).withNames(VALID_NAME_ALEX).build();
        assertFalse(applyLeaveNextDayGhost.equals(editedTask));
        editedTask = new TaskBuilder(applyLeaveNextDayGhost).withNames(VALID_NAME_KEITH, VALID_NAME_ALEX).build();
        assertFalse(applyLeaveNextDayGhost.equals(editedTask));

        // different date -> returns false
        editedTask = new TaskBuilder(applyLeaveNextDayGhost).withDateTime(VALID_DATE_JAN, VALID_TIME_SEVENPM).build();
        assertFalse(applyLeaveNextDayGhost.equals(editedTask));

        // different time -> returns false
        editedTask = new TaskBuilder(applyLeaveNextDayGhost).withDateTime(VALID_DATE_NOV, VALID_TIME_TENAM).build();
        assertFalse(applyLeaveNextDayGhost.equals(editedTask));

        // different status -> returns false
        editedTask = new TaskBuilder(applyLeaveNextDayGhost).withStatus("true", "false").build();
        assertFalse(applyLeaveNextDayGhost.equals(editedTask));

        // different recurrence -> returns false
        editedTask = new TaskBuilder(applyLeaveNextDayGhost).withRecurrence(Recurrence.RecurrenceType.MONTH.name()).build();
        assertFalse(applyLeaveNextDayGhost.equals(editedTask));

        // different Task Type -> returns false
        assertFalse(applyLeaveNextDayGhost.equals(APPLY_LEAVE_NEXT_DAY));
    }
}

