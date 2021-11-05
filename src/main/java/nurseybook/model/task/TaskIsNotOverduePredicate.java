package nurseybook.model.task;

import java.util.function.Predicate;

/**
 * Tests that a {@code Task}'s is not yet overdue
 */
public class TaskIsNotOverduePredicate implements Predicate<Task> {

    @Override
    public boolean test(Task task) {
        return (!DateTime.isOverdue(task.getDateTime()));
    }
}
