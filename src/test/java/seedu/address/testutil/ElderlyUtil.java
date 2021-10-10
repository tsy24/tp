package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROOM_NUM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand.EditElderlyDescriptor;
import seedu.address.model.person.Elderly;
import seedu.address.model.tag.Tag;

/**
 * A utility class for Elderly.
 */
public class ElderlyUtil {

    /**
     * Returns an add command string for adding the {@code elderly}.
     */
    public static String getAddCommand(Elderly elderly) {
        return AddCommand.COMMAND_WORD + " " + getElderlyDetails(elderly);
    }

    /**
     * Returns the part of command string for the given {@code elderly}'s details.
     */
    public static String getElderlyDetails(Elderly elderly) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + elderly.getName().fullName + " ");
        sb.append(PREFIX_PHONE + elderly.getPhone().value + " ");
        sb.append(PREFIX_AGE + elderly.getAge().value + " ");
        sb.append(PREFIX_GENDER + elderly.getGender().value + " ");
        sb.append(PREFIX_ROOM_NUM + elderly.getRoomNumber().value + " ");
        sb.append(PREFIX_EMAIL + elderly.getEmail().value + " ");
        sb.append(PREFIX_ADDRESS + elderly.getAddress().value + " ");
        elderly.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditElderlyDescriptor}'s details.
     */
    public static String getEditElderlyDescriptorDetails(EditElderlyDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
        descriptor.getAge().ifPresent(age -> sb.append(PREFIX_AGE).append(age.value).append(" "));
        descriptor.getGender().ifPresent(gender -> sb.append(PREFIX_GENDER).append(gender.value).append(" "));
        descriptor.getRoomNumber().ifPresent(roomNumber ->
                sb.append(PREFIX_ROOM_NUM).append(roomNumber.value).append(" "));
        descriptor.getEmail().ifPresent(email -> sb.append(PREFIX_EMAIL).append(email.value).append(" "));
        descriptor.getAddress().ifPresent(address -> sb.append(PREFIX_ADDRESS).append(address.value).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }
}
