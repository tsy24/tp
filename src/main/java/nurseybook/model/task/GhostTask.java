package nurseybook.model.task;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

import nurseybook.model.person.Name;

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
     * Copies the task and returns it.
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
     * @return same task object that has been marked as done
     */
    @Override
    public GhostTask markAsDone() {
        String overdueStatus = isTaskOverdue() ? "true" : "false";
        return new GhostTask(this.getDesc(), this.getDateTime(), super.getRelatedNames(),
                new Status("true", overdueStatus), super.getRecurrence());
    }

    /**
     * Marks task as overdue.
     *
     * @return same task object that has been marked as overdue
     */
    @Override
    public GhostTask markAsOverdue() {
        String completedStatus = isTaskDone() ? "true" : "false";
        return new GhostTask(this.getDesc(), this.getDateTime(), super.getRelatedNames(),
                new Status(completedStatus, "true"), super.getRecurrence());
    }


    /**
     * Resets the overdue status of the task.
     *
     * @return same task object that has been marked as undone and not overdue
     */
    @Override
    public GhostTask markAsNotOverdue() {
        String completedStatus = isTaskDone() ? "true" : "false";
        return new GhostTask(this.getDesc(), this.getDateTime(), super.getRelatedNames(),
                new Status(completedStatus, "false"), super.getRecurrence());
    }

    /**
     * Updates the date of the recurring task such that it is not overdue.
     *
     * @return same task object that has a date in the future
     */
    @Override
    public GhostTask updateDateRecurringTask() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        if (getRecurrence().isRecurring()) {
            Recurrence.RecurrenceType recurrenceType = getRecurrence().getRecurrenceType();
            assert(recurrenceType != Recurrence.RecurrenceType.NONE);

            DateTime dateTime = changeTaskDate(currentDateTime, recurrenceType);
            return new GhostTask(getDesc(), dateTime, getRelatedNames(),
                    new Status("false", "false"), getRecurrence());
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
        if (!this.checkIfTaskRecurring()) {
            return this;
        }

        GhostTask copyTask = this.copyTask();

        DateTime nextDateTime;
        Recurrence.RecurrenceType taskRecurrenceType = copyTask.getRecurrence().getRecurrenceType();
        if (taskRecurrenceType == Recurrence.RecurrenceType.DAY) {
            nextDateTime = copyTask.getDateTime().incrementDateByDays(1);
        } else if (taskRecurrenceType == Recurrence.RecurrenceType.WEEK) {
            nextDateTime = copyTask.getDateTime().incrementDateByWeeks(1);
        } else { //taskRecurrenceType == RecurrenceType.MONTH
            //a month is assumed to be 4 weeks long only, since all months do not have an equivalent number of days.
            nextDateTime = copyTask.getDateTime().incrementDateByDays(28);
        }

        copyTask.setDateTime(nextDateTime);
        return copyTask;
    }

    private DateTime changeTaskDate(LocalDateTime currentDateTime, Recurrence.RecurrenceType recurrenceType) {
        LocalDate taskDate = getDateTime().date;
        LocalTime taskTime = getDateTime().time;
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

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof GhostTask) {
            GhostTask other = (GhostTask) obj;
            return other.getDesc().equals(super.getDesc())
                    && other.getDateTime().equals(super.getDateTime())
                    && other.getRelatedNames().equals(super.getRelatedNames())
                    && other.getStatus().equals(super.getStatus())
                    && other.getRecurrence().equals(super.getRecurrence());
        }
        return false;
    }
}
