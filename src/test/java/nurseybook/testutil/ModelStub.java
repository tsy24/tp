package nurseybook.testutil;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import nurseybook.commons.core.GuiSettings;
import nurseybook.logic.commands.CommandResult;
import nurseybook.model.Model;
import nurseybook.model.ReadOnlyNurseyBook;
import nurseybook.model.ReadOnlyUserPrefs;
import nurseybook.model.person.Elderly;
import nurseybook.model.person.Name;
import nurseybook.model.task.Task;

/**
 * A default model stub that have all of the methods failing except isElderlyPresent(),
 * which returns false with the assumption that there are no elderlies present.
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
    public Path getNurseyBookFilePath() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setNurseyBookFilePath(Path nurseyBookFilePath) {
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
    public void setVersionedNurseyBook(ReadOnlyNurseyBook newData) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyNurseyBook getVersionedNurseyBook() {
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
    public boolean isElderlyPresent(Name name) {
        return false;
    }

    @Override
    public void updateElderlyNameInTasks(Elderly target, Elderly editedElderly) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteElderlyNameInTasks(Elderly elderlyToDelete) {
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
    public void updateTasksAccordingToTime() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteGhostTasks() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addPossibleGhostTasksWithMatchingDate(LocalDate date) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void commitNurseyBook(CommandResult commandResult) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public CommandResult undoNurseyBook() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean canUndoNurseyBook() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public CommandResult redoNurseyBook() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean canRedoNurseyBook() {
        throw new AssertionError("This method should not be called.");
    }

}
