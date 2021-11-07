package nurseybook.model;

import javafx.collections.ObservableList;
import nurseybook.model.person.Elderly;
import nurseybook.model.task.Task;

/**
 * Unmodifiable view of an nursey book
 */
public interface ReadOnlyNurseyBook {

    /**
     * Returns an unmodifiable view of the elderlies list.
     * This list will not contain any duplicate elderlies.
     */
    ObservableList<Elderly> getElderlyList();

    /**
     * Returns an unmodifiable view of the task list.
     */
    ObservableList<Task> getTaskList();

    /**
     * Returns an unmodifiable view of real tasks within the task list.
     */
    ObservableList<Task> getRealTaskList();

    /**
     * Returns true if all tasks contain only names of existing elderly in NurseyBook.
     */
    boolean doTasksContainValidNames();
}
