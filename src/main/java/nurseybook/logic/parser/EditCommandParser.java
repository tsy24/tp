//@@author Superbestron
package nurseybook.logic.parser;

import static java.util.Objects.requireNonNull;
import static nurseybook.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static nurseybook.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static nurseybook.logic.parser.CliSyntax.PREFIX_AGE;
import static nurseybook.logic.parser.CliSyntax.PREFIX_ALL;
import static nurseybook.logic.parser.CliSyntax.PREFIX_EMAIL;
import static nurseybook.logic.parser.CliSyntax.PREFIX_GENDER;
import static nurseybook.logic.parser.CliSyntax.PREFIX_NAME;
import static nurseybook.logic.parser.CliSyntax.PREFIX_NOK_NAME;
import static nurseybook.logic.parser.CliSyntax.PREFIX_PHONE;
import static nurseybook.logic.parser.CliSyntax.PREFIX_PREAMBLE;
import static nurseybook.logic.parser.CliSyntax.PREFIX_RELATIONSHIP;
import static nurseybook.logic.parser.CliSyntax.PREFIX_ROOM_NUM;
import static nurseybook.logic.parser.CliSyntax.PREFIX_TAG;
import static nurseybook.logic.parser.ParserUtil.MESSAGE_INDEX_TOO_EXTREME;
import static nurseybook.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static nurseybook.logic.parser.ParserUtil.MESSAGE_UNKNOWN_INDEX;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import nurseybook.commons.core.index.Index;
import nurseybook.logic.commands.EditCommand;
import nurseybook.logic.commands.EditCommand.EditElderlyDescriptor;
import nurseybook.logic.parser.exceptions.ParseException;
import nurseybook.model.tag.Tag;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    private static final List<Prefix> EXPECTED_PREFIXES = List.of(PREFIX_PREAMBLE, PREFIX_NAME, PREFIX_AGE,
            PREFIX_GENDER, PREFIX_ROOM_NUM, PREFIX_NOK_NAME, PREFIX_RELATIONSHIP, PREFIX_PHONE, PREFIX_EMAIL,
            PREFIX_ADDRESS, PREFIX_TAG);
    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_ALL);

        Index index;

        try {
            String str = argMultimap.getPreamble();
            if (str.contains("/ ")) {
                throw new ParseException(MESSAGE_UNKNOWN_INDEX);
            }
            if (!str.matches(".*\\d.*")) {
                throw new ParseException(MESSAGE_UNKNOWN_INDEX);
            }
            index = ParserUtil.parseIndex(str);
        } catch (ParseException pe) {
            if (pe.getMessage().equals(MESSAGE_INDEX_TOO_EXTREME) || pe.getMessage().equals(MESSAGE_INVALID_INDEX)) {
                throw pe;
            } else {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
            }
        }

        if (!onlyExpectedPrefixesPresent(argMultimap)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }

        EditElderlyDescriptor editElderlyDescriptor = new EditElderlyDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editElderlyDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }

        if (argMultimap.getValue(PREFIX_AGE).isPresent()) {
            editElderlyDescriptor.setAge(ParserUtil.parseAge(argMultimap.getValue(PREFIX_AGE).get()));
        }

        if (argMultimap.getValue(PREFIX_GENDER).isPresent()) {
            editElderlyDescriptor.setGender(ParserUtil
                    .parseGender(argMultimap.getValue(PREFIX_GENDER).get()));
        }

        if (argMultimap.getValue(PREFIX_ROOM_NUM).isPresent()) {
            editElderlyDescriptor.setRoomNumber(
                    ParserUtil.parseRoomNumber(argMultimap.getValue(PREFIX_ROOM_NUM).get()));
        }

        if (argMultimap.getValue(PREFIX_NOK_NAME).isPresent()) {
            editElderlyDescriptor.setNokName(ParserUtil
                    .parseNokName(argMultimap.getValue(PREFIX_NOK_NAME).get()));
        }

        if (argMultimap.getValue(PREFIX_RELATIONSHIP).isPresent()) {
            editElderlyDescriptor.setRelationship(
                    ParserUtil.parseRelationship(argMultimap.getValue(PREFIX_RELATIONSHIP).get()));
        }

        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            editElderlyDescriptor.setNokPhone(ParserUtil
                    .parsePhone(argMultimap.getValue(PREFIX_PHONE).get()));
        }

        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            editElderlyDescriptor.setNokEmail(ParserUtil
                    .parseEmail(argMultimap.getValue(PREFIX_EMAIL).get()));
        }

        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            editElderlyDescriptor.setNokAddress(ParserUtil
                    .parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get()));
        }

        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editElderlyDescriptor::setTags);

        if (!editElderlyDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editElderlyDescriptor);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

    /**
     * Returns true if all the prefixes in the {@code ArgumentMultimap} are
     * expected prefixes for this command.
     */
    private static boolean onlyExpectedPrefixesPresent(ArgumentMultimap argumentMultimap) {
        return argumentMultimap.getPrefixes().stream().allMatch(prefix -> EXPECTED_PREFIXES.contains(prefix));
    }
}
