package nurseybook.model.task;

import java.util.function.Predicate;

/**
 * Tests that a {@code Task}'s is overdue (current date and time passed task's assigned date and time).
 */
public class TaskIsOverduePredicate implements Predicate<Task> {

    @Override
    public boolean test(Task task) {
        return DateTime.isOverdue(task.getDateTime());
    }
}
