package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.deleteFirstElderly;
import static seedu.address.testutil.TypicalElderlies.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class UndoCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute() {
        CommandResult firstCommandResult = deleteFirstElderly(model);
        CommandResult secondCommandResult = deleteFirstElderly(model);

        deleteFirstElderly(expectedModel);
        deleteFirstElderly(expectedModel);

        // multiple undo-able states
        expectedModel.undoNurseyBook();
        String expectedMessage = UndoCommand.MESSAGE_SUCCESS + secondCommandResult.getFeedbackToUser();
        CommandResult expectedCommandResult = new CommandResult(expectedMessage,
                    secondCommandResult.getDisplayChange());
        assertCommandSuccess(new UndoCommand(), model, expectedCommandResult, expectedModel);

        // one undo-able state
        expectedModel.undoNurseyBook();
        expectedMessage = UndoCommand.MESSAGE_SUCCESS + firstCommandResult.getFeedbackToUser();
        expectedCommandResult = new CommandResult(expectedMessage, firstCommandResult.getDisplayChange());
        assertCommandSuccess(new UndoCommand(), model, expectedCommandResult, expectedModel);

        // no undo-able states
        assertCommandFailure(new UndoCommand(), model, UndoCommand.MESSAGE_FAILURE);
    }

}
