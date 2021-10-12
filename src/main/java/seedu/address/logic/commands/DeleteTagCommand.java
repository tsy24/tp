package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ELDERLIES;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Elderly;
import seedu.address.model.tag.Tag;

/**
 * Deletes at least one tag from an existing elderly in the NurseyBook.
 */
public class DeleteTagCommand extends Command {

    public static final String COMMAND_WORD = "deleteTag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Delete tag(s) from the elderly identified "
            + "by the index number used in the last elderly listing. "
            + "Must contain one or more tags\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_TAG + "[TAG]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_TAG + "Diabetes";

    public static final String MESSAGE_DELETE_TAG_SUCCESS = "Deleted tag(s) from Elderly: %1$s";
    public static final String MESSAGE_NO_SUCH_TAG = "'%1$s' tag does not exist for elderly";

    private final Index index;
    private final Set<Tag> tags;

    /**
     * @param index of the elderly in the filtered elderly list to add the tag
     * @param tags of the elderly to be added
     */
    public DeleteTagCommand(Index index, Set<Tag> tags) {
        requireAllNonNull(index, tags);

        this.index = index;
        this.tags = tags;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Elderly> lastShownList = model.getFilteredElderlyList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ELDERLY_DISPLAYED_INDEX);
        }

        Elderly elderlyToDeleteTag = lastShownList.get(index.getZeroBased());
        Set<Tag> updatedTags = new HashSet<>(elderlyToDeleteTag.getTags());
        for (Tag tagToDelete: tags) {
            if (!updatedTags.contains(tagToDelete)) {
                throw new CommandException(String.format(MESSAGE_NO_SUCH_TAG, tagToDelete.tagName));
            }
            updatedTags.remove(tagToDelete);
        }
        Elderly updatedElderly = new Elderly(
                elderlyToDeleteTag.getName(), elderlyToDeleteTag.getAge(),
                elderlyToDeleteTag.getGender(), elderlyToDeleteTag.getRoomNumber(), elderlyToDeleteTag.getNok(),
                elderlyToDeleteTag.getRemark(), updatedTags);

        model.setElderly(elderlyToDeleteTag, updatedElderly);
        model.updateFilteredElderlyList(PREDICATE_SHOW_ALL_ELDERLIES);

        return new CommandResult(String.format(MESSAGE_DELETE_TAG_SUCCESS, updatedElderly));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteTagCommand)) {
            return false;
        }

        // state check
        DeleteTagCommand e = (DeleteTagCommand) other;
        return index.equals(e.index)
                && tags.equals(e.tags);
    }
}


