package nurseybook.model;

import static nurseybook.testutil.TypicalElderlies.getTypicalNurseyBook;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import nurseybook.logic.commands.CommandResult;
import nurseybook.testutil.NurseyBookBuilder;

public class NurseyBookStateTest {

    @Test
    public void equals() {
        NurseyBookState nurseyBookState = new NurseyBookState(getTypicalNurseyBook(),
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
        ReadOnlyNurseyBook differentNurseyBook = new NurseyBookBuilder().build();
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
