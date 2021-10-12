package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOK_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RELATIONSHIP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROOM_NUM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ELDERLIES;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
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

import javax.management.relation.InvalidRelationIdException;

/**
 * Edits the details of an existing elderly in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "editElderly";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the elderly identified "
            + "by the index number used in the displayed elderly list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_AGE + "AGE] "
            + "[" + PREFIX_GENDER + "GENDER] "
            + "[" + PREFIX_ROOM_NUM + "ROOM_NUMBER] "
            + "[" + PREFIX_NOK_NAME + "NOK NAME] "
            + "[" + PREFIX_RELATIONSHIP + "RELATIONSHIP] "
            + "[" + PREFIX_PHONE + "NOK PHONE] "
            + "[" + PREFIX_EMAIL + "NOK EMAIL] "
            + "[" + PREFIX_ADDRESS + "NOK ADDRESS] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_EDIT_ELDERLY_SUCCESS = "Edited Elderly: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_ELDERLY = "This elderly already exists in the address book.";

    private final Index index;
    private final EditElderlyDescriptor editElderlyDescriptor;

    /**
     * @param index of the elderly in the filtered elderly list to edit
     * @param editElderlyDescriptor details to edit the elderly with
     */
    public EditCommand(Index index, EditElderlyDescriptor editElderlyDescriptor) {
        requireNonNull(index);
        requireNonNull(editElderlyDescriptor);

        this.index = index;
        this.editElderlyDescriptor = new EditElderlyDescriptor(editElderlyDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Elderly> lastShownList = model.getFilteredElderlyList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ELDERLY_DISPLAYED_INDEX);
        }

        Elderly elderlyToEdit = lastShownList.get(index.getZeroBased());
        Elderly editedElderly = createEditedElderly(elderlyToEdit, editElderlyDescriptor);

        if (!elderlyToEdit.isSameElderly(editedElderly) && model.hasElderly(editedElderly)) {
            throw new CommandException(MESSAGE_DUPLICATE_ELDERLY);
        }

        model.setElderly(elderlyToEdit, editedElderly);
        model.updateFilteredElderlyList(PREDICATE_SHOW_ALL_ELDERLIES);
        return new CommandResult(String.format(MESSAGE_EDIT_ELDERLY_SUCCESS, editedElderly));
    }

    /**
     * Creates and returns a {@code Elderly} with the details of {@code elderlyToEdit}
     * edited with {@code editElderlyDescriptor}.
     */
    private static Elderly createEditedElderly(Elderly elderlyToEdit, EditElderlyDescriptor editElderlyDescriptor) {
        assert elderlyToEdit != null;

        Name updatedName = editElderlyDescriptor.getName().orElse(elderlyToEdit.getName());
        Age updatedAge = editElderlyDescriptor.getAge().orElse(elderlyToEdit.getAge());
        Gender updatedGender = editElderlyDescriptor.getGender().orElse(elderlyToEdit.getGender());
        RoomNumber updatedRoomNumber = editElderlyDescriptor.getRoomNumber().orElse(elderlyToEdit.getRoomNumber());
        Name updatedNokName = editElderlyDescriptor.getNokName().orElse(elderlyToEdit.getNok().getName());
        Relationship updatedRelationship = editElderlyDescriptor.getRelationship()
                .orElse(elderlyToEdit.getNok().getRelationship());
        Phone updatedNokPhone = editElderlyDescriptor.getNokPhone().orElse(elderlyToEdit.getNok().getPhone());
        Email updatedNokEmail = editElderlyDescriptor.getNokEmail().orElse(elderlyToEdit.getNok().getEmail());
        Address updatedNokAddress = editElderlyDescriptor.getNokAddress().orElse(elderlyToEdit.getNok().getAddress());
        Remark updatedRemark = elderlyToEdit.getRemark(); // edit command does not allow editing remarks
        Set<Tag> updatedTags = editElderlyDescriptor.getTags().orElse(elderlyToEdit.getTags());

        return new Elderly(updatedName, updatedAge, updatedGender, updatedRoomNumber,
                new Nok(updatedNokName, updatedRelationship, updatedNokPhone, updatedNokEmail, updatedNokAddress),
                updatedRemark, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditCommand e = (EditCommand) other;
        return index.equals(e.index)
                && editElderlyDescriptor.equals(e.editElderlyDescriptor);
    }

    /**
     * Stores the details to edit the elderly with. Each non-empty field value will replace the
     * corresponding field value of the elderly.
     */
    public static class EditElderlyDescriptor {
        private Name name;
        private Age age;
        private Gender gender;
        private RoomNumber roomNumber;
        private Name nokName;
        private Relationship relationship;
        private Phone nokPhone;
        private Email nokEmail;
        private Address nokAddress;
        private Set<Tag> tags;

        public EditElderlyDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditElderlyDescriptor(EditElderlyDescriptor toCopy) {
            setName(toCopy.name);
            setAge(toCopy.age);
            setGender(toCopy.gender);
            setRoomNumber(toCopy.roomNumber);
            setNokName(toCopy.nokName);
            setRelationship(toCopy.relationship);
            setNokPhone(toCopy.nokPhone);
            setNokEmail(toCopy.nokEmail);
            setNokAddress(toCopy.nokAddress);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, age, gender, roomNumber, nokName, relationship, nokPhone,
                    nokEmail, nokAddress, tags);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setAge(Age age) {
            this.age = age;
        }

        public Optional<Age> getAge() {
            return Optional.ofNullable(age);
        }

        public void setGender(Gender gender) {
            this.gender = gender;
        }

        public Optional<Gender> getGender() {
            return Optional.ofNullable(gender);
        }

        public void setRoomNumber(RoomNumber roomNumber) {
            this.roomNumber = roomNumber;
        }

        public Optional<RoomNumber> getRoomNumber() {
            return Optional.ofNullable(roomNumber);
        }

        public void setNokName(Name nokName) {
            this.nokName = nokName;
        }

        public Optional<Name> getNokName() {
            return Optional.ofNullable(nokName);
        }

        public void setRelationship(Relationship relationship) {
            this.relationship = relationship;
        }

        public Optional<Relationship> getRelationship() {
            return Optional.ofNullable(relationship);
        }

        public void setNokPhone(Phone nokPhone) {
            this.nokPhone = nokPhone;
        }

        public Optional<Phone> getNokPhone() {
            return Optional.ofNullable(nokPhone);
        }

        public void setNokEmail(Email nokEmail) {
            this.nokEmail = nokEmail;
        }

        public Optional<Email> getNokEmail() {
            return Optional.ofNullable(nokEmail);
        }

        public void setNokAddress(Address nokAddress) {
            this.nokAddress = nokAddress;
        }

        public Optional<Address> getNokAddress() {
            return Optional.ofNullable(nokAddress);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditElderlyDescriptor)) {
                return false;
            }

            // state check
            EditElderlyDescriptor e = (EditElderlyDescriptor) other;

            return getName().equals(e.getName())
                    && getAge().equals(e.getAge())
                    && getGender().equals(e.getGender())
                    && getRoomNumber().equals(e.getRoomNumber())
                    && getNokName().equals(e.getNokName())
                    && getRelationship().equals(e.getRelationship())
                    && getNokPhone().equals(e.getNokPhone())
                    && getNokEmail().equals(e.getNokEmail())
                    && getNokAddress().equals(e.getNokAddress())
                    && getTags().equals(e.getTags());
        }
    }
}
