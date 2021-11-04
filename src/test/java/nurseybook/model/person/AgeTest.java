package nurseybook.model.person;

import static nurseybook.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        assertFalse(Age.isValidAge("20")); // below lower limit
        assertFalse(Age.isValidAge("146")); // above lower limit

        // valid phones
        assertTrue(Age.isValidAge("21")); // lower limit
        assertTrue(Age.isValidAge("145")); // upper limit
    }
}
