package seedu.address.model.task;

import java.time.LocalDate;
import java.util.function.Predicate;

/**
 * Tests whether a {@code Task} is a reminder at the date and time of the command execution.
 */
public class TaskIsReminderPredicate implements Predicate<Task> {
    private final LocalDate currentTime;

    public TaskIsReminderPredicate(LocalDate currentTime) {
        this.currentTime = currentTime;
    }

    @Override
    public boolean test(Task task) {
        DateTime now = new DateTime(currentTime.toString(), "00:00");
        DateTime limit = new DateTime(currentTime.plusDays(4).toString(), "00:00");

        return task.isTaskDone() && task.isAfter(now) && task.isBefore(limit);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskIsReminderPredicate // instanceof handles nulls
                && currentTime.equals(((TaskIsReminderPredicate) other).currentTime)); // state check
    }
}
