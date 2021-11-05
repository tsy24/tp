package nurseybook.model.task;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import nurseybook.model.person.Name;
import nurseybook.model.task.Recurrence.RecurrenceType;

/**
 * Represents a Ghost Task in NurseyBook. Ghost tasks are tasks that are not meant to be shown to the user
 * as part of the main list of tasks.
 */
public class GhostTask extends Task {

    /**
     * Creates a Ghost Task object.
     * Ghost Tasks are tasks that are not meant to be shown to the user as part of the main list of tasks.
     *
     * @param desc                      the description of the task
     * @param dt                        the date and time of the task
     * @param names                     the names of people associated with the task
     * @param status                    the completion status of the task
     * @param recurrence                the recurrence type of the task
     */
    public GhostTask(Description desc, DateTime dt, Set<Name> names, Status status,
                     Recurrence recurrence) {
        super(desc, dt, names, status, recurrence);
    }

    /**
     * Copies the task and all it's fields and returns a new instance of it.
     *
     * @return A copy of the current task.
     */
    @Override
    public GhostTask copyTask() {
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

    /**
     * Marks task as done.
     *
     * @return A new duplicate task object, except that it is marked as done
     */
    @Override
    public GhostTask markAsDone() {
        String overdueStatus = Boolean.toString(isTaskOverdue());

        return new GhostTask(this.getDesc(), this.getDateTime(),
                super.getRelatedNames(), new Status("true", overdueStatus), super.getRecurrence());
    }

    /**
     * Marks task as overdue.
     *
     * @return A new duplicate task object, except that it is marked as overdue
     */
    @Override
    public GhostTask markAsOverdue() {
        String completedStatus = Boolean.toString(isTaskDone());

        return new GhostTask(this.getDesc(), this.getDateTime(),
                super.getRelatedNames(), new Status(completedStatus, "true"), super.getRecurrence());
    }


    /**
     * Resets the overdue status of the task.
     *
     * @return A new duplicate task object, except that it is marked as undone and not overdue
     */
    @Override
    public GhostTask markAsNotOverdue() {
        String completedStatus = Boolean.toString(isTaskDone());

        return new GhostTask(this.getDesc(), this.getDateTime(),
                super.getRelatedNames(), new Status(completedStatus, "false"), super.getRecurrence());
    }

    @Override
    public GhostTask updateDateRecurringTask() {
        LocalDateTime currentDateTime = LocalDateTime.now();

        if (getRecurrence().isRecurring()) {
            Recurrence.RecurrenceType recurrenceType = getRecurrence().getRecurrenceType();
            assert(recurrenceType != Recurrence.RecurrenceType.NONE);

            DateTime dateTime = changeTaskDate(currentDateTime, recurrenceType);
            return new GhostTask(getDesc(), dateTime,
                    getRelatedNames(), new Status("false", "false"), getRecurrence());
        } else {
            return this;
        }
    }

    /**
     * Copies the task and returns the next occurrence of it if it is a recurring task.
     * Keeps all other fields as it is.
     * If the task is not a recurring task, simply returns the current instance.
     *
     * @return A copy of the next occurrence of the given task if recurring; otherwise returns current instance.
     */
    public GhostTask createNextTaskOccurrence() {
        if (!this.isTaskRecurring()) {
            return this;
        }

        GhostTask copyTask = this.copyTask();

        DateTime nextDateTime;
        RecurrenceType taskRecurrenceType = copyTask.getRecurrence().getRecurrenceType();
        if (taskRecurrenceType == RecurrenceType.DAY) {
            nextDateTime = copyTask.getDateTime().incrementDateByDays(1);
        } else if (taskRecurrenceType == RecurrenceType.WEEK) {
            nextDateTime = copyTask.getDateTime().incrementDateByWeeks(1);
        } else { //taskRecurrenceType == RecurrenceType.MONTH
            //a month is assumed to be 4 weeks long only, since all months do not have an equivalent number of days.
            nextDateTime = copyTask.getDateTime().incrementDateByDays(28);
        }

        copyTask.setDateTime(nextDateTime);
        return copyTask;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof GhostTask) {

            return super.equals(obj);
        }
        return false;
    }
}
