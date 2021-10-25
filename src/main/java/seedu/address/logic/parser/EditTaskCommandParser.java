package seedu.address.logic.parser;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditTaskCommand;
import seedu.address.logic.commands.EditTaskCommand.EditTaskDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_DESC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_RECURRING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_TIME;

public class EditTaskCommandParser implements Parser<EditTaskCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditTaskCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_TASK_DESC, PREFIX_TASK_DATE,
                        PREFIX_TASK_TIME, PREFIX_TASK_RECURRING);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditTaskCommand.MESSAGE_USAGE), pe);
        }

        EditTaskDescriptor editTaskDescriptor = new EditTaskDescriptor();
        if (argMultimap.getValue(PREFIX_TASK_DESC).isPresent()) {
            editTaskDescriptor.setDescription(ParserUtil.parseDesc(argMultimap.getValue(PREFIX_TASK_DESC).get()));
        }

        if (argMultimap.getValue(PREFIX_TASK_DATE).isPresent()) {
            editTaskDescriptor.setDate(ParserUtil.parseDate(argMultimap.getValue(PREFIX_TASK_DATE).get()));
        }

        if (argMultimap.getValue(PREFIX_TASK_TIME).isPresent()) {
            editTaskDescriptor.setTime(ParserUtil.parseTime(argMultimap.getValue(PREFIX_TASK_TIME).get()));
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
}
