package seedu.address.model.task;

import java.util.function.Predicate;

public class TaskIsRecurringAndOverduePredicate implements Predicate<Task> {

    @Override
    public boolean test(Task task) {
        return task.getRecurrence().isRecurring() && DateTime.isOverdue(task.getDateTime());
    }
}
