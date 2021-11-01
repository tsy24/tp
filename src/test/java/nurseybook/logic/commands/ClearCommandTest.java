package nurseybook.logic.commands;

import static nurseybook.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import nurseybook.model.Model;
import nurseybook.model.ModelManager;
import nurseybook.model.NurseyBook;
import nurseybook.model.UserPrefs;
import nurseybook.testutil.TypicalElderlies;

public class ClearCommandTest {

    @Test
    public void execute_emptyNurseyBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        expectedModel.commitNurseyBook(new CommandResult(ClearCommand.MESSAGE_SUCCESS));

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyNurseyBook_success() {
        Model model = new ModelManager(TypicalElderlies.getTypicalNurseyBook(), new UserPrefs());
        Model expectedModel = new ModelManager(TypicalElderlies.getTypicalNurseyBook(), new UserPrefs());
        expectedModel.setVersionedNurseyBook(new NurseyBook());
        expectedModel.commitNurseyBook(new CommandResult(ClearCommand.MESSAGE_SUCCESS));

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
