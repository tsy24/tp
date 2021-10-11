package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.RemindCommand.MESSAGE_SUCCESS;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.time.Clock;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.task.DateTime;
import seedu.address.model.task.Task;

public class RemindCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_tasksAreNotFiltered_showsSameList() {
        CommandResult expectedCommandResult = new CommandResult(MESSAGE_SUCCESS,
                CommandResult.ListDisplayChange.TASK);
        assertCommandSuccess(new RemindCommand(), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_remind_success() {
        Clock cl = Clock.systemUTC();

        LocalDate currentDate = LocalDate.now(cl);
        DateTime now = new DateTime(currentDate.toString(), "00:00");
        DateTime limit = new DateTime(currentDate.plusDays(4).toString(), "00:00");

        // not yet completed

        CommandResult expectedCommandResult = new CommandResult(MESSAGE_SUCCESS, CommandResult.ListDisplayChange.TASK);
        assertCommandSuccess(new RemindCommand(), model, expectedCommandResult, expectedModel);
    }
}
