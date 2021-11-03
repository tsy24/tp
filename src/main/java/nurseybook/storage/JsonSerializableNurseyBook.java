package nurseybook.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import nurseybook.commons.exceptions.IllegalValueException;
import nurseybook.model.NurseyBook;
import nurseybook.model.ReadOnlyNurseyBook;
import nurseybook.model.person.Elderly;
import nurseybook.model.task.Task;

/**
 * An Immutable NurseyBook that is serializable to JSON format.
 */
@JsonRootName(value = "nurseybook")
class JsonSerializableNurseyBook {

    public static final String MESSAGE_DUPLICATE_ELDERLY = "Elderlies list contains duplicate elderly(s).";

    private final List<JsonAdaptedElderly> elderlies = new ArrayList<>();
    private final List<JsonAdaptedTask> tasks = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableNurseyBook} with the given elderlies and tasks.
     */
    @JsonCreator
    public JsonSerializableNurseyBook(@JsonProperty("elderlies") List<JsonAdaptedElderly> elderlies,
                                      @JsonProperty("tasks") List<JsonAdaptedTask> tasks) {
        this.elderlies.addAll(elderlies);
        this.tasks.addAll(tasks);
    }

    /**
     * Converts a given {@code ReadOnlyNurseyBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableNurseyBook}.
     */
    public JsonSerializableNurseyBook(ReadOnlyNurseyBook source) {
        elderlies.addAll(source.getElderlyList().stream().map(JsonAdaptedElderly::new).collect(Collectors.toList()));
        tasks.addAll(source.getRealTaskList().stream().map(JsonAdaptedTask::new).collect(Collectors.toList()));
    }

    /**
     * Converts this nursey book into the model's {@code NurseyBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public NurseyBook toModelType() throws IllegalValueException {
        NurseyBook nurseyBook = new NurseyBook();
        for (JsonAdaptedElderly jsonAdaptedElderly : elderlies) {
            Elderly elderly = jsonAdaptedElderly.toModelType();
            if (nurseyBook.hasElderly(elderly)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_ELDERLY);
            }
            nurseyBook.addElderly(elderly);
        }
        for (JsonAdaptedTask jsonAdaptedTask : tasks) {
            Task task = jsonAdaptedTask.toModelType();
            nurseyBook.addTask(task);
        }
        return nurseyBook;
    }

}
