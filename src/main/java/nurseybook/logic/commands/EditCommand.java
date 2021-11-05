package nurseybook.logic.commands;

import static java.util.Objects.requireNonNull;
import static nurseybook.commons.core.Messages.MESSAGE_NO_CHANGES;
import static nurseybook.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static nurseybook.logic.parser.CliSyntax.PREFIX_AGE;
import static nurseybook.logic.parser.CliSyntax.PREFIX_EMAIL;
import static nurseybook.logic.parser.CliSyntax.PREFIX_GENDER;
import static nurseybook.logic.parser.CliSyntax.PREFIX_NAME;
import static nurseybook.logic.parser.CliSyntax.PREFIX_NOK_NAME;
import static nurseybook.logic.parser.CliSyntax.PREFIX_PHONE;
import static nurseybook.logic.parser.CliSyntax.PREFIX_RELATIONSHIP;
import static nurseybook.logic.parser.CliSyntax.PREFIX_ROOM_NUM;
import static nurseybook.logic.parser.CliSyntax.PREFIX_TAG;
import static nurseybook.model.Model.PREDICATE_SHOW_ALL_ELDERLIES;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import nurseybook.commons.core.Messages;
import nurseybook.commons.core.index.Index;
import nurseybook.commons.util.CollectionUtil;
import nurseybook.logic.commands.exceptions.CommandException;
import nurseybook.model.Model;
import nurseybook.model.person.Address;
import nurseybook.model.person.Age;
import nurseybook.model.person.Elderly;
import nurseybook.model.person.Email;
import nurseybook.model.person.Gender;
import nurseybook.model.person.Name;
import nurseybook.model.person.Nok;
import nurseybook.model.person.Phone;
import nurseybook.model.person.Relationship;
import nurseybook.model.person.Remark;
import nurseybook.model.person.RoomNumber;
import nurseybook.model.tag.Tag;

/**
 * Edits the details of an existing elderly in the nursey book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "editElderly";
    public static final String[] PARAMETERS = { Index.VALID_INDEX_CRITERIA,
        PREFIX_NAME + "NAME", PREFIX_AGE + "AGE", PREFIX_GENDER + "GENDER",
        PREFIX_ROOM_NUM + "ROOM_NUMBER", "[" + PREFIX_NOK_NAME + "NOK_NAME]",
        "[" + PREFIX_RELATIONSHIP + "NOK_RELATIONSHIP]", "[" + PREFIX_PHONE + "NOK_PHONE_NUMBER]",
        "[" + PREFIX_EMAIL + "NOK_EMAIL]", "[" + PREFIX_ADDRESS + "NOK_ADDRESS]", "[" + PREFIX_TAG + "TAG]..." };

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the elderly identified "
            + "by the index number used in the displayed elderly list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: "
            + String.join(" ", PARAMETERS)
            + "\nExample: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_EDIT_ELDERLY_SUCCESS = "Edited Elderly: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_ELDERLY = "This elderly already exists in the nursey book.";

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

        if (elderlyToEdit.equals(editedElderly)) {
            throw new CommandException(MESSAGE_NO_CHANGES);
        }

        if (!elderlyToEdit.isSameElderly(editedElderly) && model.hasElderly(editedElderly)) {
            throw new CommandException(MESSAGE_DUPLICATE_ELDERLY);
        }

        model.setElderly(elderlyToEdit, editedElderly);
        CommandResult result = new CommandResult(String.format(MESSAGE_EDIT_ELDERLY_SUCCESS, editedElderly));

        // Update elderly name in tasks
        model.updateElderlyNameInTasks(elderlyToEdit, editedElderly);

        model.commitNurseyBook(result);
        return result;
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
