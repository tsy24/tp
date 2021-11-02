package nurseybook.logic.commands;

import static nurseybook.logic.commands.CommandResult.ListDisplayChange.ELDERLY;
import static nurseybook.logic.commands.ViewElderlyCommand.MESSAGE_SUCCESS;
import static nurseybook.testutil.TypicalElderlies.getTypicalNurseyBook;
import static nurseybook.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        model = new ModelManager(getTypicalNurseyBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getVersionedNurseyBook(), new UserPrefs());
    }

    @Test
    public void execute_elderlyAreNotFiltered_showsSameList() {
        CommandResult expectedCommandResult = new CommandResult(MESSAGE_SUCCESS, ELDERLY);
        CommandTestUtil.assertCommandSuccess(new ViewElderlyCommand(), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_elderlyAreFiltered_showsEverything() {
        CommandTestUtil.showElderlyAtIndex(model, INDEX_FIRST);
        CommandResult expectedCommandResult = new CommandResult(MESSAGE_SUCCESS, ELDERLY);
        CommandTestUtil.assertCommandSuccess(new ViewElderlyCommand(), model, expectedCommandResult, expectedModel);
    }
}
