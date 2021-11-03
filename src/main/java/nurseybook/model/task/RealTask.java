package nurseybook.model.task;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import nurseybook.model.person.Name;

/**
 * Represents a Real Task in NurseyBook.
 */
public class RealTask extends Task {

    /**
     * Creates a RealTask object.
     *
     * @param desc                      the description of the task
     * @param dt                        the date and time of the task
     * @param names                     the names of people associated with the task
     * @param recurrence                the recurrence type of the task
     */
    public RealTask(Description desc, DateTime dt, Set<Name> names, Recurrence recurrence) {
        super(desc, dt, names, recurrence);
    }

    /**
     * Creates a RealTask object.
     *
     * @param desc                      the description of the task
     * @param dt                        the date and time of the task
     * @param names                     the names of people associated with the task
     * @param status                    the completion status of the task
     * @param recurrence                the recurrence type of the task
     */
    public RealTask(Description desc, DateTime dt, Set<Name> names, Status status, Recurrence recurrence) {
        super(desc, dt, names, status, recurrence);
    }

    /**
     * Marks task as done.
     *
     * @return A new duplicate task object, except that it is marked as done
     */
    @Override
    public RealTask markAsDone() {
        String overdueStatus = Boolean.toString(isTaskOverdue());

        return new RealTask(this.getDesc(),
                this.getDateTime(), super.getRelatedNames(), new Status("true", overdueStatus), super.getRecurrence());
    }

    /**
     * Marks task as overdue.
     *
     * @return A new duplicate task object, except that it is marked as overdue
     */
    @Override
    public RealTask markAsOverdue() {
        String completedStatus = Boolean.toString(isTaskDone());

        return new RealTask(this.getDesc(), this.getDateTime(),
                super.getRelatedNames(), new Status(completedStatus, "true"), super.getRecurrence());
    }


    /**
     * Resets the overdue status of the task.
     *
     * @return A new duplicate task object, except that it is marked as undone and not overdue
     */
    @Override
    public RealTask markAsNotOverdue() {
        String completedStatus = Boolean.toString(isTaskDone());

        return new RealTask(this.getDesc(), this.getDateTime(),
                super.getRelatedNames(), new Status(completedStatus, "false"), super.getRecurrence());
    }

    /**
     * Updates the date of the recurring task such that it is not overdue.
     *
     * @return A new duplicate task object, except with a date in the future
     */
    @Override
    public RealTask updateDateRecurringTask() {
        LocalDateTime currentDateTime = LocalDateTime.now();

        if (getRecurrence().isRecurring()) {
            Recurrence.RecurrenceType recurrenceType = getRecurrence().getRecurrenceType();
            assert(recurrenceType != Recurrence.RecurrenceType.NONE);

            DateTime dateTime = changeTaskDate(currentDateTime, recurrenceType);
            return new RealTask(getDesc(), dateTime,
                    getRelatedNames(), new Status("false", "false"), getRecurrence());
        } else {
            return this;
        }
    }

    /**
     * Copies the task and returns it.
     *
     * @return A copy of the current task.
     */
    public RealTask copyTask() {
        Description copyDesc = new Description(getDesc().value);
        DateTime copyDt = new DateTime(getDateTime().getStringDate(), getDateTime().getStringTime());
        Set<Name> copyRelatedNames = new HashSet<>();
        for (Name name : getRelatedNames()) {
            copyRelatedNames.add(new Name(name.fullName));
        }
        Status copyStatus = new Status(getStatus().isDone, getStatus().isOverdue);
        Recurrence copyRecurrence = new Recurrence(getRecurrence().toString());

        return new RealTask(copyDesc, copyDt, copyRelatedNames, copyStatus, copyRecurrence);
    }

    /**
     * Copies the contents of this task into a new GhostTask.
     *
     * @return A copy of the contents of this task into a new GhostTask.
     */
    public GhostTask copyToGhostTask() {
        Description copyDesc = new Description(getDesc().value);
        DateTime copyDt = new DateTime(getDateTime().getStringDate(), getDateTime().getStringTime());
        Set<Name> copyRelatedNames = new HashSet<>();
        for (Name name : getRelatedNames()) {
            copyRelatedNames.add(new Name(name.fullName));
        }
        Status copyStatus = new Status(getStatus().isDone, getStatus().isOverdue);
        Recurrence copyRecurrence = new Recurrence(getRecurrence().toString());

        return new GhostTask(copyDesc, copyDt, copyRelatedNames, copyStatus, copyRecurrence);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof RealTask) {

            return super.equals(obj);
        }
        return false;
    }

}
