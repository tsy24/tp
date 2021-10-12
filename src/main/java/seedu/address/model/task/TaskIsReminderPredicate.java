package seedu.address.model.task;

import java.time.LocalDate;
import java.util.function.Predicate;

/**
 * Tests whether a {@code Task} is a reminder at the date and time of the command execution.
 */
public class TaskIsReminderPredicate implements Predicate<Task> {

    private final LocalDate currentDate;

    public TaskIsReminderPredicate(LocalDate currentDate) {
        this.currentDate = currentDate;
    }

    @Override
    public boolean test(Task task) {
        DateTime now = new DateTime(currentDate.plusDays(-1).toString(), "23:59");
        DateTime limit = new DateTime(currentDate.plusDays(4).toString(), "00:00");

        return task.isAfter(now) && task.isBefore(limit) && !task.isTaskDone();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskIsReminderPredicate // instanceof handles nulls
                && currentDate.equals(((TaskIsReminderPredicate) other).currentDate)); // state check
    }
}
