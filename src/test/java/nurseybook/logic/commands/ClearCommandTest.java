package nurseybook.logic.commands;

import static nurseybook.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import nurseybook.testutil.TypicalElderlies;
import nurseybook.model.AddressBook;
import nurseybook.model.Model;
import nurseybook.model.ModelManager;
import nurseybook.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        expectedModel.commitNurseyBook(new CommandResult(ClearCommand.MESSAGE_SUCCESS));

        CommandTestUtil.assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(TypicalElderlies.getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(TypicalElderlies.getTypicalAddressBook(), new UserPrefs());
        expectedModel.setVersionedNurseyBook(new AddressBook());
        expectedModel.commitNurseyBook(new CommandResult(ClearCommand.MESSAGE_SUCCESS));

        CommandTestUtil.assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
