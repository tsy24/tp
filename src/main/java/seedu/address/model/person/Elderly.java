package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents an Elderly in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Elderly extends Person {

    // Identity fields
    private final Phone phone;
    private final Age age;
    private final Gender gender;
    private final Email email;
    private final RoomNumber roomNumber;

    // Data fields
    private final Address address;
    private final Remark remark;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Elderly(Name name, Phone phone, Age age, Gender gender, RoomNumber roomNumber, Email email, Address address,
                   Remark remark, Set<Tag> tags) {
        super(name);
        requireAllNonNull(phone, age, gender, email, address, tags);
        this.phone = phone;
        this.age = age;
        this.gender = gender;
        this.roomNumber = roomNumber;
        this.email = email;
        this.address = address;
        this.remark = remark;
        this.tags.addAll(tags);
    }

    public Name getName() {
        return super.getName();
    }

    public Phone getPhone() {
        return phone;
    }

    public Age getAge() {
        return age;
    }

    public Gender getGender() {
        return gender;
    }

    public RoomNumber getRoomNumber() {
        return roomNumber;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public Remark getRemark() {
        return remark;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both elderlies have the same name.
     * This defines a weaker notion of equality between two elderlies.
     */
    public boolean isSameElderly(Elderly otherElderly) {
        if (otherElderly == this) {
            return true;
        }

        return otherElderly != null
                && otherElderly.getName().equals(getName());
    }

    /**
     * Returns true if elderly has this name.
     */
    public boolean hasName(Name name) {
        return this.getName().equals(name);
    }

    /**
     * Returns true if both elderlies have the same identity and data fields.
     * This defines a stronger notion of equality between two elderlies.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Elderly)) {
            return false;
        }

        Elderly otherElderly = (Elderly) other;
        return otherElderly.getName().equals(getName())
                && otherElderly.getPhone().equals(getPhone())
                && otherElderly.getAge().equals(getAge())
                && otherElderly.getGender().equals(getGender())
                && otherElderly.getRoomNumber().equals(getRoomNumber())
                && otherElderly.getEmail().equals(getEmail())
                && otherElderly.getAddress().equals(getAddress())
                && otherElderly.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(this.getName(), phone, age, gender, roomNumber, email, address, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append("; Phone: ")
                .append(getPhone())
                .append("; Age: ")
                .append(getAge())
                .append("; Gender: ")
                .append(getAge())
                .append("; RoomNumber: ")
                .append(getRoomNumber())
                .append("; Email: ")
                .append(getEmail())
                .append("; Address: ")
                .append(getAddress())
                .append("; Remark: ")
                .append(getRemark());

        Set<Tag> tags = getTags();
        if (!tags.isEmpty()) {
            builder.append("; Tags: ");
            tags.forEach(builder::append);
        }
        return builder.toString();
    }
}
