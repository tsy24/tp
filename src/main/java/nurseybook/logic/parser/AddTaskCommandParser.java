package nurseybook.logic.parser;

import static nurseybook.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static nurseybook.logic.parser.CliSyntax.PREFIX_NAME;
import static nurseybook.logic.parser.CliSyntax.PREFIX_TASK_DATE;
import static nurseybook.logic.parser.CliSyntax.PREFIX_TASK_DESC;
import static nurseybook.logic.parser.CliSyntax.PREFIX_TASK_RECURRING;
import static nurseybook.logic.parser.CliSyntax.PREFIX_TASK_TIME;

import java.util.Set;
import java.util.stream.Stream;

import nurseybook.logic.commands.AddTaskCommand;
import nurseybook.logic.parser.exceptions.ParseException;
import nurseybook.model.person.Name;
import nurseybook.model.task.DateTime;
import nurseybook.model.task.Description;
import nurseybook.model.task.Recurrence;
import nurseybook.model.task.Recurrence.RecurrenceType;
import nurseybook.model.task.Task;

/**
 * Parses input arguments and creates a new AddTaskCommand object
 */
public class AddTaskCommandParser implements Parser<AddTaskCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddTaskCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_TASK_DESC, PREFIX_TASK_DATE, PREFIX_TASK_TIME,
                        PREFIX_TASK_RECURRING);

        if (!arePrefixesPresent(argMultimap, PREFIX_TASK_DESC, PREFIX_TASK_DATE, PREFIX_TASK_TIME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE));
        }

        Set<Name> relatedNames = ParserUtil.parseNames(argMultimap.getAllValues(PREFIX_NAME));
        Description description = ParserUtil.parseDesc(argMultimap.getValue(PREFIX_TASK_DESC).get());
        DateTime dateTime = ParserUtil.parseDateTime(
                argMultimap.getValue(PREFIX_TASK_DATE).get(),
                argMultimap.getValue(PREFIX_TASK_TIME).get());
        Recurrence recurrence = ParserUtil.parseRecurrence(
                argMultimap.getValue(PREFIX_TASK_RECURRING).orElse(RecurrenceType.NONE.name()));

        Task task = new Task(description, dateTime, relatedNames, recurrence);

        return new AddTaskCommand(task);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
