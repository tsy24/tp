package nurseybook.model.task;

import static java.util.Objects.requireNonNull;
import static nurseybook.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import nurseybook.model.task.Recurrence.RecurrenceType;
import nurseybook.model.task.exceptions.DuplicateTaskException;
import nurseybook.model.task.exceptions.TaskNotFoundException;

/**
 * A list of tasks that does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 */
public class UniqueTaskList implements Iterable<Task> {

    private final ObservableList<Task> internalList = FXCollections.observableArrayList();
    private final ObservableList<Task> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Adds a task to the list and sorts the list after each addition by the DateTime
     * field of each Task instance.
     */
    public void add(Task toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateTaskException();
        }
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

        if (!target.isSameTask(editedTask) && contains(editedTask)) {
            throw new DuplicateTaskException();
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
    public void setTasks(UniqueTaskList replacement) {
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
     * Creates and returns a UniqueTaskList consisting of only the real tasks within this task list.
     *
     * @returns New task List consisting of only the real tasks within this task list.
     */
    public UniqueTaskList getRealTaskList() {
        UniqueTaskList realTaskList = new UniqueTaskList();
        for (Task task : this.internalList) {
            if (task.isRealTask()) {
                realTaskList.add(task);
            }
        }

        return realTaskList;
    }


    /**
     * Removes all tasks that are ghost tasks, if any.
     */
    public void deleteGhostTasks() {
        this.internalList.removeIf(task -> !task.isRealTask());
    }

    /**
     * For each task in the TaskList, if it is a real and recurring task, all possible future occurrences of the task
     * are compared against the given keyDate. If any future task's date matches with the given keyDate,
     * the future task is added as a ghost task to the TaskList.
     */
    public void addPossibleGhostTasksWithMatchingDate(LocalDate keyDate) {
        List<GhostTask> ghostTaskList = new ArrayList<GhostTask>();
        for (Task task : this.internalList) {
            if (task.isTaskRecurring() && task.isRealTask()) {
                GhostTask ghostTask = createPossibleFutureTaskWithMatchingDate((RealTask) task, keyDate);
                if (ghostTask != null) {
                    ghostTaskList.add(ghostTask);
                }
            }
        }

        for (GhostTask ghostTask : ghostTaskList) {
            this.add(ghostTask);
        }
    }

    /**
     * Checks if any of the given recurring task's future occurrences coincide with the given keyDate. If it does,
     * a GhostTask is created and returned to represent the future task occurrence.
     */
    private GhostTask createPossibleFutureTaskWithMatchingDate(RealTask task, LocalDate keyDate) {
        RecurrenceType taskRecurrenceType = task.getRecurrence().getRecurrenceType();
        int interval; //interval between task occurrences depending on RecurrenceType.
        if (taskRecurrenceType == RecurrenceType.DAY) {
            interval = 1;
        } else if (taskRecurrenceType == RecurrenceType.WEEK) {
            interval = 7;
        } else { //taskRecurrenceType == RecurrenceType.MONTH
            interval = 28;
        }

        GhostTask ghostTaskCopy = task.copyToGhostTask();
        GhostTask currTask = ghostTaskCopy.createNextTaskOccurrence();

        //No. of days to check for recurring tasks in the future is set to 84 days, or 12 weeks.
        int daysLeftToCheck = 84 - interval;

        while (daysLeftToCheck > 0) {
            if (currTask.doesTaskFallOnDate(keyDate) && !this.contains(currTask)) {
                return currTask;
            }

            currTask = currTask.createNextTaskOccurrence();
            daysLeftToCheck -= interval;
        }
        return null;
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
                || (other instanceof UniqueTaskList // instanceof handles nulls
                && internalList.equals(((UniqueTaskList) other).internalList));
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
        return internalList.stream().anyMatch(t::isSameTask);
    }
}
