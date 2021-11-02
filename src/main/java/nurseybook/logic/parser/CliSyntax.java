package nurseybook.logic.parser;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_PREAMBLE = new Prefix("");
    public static final Prefix PREFIX_NAME = new Prefix("en/");
    public static final Prefix PREFIX_AGE = new Prefix("a/");
    public static final Prefix PREFIX_GENDER = new Prefix("g/");
    public static final Prefix PREFIX_ROOM_NUM = new Prefix("r/");
    public static final Prefix PREFIX_NOK_NAME = new Prefix("nn/");
    public static final Prefix PREFIX_RELATIONSHIP = new Prefix("rs/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("addr/");
    public static final Prefix PREFIX_REMARK = new Prefix("re/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_TASK_DESC = new Prefix("desc/");
    public static final Prefix PREFIX_TASK_DATE = new Prefix("date/");
    public static final Prefix PREFIX_TASK_TIME = new Prefix("time/");
    public static final Prefix PREFIX_TASK_RECURRING = new Prefix("recur/");

    public static final Prefix[] PREFIX_ALL = {PREFIX_NAME, PREFIX_AGE, PREFIX_GENDER, PREFIX_ROOM_NUM,
            PREFIX_NOK_NAME, PREFIX_RELATIONSHIP, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_REMARK, PREFIX_TAG,
            PREFIX_TASK_DESC, PREFIX_TASK_DATE, PREFIX_TASK_TIME, PREFIX_TASK_RECURRING};

}
