package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import javafx.collections.ObservableList;
import seedu.address.model.person.Elderly;
import seedu.address.model.person.Name;
import seedu.address.model.person.UniqueElderlyList;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameElderly comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniqueElderlyList elderlies;
    private final TaskList tasks;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        elderlies = new UniqueElderlyList();
        tasks = new TaskList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Elderlies in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the elderly list with {@code elderlies}.
     * {@code elderlies} must not contain duplicate elderlies.
     */
    public void setElderlies(List<Elderly> elderlies) {
        this.elderlies.setElderlies(elderlies);
    }

    /**
     * Replaces the contents of the task list with {@code tasks}.
     */
    public void setTasks(List<Task> tasks) {
        this.tasks.setTasks(tasks);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setElderlies(newData.getElderlyList());
        setTasks(newData.getTaskList());
    }

    //// elderly-level operations

    /**
     * Returns true if a elderly with the same identity as {@code elderly} exists in the address book.
     */
    public boolean hasElderly(Elderly elderly) {
        requireNonNull(elderly);
        return elderlies.contains(elderly);
    }

    public Elderly getElderly(Name name) {
        requireNonNull(name);
        return elderlies.getElderly(name);
    }

    /**
     * Adds a elderly to the address book.
     * The elderly must not already exist in the address book.
     */
    public void addElderly(Elderly p) {
        elderlies.add(p);
    }

    /**
     * Adds a task to the address book.
     */
    public void addTask(Task t) {
        tasks.add(t);
    }

    /**
     * Replaces the given elderly {@code target} in the list with {@code editedElderly}.
     * {@code target} must exist in the address book.
     * The elderly identity of {@code editedElderly} must not be the same as another existing elderly in the address
     * book.
     */
    public void setElderly(Elderly target, Elderly editedElderly) {
        requireNonNull(editedElderly);

        elderlies.setElderly(target, editedElderly);
    }

    /**
     * Removes {@code key} elderly from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeElderly(Elderly key) {
        elderlies.remove(key);
    }

    /**
     * Removes {@code key} task from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeTask(Task key) {
        tasks.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return elderlies.asUnmodifiableObservableList().size() + " elderlies\n"
                + tasks.asUnmodifiableObservableList().size() + " tasks";
        // TODO: refine later
    }

    @Override
    public ObservableList<Elderly> getElderlyList() {
        return elderlies.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Task> getTaskList() {
        return tasks.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && elderlies.equals(((AddressBook) other).elderlies)
                && tasks.equals(((AddressBook) other).tasks));
    }

    @Override
    public int hashCode() {
        return Objects.hash(elderlies, tasks);
    }

    public boolean hasTask(Task t) {
        return tasks.contains(t);
    }

    /**
     * Mark the given task {@code target} as done.
     * {@code target} must exist in the address book.
     */
    public void markTaskAsDone(Task target) {
        tasks.markTaskAsDone(target);
    }

    /**
     * Mark the given task {@code target} as overdue.
     * {@code target} must exist in the address book.
     */
    public void markTaskAsOverdue(Task target) {
        tasks.markTaskAsOverdue(target);
    }
}
