package nurseybook.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import nurseybook.testutil.EditElderlyDescriptorBuilder;

public class EditElderlyDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditCommand.EditElderlyDescriptor descriptorWithSameValues = new EditCommand.EditElderlyDescriptor(CommandTestUtil.DESC_AMY);
        Assertions.assertTrue(CommandTestUtil.DESC_AMY.equals(descriptorWithSameValues));

        // same object -> returns true
        Assertions.assertTrue(CommandTestUtil.DESC_AMY.equals(CommandTestUtil.DESC_AMY));

        // null -> returns false
        Assertions.assertFalse(CommandTestUtil.DESC_AMY.equals(null));

        // different types -> returns false
        Assertions.assertFalse(CommandTestUtil.DESC_AMY.equals(5));

        // different values -> returns false
        Assertions.assertFalse(CommandTestUtil.DESC_AMY.equals(CommandTestUtil.DESC_BOB));

        // different name -> returns false
        EditCommand.EditElderlyDescriptor editedAmy = new EditElderlyDescriptorBuilder(CommandTestUtil.DESC_AMY)
                .withName(CommandTestUtil.VALID_NAME_BOB).build();
        Assertions.assertFalse(CommandTestUtil.DESC_AMY.equals(editedAmy));

        // different age -> returns false
        editedAmy = new EditElderlyDescriptorBuilder(CommandTestUtil.DESC_AMY).withAge(CommandTestUtil.VALID_AGE_BOB).build();
        Assertions.assertFalse(CommandTestUtil.DESC_AMY.equals(editedAmy));

        // different gender -> returns false
        editedAmy = new EditElderlyDescriptorBuilder(CommandTestUtil.DESC_AMY).withGender(CommandTestUtil.VALID_GENDER_BOB).build();
        Assertions.assertFalse(CommandTestUtil.DESC_AMY.equals(editedAmy));

        // different roomNumber -> returns false
        editedAmy = new EditElderlyDescriptorBuilder(CommandTestUtil.DESC_AMY).withRoomNumber(CommandTestUtil.VALID_ROOM_NUMBER_BOB).build();
        Assertions.assertFalse(CommandTestUtil.DESC_AMY.equals(editedAmy));

        // different nokName -> returns false
        editedAmy = new EditElderlyDescriptorBuilder(CommandTestUtil.DESC_AMY).withAddress(CommandTestUtil.VALID_NOK_NAME_BOB).build();
        Assertions.assertFalse(CommandTestUtil.DESC_AMY.equals(editedAmy));

        // different phone -> returns false
        editedAmy = new EditElderlyDescriptorBuilder(CommandTestUtil.DESC_AMY).withPhone(CommandTestUtil.VALID_NOK_PHONE_BOB).build();
        Assertions.assertFalse(CommandTestUtil.DESC_AMY.equals(editedAmy));

        // different email -> returns false
        editedAmy = new EditElderlyDescriptorBuilder(CommandTestUtil.DESC_AMY).withEmail(CommandTestUtil.VALID_NOK_EMAIL_BOB).build();
        Assertions.assertFalse(CommandTestUtil.DESC_AMY.equals(editedAmy));

        // different address -> returns false
        editedAmy = new EditElderlyDescriptorBuilder(CommandTestUtil.DESC_AMY).withAddress(CommandTestUtil.VALID_NOK_ADDRESS_BOB).build();
        Assertions.assertFalse(CommandTestUtil.DESC_AMY.equals(editedAmy));

        // different relationship -> returns false
        editedAmy = new EditElderlyDescriptorBuilder(CommandTestUtil.DESC_AMY).withAddress(CommandTestUtil.VALID_NOK_RELATIONSHIP_BOB).build();
        Assertions.assertFalse(CommandTestUtil.DESC_AMY.equals(editedAmy));

        // different tags -> returns false
        editedAmy = new EditElderlyDescriptorBuilder(CommandTestUtil.DESC_AMY).withTags(CommandTestUtil.VALID_TAG_HUSBAND).build();
        Assertions.assertFalse(CommandTestUtil.DESC_AMY.equals(editedAmy));
    }
}
