package nurseybook.logic.parser;

import static nurseybook.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static nurseybook.commons.core.Messages.MESSAGE_VIEWSCHEDULE_DAYS_SUPPORTED;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import nurseybook.logic.commands.Command;
import nurseybook.logic.commands.ViewScheduleCommand;
import nurseybook.logic.parser.exceptions.ParseException;
import nurseybook.model.task.DateTime;
import nurseybook.model.task.DateTimeContainsDatePredicate;

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
        String trimmedKeyDate = args.trim();

        if (trimmedKeyDate.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewScheduleCommand.MESSAGE_USAGE));
        }

        try {
            LocalDate keyDate = ParserUtil.parseDate(trimmedKeyDate);
            isDateWithinBounds(keyDate);
            return new ViewScheduleCommand(new DateTimeContainsDatePredicate(keyDate), keyDate);
        } catch (ParseException pe) {
            if (pe.getMessage().equals(MESSAGE_VIEWSCHEDULE_DAYS_SUPPORTED)) {
                throw pe;
            } else {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewScheduleCommand.MESSAGE_USAGE), pe);
            }
        }
    }

    private void isDateWithinBounds(LocalDate keyDate) throws ParseException {
        //ViewSchedule is only supported for 12 weeks, or 84 days, in advance
        LocalDate dateToday = LocalDate.now();
        if (ChronoUnit.DAYS.between(dateToday, keyDate) > 84) {
            throw new ParseException(MESSAGE_VIEWSCHEDULE_DAYS_SUPPORTED);
        }
    }
}
