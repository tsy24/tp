package nurseybook.logic.commands;

import static nurseybook.commons.core.Messages.MESSAGE_NO_SUCH_ELDERLY;
import static nurseybook.logic.commands.CommandTestUtil.assertCommandSuccess;
import static nurseybook.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import nurseybook.logic.commands.exceptions.CommandException;
import nurseybook.model.Model;
import nurseybook.model.ModelManager;
import nurseybook.model.person.Elderly;
import nurseybook.model.task.Task;
import nurseybook.testutil.ElderlyBuilder;
import nurseybook.testutil.TaskBuilder;

public class AddTaskCommandIntegrationTest {

    @Test
    public void execute_elderliesInNurseyBook_success() {
        Model model = new ModelManager();
        Task validTask = new TaskBuilder().withNames("Alex Yeoh", "George Tan").build();
        Elderly firstElderly = new ElderlyBuilder().withName("Alex Yeoh").build();
        Elderly secondElderly = new ElderlyBuilder().withName("George Tan").build();

        model.addElderly(firstElderly);
        model.addElderly(secondElderly);


        AddTaskCommand addTaskCommand = new AddTaskCommand(validTask);

        CommandResult expectedResult = new CommandResult(String.format(AddTaskCommand.MESSAGE_SUCCESS, validTask),
                CommandResult.ListDisplayChange.TASK);
        Model expectedModel = new ModelManager();
        expectedModel.addElderly(firstElderly);
        expectedModel.addElderly(secondElderly);
        expectedModel.addTask(validTask);
        expectedModel.commitNurseyBook(expectedResult);

        assertCommandSuccess(addTaskCommand, model, expectedResult, expectedModel);
    }

    @Test
    public void execute_elderlyNotInNurseyBook_throwsCommandException() {
        Model model = new ModelManager();
        Task validTask = new TaskBuilder().withNames("Alex Yeoh").build();
        AddTaskCommand addTaskCommand = new AddTaskCommand(validTask);
        assertThrows(CommandException.class, MESSAGE_NO_SUCH_ELDERLY, () -> addTaskCommand.execute(model));
    }

}
