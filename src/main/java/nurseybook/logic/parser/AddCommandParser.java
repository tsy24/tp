package nurseybook.logic.parser;

import static nurseybook.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
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

import java.util.Set;
import java.util.stream.Stream;

import nurseybook.logic.commands.AddCommand;
import nurseybook.logic.parser.exceptions.ParseException;
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
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_AGE, PREFIX_GENDER, PREFIX_ROOM_NUM,
                        PREFIX_NOK_NAME, PREFIX_RELATIONSHIP, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_AGE, PREFIX_GENDER, PREFIX_ROOM_NUM)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Age age = ParserUtil.parseAge(argMultimap.getValue(PREFIX_AGE).get());
        Gender gender = ParserUtil.parseGender(argMultimap.getValue(PREFIX_GENDER).get());
        Name nokName = ParserUtil.parseNokName(argMultimap.getValue(PREFIX_NOK_NAME).orElse("NIL"));
        Relationship relationship = ParserUtil.parseRelationship(argMultimap.getValue(PREFIX_RELATIONSHIP)
                .orElse(""));
        Phone nokPhone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).orElse(""));
        Email nokEmail = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).orElse(""));
        Address nokAddress = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).orElse(""));
        Remark remark = new Remark(""); // add command does not allow adding remarks straight away
        RoomNumber roomNumber = ParserUtil.parseRoomNumber(argMultimap.getValue(PREFIX_ROOM_NUM).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Elderly elderly = new Elderly(name, age, gender, roomNumber,
                new Nok(nokName, relationship, nokPhone, nokEmail, nokAddress), remark, tagList);

        return new AddCommand(elderly);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
