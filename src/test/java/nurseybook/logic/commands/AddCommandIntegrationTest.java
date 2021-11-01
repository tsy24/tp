package nurseybook.logic.commands;

import static nurseybook.logic.commands.CommandTestUtil.assertCommandFailure;
import static nurseybook.logic.commands.CommandTestUtil.assertCommandSuccess;

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
        assertCommandFailure(new AddCommand(elderlyInList), model, AddCommand.MESSAGE_DUPLICATE_ELDERLY);
    }

}
