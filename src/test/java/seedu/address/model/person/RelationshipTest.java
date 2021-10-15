package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class RelationshipTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Relationship(null));
    }

    @Test
    public void isValidRelationship() {
        // null relationship
        assertThrows(NullPointerException.class, () -> Relationship.isValidRelationship(null));

        // invalid relationships
        assertFalse(Relationship.isValidRelationship(" ")); // spaces only
        assertFalse(Relationship.isValidRelationship("6587")); // relationship with numbers
        assertFalse(Relationship.isValidRelationship("mother1")); // relationship with numbers and letters


        // valid relationships
        assertTrue(Relationship.isValidRelationship("Mother")); // alphabets
        assertTrue(Relationship.isValidRelationship("ma")); //short relationship
        assertTrue(Relationship.isValidRelationship("step-father")); // symbols
        assertTrue(Relationship.isValidRelationship("motherfatherbrother")); // long relationship
    }
}
