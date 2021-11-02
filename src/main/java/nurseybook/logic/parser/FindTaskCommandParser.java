package nurseybook.logic.parser;

import static nurseybook.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import nurseybook.logic.commands.Command;
import nurseybook.logic.commands.FindTaskCommand;
import nurseybook.logic.parser.exceptions.ParseException;
import nurseybook.model.task.DescriptionContainsKeywordPredicate;

/**
 * Parses input arguments and creates a new FindTaskCommand object.
 */
public class FindTaskCommandParser implements Parser<Command> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindTaskCommand
     * and returns a FindTaskCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindTaskCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindTaskCommand.MESSAGE_USAGE));
        }

        String[] descKeywords = trimmedArgs.split("\\s+");

        return new FindTaskCommand(new DescriptionContainsKeywordPredicate(Arrays.asList(descKeywords)));
    }
}
