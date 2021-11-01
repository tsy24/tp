package nurseybook.logic.commands;

import static nurseybook.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import nurseybook.testutil.TypicalElderlies;
import nurseybook.model.Model;
import nurseybook.model.ModelManager;
import nurseybook.model.UserPrefs;

public class UndoCommandTest {
    private Model model = new ModelManager(TypicalElderlies.getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(TypicalElderlies.getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute() {
        CommandResult firstCommandResult = CommandTestUtil.deleteFirstElderly(model);
        CommandResult secondCommandResult = CommandTestUtil.deleteFirstElderly(model);

        CommandTestUtil.deleteFirstElderly(expectedModel);
        CommandTestUtil.deleteFirstElderly(expectedModel);

        // multiple undo-able states
        expectedModel.undoNurseyBook();
        String expectedMessage = UndoCommand.MESSAGE_SUCCESS + secondCommandResult.getFeedbackToUser();
        CommandResult expectedCommandResult = new CommandResult(expectedMessage,
                    secondCommandResult.getDisplayChange());
        CommandTestUtil.assertCommandSuccess(new UndoCommand(), model, expectedCommandResult, expectedModel);

        // one undo-able state
        expectedModel.undoNurseyBook();
        expectedMessage = UndoCommand.MESSAGE_SUCCESS + firstCommandResult.getFeedbackToUser();
        expectedCommandResult = new CommandResult(expectedMessage, firstCommandResult.getDisplayChange());
        CommandTestUtil.assertCommandSuccess(new UndoCommand(), model, expectedCommandResult, expectedModel);

        // no undo-able states
        CommandTestUtil.assertCommandFailure(new UndoCommand(), model, UndoCommand.MESSAGE_FAILURE);
    }

}
