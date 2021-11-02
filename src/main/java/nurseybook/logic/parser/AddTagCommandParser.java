package nurseybook.logic.parser;

import static nurseybook.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static nurseybook.logic.parser.CliSyntax.PREFIX_ALL;
import static nurseybook.logic.parser.CliSyntax.PREFIX_PREAMBLE;
import static nurseybook.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import nurseybook.commons.core.index.Index;
import nurseybook.logic.commands.AddTagCommand;
import nurseybook.logic.parser.exceptions.ParseException;
import nurseybook.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddTagCommand object
 */
public class AddTagCommandParser implements Parser<AddTagCommand> {

    private static final List<Prefix> EXPECTED_PREFIXES = List.of(PREFIX_PREAMBLE, PREFIX_TAG);

    /**
     * Parses the given {@code String} of arguments in the context of the AddTagCommand
     * and returns an AddTagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddTagCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ALL);

        if (!arePrefixesPresent(argMultimap, PREFIX_TAG)
                || !onlyExpectedPrefixesPresent(argMultimap) || argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE));
        }

        Index index = ParserUtil.parseIndex(argMultimap.getPreamble());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        return new AddTagCommand(index, tagList);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Returns true if all the prefixes in the {@code ArgumentMultimap} are
     * expected prefixes for this command.
     */
    private static boolean onlyExpectedPrefixesPresent(ArgumentMultimap argumentMultimap) {
        return argumentMultimap.getPrefixes().stream().allMatch(prefix -> EXPECTED_PREFIXES.contains(prefix));
    }

}

