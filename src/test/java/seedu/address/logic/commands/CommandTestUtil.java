package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.DeleteCommand.MESSAGE_DELETE_ELDERLY_SUCCESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOK_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RELATIONSHIP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROOM_NUM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.person.Elderly;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.EditElderlyDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_AGE_AMY = "67";
    public static final String VALID_AGE_BOB = "54";
    public static final String VALID_GENDER_AMY = "F";
    public static final String VALID_GENDER_BOB = "M";
    public static final String VALID_ROOM_NUMBER_AMY = "21";
    public static final String VALID_ROOM_NUMBER_BOB = "32";
    public static final String VALID_NOK_PHONE_AMY = "11111111";
    public static final String VALID_NOK_PHONE_BOB = "22222222";
    public static final String VALID_NOK_NAME_AMY = "Amy Cee";
    public static final String VALID_NOK_NAME_BOB = "Bob boo";
    public static final String VALID_NOK_RELATIONSHIP_AMY = "Mother";
    public static final String VALID_NOK_RELATIONSHIP_BOB = "Father";
    public static final String VALID_NOK_EMAIL_AMY = "amy@example.com";
    public static final String VALID_NOK_EMAIL_BOB = "bob@example.com";
    public static final String VALID_NOK_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_NOK_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_REMARK_AMY = "Like skiing.";
    public static final String VALID_REMARK_BOB = "Favourite pastime: Eating";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friends";
    public static final String VALID_TAG_DIABETES = "diabetes";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String AGE_DESC_AMY = " " + PREFIX_AGE + VALID_AGE_AMY;
    public static final String AGE_DESC_BOB = " " + PREFIX_AGE + VALID_AGE_BOB;
    public static final String GENDER_DESC_AMY = " " + PREFIX_GENDER + VALID_GENDER_AMY;
    public static final String GENDER_DESC_BOB = " " + PREFIX_GENDER + VALID_GENDER_BOB;
    public static final String ROOM_NUMBER_DESC_AMY = " " + PREFIX_ROOM_NUM + VALID_ROOM_NUMBER_AMY;
    public static final String ROOM_NUMBER_DESC_BOB = " " + PREFIX_ROOM_NUM + VALID_ROOM_NUMBER_BOB;
    public static final String NOK_NAME_DESC_AMY = " " + PREFIX_NOK_NAME + VALID_NOK_NAME_AMY;
    public static final String NOK_NAME_DESC_BOB = " " + PREFIX_NOK_NAME + VALID_NOK_NAME_BOB;
    public static final String NOK_RELATIONSHIP_DESC_AMY = " " + PREFIX_RELATIONSHIP + VALID_NOK_RELATIONSHIP_AMY;
    public static final String NOK_RELATIONSHIP_DESC_BOB = " " + PREFIX_RELATIONSHIP + VALID_NOK_RELATIONSHIP_BOB;
    public static final String NOK_PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_NOK_PHONE_AMY;
    public static final String NOK_PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_NOK_PHONE_BOB;
    public static final String NOK_EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_NOK_EMAIL_AMY;
    public static final String NOK_EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_NOK_EMAIL_BOB;
    public static final String NOK_ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_NOK_ADDRESS_AMY;
    public static final String NOK_ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_NOK_ADDRESS_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;
    public static final String TAG_DESC_DIABETES = " " + PREFIX_TAG + VALID_TAG_DIABETES;
    public static final String TAG_EMPTY = " " + PREFIX_TAG;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_AGE_DESC = " " + PREFIX_AGE + "2d7"; // 'd' not allowed in age
    public static final String INVALID_GENDER_DESC = " " + PREFIX_GENDER + "a"; // other than 'M' or 'F' not allowed
    public static final String INVALID_ROOM_NUMBER_DESC = " "
            + PREFIX_ROOM_NUM + "18a"; // '18a' not allowed in room numbers
    public static final String INVALID_NOK_NAME_DESC = " " + PREFIX_NOK_NAME + "James&"; // '&' not allowed in nokNames
    public static final String INVALID_NOK_RELATIONSHIP_DESC = " "
            + PREFIX_RELATIONSHIP + "2da"; // '2' not allowed in relationship
    public static final String INVALID_NOK_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_NOK_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditElderlyDescriptor DESC_AMY;
    public static final EditCommand.EditElderlyDescriptor DESC_BOB;
    public static final Set<Tag> SET_ONE_TAG;
    public static final Set<Tag> SET_TWO_TAGS;

    static {
        DESC_AMY = new EditElderlyDescriptorBuilder().withName(VALID_NAME_AMY)
                .withAge(VALID_AGE_AMY).withGender(VALID_GENDER_AMY).withRoomNumber(VALID_ROOM_NUMBER_AMY)
                .withNokName(VALID_NOK_NAME_AMY).withRelationship(VALID_NOK_RELATIONSHIP_AMY)
                .withPhone(VALID_NOK_PHONE_AMY).withEmail(VALID_NOK_EMAIL_AMY)
                .withAddress(VALID_NOK_ADDRESS_AMY).withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditElderlyDescriptorBuilder().withName(VALID_NAME_BOB)
                .withAge(VALID_AGE_BOB).withGender(VALID_GENDER_BOB).withRoomNumber(VALID_ROOM_NUMBER_AMY)
                .withNokName(VALID_NOK_NAME_BOB).withRelationship(VALID_NOK_RELATIONSHIP_BOB)
                .withPhone(VALID_NOK_PHONE_BOB).withEmail(VALID_NOK_EMAIL_BOB)
                .withAddress(VALID_NOK_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();

        SET_ONE_TAG = Set.of(new Tag(VALID_TAG_DIABETES));
        SET_TWO_TAGS = Set.of(new Tag(VALID_TAG_DIABETES), new Tag(VALID_TAG_FRIEND));
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage} and boolean {@code expectedIsViewDetails} .
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
                                            boolean expectedIsViewDetails, Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage, expectedIsViewDetails);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered elderly list and selected elderly in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getVersionedNurseyBook());
        List<Elderly> expectedFilteredList = new ArrayList<>(actualModel.getFilteredElderlyList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAddressBook, actualModel.getVersionedNurseyBook());
        assertEquals(expectedFilteredList, actualModel.getFilteredElderlyList());
    }
    /**
     * Updates {@code model}'s filtered list to show only the elderly at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showElderlyAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredElderlyList().size());

        Elderly elderly = model.getFilteredElderlyList().get(targetIndex.getZeroBased());
        final String[] splitName = elderly.getName().fullName.split("\\s+");
        model.updateFilteredElderlyList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredElderlyList().size());
    }

    /**
     * Deletes the first person in {@code model}'s filtered list from {@code model}'s address book.
     */
    public static CommandResult deleteFirstElderly(Model model) {
        Elderly firstElderly = model.getFilteredElderlyList().get(0);
        model.deleteElderly(firstElderly);
        CommandResult result = new CommandResult(String.format(MESSAGE_DELETE_ELDERLY_SUCCESS, firstElderly));
        model.commitNurseyBook(result);
        return result;
    }
}
