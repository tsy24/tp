package nurseybook.model.task;

import static nurseybook.logic.commands.TaskCommandTestUtil.VALID_DATE_JAN;
import static nurseybook.logic.commands.TaskCommandTestUtil.VALID_DATE_NOV;
import static nurseybook.logic.commands.TaskCommandTestUtil.VALID_DESC_MEDICINE;
import static nurseybook.logic.commands.TaskCommandTestUtil.VALID_NAME_ALICE;
import static nurseybook.logic.commands.TaskCommandTestUtil.VALID_NAME_GEORGE;
import static nurseybook.logic.commands.TaskCommandTestUtil.VALID_TIME_SEVENPM;
import static nurseybook.logic.commands.TaskCommandTestUtil.VALID_TIME_TENAM;
import static nurseybook.testutil.TypicalTasks.ALICE_INSULIN;
import static nurseybook.testutil.TypicalTasks.APPLY_LEAVE_DAY_NEXT_RECURRENCE_GHOST;
import static nurseybook.testutil.TypicalTasks.APPLY_LEAVE_NEXT_DAY;
import static nurseybook.testutil.TypicalTasks.GEORGE_INSULIN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import nurseybook.testutil.TaskBuilder;

public class RealTaskTest {

    private final RealTask georgeInsulin = (RealTask) new TaskBuilder(GEORGE_INSULIN).build();

    @Test
    void copyTask() {
        //check if copyTask() returns a different instance of RealTask.
        assertNotSame(georgeInsulin, georgeInsulin.copyTask());

        //check if each field in copied task is a new instance.
        RealTask copyTask = georgeInsulin.copyTask();
        assertNotSame(georgeInsulin.getDesc(), copyTask.getDesc());
        assertNotSame(georgeInsulin.getDateTime(), copyTask.getDateTime());
        assertNotSame(georgeInsulin.getRelatedNames(), copyTask.getRelatedNames());
        assertNotSame(georgeInsulin.getRecurrence(), copyTask.getRecurrence());


        //check if copyTask() returns a RealTask that has the same field values as the copied task.
        assertEquals(georgeInsulin, copyTask);
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
        assertTrue(georgeInsulin.equals(GEORGE_INSULIN));
        Task alexToKeith = new TaskBuilder(ALICE_INSULIN).withNames(VALID_NAME_GEORGE).withDesc(VALID_DESC_MEDICINE)
                .withDateTime(VALID_DATE_NOV, VALID_TIME_SEVENPM).withStatus("false", "true")
                .withRecurrence(Recurrence.RecurrenceType.NONE.name()).build();
        assertTrue(georgeInsulin.equals(alexToKeith));

        // same object -> returns true
        assertTrue(georgeInsulin.equals(georgeInsulin));

        // null -> returns false
        assertFalse(georgeInsulin.equals(null));

        // different type -> returns false
        assertFalse(georgeInsulin.equals(5));

        // different name -> returns false
        Task editedTask = new TaskBuilder(georgeInsulin).withNames(VALID_NAME_ALICE).build();
        assertFalse(georgeInsulin.equals(editedTask));
        editedTask = new TaskBuilder(georgeInsulin).withNames(VALID_NAME_GEORGE, VALID_NAME_ALICE).build();
        assertFalse(georgeInsulin.equals(editedTask));

        // different date -> returns false
        editedTask = new TaskBuilder(georgeInsulin).withDateTime(VALID_DATE_JAN, VALID_TIME_SEVENPM).build();
        assertFalse(georgeInsulin.equals(editedTask));

        // different time -> returns false
        editedTask = new TaskBuilder(georgeInsulin).withDateTime(VALID_DATE_NOV, VALID_TIME_TENAM).build();
        assertFalse(georgeInsulin.equals(editedTask));

        // different status -> returns false
        editedTask = new TaskBuilder(georgeInsulin).withStatus("true", "false").build();
        assertFalse(georgeInsulin.equals(editedTask));

        // different recurrence -> returns false
        editedTask = new TaskBuilder(georgeInsulin).withRecurrence(Recurrence.RecurrenceType.MONTH.name()).build();
        assertFalse(georgeInsulin.equals(editedTask));

        // different Task Type -> returns false
        assertFalse(georgeInsulin.equals(APPLY_LEAVE_DAY_NEXT_RECURRENCE_GHOST));
    }
}
