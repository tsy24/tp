package nurseybook.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static nurseybook.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import nurseybook.testutil.Assert;

public class RoomNumberTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new RoomNumber(null));
    }

    @Test
    public void constructor_invalidRoomNumber_throwsIllegalArgumentException() {
        String invalidRoomNumber = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new RoomNumber(invalidRoomNumber));
    }

    @Test
    public void isValidRoomNumber() {
        // null room number
        Assert.assertThrows(NullPointerException.class, () -> RoomNumber.isValidRoomNumber(null));

        // invalid room numbers
        assertFalse(RoomNumber.isValidRoomNumber("")); // empty string
        assertFalse(RoomNumber.isValidRoomNumber(" ")); // spaces only
        assertFalse(RoomNumber.isValidRoomNumber("roomNumber")); // non-numeric
        assertFalse(RoomNumber.isValidRoomNumber("9011p041")); // alphabets within digits
        assertFalse(RoomNumber.isValidRoomNumber("9312 1534")); // spaces within digits

        // valid room numbers
        assertTrue(RoomNumber.isValidRoomNumber("9")); // exactly 1 number
        assertTrue(RoomNumber.isValidRoomNumber("572"));
        assertTrue(RoomNumber.isValidRoomNumber("124293842033123")); // long room numbers
    }
}
