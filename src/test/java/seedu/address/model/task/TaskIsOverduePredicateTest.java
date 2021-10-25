package seedu.address.model.task;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalTasks.APPLY_LEAVE;
import static seedu.address.testutil.TypicalTasks.KEITH_INSULIN;
import static seedu.address.testutil.TypicalTasks.YASMINE_PHYSIO;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.TaskBuilder;

public class TaskIsOverduePredicateTest {

    private Task keithInsulin = new TaskBuilder(KEITH_INSULIN).build(); // date: 2021-10-01, time: 00:00
    private Task yasminPhysio = new TaskBuilder(YASMINE_PHYSIO).build(); // date: 2021-09-13, time: 15:30

    @Test
    public void test_overdueTasks_returnsTrue() {
        // default isOverdue = true -> returns true
        TaskIsOverduePredicate predicate = new TaskIsOverduePredicate();
        assertTrue(predicate.test(keithInsulin));

        // day has passed -> returns true
        assertTrue(predicate.test(yasminPhysio));

        // same day but one hour before -> returns true
        LocalDateTime now = LocalDateTime.now().withMinute(0).withSecond(0).withNano(0);
        LocalDateTime past = now.minusHours(1);
        String[] dateTime = past.toString().split("T");
        String date = dateTime[0];
        String time = dateTime[1].substring(0, 5);

        Task overdueKeithInsulin = new TaskBuilder(KEITH_INSULIN).withDateTime(date, time).build();
        assertTrue(predicate.test(overdueKeithInsulin));
    }

    @Test
    public void test_notOverdueTasks_returnsFalse() {

        TaskIsOverduePredicate predicate = new TaskIsOverduePredicate();

        LocalDateTime now = LocalDateTime.now().withMinute(0).withSecond(0);
        LocalDateTime future = now.plusDays(30);

        String[] dateTime = future.toString().split("T");
        String date = dateTime[0];
        String time = dateTime[1].substring(0, 5);

        // 30 days later than date in predicate -> returns false
        Task notOverdueKeithInsulin = new TaskBuilder(KEITH_INSULIN).withDateTime(date, time).build();
        assertFalse(predicate.test(notOverdueKeithInsulin));

        // 1 hour after than date and time in predicate -> returns false
        future = now.plusHours(1);

        dateTime = future.toString().split("T");
        date = dateTime[0];
        time = dateTime[1].substring(0, 5);

        notOverdueKeithInsulin = new TaskBuilder(KEITH_INSULIN).withDateTime(date, time).build();
        assertFalse(predicate.test(notOverdueKeithInsulin));

    }
}
