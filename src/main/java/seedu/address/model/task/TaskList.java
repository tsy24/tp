package seedu.address.model.task;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
     * Mark the task {@code target} in the list as overdue.
     * {@code target} must exist in the list.
     */
    public void markTaskAsOverdue(Task toMark) {
        setTask(toMark, toMark.markAsOverdue());
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
     * Removes all tasks that are ghost tasks, if any.
     */
    public void deleteGhostTasks() {
        this.internalList.removeIf(task -> !task.checkIfRealTask());
    }

    /**
     * For each task in the TaskList, if it is a real and recurring task, all possible future occurrences of the task
     * are compared against the given keyDate. If any future task's date matches with the given keyDate,
     * the future task is added as a ghost task to the TaskList.
     */
    public void addPossibleGhostTasksWithMatchingDate(LocalDate keyDate) {
        List<Task> ghostTaskList = new ArrayList<Task>();
        for (Task task : this.internalList) {
            if (task.checkIfTaskRecurring() && task.checkIfRealTask()) {
                Task ghostTask = (addFutureGhostTasksWithMatchingDate(task, keyDate));
                if (ghostTask != null) {
                    ghostTaskList.add(ghostTask);
                }
            }
        }

        for (Task task : ghostTaskList) {
            this.add(task);
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

                changeTaskDate(currentDateTime, t, recurrenceType);
            }
        }
        // Re-sorts task list when task date is changed
        internalList.sort(Comparator.naturalOrder());
    }

    private void changeTaskDate(LocalDateTime currentDateTime, Task t,
                                                 RecurrenceType recurrenceType) {
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
            changeTaskDateBasedOnRecurrence(t, recurrenceType, taskLocalDateTime, daysBetween);
            this.markTaskAsUndone(t);
        }
    }

    private void changeTaskDateBasedOnRecurrence(Task t, RecurrenceType recurrenceType,
                                                 LocalDateTime taskLocalDateTime, long daysBetween) {
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

    /**
     * Checks if any of the given recurring task's future occurrences coincide with the given keyDate. If it does,
     * the future task is added as a ghost task to the TaskList.
     */
    private Task addFutureGhostTasksWithMatchingDate(Task task, LocalDate keyDate) {
        RecurrenceType taskRecurrenceType = task.getRecurrence().getRecurrenceType();
        int interval; //interval between task occurrences depending on RecurrenceType.
        if (taskRecurrenceType == RecurrenceType.DAY) {
            interval = 1;
        } else if (taskRecurrenceType == RecurrenceType.WEEK) {
            interval = 7;
        } else { //taskRecurrenceType == RecurrenceType.MONTH
            interval = 28;
        }

        Task nextTaskOccurrence = task.createNextTaskOccurrence();
        nextTaskOccurrence.setGhostTask();
        Task currTask = nextTaskOccurrence;

        //No. of days to check for recurring tasks in the future is set to 84 days, or 12 weeks.
        int daysLeftToCheck = 84 - interval;

        while (daysLeftToCheck > 0) {
            if (currTask.checkIfTaskFallsOnDate(keyDate) && !this.contains(currTask)) {
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
