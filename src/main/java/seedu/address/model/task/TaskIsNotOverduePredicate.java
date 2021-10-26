package seedu.address.model.task;

import java.util.function.Predicate;

public class TaskIsNotOverduePredicate implements Predicate<Task> {

    @Override
    public boolean test(Task task) {
        return !DateTime.isOverdue(task.getDateTime());
    }
}
