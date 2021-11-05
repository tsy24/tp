package nurseybook.model.task;

import static nurseybook.testutil.TypicalTasks.GEORGE_INSULIN;
import static nurseybook.testutil.TypicalTasks.YASMINE_PHYSIO;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import nurseybook.testutil.TaskBuilder;

public class TaskIsNotOverduePredicateTest {

    private Task georgeInsulin = new TaskBuilder(GEORGE_INSULIN).build(); // date: 2021-10-01, time: 00:00

    @Test
    public void test_overdueTasks_returnsFalse() {
        TaskIsNotOverduePredicate predicate = new TaskIsNotOverduePredicate();

        // one minute before current time -> is overdue -> returns false
        LocalDateTime now = LocalDateTime.now().withSecond(0).withNano(0);
        LocalDateTime past = now.minusMinutes(1);
        String[] dateTime = past.toString().split("T");
        String date = dateTime[0];
        String time = dateTime[1].substring(0, 5);

        Task overdueYasminEPhysio = new TaskBuilder(YASMINE_PHYSIO).withDateTime(date, time).build();
        assertFalse(predicate.test(overdueYasminEPhysio));
    }

    @Test
    public void test_notOverdueTasks_returnsTrue() {

        TaskIsNotOverduePredicate predicate = new TaskIsNotOverduePredicate();

        LocalDateTime now = LocalDateTime.now().withSecond(0).withNano(0);

        // with current time -> not yet overdue -> returns true
        String[] dateTime = now.toString().split("T");
        String date = dateTime[0];
        String time = dateTime[1].substring(0, 5);

        Task notOverdueGeorgeInsulin = new TaskBuilder(georgeInsulin).withDateTime(date, time).build();
        assertTrue(predicate.test(notOverdueGeorgeInsulin));

        // one minute after current time -> not overdue -> returns true
        LocalDateTime future = now.plusMinutes(1);
        dateTime = future.toString().split("T");
        date = dateTime[0];
        time = dateTime[1].substring(0, 5);

        Task notOverdueYasminePhysio = new TaskBuilder(YASMINE_PHYSIO).withDateTime(date, time).build();
        assertTrue(predicate.test(notOverdueYasminePhysio));

    }
}
