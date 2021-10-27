package seedu.address.model.task;

import java.util.function.Predicate;

public class TaskIsOverduePredicate implements Predicate<Task> {

    @Override
    public boolean test(Task task) {
        if (task.isTaskOverdue()) {
            return true;
        }

        return DateTime.isOverdue(task.getDateTime());
    }
}
