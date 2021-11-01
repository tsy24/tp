package nurseybook.testutil;

import java.util.Set;

import nurseybook.logic.commands.AddCommand;
import nurseybook.logic.commands.EditCommand;
import nurseybook.logic.parser.CliSyntax;
import nurseybook.model.person.Elderly;
import nurseybook.model.tag.Tag;

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
        sb.append(CliSyntax.PREFIX_NAME + elderly.getName().fullName + " ");
        sb.append(CliSyntax.PREFIX_AGE + elderly.getAge().value + " ");
        sb.append(CliSyntax.PREFIX_GENDER + elderly.getGender().value + " ");
        sb.append(CliSyntax.PREFIX_NOK_NAME + elderly.getNok().getName().fullName + " ");
        sb.append(CliSyntax.PREFIX_RELATIONSHIP + elderly.getNok().getRelationship().value + " ");
        sb.append(CliSyntax.PREFIX_ROOM_NUM + elderly.getRoomNumber().value + " ");
        sb.append(CliSyntax.PREFIX_PHONE + elderly.getNok().getPhone().value + " ");
        sb.append(CliSyntax.PREFIX_EMAIL + elderly.getNok().getEmail().value + " ");
        sb.append(CliSyntax.PREFIX_ADDRESS + elderly.getNok().getAddress().value + " ");
        elderly.getTags().stream().forEach(
            s -> sb.append(CliSyntax.PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditElderlyDescriptor}'s details.
     */
    public static String getEditElderlyDescriptorDetails(EditCommand.EditElderlyDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(CliSyntax.PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getAge().ifPresent(age -> sb.append(CliSyntax.PREFIX_AGE).append(age.value).append(" "));
        descriptor.getGender().ifPresent(gender -> sb.append(CliSyntax.PREFIX_GENDER).append(gender.value).append(" "));
        descriptor.getRoomNumber().ifPresent(roomNumber ->
                sb.append(CliSyntax.PREFIX_ROOM_NUM).append(roomNumber.value).append(" "));
        descriptor.getNokName().ifPresent(nokName -> sb.append(CliSyntax.PREFIX_NOK_NAME).append(nokName.fullName).append(" "));
        descriptor.getRelationship().ifPresent(relationship ->
                sb.append(CliSyntax.PREFIX_RELATIONSHIP).append(relationship.value).append(" "));
        descriptor.getNokPhone().ifPresent(phone -> sb.append(CliSyntax.PREFIX_PHONE).append(phone.value).append(" "));
        descriptor.getNokEmail().ifPresent(email -> sb.append(CliSyntax.PREFIX_EMAIL).append(email.value).append(" "));
        descriptor.getNokAddress().ifPresent(address -> sb.append(CliSyntax.PREFIX_ADDRESS).append(address.value).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(CliSyntax.PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(CliSyntax.PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }
}
