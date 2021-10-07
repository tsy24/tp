package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class AgeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Age(null));
    }

    @Test
    public void constructor_invalidAge_throwsIllegalArgumentException() {
        String invalidAge = "";
        assertThrows(IllegalArgumentException.class, () -> new Age(invalidAge));
    }

    @Test
    public void isValidAge() {
        // null age
        assertThrows(NullPointerException.class, () -> Age.isValidAge(null));

        // invalid phones
        assertFalse(Age.isValidAge("")); // empty string
        assertFalse(Age.isValidAge(" ")); // spaces only
        assertFalse(Age.isValidAge("-5")); // negative numbers
        assertFalse(Age.isValidAge("phone")); // non-numeric
        assertFalse(Age.isValidAge("9011p041")); // alphabets within digits
        assertFalse(Age.isValidAge("9312 1534")); // spaces within digits

        // valid phones
        assertTrue(Age.isValidAge("911")); // exactly 3 numbers
        assertTrue(Age.isValidAge("93121534")); // although ridiculous but legit ages
    }
}
