package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.ViewTasksCommand.MESSAGE_SUCCESS;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class ViewTasksCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_viewTask_success() {
        CommandResult expectedCommandResult = new CommandResult(MESSAGE_SUCCESS, CommandResult.ListDisplayChange.TASK);
        assertCommandSuccess(new ViewTasksCommand(), model, expectedCommandResult, expectedModel);
    }
}
