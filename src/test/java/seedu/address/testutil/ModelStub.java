package seedu.address.testutil;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.person.Elderly;
import seedu.address.model.task.Task;

/**
 * A default model stub that have all of the methods failing.
 */
public class ModelStub implements Model {
    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public GuiSettings getGuiSettings() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Path getAddressBookFilePath() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addElderly(Elderly elderly) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setElderlyOfInterest(Elderly e) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Elderly getElderlyOfInterest() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addTask(Task task) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setAddressBook(ReadOnlyAddressBook newData) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasElderly(Elderly elderly) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasTask(Task t) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteElderly(Elderly target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteTask(Task taskToDelete) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setElderly(Elderly target, Elderly editedElderly) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setTask(Task target, Task editedTask) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void markTaskAsDone(Task target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void markTaskAsOverdue(Task target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void markTaskAsNotOverdue(Task target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateDateRecurringTask(Task target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Elderly> getFilteredElderlyList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Task> getFilteredTaskList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredElderlyList(Predicate<Elderly> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredTaskList(Predicate<Task> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateOverdueTaskList() {
        throw new ArithmeticException("This method should not be called.");
    }

    @Override
    public void updateNotOverdueTaskList() {
        throw new ArithmeticException("This method should not be called.");
    }

    @Override
    public void updateDateRecurringTaskList() {
        throw new ArithmeticException("This method should not be called.");
    }
}
