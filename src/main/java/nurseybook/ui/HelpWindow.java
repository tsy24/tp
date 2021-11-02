package nurseybook.ui;

import java.util.Arrays;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.Stage;
import nurseybook.commons.core.LogsCenter;
import nurseybook.logic.commands.AddCommand;
import nurseybook.logic.commands.AddTagCommand;
import nurseybook.logic.commands.AddTaskCommand;
import nurseybook.logic.commands.ClearCommand;
import nurseybook.logic.commands.DeleteCommand;
import nurseybook.logic.commands.DeleteNokCommand;
import nurseybook.logic.commands.DeleteTagCommand;
import nurseybook.logic.commands.DeleteTaskCommand;
import nurseybook.logic.commands.DoneTaskCommand;
import nurseybook.logic.commands.EditCommand;
import nurseybook.logic.commands.EditTaskCommand;
import nurseybook.logic.commands.ExitCommand;
import nurseybook.logic.commands.FilterCommand;
import nurseybook.logic.commands.FindElderlyCommand;
import nurseybook.logic.commands.FindTaskCommand;
import nurseybook.logic.commands.HelpCommand;
import nurseybook.logic.commands.RedoCommand;
import nurseybook.logic.commands.RemarkCommand;
import nurseybook.logic.commands.RemindCommand;
import nurseybook.logic.commands.UndoCommand;
import nurseybook.logic.commands.ViewDetailsCommand;
import nurseybook.logic.commands.ViewElderlyCommand;
import nurseybook.logic.commands.ViewScheduleCommand;
import nurseybook.logic.commands.ViewTasksCommand;

/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart<Stage> {

    public static final String USERGUIDE_URL = "https://ay2122s1-cs2103t-f13-2.github.io/tp/UserGuide.html";
    public static final String HELP_MESSAGE = "Refer to the user guide for in-depth info: " + USERGUIDE_URL;

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";
    private static final String NO_PARAMS = "No parameters";
    private static final String PARAM_INFO = "Notations\n[ ] - Optional parameter\n... - Multiple parameters allowed";
    private static final String DELIMITER = "\n-----------\n";

    @FXML
    private Button copyButton;

    @FXML
    private Label helpMessage;

    @FXML
    private TreeView<String> commandTree;

    @FXML
    private Label params;

    @FXML
    private Label paramInfo;

    private TreeItem<String> elderlyNode;
    private TreeItem<String> taskNode;
    private TreeItem<String> miscNode;


    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public HelpWindow(Stage root) {
        super(FXML, root);
        helpMessage.setText(HELP_MESSAGE);

        fillCommandTree();
        commandTree.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        paramInfo.setText(PARAM_INFO);
    }

    /**
     * Creates a new HelpWindow.
     */
    public HelpWindow() {
        this(new Stage());
    }

    /**
     * Shows the help window.
     * @throws IllegalStateException
     * <ul>
     *     <li>
     *         if this method is called on a thread other than the JavaFX Application Thread.
     *     </li>
     *     <li>
     *         if this method is called during animation or layout processing.
     *     </li>
     *     <li>
     *         if this method is called on the primary stage.
     *     </li>
     *     <li>
     *         if {@code dialogStage} is already showing.
     *     </li>
     * </ul>
     */
    public void show() {
        logger.fine("Showing help page about the application.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the help window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the help window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the help window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

    /**
     * Fills treeview with commands.
     */
    private void fillCommandTree() {
        TreeItem<String> rootNode = new TreeItem<>("Commands");
        elderlyNode = new TreeItem<>("Elderly commands");
        taskNode = new TreeItem<>("Task commands");
        miscNode = new TreeItem<>("Other commands");

        fillElderlyCommands();
        fillTaskCommands();
        fillMiscCommands();

        rootNode.getChildren().addAll(Arrays.asList(elderlyNode, taskNode, miscNode));
        commandTree.setRoot(rootNode);
        commandTree.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        commandTree.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> handle(newValue.getValue()));
    }

    private void handle(String command) {
        switch (command) {
        case AddCommand.COMMAND_WORD:
            params.setText(String.join(DELIMITER, AddCommand.PARAMETERS));
            break;

        case AddTaskCommand.COMMAND_WORD:
            params.setText(String.join(DELIMITER, AddTaskCommand.PARAMETERS));
            break;

        case DeleteCommand.COMMAND_WORD:
            params.setText(String.join(DELIMITER, DeleteCommand.PARAMETERS));
            break;

        case DeleteNokCommand.COMMAND_WORD:
            params.setText(String.join(DELIMITER, DeleteNokCommand.PARAMETERS));
            break;

        case DeleteTaskCommand.COMMAND_WORD:
            params.setText(String.join(DELIMITER, DeleteTaskCommand.PARAMETERS));
            break;

        case DoneTaskCommand.COMMAND_WORD:
            params.setText(String.join(DELIMITER, DoneTaskCommand.PARAMETERS));
            break;

        case EditCommand.COMMAND_WORD:
            params.setText(String.join(DELIMITER, EditCommand.PARAMETERS));
            break;

        case EditTaskCommand.COMMAND_WORD:
            params.setText(String.join(DELIMITER, EditTaskCommand.PARAMETERS));
            break;

        case FindElderlyCommand.COMMAND_WORD:
            params.setText(String.join(DELIMITER, FindElderlyCommand.PARAMETERS));
            break;

        case FindTaskCommand.COMMAND_WORD:
            params.setText(String.join(DELIMITER, FindTaskCommand.PARAMETERS));
            break;

        case RemarkCommand.COMMAND_WORD:
            params.setText(String.join(DELIMITER, RemarkCommand.PARAMETERS));
            break;

        case ViewDetailsCommand.COMMAND_WORD:
            params.setText(String.join(DELIMITER, ViewDetailsCommand.PARAMETERS));
            break;

        case AddTagCommand.COMMAND_WORD:
            params.setText(String.join(DELIMITER, AddTagCommand.PARAMETERS));
            break;

        case DeleteTagCommand.COMMAND_WORD:
            params.setText(String.join(DELIMITER, DeleteTagCommand.PARAMETERS));
            break;

        case FilterCommand.COMMAND_WORD:
            params.setText(String.join(DELIMITER, FilterCommand.PARAMETERS));
            break;

        case ViewScheduleCommand.COMMAND_WORD:
            params.setText(String.join(DELIMITER, ViewScheduleCommand.PARAMETERS));
            break;

        case RemindCommand.COMMAND_WORD:

        case ViewTasksCommand.COMMAND_WORD:

        case ViewElderlyCommand.COMMAND_WORD:

        case ExitCommand.COMMAND_WORD:

        case ClearCommand.COMMAND_WORD:

        case HelpCommand.COMMAND_WORD:

        case UndoCommand.COMMAND_WORD:

        case RedoCommand.COMMAND_WORD:
            params.setText(NO_PARAMS);
            break;

        default:
            params.setText("");
            break;
        }
    }

    /**
     * Fills treeview with elderly commands.
     */
    private void fillElderlyCommands() {
        TreeItem<String> viewElderlyLeaf = new TreeItem<>(ViewElderlyCommand.COMMAND_WORD);
        TreeItem<String> addLeaf = new TreeItem<>(AddCommand.COMMAND_WORD);
        TreeItem<String> addTagLeaf = new TreeItem<>(AddTagCommand.COMMAND_WORD);
        TreeItem<String> deleteLeaf = new TreeItem<>(DeleteCommand.COMMAND_WORD);
        TreeItem<String> deleteNokLeaf = new TreeItem<>(DeleteNokCommand.COMMAND_WORD);
        TreeItem<String> deleteTagLeaf = new TreeItem<>(DeleteTagCommand.COMMAND_WORD);
        TreeItem<String> editLeaf = new TreeItem<>(EditCommand.COMMAND_WORD);
        TreeItem<String> filterLeaf = new TreeItem<>(FilterCommand.COMMAND_WORD);
        TreeItem<String> findLeaf = new TreeItem<>(FindElderlyCommand.COMMAND_WORD);
        TreeItem<String> remarkLeaf = new TreeItem<>(RemarkCommand.COMMAND_WORD);
        TreeItem<String> viewDetailsLeaf = new TreeItem<>(ViewDetailsCommand.COMMAND_WORD);


        elderlyNode.getChildren().addAll(Arrays.asList(viewElderlyLeaf, addLeaf, addTagLeaf, deleteLeaf, deleteNokLeaf,
                deleteTagLeaf, editLeaf, filterLeaf, findLeaf, remarkLeaf, viewDetailsLeaf));
    }

    /**
     * Fills treeview with task commands.
     */
    private void fillTaskCommands() {
        TreeItem<String> viewTaskLeaf = new TreeItem<>(ViewTasksCommand.COMMAND_WORD);
        TreeItem<String> addTaskLeaf = new TreeItem<>(AddTaskCommand.COMMAND_WORD);
        TreeItem<String> deleteTaskLeaf = new TreeItem<>(DeleteTaskCommand.COMMAND_WORD);
        TreeItem<String> doneTaskLeaf = new TreeItem<>(DoneTaskCommand.COMMAND_WORD);
        TreeItem<String> editTaskLeaf = new TreeItem<>(EditTaskCommand.COMMAND_WORD);
        TreeItem<String> findTaskLeaf = new TreeItem<>(FindTaskCommand.COMMAND_WORD);
        TreeItem<String> remindLeaf = new TreeItem<>(RemindCommand.COMMAND_WORD);
        TreeItem<String> viewScheduleLeaf = new TreeItem<>(ViewScheduleCommand.COMMAND_WORD);

        taskNode.getChildren().addAll(Arrays.asList(viewTaskLeaf, addTaskLeaf, deleteTaskLeaf, doneTaskLeaf,
                editTaskLeaf, findTaskLeaf, remindLeaf, viewScheduleLeaf));
    }

    /**
     * Fills treeview with miscellaneous commands.
     */
    private void fillMiscCommands() {
        TreeItem<String> clearLeaf = new TreeItem<>(ClearCommand.COMMAND_WORD);
        TreeItem<String> undoLeaf = new TreeItem<>(UndoCommand.COMMAND_WORD);
        TreeItem<String> redoLeaf = new TreeItem<>(RedoCommand.COMMAND_WORD);
        TreeItem<String> helpLeaf = new TreeItem<>(HelpCommand.COMMAND_WORD);
        TreeItem<String> exitLeaf = new TreeItem<>(ExitCommand.COMMAND_WORD);

        miscNode.getChildren().addAll(Arrays.asList(clearLeaf, undoLeaf, redoLeaf, helpLeaf, exitLeaf));
    }

    /**
     * Copies the URL to the user guide to the clipboard.
     */
    @FXML
    private void copyUrl() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent url = new ClipboardContent();
        url.putString(USERGUIDE_URL);
        clipboard.setContent(url);
    }

}
