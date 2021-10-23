package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.FindElderlyCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindElderlyCommand object
 */
public class FindElderlyCommandParser implements Parser<FindElderlyCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindElderlyCommand
     * and returns a FindElderlyCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindElderlyCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindElderlyCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindElderlyCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
