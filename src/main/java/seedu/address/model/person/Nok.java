package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents a Next-of-Kin in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Nok extends Person {
    //Identity fields
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;

    /**
     * Every field must be present and not null.
     */
    public Nok(Name name, Phone phone, Address address, Email email) {
        super(name);
        requireAllNonNull(phone, email, address);
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    public Name getName() {
        return super.getName();
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
                && otherNok.getPhone().equals(getPhone())
                && otherNok.getEmail().equals(getEmail())
                && otherNok.getAddress().equals(getAddress());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(this.getName(), phone, email, address);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("; nokName: ")
                .append(getName())
                .append("; nokPhone: ")
                .append(getPhone())
                .append("; nokEmail: ")
                .append(getEmail())
                .append("; nokAddress: ")
                .append(getAddress());

        return builder.toString();
    }

}
