package nurseybook.testutil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import nurseybook.model.person.Name;
import nurseybook.model.task.DateTime;
import nurseybook.model.task.Description;
import nurseybook.model.task.GhostTask;
import nurseybook.model.task.RealTask;
import nurseybook.model.task.Recurrence;
import nurseybook.model.task.Status;
import nurseybook.model.task.Task;

public class TaskBuilder {

    public static final String DEFAULT_DESC = "inject 5000 mg tylenol";
    public static final String DEFAULT_DATE = "2069-06-09";
    public static final String DEFAULT_TIME = "18:09";
    public static final List<String> DEFAULT_STATUS = Arrays.asList("false", "false");
    public static final String DEFAULT_RECURRENCE = Recurrence.RecurrenceType.NONE.name();

    private Description desc;
    private DateTime dateTime;
    private Set<Name> names;
    private Status status;
    private Recurrence recurrence;
    private Boolean isRealTask;

    /**
     * Creates a {@code TaskBuilder} with the default details.
     */
    public TaskBuilder() {
        desc = new Description(DEFAULT_DESC);
        dateTime = new DateTime(DEFAULT_DATE, DEFAULT_TIME);
        names = new HashSet<>();

        String isDone = DEFAULT_STATUS.get(0);
        String isOverdue = DEFAULT_STATUS.get(1);
        status = new Status(isDone, isOverdue);

        recurrence = new Recurrence(DEFAULT_RECURRENCE);
        isRealTask = true;
    }

    /**
     * Creates a {@code TaskBuilder} with the default details.
     *
     * @param isRealTask Whether the task to build is a real or ghost task.
     */
    public TaskBuilder(Boolean isRealTask) {
        desc = new Description(DEFAULT_DESC);
        dateTime = new DateTime(DEFAULT_DATE, DEFAULT_TIME);
        names = new HashSet<>();

        String isDone = DEFAULT_STATUS.get(0);
        String isOverdue = DEFAULT_STATUS.get(1);
        status = new Status(isDone, isOverdue);

        recurrence = new Recurrence(DEFAULT_RECURRENCE);
        this.isRealTask = isRealTask;
    }

    /**
     * Initializes the TaskBuilder with the data of {@code taskToCopy}. Task type of task is determined by
     * {@code isRealTask}.
     *
     * @param taskToCopy Task to copy.
     * @param isRealTask Whether the task built is real or a ghost task.
     */
    public TaskBuilder(Task taskToCopy, Boolean isRealTask) {
        desc = taskToCopy.getDesc();
        dateTime = taskToCopy.getDateTime();
        names = new HashSet<>(taskToCopy.getRelatedNames());
        status = taskToCopy.getStatus();
        recurrence = taskToCopy.getRecurrence();
        this.isRealTask = isRealTask;
    }

    /**
     * Initializes the TaskBuilder with the data of {@code taskToCopy}.
     */
    public TaskBuilder(Task taskToCopy) {
        desc = taskToCopy.getDesc();
        dateTime = taskToCopy.getDateTime();
        names = new HashSet<>(taskToCopy.getRelatedNames());
        status = taskToCopy.getStatus();
        recurrence = taskToCopy.getRecurrence();
        isRealTask = taskToCopy.isRealTask();
    }

    /**
     * Sets the {@code Description} of the {@code Task} that we are building.
     */
    public TaskBuilder withDesc(String desc) {
        this.desc = new Description(desc);
        return this;
    }

    /**
     * Sets the {@code DateTime} of the {@code Task} that we are building.
     */
    public TaskBuilder withDateTime(String date, String time) {
        this.dateTime = new DateTime(date, time);
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code Task} that we are building.
     */
    public TaskBuilder withDate(String dateStr) {
        LocalDate date = LocalDate.parse(dateStr);
        this.dateTime = new DateTime(date, this.dateTime.time);
        return this;
    }

    /**
     * Sets the {@code Time} of the {@code Task} that we are building.
     */
    public TaskBuilder withTime(String timeStr) {
        LocalTime time = LocalTime.parse(timeStr);
        this.dateTime = new DateTime(this.dateTime.date, time);
        return this;
    }

    /**
     * Sets the {@code DateTime} of the {@code Task} that we are building.
     */
    public TaskBuilder withNames(String... names) {
        this.names = Arrays.stream(names)
                .map(Name::new)
                .collect(Collectors.toSet());
        return this;
    }

    /**
     * Sets the {@code DateTime} of the {@code Task} that we are building.
     */
    public TaskBuilder withStatus(String isDone, String isOverdue) {
        this.status = new Status(isDone, isOverdue);
        return this;
    }

    /**
     * Sets the {@code DateTime} of the {@code Task} that we are building.
     */
    public TaskBuilder withRecurrence(String recurrence) {
        this.recurrence = new Recurrence(recurrence);
        return this;
    }

    /**
     * Returns task object created with fields of the taskBuilder
     */
    public Task build() {
        if (!isRealTask) {
            return new GhostTask(desc, dateTime, names, status, recurrence);
        }
        return new RealTask(desc, dateTime, names, status, recurrence);
    }
}
