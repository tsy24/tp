package nurseybook.logic.parser;

import static nurseybook.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import nurseybook.commons.core.index.Index;
import nurseybook.logic.commands.DeleteNokCommand;
import nurseybook.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteNokCommand object
 */
public class DeleteNokCommandParser implements Parser<DeleteNokCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteNokCommand
     * and returns a DeleteNokCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteNokCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteNokCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteNokCommand.MESSAGE_USAGE), pe);
        }
    }

}
