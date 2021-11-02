package nurseybook.storage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import nurseybook.commons.exceptions.IllegalValueException;
import nurseybook.model.person.Name;
import nurseybook.model.task.DateTime;
import nurseybook.model.task.Description;
import nurseybook.model.task.GhostTask;
import nurseybook.model.task.Recurrence;
import nurseybook.model.task.Status;
import nurseybook.model.task.Task;

public class JsonAdaptedTask {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Task's %s field is missing!";

    private final String description;
    private final List<String> names = new ArrayList<>();
    private final String date;
    private final String time;
    private final List<String> status;
    private final String recurrence;
    private final String ghostTask;

    /**
     * Constructs a {@code JsonAdaptedTask} with the given task details.
     */
    @JsonCreator
    public JsonAdaptedTask(@JsonProperty("names") List<String> names, @JsonProperty("description") String description,
                           @JsonProperty("date") String date, @JsonProperty("time") String time,
                           @JsonProperty("status") List<String> status, @JsonProperty("recurrence") String recurrence,
                           @JsonProperty("ghostTask") String ghostTask) {
        if (names != null) {
            this.names.addAll(names);
        }
        this.description = description;
        this.date = date;
        this.time = time;
        this.status = status;
        this.recurrence = recurrence;
        this.ghostTask = ghostTask;
    }

    /**
     * Converts a given {@code Task} into this class for Jackson use.
     */
    public JsonAdaptedTask(Task source) {
        description = source.getDesc().value;
        date = source.getDateTime().getStringDate();
        time = source.getDateTime().getStringTime();
        names.addAll(source.getRelatedNames().stream()
                .map(name -> name.fullName)
                .collect(Collectors.toList()));
        status = Arrays.asList(source.getStatus().toString().split("; "));
        recurrence = source.getRecurrence().toString();
        ghostTask = source.getGhostTask().toString();
    }

    /**
     * Converts this Jackson-friendly adapted task object into the model's {@code Task} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task.
     */
    public Task toModelType() throws IllegalValueException {
        final List<Name> relatedNames = new ArrayList<>();
        for (String name : names) {
            relatedNames.add(new Name(name));
        }

        if (description == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Description.class.getSimpleName()));
        }
        if (!Description.isValidDescription(description)) {
            throw new IllegalValueException(Description.MESSAGE_CONSTRAINTS);
        }
        final Description modelDesc = new Description(description);

        if (date == null || time == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    DateTime.class.getSimpleName()));
        }
        if (!DateTime.isValidDate(date)) {
            throw new IllegalValueException(DateTime.MESSAGE_DATE_CONSTRAINTS);
        }
        if (!DateTime.isValidTime(time)) {
            throw new IllegalValueException(DateTime.MESSAGE_TIME_CONSTRAINTS);
        }
        final DateTime modelDt = new DateTime(date, time);

        if (status == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Status.class.getSimpleName()));
        }

        if (!Status.isValidStatus(status.get(0))
                || !Status.isValidStatus(status.get(1))) {
            throw new IllegalValueException(Status.MESSAGE_CONSTRAINTS);
        }

        final Status modelStatus = new Status(status.get(0), status.get(1));

        if (recurrence == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Status.class.getSimpleName()));
        }

        if (!Recurrence.isValidRecurrence(recurrence)) {
            throw new IllegalValueException(Recurrence.MESSAGE_CONSTRAINTS);
        }

        final Recurrence modelRecurrence = new Recurrence(recurrence);

        if (ghostTask == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    GhostTask.class.getSimpleName()));
        }

        if (!GhostTask.isValidGhostTask(ghostTask)) {
            throw new IllegalValueException(GhostTask.MESSAGE_CONSTRAINTS);
        }

        final GhostTask modelIsGhostTask = new GhostTask(ghostTask);

        final Set<Name> modelNames = new HashSet<>(relatedNames);

        return new Task(modelDesc, modelDt, modelNames, modelStatus, modelRecurrence, modelIsGhostTask);
    }
}
