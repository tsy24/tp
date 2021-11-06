package nurseybook.logic.commands;

import static nurseybook.commons.util.CollectionUtil.requireAllNonNull;
import static nurseybook.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nurseybook.commons.core.Messages;
import nurseybook.commons.core.index.Index;
import nurseybook.logic.commands.exceptions.CommandException;
import nurseybook.model.Model;
import nurseybook.model.person.Elderly;
import nurseybook.model.tag.Tag;

/**
 * Deletes at least one tag from an existing elderly in the NurseyBook.
 */
public class DeleteTagCommand extends Command {

    public static final String COMMAND_WORD = "deleteTag";
    public static final String[] PARAMETERS = { Index.VALID_INDEX_CRITERIA,
        PREFIX_TAG + "TAG", "[" + PREFIX_TAG + "MORE_TAGS]..." };

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Delete tag(s) from the elderly identified "
            + "by the index number used in the last elderly listing. "
            + "Must contain one or more tags\n"
            + "Parameters: "
            + String.join(" ", PARAMETERS)
            + "\nExample: " + COMMAND_WORD + " 1 "
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
        Set<Tag> updatedTags = removeTagsFromSet(elderlyToDeleteTag.getTags());
        Elderly updatedElderly = new Elderly(
                elderlyToDeleteTag.getName(), elderlyToDeleteTag.getAge(),
                elderlyToDeleteTag.getGender(), elderlyToDeleteTag.getRoomNumber(), elderlyToDeleteTag.getNok(),
                elderlyToDeleteTag.getRemark(), updatedTags);

        model.setElderly(elderlyToDeleteTag, updatedElderly);

        CommandResult result = new CommandResult(String.format(MESSAGE_DELETE_TAG_SUCCESS, updatedElderly));
        model.commitNurseyBook(result);
        return result;
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

    private Set<Tag> removeTagsFromSet(Set<Tag> currentTags) throws CommandException {
        Set<Tag> updatedTags = new HashSet<>(currentTags);
        for (Tag tagToDelete: tags) {
            if (!updatedTags.contains(tagToDelete)) {
                throw new CommandException(String.format(MESSAGE_NO_SUCH_TAG, tagToDelete.tagName));
            }
            updatedTags.remove(tagToDelete);
        }
        return updatedTags;
    }
}


