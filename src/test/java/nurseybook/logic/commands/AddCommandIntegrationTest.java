package nurseybook.logic.commands;

<<<<<<< HEAD:src/test/java/nurseybook/logic/commands/AddCommandIntegrationTest.java
import static nurseybook.logic.commands.CommandTestUtil.assertCommandFailure;
import static nurseybook.logic.commands.CommandTestUtil.assertCommandSuccess;
=======
import static seedu.address.commons.core.Messages.MESSAGE_DUPLICATE_ELDERLY;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalElderlies.getTypicalAddressBook;
>>>>>>> 05b67180673b53490a68ffa0e70b2353fc8aa2af:src/test/java/seedu/address/logic/commands/AddCommandIntegrationTest.java

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import nurseybook.model.Model;
import nurseybook.model.ModelManager;
import nurseybook.model.UserPrefs;
import nurseybook.model.person.Elderly;
import nurseybook.testutil.ElderlyBuilder;
import nurseybook.testutil.TypicalElderlies;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(TypicalElderlies.getTypicalNurseyBook(), new UserPrefs());
    }

    @Test
    public void execute_newElderly_success() {
        Elderly validElderly = new ElderlyBuilder().build();

        Model expectedModel = new ModelManager(model.getVersionedNurseyBook(), new UserPrefs());
        expectedModel.addElderly(validElderly);
        CommandResult expectedCommandResult = new CommandResult(String.format(AddCommand.MESSAGE_SUCCESS, validElderly),
                CommandResult.ListDisplayChange.ELDERLY);
        expectedModel.commitNurseyBook(expectedCommandResult);

        assertCommandSuccess(new AddCommand(validElderly), model,
                expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_duplicateElderly_throwsCommandException() {
        Elderly elderlyInList = model.getVersionedNurseyBook().getElderlyList().get(0);
        assertCommandFailure(new AddCommand(elderlyInList), model, MESSAGE_DUPLICATE_ELDERLY);
    }

}
