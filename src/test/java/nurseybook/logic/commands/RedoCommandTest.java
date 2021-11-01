package nurseybook.logic.commands;

import static nurseybook.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import nurseybook.testutil.TypicalElderlies;
import nurseybook.model.Model;
import nurseybook.model.ModelManager;
import nurseybook.model.UserPrefs;

public class RedoCommandTest {
    private Model model = new ModelManager(TypicalElderlies.getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(TypicalElderlies.getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute() {
        CommandResult firstCommandResult = CommandTestUtil.deleteFirstElderly(model);
        CommandResult secondCommandResult = CommandTestUtil.deleteFirstElderly(model);
        model.undoNurseyBook();
        model.undoNurseyBook();

        CommandTestUtil.deleteFirstElderly(expectedModel);
        CommandTestUtil.deleteFirstElderly(expectedModel);
        expectedModel.undoNurseyBook();
        expectedModel.undoNurseyBook();

        // multiple redo-able states
        expectedModel.redoNurseyBook();
        String expectedMessage = RedoCommand.MESSAGE_SUCCESS + firstCommandResult.getFeedbackToUser();
        CommandResult expectedCommandResult = new CommandResult(expectedMessage, firstCommandResult.getDisplayChange());
        CommandTestUtil.assertCommandSuccess(new RedoCommand(), model, expectedCommandResult, expectedModel);

        // one redo-able state
        expectedModel.redoNurseyBook();
        expectedMessage = RedoCommand.MESSAGE_SUCCESS + secondCommandResult.getFeedbackToUser();
        expectedCommandResult = new CommandResult(expectedMessage, secondCommandResult.getDisplayChange());
        CommandTestUtil.assertCommandSuccess(new RedoCommand(), model, expectedCommandResult, expectedModel);

        // no redo-able states
        CommandTestUtil.assertCommandFailure(new RedoCommand(), model, RedoCommand.MESSAGE_FAILURE);
    }

}

