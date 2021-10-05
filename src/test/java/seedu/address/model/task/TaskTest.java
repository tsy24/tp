package seedu.address.model.task;

import org.junit.jupiter.api.Test;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TaskBuilder;
import seedu.address.testutil.TypicalTasks;

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
import static seedu.address.testutil.TypicalTasks.KEITH_INSULIN;

public class TaskTest {

    @Test
    public void equals() {
        // same values -> returns true
        Task KEITH_INS = new TaskBuilder(KEITH_INSULIN).build();
        assertTrue(KEITH_INS.equals(KEITH_INSULIN));
        Task alexToKeith = new TaskBuilder(ALEX_INSULIN).withNames(VALID_NAME_KEITH)
                .withDateTime(VALID_DATE_NOV, VALID_TIME_SEVENPM).build();
        assertTrue(KEITH_INS.equals(alexToKeith));

        // same object -> returns true
        assertTrue(KEITH_INS.equals(KEITH_INS));

        // null -> returns false
        assertFalse(KEITH_INS.equals(null));

        // different type -> returns false
        assertFalse(KEITH_INS.equals(5));

        // different person -> returns false
        assertFalse(KEITH_INS.equals(ALEX_INSULIN));

        // different name -> returns false
        Task editedTask = new TaskBuilder(KEITH_INS).withNames(VALID_NAME_ALEX).build();
        assertFalse(KEITH_INS.equals(editedTask));
        editedTask = new TaskBuilder(KEITH_INS).withNames(VALID_NAME_KEITH, VALID_NAME_ALEX).build();
        assertFalse(KEITH_INS.equals(editedTask));

        // different date -> returns false
        editedTask = new TaskBuilder(KEITH_INS).withDateTime(VALID_DATE_JAN, VALID_TIME_SEVENPM).build();
        assertFalse(KEITH_INS.equals(editedTask));

        // different time -> returns false
        editedTask = new TaskBuilder(KEITH_INS).withDateTime(VALID_DATE_NOV, VALID_TIME_TENAM).build();
        assertFalse(KEITH_INS.equals(editedTask));

        // different address -> returns false
        editedTask = new TaskBuilder(KEITH_INS).withDesc(VALID_DESC_PAPERWORK).build();
        assertFalse(KEITH_INS.equals(editedTask));
    }
}