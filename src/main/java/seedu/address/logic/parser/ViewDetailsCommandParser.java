package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ViewDetailsCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class ViewDetailsCommandParser implements Parser<ViewDetailsCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ViewDetailsCommand
     * and returns a ViewDetailsCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewDetailsCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new ViewDetailsCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewDetailsCommand.MESSAGE_USAGE), pe);
        }
    }

}
