package nurseybook.model.task;

import static nurseybook.logic.commands.TaskCommandTestUtil.VALID_DATE_JAN;
import static nurseybook.logic.commands.TaskCommandTestUtil.VALID_DATE_NOV;
import static nurseybook.logic.commands.TaskCommandTestUtil.VALID_NAME_ALICE;
import static nurseybook.logic.commands.TaskCommandTestUtil.VALID_NAME_GEORGE;
import static nurseybook.logic.commands.TaskCommandTestUtil.VALID_TIME_SEVENPM;
import static nurseybook.logic.commands.TaskCommandTestUtil.VALID_TIME_TENAM;
import static nurseybook.testutil.TypicalTasks.APPLY_LEAVE_DAY_NEXT_RECURRENCE_GHOST;
import static nurseybook.testutil.TypicalTasks.APPLY_LEAVE_NEXT_DAY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import nurseybook.testutil.TaskBuilder;

public class GhostTaskTest {

    private final GhostTask applyLeaveNextDayGhost =
            (GhostTask) new TaskBuilder(APPLY_LEAVE_DAY_NEXT_RECURRENCE_GHOST).build();

    @Test
    void copyTask() {
        //check if copyTask() returns a different instance of RealTask.
        assertNotSame(applyLeaveNextDayGhost, applyLeaveNextDayGhost.copyTask());

        //check if each field in copied task is a new instance.
        GhostTask copyTask = applyLeaveNextDayGhost.copyTask();
        assertNotSame(applyLeaveNextDayGhost.getDesc(), copyTask.getDesc());
        assertNotSame(applyLeaveNextDayGhost.getDateTime(), copyTask.getDateTime());
        assertNotSame(applyLeaveNextDayGhost.getRelatedNames(), copyTask.getRelatedNames());
        assertNotSame(applyLeaveNextDayGhost.getRecurrence(), copyTask.getRecurrence());

        //check if copyTask() returns a RealTask that has the same field values as the copied task.
        assertEquals(applyLeaveNextDayGhost, applyLeaveNextDayGhost.copyTask());
    }

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
        Task editedTask = new TaskBuilder(applyLeaveNextDayGhost).withNames(VALID_NAME_GEORGE).build();
        assertFalse(applyLeaveNextDayGhost.equals(editedTask));
        editedTask = new TaskBuilder(applyLeaveNextDayGhost).withNames(VALID_NAME_GEORGE, VALID_NAME_ALICE).build();
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
        editedTask = new TaskBuilder(applyLeaveNextDayGhost)
                .withRecurrence(Recurrence.RecurrenceType.MONTH.name()).build();
        assertFalse(applyLeaveNextDayGhost.equals(editedTask));

        // different Task Type -> returns false
        assertFalse(applyLeaveNextDayGhost.equals(APPLY_LEAVE_NEXT_DAY));
    }
}

