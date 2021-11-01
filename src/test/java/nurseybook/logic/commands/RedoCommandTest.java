package nurseybook.logic.commands;

import static nurseybook.logic.commands.CommandTestUtil.assertCommandFailure;
import static nurseybook.logic.commands.CommandTestUtil.assertCommandSuccess;
import static nurseybook.logic.commands.CommandTestUtil.deleteFirstElderly;
import static nurseybook.testutil.TypicalElderlies.getTypicalNurseyBook;

import org.junit.jupiter.api.Test;

import nurseybook.model.Model;
import nurseybook.model.ModelManager;
import nurseybook.model.UserPrefs;

public class RedoCommandTest {
    private Model model = new ModelManager(getTypicalNurseyBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalNurseyBook(), new UserPrefs());

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

        // multiple redo-able states
        expectedModel.redoNurseyBook();
        String expectedMessage = RedoCommand.MESSAGE_SUCCESS + firstCommandResult.getFeedbackToUser();
        CommandResult expectedCommandResult = new CommandResult(expectedMessage, firstCommandResult.getDisplayChange());
        assertCommandSuccess(new RedoCommand(), model, expectedCommandResult, expectedModel);

        // one redo-able state
        expectedModel.redoNurseyBook();
        expectedMessage = RedoCommand.MESSAGE_SUCCESS + secondCommandResult.getFeedbackToUser();
        expectedCommandResult = new CommandResult(expectedMessage, secondCommandResult.getDisplayChange());
        assertCommandSuccess(new RedoCommand(), model, expectedCommandResult, expectedModel);

        // no redo-able states
        assertCommandFailure(new RedoCommand(), model, RedoCommand.MESSAGE_FAILURE);
    }

}

