package nurseybook.model.task;

import static nurseybook.testutil.TypicalTasks.FIONA_PHYSIO;
import static nurseybook.testutil.TypicalTasks.GEORGE_INSULIN;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import nurseybook.testutil.TaskBuilder;

public class TaskIsOverduePredicateTest {

    private Task georgeInsulin = new TaskBuilder(GEORGE_INSULIN).build(); // date: 2021-10-01, time: 00:00
    private Task fionaPhysio = new TaskBuilder(FIONA_PHYSIO).build(); // date: 2021-09-13, time: 15:30

    @Test
    public void test_overdueTasks_returnsTrue() {
        TaskIsOverduePredicate predicate = new TaskIsOverduePredicate();

        // default isOverdue = true -> returns true
        assertTrue(predicate.test(georgeInsulin));

        // one minute before current time -> returns true
        LocalDateTime now = LocalDateTime.now().withSecond(0).withNano(0);
        LocalDateTime past = now.minusMinutes(1);
        String[] dateTime = past.toString().split("T");
        String date = dateTime[0];
        String time = dateTime[1].substring(0, 5);

        Task overdueFionaPhysio = new TaskBuilder(fionaPhysio).withDateTime(date, time).build();
        assertTrue(predicate.test(overdueFionaPhysio));

    }

    @Test
    public void test_notOverdueTasks_returnsFalse() {

        TaskIsOverduePredicate predicate = new TaskIsOverduePredicate();

        LocalDateTime now = LocalDateTime.now().withSecond(0).withNano(0);

        // with current time -> returns false
        String[] dateTime = now.toString().split("T");
        String date = dateTime[0];
        String time = dateTime[1].substring(0, 5);

        Task notOverdueGeorgeInsulin = new TaskBuilder(georgeInsulin).withDateTime(date, time).build();
        assertFalse(predicate.test(notOverdueGeorgeInsulin));

        // one minute after current time -> returns false
        LocalDateTime future = now.plusMinutes(1);
        dateTime = future.toString().split("T");
        date = dateTime[0];
        time = dateTime[1].substring(0, 5);

        Task notOverdueFionaPhysio = new TaskBuilder(fionaPhysio).withDateTime(date, time).build();
        assertFalse(predicate.test(notOverdueFionaPhysio));

    }
}
