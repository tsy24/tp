package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalElderlies.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Elderly;
import seedu.address.testutil.ElderlyBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newElderly_success() {
        Elderly validElderly = new ElderlyBuilder().build();

        Model expectedModel = new ModelManager(model.getVersionedNurseyBook(), new UserPrefs());
        expectedModel.addElderly(validElderly);
        CommandResult expectedCommandResult = new CommandResult(String.format(AddCommand.MESSAGE_SUCCESS, validElderly),
                CommandResult.ListDisplayChange.PERSON);
        expectedModel.commitNurseyBook(expectedCommandResult);

        assertCommandSuccess(new AddCommand(validElderly), model,
                expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_duplicateElderly_throwsCommandException() {
        Elderly elderlyInList = model.getVersionedNurseyBook().getElderlyList().get(0);
        assertCommandFailure(new AddCommand(elderlyInList), model, AddCommand.MESSAGE_DUPLICATE_ELDERLY);
    }

}
