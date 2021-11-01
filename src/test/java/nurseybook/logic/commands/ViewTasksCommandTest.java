package nurseybook.logic.commands;

import static nurseybook.logic.commands.CommandTestUtil.assertCommandSuccess;

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
        CommandResult expectedCommandResult = new CommandResult(ViewTasksCommand.MESSAGE_SUCCESS, CommandResult.ListDisplayChange.TASK);
        CommandTestUtil.assertCommandSuccess(new ViewTasksCommand(), model, expectedCommandResult, expectedModel);
    }
}
