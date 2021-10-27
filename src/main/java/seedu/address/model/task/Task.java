package seedu.address.model.task;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Elderly;
import seedu.address.model.person.Name;

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
        this.desc = desc;
        this.dateTime = dt;
        this.relatedNames.addAll(names);
        this.status = new Status("false");
        this.recurrence = new Recurrence(Recurrence.RecurrenceType.NONE.name());
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
        this.desc = desc;
        this.dateTime = dt;
        this.relatedNames.addAll(names);
        this.status = new Status("false");
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
        this.recurrence = new Recurrence(Recurrence.RecurrenceType.NONE.name());
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
        return new Task(desc, dt, names, status, recurrence,new GhostTask("true"));
    }

    /**
     * Marks task as done.
     *
     * @return same task object that has been marked as done
     */
    public Task markAsDone() {
        return new Task(desc, dateTime, relatedNames, new Status("true"), recurrence);
    }

    public Task markAsUndone() {
        return new Task(desc, dateTime, relatedNames, new Status("false"), recurrence);
    }

    /**
     * Checks if this task is a recurring task.
     *
     * @return True if the task is a recurring task; false otherwise.
     */
    public boolean isTaskRecurring() {
        return this.recurrence.isRecurring();
    }

    /**
     * Checks if this is a real task.
     *
     * @return True if real task; false if ghost task.
     */
    public boolean isRealTask() {
        return !this.ghostTask.checkIfGhostTask();
    }

    /**
     * Checks if this task falls on the same date as the given date.
     *
     * @return True if the task is on the same date; false otherwise.
     */
    public boolean isSameDateTask(LocalDate givenDate) {
        return this.dateTime.isSameDate(givenDate);
    }

    /**
     * Converts the given task to a ghost task.
     */
    public void setGhostTask() {
        //TODO change variables to new copies. new Desc(desc) etc.
        ghostTask = new GhostTask("true");
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
     * Returns set date of this task.
     *
     * @return task description
     */
    public DateTime getDateTime() {
        return dateTime;
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
        Status copyStatus = new Status(this.status.toString());
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
        if (!this.isTaskRecurring()) {
            return this;
        }

        Task copyTask = this.copyTask();

        DateTime nextDateTime;
        Recurrence.RecurrenceType taskRecurrenceType = copyTask.recurrence.getRecurrenceType();
        if (taskRecurrenceType == Recurrence.RecurrenceType.DAY) {
            nextDateTime = copyTask.dateTime.incrementDateByDays(1);
        } else if (taskRecurrenceType == Recurrence.RecurrenceType.WEEK) {
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
