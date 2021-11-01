package nurseybook.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import nurseybook.commons.exceptions.IllegalValueException;
import nurseybook.model.AddressBook;
import nurseybook.model.ReadOnlyAddressBook;
import nurseybook.model.person.Elderly;
import nurseybook.model.task.Task;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_ELDERLY = "Elderlies list contains duplicate elderly(s).";

    private final List<JsonAdaptedElderly> elderlies = new ArrayList<>();
    private final List<JsonAdaptedTask> tasks = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given elderlies and tasks.
     */
    @JsonCreator
    public JsonSerializableAddressBook(@JsonProperty("elderlies") List<JsonAdaptedElderly> elderlies,
                                       @JsonProperty("tasks") List<JsonAdaptedTask> tasks) {
        this.elderlies.addAll(elderlies);
        this.tasks.addAll(tasks);
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableAddressBook(ReadOnlyAddressBook source) {
        elderlies.addAll(source.getElderlyList().stream().map(JsonAdaptedElderly::new).collect(Collectors.toList()));
        tasks.addAll(source.getTaskList().stream().map(JsonAdaptedTask::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();
        for (JsonAdaptedElderly jsonAdaptedElderly : elderlies) {
            Elderly elderly = jsonAdaptedElderly.toModelType();
            if (addressBook.hasElderly(elderly)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_ELDERLY);
            }
            addressBook.addElderly(elderly);
        }
        for (JsonAdaptedTask jsonAdaptedTask : tasks) {
            Task task = jsonAdaptedTask.toModelType();
            addressBook.addTask(task);
        }
        return addressBook;
    }

}
