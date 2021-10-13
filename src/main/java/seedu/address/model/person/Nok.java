package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents a Next-of-Kin in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Nok extends Person {
    //Default values
    private static final String defaultNameField = "NIL";
    private static final String defaultNonNameField = "";

    //Identity fields
    private final Relationship relationship;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;

    /**
     * Every field must be present and not null.
     */
    public Nok(Name name, Relationship relationship, Phone phone, Email email, Address address) {
        super(name);
        requireAllNonNull(relationship, phone, email, address);
        this.relationship = relationship;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    public Name getName() {
        return super.getName();
    }

    public Relationship getRelationship() {
        return relationship;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    /**
     * Creates and returns a Nok with all fields set to default values. Equivalent to not specifying any fields in Nok.
     *
     * @return Nok with all fields set to default values.
     */
    public static Nok createDefaultNok() {
        Nok blankNok = new Nok(new Name(defaultNameField), new Relationship(defaultNonNameField),
                new Phone(defaultNonNameField), new Email(defaultNonNameField), new Address(defaultNonNameField));
        return blankNok;
    }

    /**
     * Returns true if both NoKs have the same name.
     * This defines a weaker notion of equality between two NoKs.
     */
    public boolean isSameNok(Nok otherNok) {
        if (otherNok == this) {
            return true;
        }

        return otherNok != null
                && otherNok.getName().equals(getName());
    }

    /**
     * Returns true if both NoKs have the same identity and data fields.
     * This defines a stronger notion of equality between two NoK.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Nok)) {
            return false;
        }

        Nok otherNok = (Nok) other;
        return otherNok.getName().equals(getName())
                && otherNok.getRelationship().equals(getRelationship())
                && otherNok.getPhone().equals(getPhone())
                && otherNok.getEmail().equals(getEmail())
                && otherNok.getAddress().equals(getAddress());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(this.getName(), relationship, phone, email, address);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("; nokName: ")
                .append(getName())
                .append("; Relationship: ")
                .append(getRelationship())
                .append("; nokPhone: ")
                .append(getPhone())
                .append("; nokEmail: ")
                .append(getEmail())
                .append("; nokAddress: ")
                .append(getAddress());

        return builder.toString();
    }

}
