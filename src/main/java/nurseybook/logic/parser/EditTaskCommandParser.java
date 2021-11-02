package nurseybook.logic.parser;

import static java.util.Objects.requireNonNull;
import static nurseybook.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static nurseybook.logic.parser.CliSyntax.PREFIX_ALL;
import static nurseybook.logic.parser.CliSyntax.PREFIX_NAME;
import static nurseybook.logic.parser.CliSyntax.PREFIX_PREAMBLE;
import static nurseybook.logic.parser.CliSyntax.PREFIX_TASK_DATE;
import static nurseybook.logic.parser.CliSyntax.PREFIX_TASK_DESC;
import static nurseybook.logic.parser.CliSyntax.PREFIX_TASK_RECURRING;
import static nurseybook.logic.parser.CliSyntax.PREFIX_TASK_TIME;
import static nurseybook.logic.parser.ParserUtil.MESSAGE_INDEX_TOO_EXTREME;
import static nurseybook.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import nurseybook.commons.core.index.Index;
import nurseybook.logic.commands.EditTaskCommand;
import nurseybook.logic.commands.EditTaskCommand.EditTaskDescriptor;
import nurseybook.logic.parser.exceptions.ParseException;
import nurseybook.model.person.Name;

public class EditTaskCommandParser implements Parser<EditTaskCommand> {

    private static final List<Prefix> EXPECTED_PREFIXES = List.of(PREFIX_PREAMBLE, PREFIX_NAME, PREFIX_TASK_DESC,
            PREFIX_TASK_DATE, PREFIX_TASK_TIME, PREFIX_TASK_RECURRING);
    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditTaskCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ALL);

        Index index;

        try {
            String str = argMultimap.getPreamble();
            index = ParserUtil.parseIndex(str);
        } catch (ParseException pe) {
            if (pe.getMessage().equals(MESSAGE_INDEX_TOO_EXTREME) || pe.getMessage().equals(MESSAGE_INVALID_INDEX)) {
                throw pe;
            } else {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditTaskCommand.MESSAGE_USAGE), pe);
            }
        }

        if (!onlyExpectedPrefixesPresent(argMultimap)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditTaskCommand.MESSAGE_USAGE));
        }

        EditTaskDescriptor editTaskDescriptor = new EditTaskDescriptor();
        if (argMultimap.getValue(PREFIX_TASK_DESC).isPresent()) {
            editTaskDescriptor.setDescription(ParserUtil
                    .parseDesc(argMultimap.getValue(PREFIX_TASK_DESC).get()));
        }

        if (argMultimap.getValue(PREFIX_TASK_DATE).isPresent()) {
            editTaskDescriptor.setDate(ParserUtil
                    .parseDate(argMultimap.getValue(PREFIX_TASK_DATE).get()));
        }

        if (argMultimap.getValue(PREFIX_TASK_TIME).isPresent()) {
            editTaskDescriptor.setTime(ParserUtil
                    .parseTime(argMultimap.getValue(PREFIX_TASK_TIME).get()));
        }

        if (argMultimap.getValue(PREFIX_TASK_RECURRING).isPresent()) {
            editTaskDescriptor.setRecurrence(
                    ParserUtil.parseRecurrence(argMultimap.getValue(PREFIX_TASK_RECURRING).get()));
        }

        parseNamesForEdit(argMultimap.getAllValues(PREFIX_NAME)).ifPresent(editTaskDescriptor::setNames);

        if (!editTaskDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditTaskCommand.MESSAGE_NOT_EDITED);
        }

        return new EditTaskCommand(index, editTaskDescriptor);
    }

    /**
     * Parses {@code Collection<String> names} into a {@code Set<Name>} if {@code names} is non-empty.
     * If {@code names} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Name>} containing zero names.
     */
    private Optional<Set<Name>> parseNamesForEdit(Collection<String> names) throws ParseException {
        assert names != null;

        if (names.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> nameSet = names.size() == 1 && names.contains("") ? Collections.emptySet() : names;
        return Optional.of(ParserUtil.parseNames(nameSet));
    }
    /**
     * Returns true if all the prefixes in the {@code ArgumentMultimap} are
     * expected prefixes for this command.
     */
    private static boolean onlyExpectedPrefixesPresent(ArgumentMultimap argumentMultimap) {
        return argumentMultimap.getPrefixes().stream().allMatch(prefix -> EXPECTED_PREFIXES.contains(prefix));
    }
}
