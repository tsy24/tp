package nurseybook.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static nurseybook.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import nurseybook.testutil.Assert;

public class RelationshipTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Relationship(null));
    }

    @Test
    public void isValidRelationship() {
        // null relationship
        Assert.assertThrows(NullPointerException.class, () -> Relationship.isValidRelationship(null));

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
