package seedu.address.model.task;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Elderly;
import seedu.address.model.person.Name;

public class Task implements Comparable<Task> {
    private final Description desc;
    private final DateTime dateTime;
    private final boolean isDone;
    private final Set<Name> relatedNames = new HashSet<>();

    /**
     * Creates a Task object.
     *
     * @param desc                      the description of the task
     * @param dt                        the date & time of the task
     * @param names                     the names of people associated with the task
     * @return                          task created
     */
    public Task(Description desc, DateTime dt, Set<Name> names) {
        this.desc = desc;
        this.dateTime = dt;
        this.isDone = false;
        this.relatedNames.addAll(names);
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

    public Set<Name> getRelatedNames() {
        return relatedNames;
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
                    && other.isDone == this.isDone;
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
