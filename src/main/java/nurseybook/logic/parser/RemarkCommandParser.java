package nurseybook.logic.parser;

import static java.util.Objects.requireNonNull;
import static nurseybook.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static nurseybook.logic.parser.CliSyntax.PREFIX_ALL;
import static nurseybook.logic.parser.CliSyntax.PREFIX_PREAMBLE;
import static nurseybook.logic.parser.CliSyntax.PREFIX_REMARK;
import static nurseybook.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;

import nurseybook.commons.core.index.Index;
import nurseybook.commons.exceptions.IllegalValueException;
import nurseybook.logic.commands.EditCommand;
import nurseybook.logic.commands.RemarkCommand;
import nurseybook.logic.parser.exceptions.ParseException;
import nurseybook.model.person.Remark;

/**
 * Parses input arguments and creates a new {@code RemarkCommand} object
 */
public class RemarkCommandParser implements Parser<RemarkCommand> {

    private static final List<Prefix> EXPECTED_PREFIXES = List.of(PREFIX_PREAMBLE, PREFIX_REMARK);
    /**
     * Parses the given {@code String} of arguments in the context of the {@code RemarkCommand}
     * and returns a {@code RemarkCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemarkCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_ALL);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE), ive);
        }

        if (!onlyExpectedPrefixesPresent(argMultimap)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE));
        }

        String remark = argMultimap.getValue(PREFIX_REMARK).orElse("");

        return new RemarkCommand(index, new Remark(remark));
    }

    /**
     * Returns true if all the prefixes in the {@code ArgumentMultimap} are
     * expected prefixes for this command.
     */
    private static boolean onlyExpectedPrefixesPresent(ArgumentMultimap argumentMultimap) {
        return argumentMultimap.getPrefixes().stream().allMatch(prefix -> EXPECTED_PREFIXES.contains(prefix));
    }
}
