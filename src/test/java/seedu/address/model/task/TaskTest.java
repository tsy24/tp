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

import java.time.Clock;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.TaskBuilder;

public class TaskTest {

    private Task keithInsulin = new TaskBuilder(KEITH_INSULIN).build();

    @Test
    public void equals() {
        // same values -> returns true
        assertTrue(keithInsulin.equals(KEITH_INSULIN));
        Task alexToKeith = new TaskBuilder(ALEX_INSULIN).withNames(VALID_NAME_KEITH)
                .withDateTime(VALID_DATE_NOV, VALID_TIME_SEVENPM).withStatus("false").build();
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
        editedTask = new TaskBuilder(keithInsulin).withStatus("true").build();
        assertFalse(keithInsulin.equals(editedTask));
    }

    @Test
    void isTaskDone() {
        assertFalse(keithInsulin.isTaskDone()); //status: isDone = "false"

        Task applyLeave = new TaskBuilder(APPLY_LEAVE).build(); //status: isDone = "true"
        assertTrue(applyLeave.isTaskDone());
    }

    @Test
    void markTaskDone() {
        Task doneKeith = new TaskBuilder(keithInsulin).withStatus("true").build();
        assertEquals(keithInsulin.markAsDone(), doneKeith);
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

    @Test
    public void execute_isReminder_success() {
        Clock cl = Clock.systemUTC();
        LocalDate currentDate = LocalDate.now(cl);

        // coming up in the next two days -> returns true
        Task keithInsulin = new TaskBuilder(KEITH_INSULIN).build();
        Task editedKeith = new TaskBuilder(keithInsulin).
                withDateTime(currentDate.plusDays(2).toString(), "10:30").build();
        assertTrue(editedKeith.isReminder());

        // coming up six days later -> returns false
        Task alexInsulin = new TaskBuilder(ALEX_INSULIN).build();
        Task editedAlex = new TaskBuilder(alexInsulin).
                withDateTime(currentDate.plusDays(6).toString(), "18:55").build();
        assertFalse(editedAlex.isReminder());

        // coming up on the current date -> returns true
        Task doPaperwork = new TaskBuilder(DO_PAPERWORK).build();
        Task editedPaperwork = new TaskBuilder(doPaperwork).
                withDateTime(currentDate.toString(), "23:10").build();
        assertTrue(editedPaperwork.isReminder());
    }

}
