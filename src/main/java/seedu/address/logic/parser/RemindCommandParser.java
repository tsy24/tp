package seedu.address.logic.parser;

import java.time.Clock;
import java.time.LocalDate;

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
        Clock cl = Clock.systemUTC();
        LocalDate currentDate = LocalDate.now(cl);

        return new RemindCommand(new TaskIsReminderPredicate(currentDate));
    }

}
