package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.*;

public class TaskCommandTestUtil extends CommandTestUtil {
    public static final String VALID_DESC_PAPERWORK = "Do paperwork";
    public static final String VALID_DESC_MEDICINE = "Bring insulin shots";
    public static final String VALID_DATE_NOV = "2020-11-01";
    public static final String VALID_DATE_JAN = "2022-01-31";
    public static final String VALID_TIME_TENAM = "10:20";
    public static final String VALID_TIME_SEVENPM = "19:45";
    public static final String VALID_NAME_KEITH = "Keith Goh";
    public static final String VALID_NAME_ALEX = "Alex Andre";

    public static final String DESC_PAPERWORK = " " + PREFIX_TASK_DESC + VALID_DESC_PAPERWORK;
    public static final String DESC_MEDICINE = " " + PREFIX_TASK_DESC + VALID_DESC_MEDICINE;
    public static final String DATE_DESC_NOV = " " + PREFIX_TASK_DATE + VALID_DATE_NOV;
    public static final String DATE_DESC_JAN = " " + PREFIX_TASK_DATE + VALID_DATE_JAN;
    public static final String TIME_DESC_TENAM = " " + PREFIX_TASK_TIME + VALID_TIME_TENAM;
    public static final String TIME_DESC_SEVENPM = " " + PREFIX_TASK_TIME + VALID_TIME_SEVENPM;
    public static final String NAME_DESC_KEITH = " " + PREFIX_NAME + VALID_NAME_KEITH;
    public static final String NAME_DESC_ALEX = " " + PREFIX_NAME + VALID_NAME_ALEX;

    public static final String INVALID_DESC = " " + PREFIX_TASK_DESC;
    public static final String INVALID_DATE = " " + PREFIX_TASK_DATE + "07-10-2009"; //should be yyyy-mm-dd
    public static final String INVALID_TIME = " " + PREFIX_TASK_TIME + "1900"; //should be 19:00

}
