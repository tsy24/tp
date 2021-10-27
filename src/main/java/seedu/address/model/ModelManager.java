package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Elderly;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskIsOverduePredicate;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Elderly> filteredElderlies;
    private final FilteredList<Task> filteredTasks;
    private Elderly elderlyOfInterest;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredElderlies = new FilteredList<>(this.addressBook.getElderlyList());
        filteredTasks = new FilteredList<>(this.addressBook.getTaskList());
        this.elderlyOfInterest = null;
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public boolean hasElderly(Elderly elderly) {
        requireNonNull(elderly);
        return addressBook.hasElderly(elderly);
    }

    @Override
    public boolean hasTask(Task t) {
        requireNonNull(t);
        return addressBook.hasTask(t);
    }

    @Override
    public void markTaskAsDone(Task target) {
        addressBook.markTaskAsDone(target);
    }

    @Override
    public void markTaskAsOverdue(Task target) {
        addressBook.markTaskAsOverdue(target);
    }

    @Override
    public void deleteElderly(Elderly target) {
        addressBook.removeElderly(target);
    }

    @Override
    public void deleteTask(Task target) {
        addressBook.removeTask(target);
    }

    @Override
    public void addElderly(Elderly elderly) {
        addressBook.addElderly(elderly);
        updateFilteredElderlyList(PREDICATE_SHOW_ALL_ELDERLIES);
    }

    @Override
    public void addTask(Task task) {
        addressBook.addTask(task);
        updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
    }

    @Override
    public void setElderly(Elderly target, Elderly editedElderly) {
        requireAllNonNull(target, editedElderly);

        addressBook.setElderly(target, editedElderly);
    }

    @Override
    public void deleteGhostTasks() {
        for (Task task : addressBook.getTaskList()) {
            if (!task.isRealTask()) {
                this.deleteTask(task);
            }
        }
    }

    //=========== Elderly of interest Accessors =============================================================
    public void setElderlyOfInterest(Elderly e) {
        requireNonNull(e);
        this.elderlyOfInterest = e;
    }

    public Elderly getElderlyOfInterest() {
        return elderlyOfInterest;
    }

    //=========== Filtered Lists Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Elderly} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Elderly> getFilteredElderlyList() {
        return filteredElderlies;
    }

    @Override
    public ObservableList<Task> getFilteredTaskList() {
        return filteredTasks;
    }

    @Override
    public void updateFilteredElderlyList(Predicate<Elderly> predicate) {
        requireNonNull(predicate);
        filteredElderlies.setPredicate(predicate);
    }

    @Override
    public void updateFilteredTaskList(Predicate<Task> predicate) {
        requireNonNull(predicate);
        updateOverdueTaskList();
        filteredTasks.setPredicate(predicate);
    }

    @Override
    public void updateOverdueTaskList() {
        Predicate<Task> predicate = new TaskIsOverduePredicate();
        filteredTasks.setPredicate(predicate);
        filteredTasks.forEach(task -> markTaskAsOverdue(task));
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        if (elderlyOfInterest == null) {
            return other.elderlyOfInterest == null
                    && addressBook.equals(other.addressBook)
                    && userPrefs.equals(other.userPrefs)
                    && filteredElderlies.equals(other.filteredElderlies);
        }
        return other.elderlyOfInterest != null
                && addressBook.equals(other.addressBook)
                && userPrefs.equals(other.userPrefs)
                && filteredElderlies.equals(other.filteredElderlies)
                && elderlyOfInterest.equals(other.elderlyOfInterest);
    }

}
