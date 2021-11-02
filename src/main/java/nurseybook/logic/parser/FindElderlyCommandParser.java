package nurseybook.logic.parser;

import static nurseybook.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import nurseybook.logic.commands.FindElderlyCommand;
import nurseybook.logic.parser.exceptions.ParseException;
import nurseybook.model.person.NameContainsKeywordsPredicate;

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
