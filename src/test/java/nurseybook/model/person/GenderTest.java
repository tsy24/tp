package nurseybook.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static nurseybook.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import nurseybook.testutil.Assert;

public class GenderTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Gender(null));
    }

    @Test
    public void constructor_invalidGender_throwsIllegalArgumentException() {
        String invalidGender = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Gender(invalidGender));
    }

    @Test
    public void isValidGender() {
        // null gender
        Assert.assertThrows(NullPointerException.class, () -> Gender.isValidGender(null));

        // invalid phone numbers
        assertFalse(Gender.isValidGender("")); // empty string
        assertFalse(Gender.isValidGender(" ")); // spaces only
        assertFalse(Gender.isValidGender("mf")); // more than 1 character
        assertFalse(Gender.isValidGender("e")); // invalid character
        assertFalse(Gender.isValidGender("9")); // numbers
        assertFalse(Gender.isValidGender("9312 1534")); // more than 1 number

        // valid phone numbers
        assertTrue(Gender.isValidGender("M")); // Male
        assertTrue(Gender.isValidGender("m")); // lower case
    }
}

