package nurseybook.logic.commands;

import static nurseybook.logic.commands.CommandResult.ListDisplayChange.TASK;
import static nurseybook.logic.commands.CommandTestUtil.assertCommandSuccess;
import static nurseybook.logic.commands.ViewTasksCommand.MESSAGE_SUCCESS;

import org.junit.jupiter.api.Test;

import nurseybook.model.Model;
import nurseybook.model.ModelManager;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ViewTasksCommand.
 */
public class ViewTasksCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_viewTask_success() {
        CommandResult expectedCommandResult = new CommandResult(MESSAGE_SUCCESS, TASK);
        assertCommandSuccess(new ViewTasksCommand(), model, expectedCommandResult, expectedModel);
    }
}
