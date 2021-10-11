package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.person.Elderly;
import seedu.address.model.task.Task;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the elderlies list.
     * This list will not contain any duplicate elderlies.
     */
    ObservableList<Elderly> getElderlyList();

    /**
     * Returns an unmodifiable view of the task list.
     */
    ObservableList<Task> getTaskList();

}
