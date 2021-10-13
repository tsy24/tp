package seedu.address.model.task;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Elderly;
import seedu.address.model.person.Name;

public class Task implements Comparable<Task> {

    private final Description desc;
    private final DateTime dateTime;
    private final Status status;
    private final Set<Name> relatedNames = new HashSet<>();

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
    }

    /**
     * Marks task as done.
     *
     * @return same task object that has been marked as done
     */
    public Task markAsDone() {
        if (isTaskDone()) {

        }
        return new Task(desc, dateTime, relatedNames, new Status("true"));
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
                    && other.getStatus().equals(this.status);
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
