package nurseybook.model.task;

import java.time.LocalDateTime;
import java.util.function.Predicate;

/**
 * Tests whether a {@code Task} is a reminder at the date and time of the command execution.
 */
public class TaskIsReminderPredicate implements Predicate<Task> {

    public final LocalDateTime currentDateTime;

    public TaskIsReminderPredicate(LocalDateTime now) {
        this.currentDateTime = now;
    }

    @Override
    public boolean test(Task task) {
        String[] dateTime = currentDateTime.toString().split("T");
        String currentDate = dateTime[0];
        String currentTime = dateTime[1].substring(0, 5);
        DateTime now = new DateTime(currentDate, currentTime);

        String[] limitDateTime = currentDateTime.plusDays(4)
                .withHour(0)
                .toString().split("T");
        String limitDate = limitDateTime[0];
        String limitTime = limitDateTime[1].substring(0, 5);
        DateTime limit = new DateTime(limitDate, limitTime);

        return task.isAfter(now) && task.isBefore(limit) && !task.isTaskDone();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskIsReminderPredicate // instanceof handles nulls
                && currentDateTime.equals(((TaskIsReminderPredicate) other).currentDateTime)); // state check
    }
}
