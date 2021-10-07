package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Adds at least one tag to an existing elderly in the NurseyBook.
 */
public class AddTagCommand extends Command {

    public static final String COMMAND_WORD = "addTag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds tag(s) to the person identified "
            + "by the index number used in the last person listing. "
            + "Must contain one or more tags\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_TAG + "[TAG]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_TAG + "Diabetes";

    public static final String MESSAGE_ADD_TAG_SUCCESS = "Added tag(s) to Person: %1$s";

    private final Index index;
    private final Set<Tag> tags;

    /**
     * @param index of the person in the filtered person list to add the tag
     * @param tags of the person to be added
     */
    public AddTagCommand(Index index, Set<Tag> tags) {
        requireAllNonNull(index, tags);

        this.index = index;
        this.tags = tags;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToAddTag = lastShownList.get(index.getZeroBased());
        Set<Tag> currentTags = personToAddTag.getTags();
        Person addedPerson = new Person(
                personToAddTag.getName(), personToAddTag.getPhone(), personToAddTag.getAge(),
                personToAddTag.getGender(), personToAddTag.getRoomNumber(),
                personToAddTag.getEmail(), personToAddTag.getAddress(), personToAddTag.getRemark(),
                addTagsToSet(currentTags, tags));

        model.setPerson(personToAddTag, addedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(String.format(MESSAGE_ADD_TAG_SUCCESS, addedPerson));
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

