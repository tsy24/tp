package seedu.address.model.task;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.task.exceptions.TaskNotFoundException;

/**
 * A list of tasks that does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 */
public class TaskList implements Iterable<Task> {

    private final ObservableList<Task> internalList = FXCollections.observableArrayList();
    private final ObservableList<Task> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Adds a task to the list and sorts the list after each addition by the DateTime
     * field of each Task instance.
     */
    public void add(Task toAdd) {
        requireNonNull(toAdd);
        internalList.add(toAdd);
        internalList.sort(Comparator.naturalOrder());
    }

    /**
     * Replaces the task {@code target} in the list with {@code editedTask}.
     * {@code target} must exist in the list.
     * The task's description of {@code editedTask} must not be the same as another existing task in the list.
     */
    public void setTask(Task target, Task editedTask) {
        requireAllNonNull(target, editedTask);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new TaskNotFoundException();
        }
        internalList.set(index, editedTask);
    }

    /**
     * Marks the task {@code target} in the list as done.
     * {@code target} must exist in the list.
     */
    public void markTaskAsDone(Task toMark) {
        setTask(toMark, toMark.markAsDone());
    }

    /**
     * Marks the task {@code target} in the list as overdue.
     * {@code target} must exist in the list.
     */
    public void markTaskAsOverdue(Task toMark) {
        setTask(toMark, toMark.markAsOverdue());
    }

    /**
     * Changes the task's {@code DateTime} to the next earliest {@code DateTime},
     * if a recurring task's original DateTime is before the current {@code DateTime},
     * according to whether is it a {@code DAY}, {@code WEEK} or {@code MONTH} recurrence type.
     * At the same time, after changing the {@code DateTime},
     * it also maintains the sorted order of tasks according to {@code DateTime}.
     * All tasks which had their {@code DateTime} changed will also have their status's {@code isDone} as false.
     */
    public void updateDateOfRecurringTask(Task toMark) {
        setTask(toMark, toMark.updateDateRecurringTask());
        // Re-sorts task list when task date is changed
        internalList.sort(Comparator.naturalOrder());
    }

    /**
     * Updates the task {@code target} in the list as overdue.
     * {@code target} must exist in the list.
     */
    public void markTaskAsNotOverdue(Task toMark) {
        setTask(toMark, toMark.markAsNotOverdue());
    }

    /**
     * Replaces this list with the list from {@code replacement}.
     */
    public void setTasks(TaskList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code tasks}.
     */
    public void setTasks(List<Task> tasks) {
        requireAllNonNull(tasks);
        internalList.setAll(tasks);
    }

    /**
     * Removes the equivalent task from the list.
     * The task must exist in the list.
     */
    public void remove(Task toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new TaskNotFoundException();
        }
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Task> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Task> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskList // instanceof handles nulls
                && internalList.equals(((TaskList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if a task {@code task} exists in the task list.
     */
    public boolean contains(Task t) {
        requireNonNull(t);
        return internalList.contains(t);
    }
}
