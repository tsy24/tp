package nurseybook.model.task;

import static nurseybook.testutil.TypicalTasks.KEITH_INSULIN;
import static nurseybook.testutil.TypicalTasks.YASMINE_PHYSIO;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import nurseybook.testutil.TaskBuilder;

public class TaskIsOverduePredicateTest {

    private Task keithInsulin = new TaskBuilder(KEITH_INSULIN).build(); // date: 2021-10-01, time: 00:00

    @Test
    public void test_overdueTasks_returnsTrue() {
        TaskIsOverduePredicate predicate = new TaskIsOverduePredicate();

        // default isOverdue = true -> returns true
        assertTrue(predicate.test(keithInsulin));

        // one minute before current time -> returns true
        LocalDateTime now = LocalDateTime.now().withSecond(0).withNano(0);
        LocalDateTime past = now.minusMinutes(1);
        String[] dateTime = past.toString().split("T");
        String date = dateTime[0];
        String time = dateTime[1].substring(0, 5);

        Task overdueYasminPhysio = new TaskBuilder(YASMINE_PHYSIO).withDateTime(date, time).build();
        assertTrue(predicate.test(overdueYasminPhysio));

    }

    @Test
    public void test_notOverdueTasks_returnsFalse() {

        TaskIsOverduePredicate predicate = new TaskIsOverduePredicate();

        LocalDateTime now = LocalDateTime.now().withSecond(0).withNano(0);

        // with current time -> returns false
        String[] dateTime = now.toString().split("T");
        String date = dateTime[0];
        String time = dateTime[1].substring(0, 5);

        Task notOverdueKeithInsulin = new TaskBuilder(KEITH_INSULIN).withDateTime(date, time).build();
        assertFalse(predicate.test(notOverdueKeithInsulin));

        // one minute after current time -> returns false
        LocalDateTime future = now.plusMinutes(1);
        dateTime = future.toString().split("T");
        date = dateTime[0];
        time = dateTime[1].substring(0, 5);

        Task notOverdueYasminPhysio = new TaskBuilder(YASMINE_PHYSIO).withDateTime(date, time).build();
        assertFalse(predicate.test(notOverdueYasminPhysio));

    }
}
