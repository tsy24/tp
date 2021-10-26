package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.deleteFirstElderly;
import static seedu.address.testutil.TypicalElderlies.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class RedoCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute() {
        CommandResult firstCommandResult = deleteFirstElderly(model);
        CommandResult secondCommandResult = deleteFirstElderly(model);
        model.undoNurseyBook();
        model.undoNurseyBook();

        deleteFirstElderly(expectedModel);
        deleteFirstElderly(expectedModel);
        expectedModel.undoNurseyBook();
        expectedModel.undoNurseyBook();


        // multiple undo-able states
        expectedModel.redoNurseyBook();
        String expectedMessage = RedoCommand.MESSAGE_SUCCESS + firstCommandResult.getFeedbackToUser();
        CommandResult expectedCommandResult = new CommandResult(expectedMessage, firstCommandResult.getDisplayChange());
        assertCommandSuccess(new RedoCommand(), model, expectedCommandResult, expectedModel);

        // one undo-able state
        expectedModel.redoNurseyBook();
        expectedMessage = RedoCommand.MESSAGE_SUCCESS + secondCommandResult.getFeedbackToUser();
        expectedCommandResult = new CommandResult(expectedMessage, secondCommandResult.getDisplayChange());
        assertCommandSuccess(new RedoCommand(), model, expectedCommandResult, expectedModel);

        // no undo-able states
        assertCommandFailure(new RedoCommand(), model, RedoCommand.MESSAGE_FAILURE);
    }

}

