package nurseybook.testutil;

import static nurseybook.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static nurseybook.logic.parser.CliSyntax.PREFIX_AGE;
import static nurseybook.logic.parser.CliSyntax.PREFIX_EMAIL;
import static nurseybook.logic.parser.CliSyntax.PREFIX_GENDER;
import static nurseybook.logic.parser.CliSyntax.PREFIX_NAME;
import static nurseybook.logic.parser.CliSyntax.PREFIX_NOK_NAME;
import static nurseybook.logic.parser.CliSyntax.PREFIX_PHONE;
import static nurseybook.logic.parser.CliSyntax.PREFIX_RELATIONSHIP;
import static nurseybook.logic.parser.CliSyntax.PREFIX_ROOM_NUM;
import static nurseybook.logic.parser.CliSyntax.PREFIX_TAG;
import static nurseybook.logic.parser.CliSyntax.PREFIX_TASK_DATE;
import static nurseybook.logic.parser.CliSyntax.PREFIX_TASK_DESC;
import static nurseybook.logic.parser.CliSyntax.PREFIX_TASK_RECURRING;
import static nurseybook.logic.parser.CliSyntax.PREFIX_TASK_TIME;

import nurseybook.logic.commands.AddTaskCommand;
import nurseybook.logic.commands.EditCommand;
import nurseybook.logic.commands.EditTaskCommand;
import nurseybook.model.person.Name;
import nurseybook.model.tag.Tag;
import nurseybook.model.task.Task;

import java.util.Set;

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
        task.getRelatedNames()
                .stream()
                .forEach(s -> sb.append(PREFIX_NAME + s.fullName + " "));
        sb.append(PREFIX_TASK_DESC + task.getDesc().value + " ");
        sb.append(PREFIX_TASK_DATE + task.getDateTime().getStringDate() + " ");
        sb.append(PREFIX_TASK_TIME + task.getDateTime().getStringTime() + " ");

        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditTaskDescriptor}'s details.
     */
    public static String getEditTaskDescriptorDetails(EditTaskCommand.EditTaskDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getDescription().ifPresent(description -> sb.append(PREFIX_TASK_DESC)
                .append(description.value).append(" "));
        descriptor.getDate().ifPresent(date -> sb.append(PREFIX_TASK_DATE).append(date).append(" "));
        descriptor.getTime().ifPresent(time -> sb.append(PREFIX_TASK_TIME).append(time).append(" "));
        descriptor.getRecurrence().ifPresent(recurrence ->
                sb.append(PREFIX_TASK_RECURRING).append(recurrence.getRecurrenceType()).append(" "));
        if (descriptor.getNames().isPresent()) {
            Set<Name> names = descriptor.getNames().get();
            if (names.isEmpty()) {
                sb.append(PREFIX_NAME);
            } else {
                names.forEach(s -> sb.append(PREFIX_TAG).append(s).append(" "));
            }
        }
        return sb.toString();
    }
}
