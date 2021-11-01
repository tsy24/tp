package nurseybook.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import nurseybook.commons.core.GuiSettings;
import nurseybook.commons.core.LogsCenter;
import nurseybook.logic.commands.Command;
import nurseybook.logic.commands.CommandResult;
import nurseybook.logic.commands.exceptions.CommandException;
import nurseybook.logic.parser.NurseyBookParser;
import nurseybook.logic.parser.exceptions.ParseException;
import nurseybook.model.Model;
import nurseybook.model.ReadOnlyNurseyBook;
import nurseybook.model.person.Elderly;
import nurseybook.model.task.Task;
import nurseybook.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final NurseyBookParser nurseyBookParser;

    /**
     * Constructs a {@code LogicManager} with the given {@code Model} and {@code Storage}.
     */
    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        nurseyBookParser = new NurseyBookParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        //deletes all ghost tasks from the model as they are no longer relevant
        model.deleteGhostTasks();

        CommandResult commandResult;
        Command command = nurseyBookParser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            storage.saveNurseyBook(model.getVersionedNurseyBook());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyNurseyBook getNurseyBook() {
        return model.getVersionedNurseyBook();
    }

    @Override
    public ObservableList<Elderly> getFilteredElderlyList() {
        return model.getFilteredElderlyList();
    }

    @Override
    public Path getNurseyBookFilePath() {
        return model.getNurseyBookFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }

    @Override
    public ObservableList<Task> getFilteredTaskList() {
        return model.getFilteredTaskList();
    }

    @Override
    public Elderly getElderlyOfInterest() {
        return model.getElderlyOfInterest();
    }
}
