package nurseybook.logic.commands;

import static nurseybook.logic.commands.CommandTestUtil.DESC_AMY;
import static nurseybook.logic.commands.CommandTestUtil.DESC_BOB;
import static nurseybook.logic.commands.CommandTestUtil.VALID_AGE_BOB;
import static nurseybook.logic.commands.CommandTestUtil.VALID_GENDER_BOB;
import static nurseybook.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static nurseybook.logic.commands.CommandTestUtil.VALID_NOK_ADDRESS_BOB;
import static nurseybook.logic.commands.CommandTestUtil.VALID_NOK_EMAIL_BOB;
import static nurseybook.logic.commands.CommandTestUtil.VALID_NOK_NAME_BOB;
import static nurseybook.logic.commands.CommandTestUtil.VALID_NOK_PHONE_BOB;
import static nurseybook.logic.commands.CommandTestUtil.VALID_NOK_RELATIONSHIP_BOB;
import static nurseybook.logic.commands.CommandTestUtil.VALID_ROOM_NUMBER_BOB;
import static nurseybook.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import nurseybook.testutil.EditElderlyDescriptorBuilder;

public class EditElderlyDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditCommand.EditElderlyDescriptor descriptorWithSameValues = new EditCommand.EditElderlyDescriptor(DESC_AMY);
        assertTrue(DESC_AMY.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_AMY.equals(DESC_AMY));

        // null -> returns false
        assertFalse(DESC_AMY.equals(null));

        // different types -> returns false
        assertFalse(DESC_AMY.equals(5));

        // different values -> returns false
        assertFalse(DESC_AMY.equals(DESC_BOB));

        // different name -> returns false
        EditCommand.EditElderlyDescriptor editedAmy = new EditElderlyDescriptorBuilder(DESC_AMY)
                .withName(VALID_NAME_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different age -> returns false
        editedAmy = new EditElderlyDescriptorBuilder(DESC_AMY).withAge(VALID_AGE_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different gender -> returns false
        editedAmy = new EditElderlyDescriptorBuilder(DESC_AMY).withGender(VALID_GENDER_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different roomNumber -> returns false
        editedAmy = new EditElderlyDescriptorBuilder(DESC_AMY).withRoomNumber(VALID_ROOM_NUMBER_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different nokName -> returns false
        editedAmy = new EditElderlyDescriptorBuilder(DESC_AMY).withAddress(VALID_NOK_NAME_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different phone -> returns false
        editedAmy = new EditElderlyDescriptorBuilder(DESC_AMY).withPhone(VALID_NOK_PHONE_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different email -> returns false
        editedAmy = new EditElderlyDescriptorBuilder(DESC_AMY).withEmail(VALID_NOK_EMAIL_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different address -> returns false
        editedAmy = new EditElderlyDescriptorBuilder(DESC_AMY).withAddress(VALID_NOK_ADDRESS_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different relationship -> returns false
        editedAmy = new EditElderlyDescriptorBuilder(DESC_AMY).withAddress(VALID_NOK_RELATIONSHIP_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different tags -> returns false
        editedAmy = new EditElderlyDescriptorBuilder(DESC_AMY).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(DESC_AMY.equals(editedAmy));
    }
}
