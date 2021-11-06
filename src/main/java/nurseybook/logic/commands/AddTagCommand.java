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
 * Adds at least one tag to an existing elderly in the nursey book.
 */
public class AddTagCommand extends Command {

    public static final String COMMAND_WORD = "addTag";
    public static final String[] PARAMETERS = { Index.VALID_INDEX_CRITERIA,
        PREFIX_TAG + "TAG", "[" + PREFIX_TAG + "MORE_TAGS]..." };

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds tag(s) to the elderly identified "
            + "by the index number used in the last elderly listing. "
            + "Must contain one or more tags\n"
            + "Parameters: "
            + String.join(" ", PARAMETERS)
            + "\nExample: " + COMMAND_WORD + " 1 "
            + PREFIX_TAG + "Diabetes";

    public static final String MESSAGE_ADD_TAG_SUCCESS = "Added tag(s) to Elderly: %1$s";

    public static final String MESSAGE_TAG_EXISTS = "'%1$s' tag already exists for elderly";

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

        CommandResult result = new CommandResult(String.format(MESSAGE_ADD_TAG_SUCCESS, addedElderly));
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
        if (!(other instanceof AddTagCommand)) {
            return false;
        }

        // state check
        AddTagCommand e = (AddTagCommand) other;
        return index.equals(e.index)
                && tags.equals(e.tags);
    }

    private Set<Tag> addTagsToSet(Set<Tag> currentTags, Set<Tag> newTags) throws CommandException {
        Set<Tag> addedSet = new HashSet<>();
        addedSet.addAll(currentTags);
        for (Tag tagToAdd: tags) {
            if (addedSet.contains(tagToAdd)) {
                throw new CommandException(String.format(MESSAGE_TAG_EXISTS, tagToAdd.tagName));
            }
            addedSet.add(tagToAdd);
        }
        return addedSet;
    }
}

