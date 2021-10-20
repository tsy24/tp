package seedu.address.logic.parser;

import java.time.LocalDateTime;

import seedu.address.logic.commands.RemindCommand;
import seedu.address.model.task.TaskIsReminderPredicate;

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
                .withSecond(0).withNano(0);

        return new RemindCommand(new TaskIsReminderPredicate(now));
    }

}
