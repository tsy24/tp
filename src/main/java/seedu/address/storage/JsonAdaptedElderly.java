package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Age;
import seedu.address.model.person.Elderly;
import seedu.address.model.person.Email;
import seedu.address.model.person.Gender;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nok;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Relationship;
import seedu.address.model.person.Remark;
import seedu.address.model.person.RoomNumber;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Elderly}.
 */
class JsonAdaptedElderly {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Elderly's %s field is missing!";

    private final String name;
    private final String age;
    private final String gender;
    private final String roomNumber;
    private final String nokName;
    private final String relationship;
    private final String phone;
    private final String address;
    private final String email;
    private final String remark;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedElderly} with the given elderly details.
     */
    @JsonCreator
    public JsonAdaptedElderly(@JsonProperty("name") String name,
                              @JsonProperty("age") String age, @JsonProperty("gender") String gender,
                              @JsonProperty("roomNumber") String roomNumber,
                              @JsonProperty("nokName") String nokName,
                              @JsonProperty("relationship") String relationship,
                              @JsonProperty("phone") String phone, @JsonProperty("email") String email,
                              @JsonProperty("address") String address,
                              @JsonProperty("remark") String remark,
                              @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.roomNumber = roomNumber;
        this.nokName = nokName;
        this.relationship = relationship;
        this.phone = phone;
        this.address = address;
        this.email = email;
        this.remark = remark;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code Elderly} into this class for Jackson use.
     */
    public JsonAdaptedElderly(Elderly source) {
        name = source.getName().fullName;
        age = source.getAge().value;
        gender = source.getGender().value;
        roomNumber = source.getRoomNumber().value;
        nokName = source.getNok().getName().fullName;
        relationship = source.getNok().getRelationship().value;
        phone = source.getNok().getPhone().value;
        email = source.getNok().getEmail().value;
        address = source.getNok().getAddress().value;
        remark = source.getRemark().value;
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted elderly object into the model's {@code Elderly} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted elderly.
     */
    public Elderly toModelType() throws IllegalValueException {
        final List<Tag> elderlyTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            elderlyTags.add(tag.toModelType());
        }

        if (name == null || nokName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name) || !Name.isValidName(nokName)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);
        final Name modelNokName = new Name(nokName);

        if (age == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Age.class.getSimpleName()));
        }
        if (!Age.isValidAge(age)) {
            throw new IllegalValueException(Age.MESSAGE_CONSTRAINTS);
        }
        final Age modelAge = new Age(age);

        if (gender == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Gender.class.getSimpleName()));
        }
        if (!Gender.isValidGender(gender)) {
            throw new IllegalValueException(Gender.MESSAGE_CONSTRAINTS);
        }
        final Gender modelGender = new Gender(gender);

        if (roomNumber == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    RoomNumber.class.getSimpleName()));
        }
        if (!RoomNumber.isValidRoomNumber(roomNumber)) {
            throw new IllegalValueException(RoomNumber.MESSAGE_CONSTRAINTS);
        }
        final RoomNumber modelRoomNumber = new RoomNumber(roomNumber);

        if (relationship == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Relationship.class.getSimpleName()));
        }
        if (!Relationship.isValidRelationship(relationship)) {
            throw new IllegalValueException(Relationship.MESSAGE_CONSTRAINTS);
        }
        final Relationship modelRelationship = new Relationship(relationship);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        if (remark == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Remark.class.getSimpleName()));
        }
        final Remark modelRemark = new Remark(remark);

        final Set<Tag> modelTags = new HashSet<>(elderlyTags);
        return new Elderly(modelName, modelAge, modelGender, modelRoomNumber,
                new Nok(modelNokName, modelRelationship, modelPhone, modelEmail, modelAddress), modelRemark, modelTags);
    }
}
