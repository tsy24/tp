package nurseybook.logic.commands;


import static nurseybook.logic.commands.CommandTestUtil.assertCommandFailure;
import static nurseybook.logic.commands.CommandTestUtil.assertCommandSuccess;
import static nurseybook.logic.commands.CommandTestUtil.deleteFirstElderly;
import static nurseybook.logic.commands.UndoCommand.MESSAGE_FAILURE;
import static nurseybook.logic.commands.UndoCommand.MESSAGE_SUCCESS;
import static nurseybook.testutil.TypicalElderlies.getTypicalNurseyBook;

import org.junit.jupiter.api.Test;

import nurseybook.model.Model;
import nurseybook.model.ModelManager;
import nurseybook.model.UserPrefs;

public class UndoCommandTest {
    private Model model = new ModelManager(getTypicalNurseyBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalNurseyBook(), new UserPrefs());

    @Test
    public void execute() {
        CommandResult firstCommandResult = deleteFirstElderly(model);
        CommandResult secondCommandResult = deleteFirstElderly(model);

        deleteFirstElderly(expectedModel);
        deleteFirstElderly(expectedModel);

        // multiple undo-able states
        expectedModel.undoNurseyBook();
        String expectedMessage = MESSAGE_SUCCESS + secondCommandResult.getFeedbackToUser();
        CommandResult expectedCommandResult = new CommandResult(expectedMessage,
                    secondCommandResult.getDisplayChange());
        assertCommandSuccess(new UndoCommand(), model, expectedCommandResult, expectedModel);

        // one undo-able state
        expectedModel.undoNurseyBook();
        expectedMessage = MESSAGE_SUCCESS + firstCommandResult.getFeedbackToUser();
        expectedCommandResult = new CommandResult(expectedMessage, firstCommandResult.getDisplayChange());
        assertCommandSuccess(new UndoCommand(), model, expectedCommandResult, expectedModel);

        // no undo-able states
        assertCommandFailure(new UndoCommand(), model, MESSAGE_FAILURE);
    }

}
