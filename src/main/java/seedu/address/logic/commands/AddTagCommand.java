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
 * Adds at least one tag to an existing elderly in the NurseyBook.
 */
public class AddTagCommand extends Command {

    public static final String COMMAND_WORD = "addTag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds tag(s) to the elderly identified "
            + "by the index number used in the last elderly listing. "
            + "Must contain one or more tags\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_TAG + "[TAG]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_TAG + "Diabetes";

    public static final String MESSAGE_ADD_TAG_SUCCESS = "Added tag(s) to Elderly: %1$s";

    private final Index index;
    private final Set<Tag> tags;

    /**
     * @param index of the elderly in the filtered elderly list to add the tag
     * @param tags of the elderly to be added
     */
    public AddTagCommand(Index index, Set<Tag> tags) {
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

        Elderly elderlyToAddTag = lastShownList.get(index.getZeroBased());
        Set<Tag> currentTags = elderlyToAddTag.getTags();
        Elderly addedElderly = new Elderly(
                elderlyToAddTag.getName(), elderlyToAddTag.getAge(),
                elderlyToAddTag.getGender(), elderlyToAddTag.getRoomNumber(),
                elderlyToAddTag.getNok(), elderlyToAddTag.getRemark(),
                addTagsToSet(currentTags, tags));

        model.setElderly(elderlyToAddTag, addedElderly);
        model.updateFilteredElderlyList(PREDICATE_SHOW_ALL_ELDERLIES);

        return new CommandResult(String.format(MESSAGE_ADD_TAG_SUCCESS, addedElderly));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddTagCommand)) {
            return false;
        }

        // state check
        AddTagCommand e = (AddTagCommand) other;
        return index.equals(e.index)
                && tags.equals(e.tags);
    }

    private Set<Tag> addTagsToSet(Set<Tag> currentTags, Set<Tag> newTags) {
        Set<Tag> addedSet = new HashSet<>();
        addedSet.addAll(currentTags);
        addedSet.addAll(newTags);
        return addedSet;
    }
}

