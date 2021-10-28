package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.time.LocalDate;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.ViewScheduleCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.task.DateTimeContainsDatePredicate;

/**
 * Parses input arguments and creates a new ViewScheduleCommand object.
 */
public class ViewScheduleCommandParser implements Parser<Command> {
    /**
     * Parses the given {@code String} of arguments in the context of the ViewScheduleCommand
     * and returns a ViewScheduleCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewScheduleCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewScheduleCommand.MESSAGE_USAGE));
        }

        String stringKeyDate = trimmedArgs.split(" ")[0];
        LocalDate keyDate = ParserUtil.parseDate(stringKeyDate);

        return new ViewScheduleCommand(new DateTimeContainsDatePredicate(keyDate), keyDate);
    }
}
