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

public class Task implements Comparable<Task> {

    private final Description desc;
    private DateTime dateTime;
    private final Status status;
    private final Set<Name> relatedNames = new HashSet<>();
    private final Recurrence recurrence;
    private GhostTask ghostTask;

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
        this.ghostTask = new GhostTask("false");
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
        this.ghostTask = new GhostTask("false");
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
        this.ghostTask = new GhostTask("false");
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
        this.ghostTask = new GhostTask("false");
    }

    /**
     * Creates a Task object that can potentially be a Ghost Task object.
     * Ghost Tasks are tasks that are not meant to be shown to the user as part of the main list of tasks.
     *
     * @param desc                      the description of the task
     * @param dt                        the date and time of the task
     * @param names                     the names of people associated with the task
     * @param status                    the completion status of the task
     * @param recurrence                the recurrence type of the task
     * @param ghostTask                 ghost identity of the task
     */
    public Task(Description desc, DateTime dt, Set<Name> names, Status status,
                 Recurrence recurrence, GhostTask ghostTask) {
        this.desc = desc;
        this.dateTime = dt;
        this.relatedNames.addAll(names);
        this.status = status;
        this.recurrence = recurrence;
        this.ghostTask = ghostTask;

    }

    /**
     * Creates a Ghost Task.
     * Ghost Tasks are tasks that are not meant to be shown to the user as part of the main list of tasks.
     *
     * @param desc                      the description of the task
     * @param dt                        the date and time of the task
     * @param names                     the names of people associated with the task
     * @param status                    the completion status of the task
     * @param recurrence                the recurrence type of the task
     * @return Ghost Task.
     */
    public Task createGhostTask(Description desc, DateTime dt, Set<Name> names, Status status, Recurrence recurrence) {
        return new Task(desc, dt, names, status, recurrence, new GhostTask("true"));
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
                new Status(completedStatus, "false"), recurrence, ghostTask);
    }

    /**
     * Updates the date of the recurring task such that it is not overdue.
     *
     * @return same task object that has a date in the future
     */
    public Task updateDateRecurringTask() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        if (recurrence.isRecurring()) {
            RecurrenceType recurrenceType = recurrence.getRecurrenceType();
            assert(recurrenceType != RecurrenceType.NONE);

            DateTime dateTime = changeTaskDate(currentDateTime, recurrenceType);
            return new Task(desc, dateTime, relatedNames,
                    new Status("false", "false"), recurrence);
        } else {
            return this;
        }


    }

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
        return !this.ghostTask.checkIfGhostTask();
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
     * Converts the given task to a ghost task.
     */
    public void setGhostTask() {
        //TODO change variables to new copies. new Desc(desc) etc.
        ghostTask = new GhostTask("true");
    }

    private DateTime changeTaskDate(LocalDateTime currentDateTime, RecurrenceType recurrenceType) {
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

    private DateTime changeTaskDateBasedOnRecurrence(RecurrenceType recurrenceType,
                                                 LocalDateTime taskLocalDateTime, long daysBetween) {
        assert(daysBetween > 0);
        LocalDateTime taskNewLocalDateTime = taskLocalDateTime;
        if (recurrenceType == RecurrenceType.DAY) {
            taskNewLocalDateTime = taskLocalDateTime.plusDays(daysBetween + 1);
        } else if (recurrenceType == RecurrenceType.WEEK) {
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
     * Returns an immutable name set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Name> getRelatedNames() {
        return Collections.unmodifiableSet(relatedNames);
    }

    /**
     * Replaces the name {@code target} of the task with {@code editedName}.
     */
    public void replaceName(Name target, Name editedName) {
        deleteName(target);
        addName(editedName);
    }

    /**
     * Deletes the name {@code target} of the task.
     */
    public void deleteName(Name target) {
        relatedNames.remove(target);
    }

    /**
     * Adds the name {@code target} of the task.
     */
    public void addName(Name target) {
        relatedNames.add(target);
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

    /**
     * Returns identity (ghost task or not) of this task.
     *
     * @return identity of this task
     */
    public GhostTask getGhostTask() {
        return ghostTask;
    }

    public void setDateTime(DateTime dt) {
        this.dateTime = dt;
    }

    /**
     * Copies the task and returns it.
     *
     * @return A copy of the current task.
     */
    public Task copyTask() {
        Description copyDesc = new Description(this.desc.value);
        DateTime copyDt = new DateTime(this.dateTime.getStringDate(), this.dateTime.getStringTime());
        Set<Name> copyRelatedNames = new HashSet<>();
        for (Name name : relatedNames) {
            copyRelatedNames.add(new Name(name.fullName));
        }
        Status copyStatus = new Status(this.status.isDone, this.status.isOverdue);
        Recurrence copyRecurrence = new Recurrence(this.recurrence.toString());

        if (ghostTask.checkIfGhostTask()) {
            return createGhostTask(copyDesc, copyDt, copyRelatedNames, copyStatus, copyRecurrence);
        } else {
            return new Task(copyDesc, copyDt, copyRelatedNames, copyStatus, copyRecurrence);
        }
    }

    /**
     * Copies the task and returns the next occurrence of it if it is a recurring task.
     * Keeps all other fields as it is.
     * If the task is not a recurring task, simply returns the current instance.
     *
     * @return A copy of the next occurrence of the given task if recurring; otherwise returns current instance.
     */
    public Task createNextTaskOccurrence() {
        if (!this.checkIfTaskRecurring()) {
            return this;
        }

        Task copyTask = this.copyTask();

        DateTime nextDateTime;
        RecurrenceType taskRecurrenceType = copyTask.recurrence.getRecurrenceType();
        if (taskRecurrenceType == RecurrenceType.DAY) {
            nextDateTime = copyTask.dateTime.incrementDateByDays(1);
        } else if (taskRecurrenceType == RecurrenceType.WEEK) {
            nextDateTime = copyTask.dateTime.incrementDateByWeeks(1);
        } else { //taskRecurrenceType == RecurrenceType.MONTH
            //a month is assumed to be 4 weeks long only, since all months do not have an equivalent number of days.
            nextDateTime = copyTask.dateTime.incrementDateByDays(28);
        }

        copyTask.setDateTime(nextDateTime);
        return copyTask;

    }

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
                && otherTask.getDesc().equals(getDesc()) && otherTask.getDateTime().equals(getDateTime())
                && otherTask.getRelatedNames().equals(getRelatedNames());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Task) {
            Task other = (Task) obj;
            return other.getDesc().equals(this.desc)
                    && other.getDateTime().equals(this.dateTime)
                    && other.getRelatedNames().equals(this.relatedNames)
                    && other.getStatus().equals(this.status)
                    && other.getRecurrence().equals(this.recurrence)
                    && other.getGhostTask().equals(this.ghostTask);
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
