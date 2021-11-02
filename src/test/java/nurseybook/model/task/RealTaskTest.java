package nurseybook.model.task;

import static nurseybook.logic.commands.TaskCommandTestUtil.VALID_DATE_JAN;
import static nurseybook.logic.commands.TaskCommandTestUtil.VALID_DATE_NOV;
import static nurseybook.logic.commands.TaskCommandTestUtil.VALID_DESC_MEDICINE;
import static nurseybook.logic.commands.TaskCommandTestUtil.VALID_NAME_ALEX;
import static nurseybook.logic.commands.TaskCommandTestUtil.VALID_NAME_KEITH;
import static nurseybook.logic.commands.TaskCommandTestUtil.VALID_TIME_SEVENPM;
import static nurseybook.logic.commands.TaskCommandTestUtil.VALID_TIME_TENAM;
import static nurseybook.testutil.TypicalTasks.ALEX_INSULIN;
import static nurseybook.testutil.TypicalTasks.APPLY_LEAVE;
import static nurseybook.testutil.TypicalTasks.APPLY_LEAVE_DAY_NEXT_RECURRENCE_GHOST;
import static nurseybook.testutil.TypicalTasks.APPLY_LEAVE_NEXT_DAY;
import static nurseybook.testutil.TypicalTasks.KEITH_INSULIN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import nurseybook.testutil.TaskBuilder;

public class RealTaskTest {

    private final Task keithInsulin = new TaskBuilder(KEITH_INSULIN).build();
    private final Task applyLeave = new TaskBuilder(APPLY_LEAVE).build();

    @Test
    void copyToGhostTask() {
        RealTask realTask = (RealTask) APPLY_LEAVE_NEXT_DAY;
        GhostTask ghostTask = realTask.copyToGhostTask();
        assertEquals(new TaskBuilder(APPLY_LEAVE_NEXT_DAY, false).build(), ghostTask);
    }

    @Test
    public void equals() {
        // same values -> returns true
        assertTrue(keithInsulin.equals(KEITH_INSULIN));
        Task alexToKeith = new TaskBuilder(ALEX_INSULIN).withNames(VALID_NAME_KEITH).withDesc(VALID_DESC_MEDICINE)
                .withDateTime(VALID_DATE_NOV, VALID_TIME_SEVENPM).withStatus("false", "true")
                .withRecurrence(Recurrence.RecurrenceType.NONE.name()).build();
        assertTrue(keithInsulin.equals(alexToKeith));

        // same object -> returns true
        assertTrue(keithInsulin.equals(keithInsulin));

        // null -> returns false
        assertFalse(keithInsulin.equals(null));

        // different type -> returns false
        assertFalse(keithInsulin.equals(5));

        // different name -> returns false
        Task editedTask = new TaskBuilder(keithInsulin).withNames(VALID_NAME_ALEX).build();
        assertFalse(keithInsulin.equals(editedTask));
        editedTask = new TaskBuilder(keithInsulin).withNames(VALID_NAME_KEITH, VALID_NAME_ALEX).build();
        assertFalse(keithInsulin.equals(editedTask));

        // different date -> returns false
        editedTask = new TaskBuilder(keithInsulin).withDateTime(VALID_DATE_JAN, VALID_TIME_SEVENPM).build();
        assertFalse(keithInsulin.equals(editedTask));

        // different time -> returns false
        editedTask = new TaskBuilder(keithInsulin).withDateTime(VALID_DATE_NOV, VALID_TIME_TENAM).build();
        assertFalse(keithInsulin.equals(editedTask));

        // different status -> returns false
        editedTask = new TaskBuilder(keithInsulin).withStatus("true", "false").build();
        assertFalse(keithInsulin.equals(editedTask));

        // different recurrence -> returns false
        editedTask = new TaskBuilder(keithInsulin).withRecurrence(Recurrence.RecurrenceType.MONTH.name()).build();
        assertFalse(keithInsulin.equals(editedTask));

        // different Task Type -> returns false
        assertFalse(keithInsulin.equals(APPLY_LEAVE_DAY_NEXT_RECURRENCE_GHOST));
    }

}
