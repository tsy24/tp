package nurseybook.logic.commands;

import static nurseybook.logic.commands.TaskCommandTestUtil.PAPERWORK_TASK;
import static nurseybook.logic.commands.TaskCommandTestUtil.VACCINE_TASK;
import static nurseybook.logic.commands.TaskCommandTestUtil.VALID_DATE_NOV;
import static nurseybook.logic.commands.TaskCommandTestUtil.VALID_DESC_MEDICINE;
import static nurseybook.logic.commands.TaskCommandTestUtil.VALID_TIME_TENAM;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import nurseybook.model.task.Recurrence;
import nurseybook.testutil.EditTaskDescriptorBuilder;

public class EditTaskDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditTaskCommand.EditTaskDescriptor descriptorWithSameValues =
                new EditTaskCommand.EditTaskDescriptor(VACCINE_TASK);
        assertTrue(VACCINE_TASK.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(VACCINE_TASK.equals(VACCINE_TASK));

        // null -> returns false
        assertFalse(VACCINE_TASK.equals(null));

        // different types -> returns false
        assertFalse(VACCINE_TASK.equals(5));

        // different values -> returns false
        assertFalse(VACCINE_TASK.equals(PAPERWORK_TASK));

        // different description -> returns false
        EditTaskCommand.EditTaskDescriptor editedVaccine = new EditTaskDescriptorBuilder(VACCINE_TASK)
                .withDescription(VALID_DESC_MEDICINE).build();
        assertFalse(VACCINE_TASK.equals(editedVaccine));

        // different date -> returns false
        editedVaccine = new EditTaskDescriptorBuilder(VACCINE_TASK).withDate(VALID_DATE_NOV).build();
        assertFalse(VACCINE_TASK.equals(editedVaccine));

        // different time -> returns false
        editedVaccine = new EditTaskDescriptorBuilder(VACCINE_TASK).withTime(VALID_TIME_TENAM).build();
        assertFalse(VACCINE_TASK.equals(editedVaccine));

        // different recurrence -> returns false
        editedVaccine = new EditTaskDescriptorBuilder(VACCINE_TASK)
                .withRecurrence(Recurrence.RecurrenceType.MONTH.name()).build();
        assertFalse(VACCINE_TASK.equals(editedVaccine));
    }
}
