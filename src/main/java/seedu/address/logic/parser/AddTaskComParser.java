package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_DESC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_TIME;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddTaskCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;
import seedu.address.model.task.DateTime;
import seedu.address.model.task.Description;
import seedu.address.model.task.Task;

/**
 * Parses input arguments and creates a new AddTaskCommand object
 */
public class AddTaskComParser implements Parser<AddTaskCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddTaskCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_TASK_DESC, PREFIX_TASK_DATE, PREFIX_TASK_TIME);

        if (!arePrefixesPresent(argMultimap, PREFIX_TASK_DESC, PREFIX_TASK_DATE, PREFIX_TASK_TIME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE));
        }

        Set<Name> relatedNames = ParserUtil.parseNames(argMultimap.getAllValues(PREFIX_NAME));
        Description description = ParserUtil.parseDesc(argMultimap.getValue(PREFIX_TASK_DESC).get());
        DateTime dateTime = ParserUtil.parseDateTime(
                argMultimap.getValue(PREFIX_TASK_DATE).get(),
                argMultimap.getValue(PREFIX_TASK_TIME).get());

        Task task = new Task(description, dateTime, relatedNames);

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
