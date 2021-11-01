package nurseybook.model.task;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static nurseybook.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import nurseybook.testutil.Assert;

class DescriptionTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Description(null));
    }

    @Test
    public void constructor_invalidDescription_throwsIllegalArgumentException() {
        String invalidDescription = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Description(invalidDescription));
    }

    @Test
    public void isValidDescription() {
        // null Description
        Assert.assertThrows(NullPointerException.class, () -> Description.isValidDescription(null));

        // invalid Descriptiones
        assertFalse(Description.isValidDescription("")); // empty string
        assertFalse(Description.isValidDescription(" ")); // spaces only

        // valid Descriptiones
        assertTrue(Description.isValidDescription("enter medical records"));
        assertTrue(Description.isValidDescription("-")); // one character
        assertTrue(Description.isValidDescription("take a 2384 meter long pole and roast beef on it as I cry"));
        // long Description
    }
}
