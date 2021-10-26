package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class CommandResultTest {

    @Test
    public void isChangeList() {
        CommandResult commandResult = new CommandResult("feedback");
        // displayChange value of NONE -> returns false
        assertFalse(commandResult.isChangeList());

        //displayChange value of TASK -> returns true
        assertTrue(new CommandResult("feedback", CommandResult.ListDisplayChange.TASK).isChangeList());

        //displayChange value of TASK -> returns true
        assertTrue(new CommandResult("feedback", CommandResult.ListDisplayChange.ELDERLY).isChangeList());
    }

    @Test
    public void shouldChangeToTask() {
        CommandResult commandResult = new CommandResult("feedback");
        // displayChange value of NONE -> returns false
        assertThrows(AssertionError.class, () -> commandResult.shouldChangeToTask());

        // displayChange value of PERSON -> returns false
        assertFalse(new CommandResult("feedback", CommandResult.ListDisplayChange.ELDERLY).shouldChangeToTask());

        // displayChange value of TASK -> returns true
        assertTrue(new CommandResult("feedback", CommandResult.ListDisplayChange.TASK).shouldChangeToTask());
    }


    @Test
    public void equals() {
        CommandResult commandResult = new CommandResult("feedback");

        // same values -> returns true
        assertTrue(commandResult.equals(new CommandResult("feedback")));
        assertTrue(commandResult.equals(new CommandResult("feedback", false, false)));
        assertTrue(commandResult.equals(new CommandResult("feedback",
                CommandResult.ListDisplayChange.NONE)));
        assertTrue(commandResult.equals(new CommandResult("feedback",
                false)));

        // same object -> returns true
        assertTrue(commandResult.equals(commandResult));

        // null -> returns false
        assertFalse(commandResult.equals(null));

        // different types -> returns false
        assertFalse(commandResult.equals(0.5f));

        // different feedbackToUser value -> returns false
        assertFalse(commandResult.equals(new CommandResult("different")));

        // different showHelp value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", true, false)));

        // different exit value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", false, true)));

        // different displayChange value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback",
                CommandResult.ListDisplayChange.TASK)));

        // different isViewDetails value -> returns false
        assertTrue(commandResult.equals(new CommandResult("feedback",
               false)));
    }

    @Test
    public void hashcode() {
        CommandResult commandResult = new CommandResult("feedback");

        // same values -> returns same hashcode
        assertEquals(commandResult.hashCode(), new CommandResult("feedback").hashCode());

        // different feedbackToUser value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("different").hashCode());

        // different showHelp value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", true, false).hashCode());

        // different exit value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", false, true).hashCode());

        // different displayChange value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback",
                CommandResult.ListDisplayChange.TASK).hashCode());
    }
}
