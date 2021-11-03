package nurseybook.testutil;

import static nurseybook.logic.parser.CliSyntax.PREFIX_NAME;
import static nurseybook.logic.parser.CliSyntax.PREFIX_TASK_DATE;
import static nurseybook.logic.parser.CliSyntax.PREFIX_TASK_DESC;
import static nurseybook.logic.parser.CliSyntax.PREFIX_TASK_TIME;

import nurseybook.logic.commands.AddTaskCommand;
import nurseybook.model.task.Task;

/**
 * A utility class for Task.
 */
public class TaskUtil {

    /**
     * Returns an add command string for adding the {@code Task}.
     */
    public static String getAddTaskCommand(Task task) {
        return AddTaskCommand.COMMAND_WORD + " " + getTaskDetails(task);
    }

    /**
     * Returns the part of command string for the given {@code Task}'s details.
     */
    public static String getTaskDetails(Task task) {
        StringBuilder sb = new StringBuilder();
        task.getRelatedElderlies()
                .stream()
                .forEach(s -> sb.append(PREFIX_NAME + s.fullName + " "));
        sb.append(PREFIX_TASK_DESC + task.getDesc().value + " ");
        sb.append(PREFIX_TASK_DATE + task.getDateTime().getStringDate() + " ");
        sb.append(PREFIX_TASK_TIME + task.getDateTime().getStringTime() + " ");

        return sb.toString();
    }
}
