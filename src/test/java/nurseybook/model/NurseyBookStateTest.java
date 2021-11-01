package nurseybook.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static nurseybook.testutil.TypicalElderlies.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import nurseybook.logic.commands.CommandResult;
import nurseybook.testutil.AddressBookBuilder;

public class NurseyBookStateTest {

    @Test
    public void equals() {
        NurseyBookState nurseyBookState = new NurseyBookState(getTypicalAddressBook(),
                new CommandResult("feedback"));

        // same object -> returns true
        assertTrue(nurseyBookState.equals(nurseyBookState));

        // same values -> returns true
        NurseyBookState nurseyBookStateCopy = new NurseyBookState(nurseyBookState.getNurseyBook(),
                nurseyBookState.getCommandResult());
        assertTrue(nurseyBookState.equals(nurseyBookStateCopy));

        // different types -> returns false
        assertFalse(nurseyBookState.equals(1));

        // null -> returns false
        assertFalse(nurseyBookState.equals(null));

        // different nurseybook -> returns false
        ReadOnlyAddressBook differentNurseyBook = new AddressBookBuilder().build();
        NurseyBookState differentNurseyBookState = new NurseyBookState(differentNurseyBook,
                nurseyBookState.getCommandResult());
        assertFalse(nurseyBookState.equals(differentNurseyBookState));

        // different nurseybook -> returns false
        CommandResult differentCommandResult = new CommandResult("different feedback");
        differentNurseyBookState = new NurseyBookState(nurseyBookState.getNurseyBook(),
                differentCommandResult);
        assertFalse(nurseyBookState.equals(differentNurseyBookState));
    }
}
