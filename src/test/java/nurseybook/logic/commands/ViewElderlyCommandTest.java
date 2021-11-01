package nurseybook.logic.commands;

import static nurseybook.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import nurseybook.testutil.TypicalElderlies;
import nurseybook.testutil.TypicalIndexes;
import nurseybook.model.Model;
import nurseybook.model.ModelManager;
import nurseybook.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ViewElderlyCommand.
 */
public class ViewElderlyCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(TypicalElderlies.getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getVersionedNurseyBook(), new UserPrefs());
    }

    @Test
    public void execute_elderlyAreNotFiltered_showsSameList() {
        CommandResult expectedCommandResult = new CommandResult(ViewElderlyCommand.MESSAGE_SUCCESS,
                CommandResult.ListDisplayChange.ELDERLY);
        CommandTestUtil.assertCommandSuccess(new ViewElderlyCommand(), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_elderlyAreFiltered_showsEverything() {
        CommandTestUtil.showElderlyAtIndex(model, TypicalIndexes.INDEX_FIRST);
        CommandResult expectedCommandResult = new CommandResult(ViewElderlyCommand.MESSAGE_SUCCESS,
                CommandResult.ListDisplayChange.ELDERLY);
        CommandTestUtil.assertCommandSuccess(new ViewElderlyCommand(), model, expectedCommandResult, expectedModel);
    }
}
