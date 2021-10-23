package seedu.address.model.task;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalTasks.APPLY_LEAVE;
import static seedu.address.testutil.TypicalTasks.KEITH_INSULIN;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.TaskBuilder;

public class TaskIsReminderPredicateTest {

    @Test
    public void equals() {
        LocalDate firstDate = LocalDate.of(2021, 10, 31);
        LocalDate secondDate = LocalDate.of(2021, 11, 11);

        TaskIsReminderPredicate firstPredicate = new TaskIsReminderPredicate(firstDate);
        TaskIsReminderPredicate secondPredicate = new TaskIsReminderPredicate(secondDate);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TaskIsReminderPredicate firstPredicateCopy = new TaskIsReminderPredicate(firstDate);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different predicate -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_validTasks_returnsTrue() {
        Task keithInsulin = new TaskBuilder(KEITH_INSULIN).build();

        // on the same day -> returns true
        TaskIsReminderPredicate predicate =
                new TaskIsReminderPredicate(LocalDate.of(2020, 11, 1));
        assertTrue(predicate.test(keithInsulin));

        // three days later -> returns true
        predicate = new TaskIsReminderPredicate(LocalDate.of(2020, 10, 29));
        assertTrue(predicate.test(keithInsulin));

        // task not yet completed -> returns true
        predicate = new TaskIsReminderPredicate(LocalDate.of(2021, 11, 11));
        assertTrue(predicate.test(new TaskBuilder()
                .withDesc("get vaccinated")
                .withDateTime("2021-11-13", "10:15")
                .withStatus("false", "false")
                .build()));
    }

    @Test
    public void test_invalidTasks_returnsFalse() {
        Task keithInsulin = new TaskBuilder(KEITH_INSULIN).build();
        Task applyLeave = new TaskBuilder(APPLY_LEAVE).build();

        // before the day -> returns false
        TaskIsReminderPredicate predicate =
                new TaskIsReminderPredicate(LocalDate.of(2020, 11, 5));
        assertFalse(predicate.test(keithInsulin));

        // more than three days later -> returns false
        predicate = new TaskIsReminderPredicate(LocalDate.of(2020, 10, 27));
        assertFalse(predicate.test(keithInsulin));

        // valid dates, but task is completed -> returns false
        predicate = new TaskIsReminderPredicate(LocalDate.of(2021, 10, 1));
        assertFalse(predicate.test(new TaskBuilder(applyLeave).build()));
    }
}
