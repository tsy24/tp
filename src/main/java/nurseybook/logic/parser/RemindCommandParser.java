package nurseybook.logic.parser;

import java.time.LocalDateTime;

import nurseybook.logic.commands.RemindCommand;
import nurseybook.model.task.TaskIsReminderPredicate;

/**
 * Parses the command (date of execution) and creates a new RemindCommand object
 */
public class RemindCommandParser implements Parser<RemindCommand> {

    /**
     * Parses the command by evaluating the date of command and returns a RemindCommand object for execution.
     */
    @Override
    public RemindCommand parse(String userInput) {
        LocalDateTime now = LocalDateTime.now()
                .withMinute(0).withSecond(0).withNano(0);

        return new RemindCommand(new TaskIsReminderPredicate(now));
    }

}
