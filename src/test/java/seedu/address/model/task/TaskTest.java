package seedu.address.model.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.TaskCommandTestUtil.VALID_DATE_JAN;
import static seedu.address.logic.commands.TaskCommandTestUtil.VALID_DATE_NOV;
import static seedu.address.logic.commands.TaskCommandTestUtil.VALID_DESC_PAPERWORK;
import static seedu.address.logic.commands.TaskCommandTestUtil.VALID_NAME_ALEX;
import static seedu.address.logic.commands.TaskCommandTestUtil.VALID_NAME_KEITH;
import static seedu.address.logic.commands.TaskCommandTestUtil.VALID_TIME_SEVENPM;
import static seedu.address.logic.commands.TaskCommandTestUtil.VALID_TIME_TENAM;
import static seedu.address.testutil.TypicalTasks.ALEX_INSULIN;
import static seedu.address.testutil.TypicalTasks.APPLY_LEAVE;
import static seedu.address.testutil.TypicalTasks.DO_PAPERWORK;
import static seedu.address.testutil.TypicalTasks.KEITH_INSULIN;

import org.junit.jupiter.api.Test;

import seedu.address.model.task.Recurrence.RecurrenceType;
import seedu.address.testutil.TaskBuilder;

public class TaskTest {

    private Task keithInsulin = new TaskBuilder(KEITH_INSULIN).build();
    private Task applyLeave = new TaskBuilder(APPLY_LEAVE).build();

    @Test
    public void equals() {
        // same values -> returns true
        assertTrue(keithInsulin.equals(KEITH_INSULIN));
        Task alexToKeith = new TaskBuilder(ALEX_INSULIN).withNames(VALID_NAME_KEITH)
                .withDateTime(VALID_DATE_NOV, VALID_TIME_SEVENPM).withStatus("false", "false")
                .withRecurrence(RecurrenceType.NONE.name()).build();
        assertTrue(keithInsulin.equals(alexToKeith));

        // same object -> returns true
        assertTrue(keithInsulin.equals(keithInsulin));

        // null -> returns false
        assertFalse(keithInsulin.equals(null));

        // different type -> returns false
        assertFalse(keithInsulin.equals(5));

        // different elderly -> returns false
        assertFalse(keithInsulin.equals(ALEX_INSULIN));

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

        // different address -> returns false
        editedTask = new TaskBuilder(keithInsulin).withDesc(VALID_DESC_PAPERWORK).build();
        assertFalse(keithInsulin.equals(editedTask));

        // different status -> returns false
        editedTask = new TaskBuilder(keithInsulin).withStatus("true", "false").build();
        assertFalse(keithInsulin.equals(editedTask));

        // different recurrence -> returns false
        editedTask = new TaskBuilder(keithInsulin).withRecurrence(RecurrenceType.MONTH.name()).build();
        assertFalse(keithInsulin.equals(editedTask));
    }

    @Test
    public void isAfter() {
        DateTime october = new DateTime("2020-10-31", "23:59");
        DateTime november = new DateTime("2020-11-01", "19:45");
        DateTime december = new DateTime("2021-12-25", "12:00");

        // keithInsulin on 2021-11-01, after 2021 October -> returns true
        assertTrue(keithInsulin.isAfter(october));

        // keithInsulin on 2021-11-01, before 2021 December -> returns false
        assertFalse(keithInsulin.isAfter(december));

        // keithInsulin on 2021-11-01, same time as november -> returns false
        assertFalse(keithInsulin.isAfter(november));
    }

    @Test
    public void isBefore() {
        DateTime october = new DateTime("2020-10-31", "23:59");
        DateTime november = new DateTime("2020-11-01", "19:45");
        DateTime december = new DateTime("2021-12-25", "12:00");

        // keithInsulin on 2021-11-01, after 2021 October -> returns false
        assertFalse(keithInsulin.isBefore(october));

        // keithInsulin on 2021-11-01, before 2021 December -> returns true
        assertTrue(keithInsulin.isBefore(december));

        // keithInsulin on 2021-11-01, same time as november -> returns false
        assertFalse(keithInsulin.isBefore(november));
    }

    @Test
    void isTaskDone() {
        assertFalse(keithInsulin.isTaskDone()); // status: isDone = "false"

        assertTrue(applyLeave.isTaskDone());
    }

    @Test
    void isTaskOverdue() {
        assertTrue(applyLeave.isTaskOverdue()); // status: isOverdue = "true"

        assertFalse(keithInsulin.isTaskOverdue()); // default status: isOverdue = "false"
    }

    @Test
    void markTaskDone() {
        Task doneKeith = new TaskBuilder(keithInsulin).withStatus("true", "false").build();
        assertEquals(keithInsulin.markAsDone(), doneKeith);
    }

    @Test
    void markTaskOverdue() {
        Task overdueKeith = new TaskBuilder(keithInsulin).withStatus("false", "true").build();
        assertEquals(keithInsulin.markAsOverdue(), overdueKeith);
    }

    @Test
    public void compareTo() {
        Task keithInsulin = new TaskBuilder(KEITH_INSULIN).build(); // date = "2020-11-01"
        Task alexInsulin = new TaskBuilder(ALEX_INSULIN).build(); // date = "2022-01-31", time: "19:45" in 24 hrs time
        Task doPaperwork = new TaskBuilder(DO_PAPERWORK).build(); // date: "2022-01-31", time: "10:20" in 24 hrs time

        // keithInsulin before alexInsulin -> returns negative value
        assertTrue(keithInsulin.compareTo(alexInsulin) < 0);

        // alexInsulin after doPaperwork -> returns positive value
        assertTrue(alexInsulin.compareTo(doPaperwork) > 0);

        // doPaperwork before alexInsulin -> returns negative value
        assertFalse(doPaperwork.compareTo(keithInsulin) == 0);
    }

}
