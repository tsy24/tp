package seedu.address.model.task;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Elderly;
import seedu.address.model.person.Name;

public class Task implements Comparable<Task> {

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
        this.recurrence = new Recurrence(Recurrence.RecurrenceType.NONE.name());
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
        this.recurrence = new Recurrence(Recurrence.RecurrenceType.NONE.name());
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
    public Task markAsDone() {
        String overdueStatus = isTaskOverdue() ? "true" : "false";

        return new Task(desc, dateTime, relatedNames, new Status("true", overdueStatus), recurrence);
    }

    /**
     * Marks task as overdue.
     *
     * @return same task object that has been marked as overdue
     */
    public Task markAsOverdue() {
        String completedStatus = isTaskDone() ? "true" : "false";
        return new Task(desc, dateTime, relatedNames, new Status(completedStatus, "true"), recurrence);
    }

    /**
     * Resets the overdue status of the task.
     *
     * @return same task object that has been marked as undone and not overdue
     */
    public Task markAsNotOverdue() {
        String completedStatus = isTaskDone() ? "true" : "false";
        return new Task(desc, dateTime, relatedNames,
                new Status(completedStatus, "false"), recurrence);
    }

    /**
     * Updates the date of the recurring task such that it is not overdue.
     *
     * @return same task object that has a date in the future
     */
    public Task updateDateRecurringTask() {
        LocalDateTime currentDateTime = LocalDateTime.now();

        Recurrence.RecurrenceType recurrenceType = recurrence.getRecurrenceType();
        assert(recurrenceType != Recurrence.RecurrenceType.NONE);

        DateTime dateTime = changeTaskDate(currentDateTime, recurrenceType);
        return new Task(desc, dateTime, relatedNames,
                new Status("false", "false"), recurrence);
    }

    private DateTime changeTaskDate(LocalDateTime currentDateTime, Recurrence.RecurrenceType recurrenceType) {
        LocalDate taskDate = dateTime.date;
        LocalTime taskTime = dateTime.time;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd MM yyyy HH mm");
        String inputString = String.format("%02d", taskDate.getDayOfMonth()) + " "
                + String.format("%02d", taskDate.getMonthValue()) + " "
                + String.format("%02d", taskDate.getYear()) + " "
                + String.format("%02d", taskTime.getHour()) + " "
                + String.format("%02d", taskTime.getMinute());
        LocalDateTime taskLocalDateTime = LocalDateTime.parse(inputString, dtf);
        long daysBetween = Duration.between(taskLocalDateTime, currentDateTime).toDays();

        return changeTaskDateBasedOnRecurrence(recurrenceType, taskLocalDateTime, daysBetween);
    }

    private DateTime changeTaskDateBasedOnRecurrence(Recurrence.RecurrenceType recurrenceType,
                                                 LocalDateTime taskLocalDateTime, long daysBetween) {
        assert(daysBetween > 0);
        LocalDateTime taskNewLocalDateTime = taskLocalDateTime;
        if (recurrenceType == Recurrence.RecurrenceType.DAY) {
            taskNewLocalDateTime = taskLocalDateTime.plusDays(daysBetween + 1);
        } else if (recurrenceType == Recurrence.RecurrenceType.WEEK) {
            int daysToAdd = ((int) (daysBetween / 7)) * 7 + 7;
            taskNewLocalDateTime = taskLocalDateTime.plusDays(daysToAdd);
        } else {
            // assume its + 4 weeks
            int daysToAdd = ((int) (daysBetween / 28)) * 28 + 28;
            taskNewLocalDateTime = taskLocalDateTime.plusDays(daysToAdd);
        }
        // time is fixed
        return new DateTime(taskNewLocalDateTime.toLocalDate(), taskLocalDateTime.toLocalTime());
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
     * Returns names of elderly related to this task.
     *
     * @return related names
     */
    public Set<Name> getRelatedNames() {
        return relatedNames;
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
     * Returns set of elderly objects related to this task.
     *
     * @param book                      address book that stores this task
     * @return                          task description
     */
    public Set<Elderly> getRelatedPeople(AddressBook book) {
        Set<Elderly> relatedPeople = new HashSet<>();
        for (Name name: relatedNames) {
            relatedPeople.add(book.getElderly(name));
        }
        return relatedPeople;
    }

    public boolean changeDateToPastWhenRecurring() {
       return DateTime.isOverdue(this.dateTime) && recurrence.isRecurring();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Task) {
            Task other = (Task) obj;
            return other.getDesc().equals(this.desc)
                    && other.getDateTime().equals(this.dateTime)
                    && other.getRelatedNames().equals(this.relatedNames)
                    && other.getStatus().equals(this.status)
                    && other.getRecurrence().equals(this.recurrence);
        }
        return false;
    }

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
