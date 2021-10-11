package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.SET_ONE_TAG;
import static seedu.address.logic.commands.CommandTestUtil.SET_TWO_TAGS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_DIABETES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class DeleteTagCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_deleteTagUnfilteredList_success() {
        Person secondPerson = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        Person tagDeletedPerson = new PersonBuilder(secondPerson).withTags(VALID_TAG_FRIEND).build();

        DeleteTagCommand deleteTagCommand = new DeleteTagCommand(INDEX_SECOND_PERSON, SET_ONE_TAG);

        String expectedMessage = String.format(DeleteTagCommand.MESSAGE_DELETE_TAG_SUCCESS, tagDeletedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(secondPerson, tagDeletedPerson);

        assertCommandSuccess(deleteTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteTagsUnfilteredList_success() {
        Person secondPerson = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        Person tagDeletedPerson = new PersonBuilder(secondPerson).withoutTags().build();

        DeleteTagCommand deleteTagCommand = new DeleteTagCommand(INDEX_SECOND_PERSON, SET_TWO_TAGS);

        String expectedMessage = String.format(DeleteTagCommand.MESSAGE_DELETE_TAG_SUCCESS, tagDeletedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(secondPerson, tagDeletedPerson);

        assertCommandSuccess(deleteTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteNewTagUnfilteredList_failure() {

        // Only one new tag
        DeleteTagCommand addTagCommand = new DeleteTagCommand(INDEX_FIRST_PERSON, SET_ONE_TAG);
        String expectedMessage = String.format(DeleteTagCommand.MESSAGE_NO_SUCH_TAG, VALID_TAG_DIABETES);
        assertCommandFailure(addTagCommand, model, expectedMessage);

        // Mix of new and existing tags
        addTagCommand = new DeleteTagCommand(INDEX_FIRST_PERSON, SET_TWO_TAGS);
        expectedMessage = String.format(DeleteTagCommand.MESSAGE_NO_SUCH_TAG, VALID_TAG_DIABETES);
        assertCommandFailure(addTagCommand, model, expectedMessage);
    }

    @Test
    public void execute_deleteTagFilteredList_success() {
        showPersonAtIndex(model, INDEX_SECOND_PERSON);

        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person tagDeletedPerson = new PersonBuilder(model.getFilteredPersonList()
                .get(INDEX_FIRST_PERSON.getZeroBased())).withTags(VALID_TAG_FRIEND).build();

        DeleteTagCommand deleteTagCommand = new DeleteTagCommand(INDEX_FIRST_PERSON, SET_ONE_TAG);

        String expectedMessage = String.format(DeleteTagCommand.MESSAGE_DELETE_TAG_SUCCESS, tagDeletedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, tagDeletedPerson);

        assertCommandSuccess(deleteTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeleteTagCommand deleteTagCommand = new DeleteTagCommand(outOfBoundIndex, SET_ONE_TAG);

        assertCommandFailure(deleteTagCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        DeleteTagCommand deleteTagCommand = new DeleteTagCommand(outOfBoundIndex, SET_ONE_TAG);

        assertCommandFailure(deleteTagCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final DeleteTagCommand standardCommand = new DeleteTagCommand(INDEX_FIRST_PERSON,
                SET_ONE_TAG);
        // same values -> returns true
        DeleteTagCommand commandWithSameValues = new DeleteTagCommand(INDEX_FIRST_PERSON,
                SET_ONE_TAG);
        assertTrue(standardCommand.equals(commandWithSameValues));
        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));
        // null -> returns false
        assertFalse(standardCommand.equals(null));
        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));
        // different index -> returns false
        assertFalse(standardCommand.equals(new DeleteTagCommand(INDEX_SECOND_PERSON,
                SET_ONE_TAG)));
        // different set of tags -> returns false
        assertFalse(standardCommand.equals(new DeleteTagCommand(INDEX_FIRST_PERSON,
                SET_TWO_TAGS)));
    }
}
