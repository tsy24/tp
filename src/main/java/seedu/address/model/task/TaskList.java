package seedu.address.model.task;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.task.Recurrence.RecurrenceType;
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
     */
    private void setTask(Task target, Task editedTask) {
        requireAllNonNull(target, editedTask);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new TaskNotFoundException();
        }
        internalList.set(index, editedTask);
    }

    /**
     * Mark the task {@code target} in the list as done.
     * {@code target} must exist in the list.
     */
    public void markTaskAsDone(Task toMark) {
        setTask(toMark, toMark.markAsDone());
    }

    /**
     * Mark the task {@code target} in the list as undone.
     * {@code target} must exist in the list.
     */
    public void markTaskAsUndone(Task toMark) {
        setTask(toMark, toMark.markAsUndone());
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
     * If a recurring task's original DateTime is before the current {@code DateTime},
     * changes the task's {@code DateTime} to the next earliest {@code DateTime},
     * according to whether is it a {@code DAY}, {@code WEEK} or {@code MONTH} recurrence type.
     * At the same time, after changing the {@code DateTime},
     * it also maintains the sorted order of tasks according to {@code DateTime}.
     * All tasks which had their {@code DateTime} changed will also have their status's {@code isDone} as false.
     */
    public void changeDateOfPastRecurringTasks() {
        LocalDateTime currentDateTime = LocalDateTime.now();

        for (Task t : this.internalList) {
            Recurrence recurrence = t.getRecurrence();
            if (recurrence.isRecurring()) {
                RecurrenceType recurrenceType = recurrence.getRecurrenceType();
                assert(recurrenceType != RecurrenceType.NONE);
                DateTime taskDateTime = t.getDateTime();
                LocalDate taskDate = taskDateTime.date;
                LocalTime taskTime = taskDateTime.time;
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd MM yyyy HH mm");
                String inputString = String.format("%02d", taskDate.getDayOfMonth()) + " "
                        + String.format("%02d", taskDate.getMonthValue()) + " "
                        + String.format("%02d", taskDate.getYear()) + " "
                        + String.format("%02d", taskTime.getHour()) + " "
                        + String.format("%02d", taskTime.getMinute());
                LocalDateTime taskLocalDateTime = LocalDateTime.parse(inputString, dtf);
                long daysBetween = Duration.between(taskLocalDateTime, currentDateTime).toDays();
                // if task dateTime is before current dateTime
                if (daysBetween > 0) {
                    LocalDateTime taskNewLocalDateTime;
                    if (recurrenceType == RecurrenceType.DAY) {
                        taskNewLocalDateTime = taskLocalDateTime.plusDays(daysBetween + 1);
                    } else if (recurrenceType == RecurrenceType.WEEK) {
                        int daysToAdd = (int) (daysBetween / 7) * 7 + 7;
                        taskNewLocalDateTime = taskLocalDateTime.plusDays(daysToAdd);
                    } else {
                        // assume its + 4 weeks
                        int daysToAdd = (int) (daysBetween / 28) * 28 + 28;
                        taskNewLocalDateTime = taskLocalDateTime.plusDays(daysToAdd);
                    }
                    // time is fixed
                    t.setDateTime(
                            new DateTime(taskNewLocalDateTime.toLocalDate(), taskLocalDateTime.toLocalTime()));
                }
                this.markTaskAsUndone(t);
            }
        }
        internalList.sort(Comparator.naturalOrder());
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
