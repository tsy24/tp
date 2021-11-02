package nurseybook.logic.parser;

import static java.util.Objects.requireNonNull;
import static nurseybook.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Collection;
import java.util.Collections;
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

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, CliSyntax.PREFIX_NAME, CliSyntax.PREFIX_AGE, CliSyntax.PREFIX_GENDER,
                        CliSyntax.PREFIX_ROOM_NUM, CliSyntax.PREFIX_NOK_NAME, CliSyntax.PREFIX_RELATIONSHIP,
                        CliSyntax.PREFIX_PHONE, CliSyntax.PREFIX_EMAIL, CliSyntax.PREFIX_ADDRESS, CliSyntax.PREFIX_TAG);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }

        EditElderlyDescriptor editElderlyDescriptor = new EditElderlyDescriptor();
        if (argMultimap.getValue(CliSyntax.PREFIX_NAME).isPresent()) {
            editElderlyDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(CliSyntax.PREFIX_NAME).get()));
        }

        if (argMultimap.getValue(CliSyntax.PREFIX_AGE).isPresent()) {
            editElderlyDescriptor.setAge(ParserUtil.parseAge(argMultimap.getValue(CliSyntax.PREFIX_AGE).get()));
        }

        if (argMultimap.getValue(CliSyntax.PREFIX_GENDER).isPresent()) {
            editElderlyDescriptor.setGender(ParserUtil
                    .parseGender(argMultimap.getValue(CliSyntax.PREFIX_GENDER).get()));
        }

        if (argMultimap.getValue(CliSyntax.PREFIX_ROOM_NUM).isPresent()) {
            editElderlyDescriptor.setRoomNumber(
                    ParserUtil.parseRoomNumber(argMultimap.getValue(CliSyntax.PREFIX_ROOM_NUM).get()));
        }

        if (argMultimap.getValue(CliSyntax.PREFIX_NOK_NAME).isPresent()) {
            editElderlyDescriptor.setNokName(ParserUtil
                    .parseNokName(argMultimap.getValue(CliSyntax.PREFIX_NOK_NAME).get()));
        }

        if (argMultimap.getValue(CliSyntax.PREFIX_RELATIONSHIP).isPresent()) {
            editElderlyDescriptor.setRelationship(
                    ParserUtil.parseRelationship(argMultimap.getValue(CliSyntax.PREFIX_RELATIONSHIP).get()));
        }

        if (argMultimap.getValue(CliSyntax.PREFIX_PHONE).isPresent()) {
            editElderlyDescriptor.setNokPhone(ParserUtil
                    .parsePhone(argMultimap.getValue(CliSyntax.PREFIX_PHONE).get()));
        }

        if (argMultimap.getValue(CliSyntax.PREFIX_EMAIL).isPresent()) {
            editElderlyDescriptor.setNokEmail(ParserUtil
                    .parseEmail(argMultimap.getValue(CliSyntax.PREFIX_EMAIL).get()));
        }

        if (argMultimap.getValue(CliSyntax.PREFIX_ADDRESS).isPresent()) {
            editElderlyDescriptor.setNokAddress(ParserUtil
                    .parseAddress(argMultimap.getValue(CliSyntax.PREFIX_ADDRESS).get()));
        }

        parseTagsForEdit(argMultimap.getAllValues(CliSyntax.PREFIX_TAG)).ifPresent(editElderlyDescriptor::setTags);

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

}
