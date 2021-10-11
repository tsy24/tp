package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedElderly.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalElderlies.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Age;
import seedu.address.model.person.Email;
import seedu.address.model.person.Gender;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.RoomNumber;


public class JsonAdaptedElderlyTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_AGE = "3d6";
    private static final String INVALID_GENDER = "9";
    private static final String INVALID_ROOM_NUMBER = "52q";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_AGE = BENSON.getAge().toString();
    private static final String VALID_GENDER = BENSON.getGender().toString();
    private static final String VALID_ROOM_NUMBER = BENSON.getRoomNumber().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final String VALID_REMARK = BENSON.getRemark().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validElderlyDetails_returnsElderly() throws Exception {
        JsonAdaptedElderly elderly = new JsonAdaptedElderly(BENSON);
        assertEquals(BENSON, elderly.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedElderly elderly =
                new JsonAdaptedElderly(INVALID_NAME, VALID_PHONE, VALID_AGE, VALID_GENDER, VALID_ROOM_NUMBER,
                        VALID_EMAIL, VALID_ADDRESS, VALID_REMARK, VALID_TAGS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, elderly::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedElderly elderly =
                new JsonAdaptedElderly(null, VALID_PHONE, VALID_AGE, VALID_EMAIL, VALID_GENDER, VALID_ROOM_NUMBER,
                        VALID_ADDRESS, VALID_REMARK, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, elderly::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedElderly elderly =
                new JsonAdaptedElderly(VALID_NAME, INVALID_PHONE, VALID_AGE, VALID_GENDER, VALID_ROOM_NUMBER,
                        VALID_EMAIL, VALID_ADDRESS, VALID_REMARK, VALID_TAGS);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, elderly::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedElderly elderly =
                new JsonAdaptedElderly(VALID_NAME, null, VALID_AGE, VALID_GENDER, VALID_ROOM_NUMBER, VALID_EMAIL,
                        VALID_ADDRESS, VALID_REMARK, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, elderly::toModelType);
    }

    @Test
    public void toModelType_invalidAge_throwsIllegalValueException() {
        JsonAdaptedElderly elderly =
                new JsonAdaptedElderly(VALID_NAME, VALID_PHONE, INVALID_AGE, VALID_GENDER, VALID_ROOM_NUMBER,
                        VALID_EMAIL, VALID_ADDRESS, VALID_REMARK, VALID_TAGS);
        String expectedMessage = Age.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, elderly::toModelType);
    }

    @Test
    public void toModelType_nullAge_throwsIllegalValueException() {
        JsonAdaptedElderly elderly =
                new JsonAdaptedElderly(VALID_NAME, VALID_PHONE, null, VALID_GENDER, VALID_ROOM_NUMBER, VALID_EMAIL,
                        VALID_ADDRESS, VALID_REMARK, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Age.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, elderly::toModelType);
    }

    @Test
    public void toModelType_invalidGender_throwsIllegalValueException() {
        JsonAdaptedElderly elderly =
                new JsonAdaptedElderly(VALID_NAME, VALID_PHONE, VALID_AGE, INVALID_GENDER, VALID_ROOM_NUMBER,
                        VALID_EMAIL, VALID_ADDRESS, VALID_REMARK, VALID_TAGS);
        String expectedMessage = Gender.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, elderly::toModelType);
    }

    @Test
    public void toModelType_nullGender_throwsIllegalValueException() {
        JsonAdaptedElderly elderly =
                new JsonAdaptedElderly(VALID_NAME, VALID_PHONE, VALID_AGE, null, VALID_ROOM_NUMBER, VALID_EMAIL,
                        VALID_ADDRESS, VALID_REMARK, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Gender.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, elderly::toModelType);
    }

    @Test
    public void toModelType_invalidRoomNumber_throwsIllegalValueException() {
        JsonAdaptedElderly elderly =
                new JsonAdaptedElderly(VALID_NAME, VALID_PHONE, VALID_AGE, VALID_GENDER, INVALID_ROOM_NUMBER,
                        VALID_EMAIL, VALID_ADDRESS, VALID_REMARK, VALID_TAGS);
        String expectedMessage = RoomNumber.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, elderly::toModelType);
    }

    @Test
    public void toModelType_nullRoomNumber_throwsIllegalValueException() {
        JsonAdaptedElderly elderly =
                new JsonAdaptedElderly(VALID_NAME, VALID_PHONE, VALID_AGE, VALID_GENDER, null, VALID_EMAIL,
                        VALID_ADDRESS, VALID_REMARK, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, RoomNumber.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, elderly::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedElderly elderly =
                new JsonAdaptedElderly(VALID_NAME, VALID_PHONE, VALID_AGE, VALID_GENDER, VALID_ROOM_NUMBER,
                        INVALID_EMAIL, VALID_ADDRESS, VALID_REMARK, VALID_TAGS);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, elderly::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedElderly elderly =
                new JsonAdaptedElderly(VALID_NAME, VALID_PHONE, VALID_AGE, VALID_GENDER, VALID_ROOM_NUMBER, null,
                        VALID_ADDRESS, VALID_REMARK, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, elderly::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedElderly elderly =
                new JsonAdaptedElderly(VALID_NAME, VALID_PHONE, VALID_AGE, VALID_GENDER, VALID_ROOM_NUMBER, VALID_EMAIL,
                        INVALID_ADDRESS, VALID_REMARK, VALID_TAGS);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, elderly::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedElderly elderly =
                new JsonAdaptedElderly(VALID_NAME, VALID_PHONE, VALID_AGE, VALID_GENDER, VALID_ROOM_NUMBER,
                        VALID_EMAIL, null, VALID_REMARK, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, elderly::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedElderly elderly =
                new JsonAdaptedElderly(VALID_NAME, VALID_PHONE, VALID_AGE, VALID_GENDER, VALID_ROOM_NUMBER, VALID_EMAIL,
                        VALID_ADDRESS, VALID_REMARK, invalidTags);
        assertThrows(IllegalValueException.class, elderly::toModelType);
    }

}
