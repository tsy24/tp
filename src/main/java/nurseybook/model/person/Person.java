package nurseybook.model.person;

import static nurseybook.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents a Person in the nursey book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public abstract class Person {
    // Identity fields
    private final Name name;

    /**
     * If Person is an Elderly, every field must be present and not null.
     */
    public Person(Name name) {
        requireAllNonNull(name);
        this.name = name;
    }

    public Name getName() {
        return this.name;
    };

}
