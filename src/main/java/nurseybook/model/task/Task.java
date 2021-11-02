package nurseybook.model.task;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import nurseybook.model.NurseyBook;
import nurseybook.model.person.Elderly;
import nurseybook.model.person.Name;
import nurseybook.model.task.Recurrence.RecurrenceType;

public abstract class Task implements Comparable<Task> {

    private final Description desc;
    private DateTime dateTime;
    private final Status status;
    private final Set<Name> relatedNames = new HashSet<>();
    private final Recurrence recurrence;

    /**
     * Creates a Task object.
     *
     * @param desc                      the description of the task
     * @param dt                        the date and time of the task
     * @param names                     the names of people associated with the task
     */
    public Task(Description desc, DateTime dt, Set<Name> names) {
        boolean isOverdue = DateTime.isOverdue(dt);

        this.desc = desc;
        this.dateTime = dt;
        this.relatedNames.addAll(names);
        this.status = new Status("false", Boolean.toString(isOverdue));
        this.recurrence = new Recurrence(RecurrenceType.NONE.name());
    }

    /**
     * Creates a Task object.
     *
     * @param desc                      the description of the task
     * @param dt                        the date and time of the task
     * @param names                     the names of people associated with the task
     * @param recurrence                the recurrence type of the task
     */
    public Task(Description desc, DateTime dt, Set<Name> names, Recurrence recurrence) {
        boolean isOverdue = DateTime.isOverdue(dt);

        this.desc = desc;
        this.dateTime = dt;
        this.relatedNames.addAll(names);
        this.status = new Status("false", Boolean.toString(isOverdue));
        this.recurrence = recurrence;
    }

    /**
     * Creates a Task object.
     *
     * @param desc                      the description of the task
     * @param dt                        the date and time of the task
     * @param names                     the names of people associated with the task
     * @param status                    the completion status of the task
     */
    public Task(Description desc, DateTime dt, Set<Name> names, Status status) {
        this.desc = desc;
        this.dateTime = dt;
        this.relatedNames.addAll(names);
        this.status = status;
        this.recurrence = new Recurrence(RecurrenceType.NONE.name());
    }

    /**
     * Creates a Task object.
     *
     * @param desc                      the description of the task
     * @param dt                        the date and time of the task
     * @param names                     the names of people associated with the task
     * @param status                    the completion status of the task
     * @param recurrence                the recurrence type of the task
     */
    public Task(Description desc, DateTime dt, Set<Name> names, Status status, Recurrence recurrence) {
        this.desc = desc;
        this.dateTime = dt;
        this.relatedNames.addAll(names);
        this.status = status;
        this.recurrence = recurrence;
    }

    /**
     * Marks task as done.
     *
     * @return same task object that has been marked as done
     */
    public abstract Task markAsDone();

    /**
     * Marks task as overdue.
     *
     * @return same task object that has been marked as overdue
     */
    public abstract Task markAsOverdue();

    /**
     * Resets the overdue status of the task.
     *
     * @return same task object that has been marked as undone and not overdue
     */
    public abstract Task markAsNotOverdue();

    /**
     * Updates the date of the recurring task such that it is not overdue.
     *
     * @return same task object that has a date in the future
     */
    public abstract Task updateDateRecurringTask();

    /**
     * Checks if this task is a recurring task.
     *
     * @return True if the task is a recurring task; false otherwise.
     */
    public boolean checkIfTaskRecurring() {
        return this.recurrence.isRecurring();
    }

    /**
     * Checks if this is a real task.
     *
     * @return True if real task; false if ghost task.
     */
    public boolean checkIfRealTask() {
        return (this instanceof RealTask);
    }

    /**
     * Checks if this task falls on the same date as the given date.
     *
     * @return True if the task is on the same date; false otherwise.
     */
    public boolean checkIfTaskFallsOnDate(LocalDate givenDate) {
        return this.dateTime.isSameDate(givenDate);
    }

    /**
     * Returns task description of this task.
     *
     * @return task description
     */
    public Description getDesc() {
        return desc;
    }

    /**
     * Returns the dateTime of this task.
     *
     * @return task dateTime
     */
    public DateTime getDateTime() {
        return dateTime;
    }

    /**
     * Returns the date of this task.
     *
     * @return task date
     */
    public LocalDate getDate() {
        return dateTime.date;
    }

    /**
     * Returns the time of this task.
     *
     * @return task time
     */
    public LocalTime getTime() {
        return dateTime.time;
    }

    /**
     * Returns an immutable name set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Name> getRelatedNames() {
        return Collections.unmodifiableSet(relatedNames);
    }

    /**
     * Returns completion status of this task.
     *
     * @return task completion status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Returns the recurrence type of this task.
     *
     * @return task recurrence type
     */
    public Recurrence getRecurrence() {
        return recurrence;
    }

    public void setDateTime(DateTime dt) {
        this.dateTime = dt;
    }

    /**
     * Copies the task and returns it.
     *
     * @return A copy of the current task.
     */
    public abstract Task copyTask();

    public void setDate(LocalDate newDate) {
        this.dateTime = new DateTime(newDate, this.dateTime.getTime());
    }

    /**
     * Returns true if task dateTime is after the date given in the argument.
     *
     * @param date                      date to be compared to
     * @return                          true if is after, false otherwise
     */
    public boolean isAfter(DateTime date) {
        return dateTime.isAfter(date);
    }

    /**
     * Returns true if task dateTime is before the date given in the argument.
     *
     * @param date                      date to be compared to
     * @return                          true if is before, false otherwise
     */
    public boolean isBefore(DateTime date) {
        return dateTime.isBefore(date);
    }

    /**
     * Returns true if task has been marked as complete
     */
    public boolean isTaskDone() {
        return status.isDone;
    }

    /**
     * Returns true if task is overdue
     */
    public boolean isTaskOverdue() {
        return status.isOverdue;
    }

    /**
     * Returns true if task is recurring and overdue
     */
    public boolean isTaskRecurringAndOverdue() {
        return getRecurrence().isRecurring() && DateTime.isOverdue(getDateTime());
    }

    /**
     * Returns set of elderly objects related to this task.
     *
     * @param book                      nursey book that stores this task
     * @return                          task description
     */
    public Set<Elderly> getRelatedPeople(NurseyBook book) {
        Set<Elderly> relatedPeople = new HashSet<>();
        for (Name name: relatedNames) {
            relatedPeople.add(book.getElderly(name));
        }
        return relatedPeople;
    }

    /**
     * Returns true if the task is past the current date and time, and it is a recurring task.
     *
     * @return true if its past and is a recurring task
     */
    public boolean isPastCurrentDateAndRecurringTask() {
        return DateTime.isOverdue(this.dateTime) && recurrence.isRecurring();
    }

    /**
     * Returns true if both tasks have the same {@code Description} and {@code DateTime}.
     * This defines a weaker notion of equality between two tasks.
     */
    public boolean isSameTask(Task otherTask) {
        if (otherTask == this) {
            return true;
        }

        return otherTask != null
                && otherTask.getDesc().equals(getDesc()) && otherTask.getDateTime().equals(getDateTime());
    }

    //TODO Delete equals

    //    @Override
    //    public boolean equals(Object obj) {
    //        if (obj instanceof Task) {
    //            Task other = (Task) obj;
    //            return other.getDesc().equals(this.desc)
    //                    && other.getDateTime().equals(this.dateTime)
    //                    && other.getRelatedNames().equals(this.relatedNames)
    //                    && other.getStatus().equals(this.status)
    //                    && other.getRecurrence().equals(this.recurrence)
    //                    && other.getGhostTask().equals(this.ghostTask);
    //        }
    //        return false;
    //    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getDesc())
                .append("; When: ")
                .append(getDateTime());
        if (!relatedNames.isEmpty()) {
            builder.append("; People: ");
            relatedNames.forEach(name -> {
                builder.append(name + " ");
            });
        }
        return builder.toString();
    }

    @Override
    public int compareTo(Task o) {
        return this.dateTime.compareTo(o.dateTime);
    }
}
