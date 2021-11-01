package nurseybook.logic.parser;

import static java.util.Objects.requireNonNull;
import static nurseybook.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import nurseybook.commons.core.index.Index;
import nurseybook.logic.commands.EditTaskCommand;
import nurseybook.logic.commands.EditTaskCommand.EditTaskDescriptor;
import nurseybook.logic.parser.exceptions.ParseException;
import nurseybook.model.person.Name;

public class EditTaskCommandParser implements Parser<EditTaskCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditTaskCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, CliSyntax.PREFIX_NAME, CliSyntax.PREFIX_TASK_DESC,
                        CliSyntax.PREFIX_TASK_DATE, CliSyntax.PREFIX_TASK_TIME, CliSyntax.PREFIX_TASK_RECURRING);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditTaskCommand.MESSAGE_USAGE), pe);
        }

        EditTaskDescriptor editTaskDescriptor = new EditTaskDescriptor();
        if (argMultimap.getValue(CliSyntax.PREFIX_TASK_DESC).isPresent()) {
            editTaskDescriptor.setDescription(ParserUtil
                    .parseDesc(argMultimap.getValue(CliSyntax.PREFIX_TASK_DESC).get()));
        }

        if (argMultimap.getValue(CliSyntax.PREFIX_TASK_DATE).isPresent()) {
            editTaskDescriptor.setDate(ParserUtil
                    .parseDate(argMultimap.getValue(CliSyntax.PREFIX_TASK_DATE).get()));
        }

        if (argMultimap.getValue(CliSyntax.PREFIX_TASK_TIME).isPresent()) {
            editTaskDescriptor.setTime(ParserUtil
                    .parseTime(argMultimap.getValue(CliSyntax.PREFIX_TASK_TIME).get()));
        }

        if (argMultimap.getValue(CliSyntax.PREFIX_TASK_RECURRING).isPresent()) {
            editTaskDescriptor.setRecurrence(
                    ParserUtil.parseRecurrence(argMultimap.getValue(CliSyntax.PREFIX_TASK_RECURRING).get()));
        }

        parseNamesForEdit(argMultimap.getAllValues(CliSyntax.PREFIX_NAME)).ifPresent(editTaskDescriptor::setNames);

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
}
