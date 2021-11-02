package nurseybook.logic;

import static nurseybook.commons.core.Messages.MESSAGE_INVALID_ELDERLY_DISPLAYED_INDEX;
import static nurseybook.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static nurseybook.logic.commands.CommandTestUtil.AGE_DESC_AMY;
import static nurseybook.logic.commands.CommandTestUtil.GENDER_DESC_AMY;
import static nurseybook.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static nurseybook.logic.commands.CommandTestUtil.NOK_ADDRESS_DESC_AMY;
import static nurseybook.logic.commands.CommandTestUtil.NOK_EMAIL_DESC_AMY;
import static nurseybook.logic.commands.CommandTestUtil.NOK_NAME_DESC_AMY;
import static nurseybook.logic.commands.CommandTestUtil.NOK_PHONE_DESC_AMY;
import static nurseybook.logic.commands.CommandTestUtil.NOK_RELATIONSHIP_DESC_AMY;
import static nurseybook.logic.commands.CommandTestUtil.ROOM_NUMBER_DESC_AMY;
import static nurseybook.testutil.Assert.assertThrows;
import static nurseybook.testutil.TypicalElderlies.AMY;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import nurseybook.logic.commands.AddCommand;
import nurseybook.logic.commands.CommandResult;
import nurseybook.logic.commands.ViewElderlyCommand;
import nurseybook.logic.commands.exceptions.CommandException;
import nurseybook.logic.parser.exceptions.ParseException;
import nurseybook.model.Model;
import nurseybook.model.ModelManager;
import nurseybook.model.ReadOnlyNurseyBook;
import nurseybook.model.UserPrefs;
import nurseybook.model.person.Elderly;
import nurseybook.storage.JsonNurseyBookStorage;
import nurseybook.storage.JsonUserPrefsStorage;
import nurseybook.storage.StorageManager;
import nurseybook.testutil.ElderlyBuilder;

public class LogicManagerTest {
    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy exception");

    @TempDir
    public Path temporaryFolder;

    private Model model = new ModelManager();
    private Logic logic;

    @BeforeEach
    public void setUp() {
        JsonNurseyBookStorage nurseyBookStorage =
                new JsonNurseyBookStorage(temporaryFolder.resolve("nurseyBook.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        StorageManager storage = new StorageManager(nurseyBookStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);
    }

    @Test
    public void execute_invalidCommandFormat_throwsParseException() {
        String invalidCommand = "uicfhmowqewca";
        assertParseException(invalidCommand, MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void execute_commandExecutionError_throwsCommandException() {
        String deleteCommand = "deleteElderly 9";
        assertCommandException(deleteCommand, MESSAGE_INVALID_ELDERLY_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validCommand_success() throws Exception {
        String viewElderlyCommand = ViewElderlyCommand.COMMAND_WORD;
        assertCommandSuccess(viewElderlyCommand, ViewElderlyCommand.MESSAGE_SUCCESS, model);
    }

    @Test
    public void execute_storageThrowsIoException_throwsCommandException() {
        // Setup LogicManager with JsonNurseyBookIoExceptionThrowingStub
        JsonNurseyBookStorage nurseyBookStorage =
                new JsonNurseyBookIoExceptionThrowingStub(temporaryFolder.resolve("ioExceptionNurseyBook.json"));
        JsonUserPrefsStorage userPrefsStorage =
                new JsonUserPrefsStorage(temporaryFolder.resolve("ioExceptionUserPrefs.json"));
        StorageManager storage = new StorageManager(nurseyBookStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);

        // Execute add command
        String addCommand = AddCommand.COMMAND_WORD + NAME_DESC_AMY + AGE_DESC_AMY + GENDER_DESC_AMY
                + ROOM_NUMBER_DESC_AMY + NOK_NAME_DESC_AMY + NOK_RELATIONSHIP_DESC_AMY + NOK_PHONE_DESC_AMY
                + NOK_EMAIL_DESC_AMY + NOK_ADDRESS_DESC_AMY;
        Elderly expectedElderly = new ElderlyBuilder(AMY).withTags().withRemark("").build();
        ModelManager expectedModel = new ModelManager();
        expectedModel.addElderly(expectedElderly);
        expectedModel.commitNurseyBook(new CommandResult(String.format(AddCommand.MESSAGE_SUCCESS, expectedElderly),
                CommandResult.ListDisplayChange.ELDERLY));
        String expectedMessage = LogicManager.FILE_OPS_ERROR_MESSAGE + DUMMY_IO_EXCEPTION;
        assertCommandFailure(addCommand, CommandException.class, expectedMessage, expectedModel);
    }

    @Test
    public void getFilteredElderlyList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredElderlyList().remove(0));
    }

    /**
     * Executes the command and confirms that
     * - no exceptions are thrown <br>
     * - the feedback message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandSuccess(String inputCommand, String expectedMessage,
            Model expectedModel) throws CommandException, ParseException {
        CommandResult result = logic.execute(inputCommand);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(expectedModel, model);
    }

    /**
     * Executes the command, confirms that a ParseException is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertParseException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, ParseException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that a CommandException is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, CommandException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that the exception is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
            String expectedMessage) {
        Model expectedModel = new ModelManager(model.getVersionedNurseyBook(), new UserPrefs());
        assertCommandFailure(inputCommand, expectedException, expectedMessage, expectedModel);
    }

    /**
     * Executes the command and confirms that
     * - the {@code expectedException} is thrown <br>
     * - the resulting error message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     * @see #assertCommandSuccess(String, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
            String expectedMessage, Model expectedModel) {
        assertThrows(expectedException, expectedMessage, () -> logic.execute(inputCommand));
        expectedModel.equals(model);
        assertEquals(expectedModel, model);
    }

    /**
     * A stub class to throw an {@code IOException} when the save method is called.
     */
    private static class JsonNurseyBookIoExceptionThrowingStub extends JsonNurseyBookStorage {
        private JsonNurseyBookIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveNurseyBook(ReadOnlyNurseyBook nurseyBook, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }
}
