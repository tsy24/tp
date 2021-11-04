---
layout: page
title: User Guide
---

* Table of Contents
  {:toc}

--------------------------------------------------------------------------------------------------------------------
## 1. Introduction

NurseyBook is a **desktop app made for nurses in nursing homes to aid them in managing contacts and tasks in their busy lives. It is optimized for use via a Command Line Interface** (CLI) while still **having the benefits of a Graphical User Interface** (GUI). If you can type fast, NurseyBook can manage your contacts & tasks done faster than traditional GUI apps! :smile:

--------------------------------------------------------------------------------------------------------------------

## 2. Quick start

1. Ensure you have Java `11` or above installed in your Computer.

2. Download the latest `nurseybook.jar` from [here](https://github.com/AY2122S1-CS2103T-F13-2/tp/releases).

3. Copy the file to the folder you want to use as the _home folder_ for your NurseyBook.

4. Double-click the file to start the app. The GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

5. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * **`viewElderly`** : Lists all elderly contacts.

   * **`viewTasks`** : Lists all tasks.

   * **`addElderly`**`addElderly en/John a/77 g/M r/420 t/diabetes` : Adds an elderly named `John` to NurseyBook.

   * **`deleteElderly`**`3` : Deletes the records of the 3rd elderly shown in the current list.

   * **`clear`** : Deletes all contacts.

   * **`exit`** : Exits the app.

Refer to the [4. Features](#4. Features) below for details of each command.


--------------------------------------------------------------------------------------------------------------------
## 3. About

### 3.1 Structure of this document

This document is structured in a manner that lets you find what you need fast and easily. To jump to various sections, you can refer to the Table of Contents.

In the following subsection, [3.2 Reading this document](#3.2 Reading this document), you can find several tips that could be beneficial when reading this guide. 
The next section, documents the main features that **NurseyBook** offers and provides you with instructions on how to 
use each one of them!


### 3.2 Reading this document

This subsection will introduce to you the symbols, syntax and technical terms that are used throughout this guide. 
Being familiar with this subsection will definitely help you out when looking through this guide.


#### 3.2.1 Special symbols

**Additional Information**

Text that appear in an information box indicates additional information that may be useful to know.

<div markdown="block" class="alert alert-info">

:information_source: **Information:**
Example additional information.

</div>

**Caution**

Text that appear in a caution box should be followed carefully, else unintended consequences might arise. 

<div markdown="block" class="alert alert-warning">

:exclamation: **Caution:**
Example warnings.

</div>

**Tips**

Text that appear in a tip box are useful for improving your experience with NurseyBook.

<div markdown="block" class="alert alert-primary">

:bulb: **Tips:**
Example tips.

</div>


#### 3.2.2 Sections of the Application Window

You can refer to the image below for the names of the different sections in NurseyBook's application window. 

![nurseybook_application_window](images/userGuide/application_window.png)


#### 3.2.3 Navigating in NurseyBook

In NurseyBook, some buttons are clickable - such as the File and Help buttons at the top of the application window.
However, the User Interface (UI) is designed primarily to be navigated using the Command Line Interface (CLI).

You can enter commands into the command box and press `Enter` to execute them. The result box will then provide a response on whether the command was successfully executed.
The display panel will similarly update itself, based on the command executed. 


#### 3.2.4 Command Format

Words that are highlighted in gray (also known as a mark-up) indicates that they are commands that you can type into the command box, and executed by the application.
e.g. `viewTasks`

Commands in this guide follow such rules:

* Words in `UPPER_CASE` are the parameters to be supplied by you.<br>
  e.g. in `addElderly en/ELDERLY_NAME`, `NAME` is a parameter which can be used as `addElderly en/Swee Choon`.

* Items in square brackets are optional.<br>
  e.g. `en/ELDERLY_NAME [t/TAG]` can be used as `en/Swee Choon t/vegan` or as `en/Swee Choon`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `en/ELDERLY_NAME p/NOK_PHONE_NUMBER`, `p/NOK_PHONE_NUMBER en/ELDERLY_NAME` is also acceptable.

<div markdown="block" class="alert alert-info">

**:information_source: Information:**

* If a parameter is expected only once in the command but you specified it multiple times, only the last occurrence of the parameter will be taken.<br>
  e.g. if you specify `p/12341234 p/56785678`, only `p/56785678` will be taken.

* Commands that do not take in parameters (such as `viewElderly`, `viewTasks`, `clear`, `exit`, `help`) will ignore the additional parameters that are added to the back of it.
  e.g. if the command specifies `viewTasks 123`, it will be interpreted as `viewTasks`.

</div>

<div markdown="block" class="alert alert-primary">

**:bulb: Tips:**

* The _User Interface_ refers to the NurseyBook application window. 
* _Command Line Interface_ refers to where you interact with the system by typing in commands - in this case, the command box. 
* _Parameters_ refer to the information to be included as an input to a command.

</div>


#### 3.2.5 Command Parameters

The table below provides a summary on the command parameters that are mentioned in this guide.

Parameter | Description
:---------|:-----------
`AGE` | Age of an elderly. A valid age is between 21 to 145. 
`DATE` | Date that a task is scheduled to occur on. It should be in the format of yyyy-mm-dd. 
`DESCRIPTION` | Description of a task. A description should not be blank.
`ELDERLY_NAME` | Name of an elderly. No two elderly should have the same name.
`GENDER` | Gender of an elderly. Gender is either `M` for males or `F` for females.
`INDEX` | Index is the number shown beside an elderly/task when elderlies/tasks are displayed in the display panel respectively.
`KEYWORD` | Keyword used to search for elderlies (by name) or tasks (by description). `MORE_KEYWORDS` are similarly defined. A keyword should not be blank. 
`NOK_ADDRESS` | Address of an elderly's next-of-kin.
`NOK_EMAIL` | Email of an elderly's next-of-kin. An email should be in the format of local-part@domain.
`NOK_NAME` | Name of an elderly's next-of-kin.
`NOK_PHONE_NUMBER` | Phone number of an elderly's next-of-kin. A phone number should either be blank, or at least 8 digits long. 
`NOK_RELATIONSHIP` | Relationship between an elderly and his/her next-of-kin. 
`RECURRENCE_TYPE` | Indicates the recurrence period of a task. Can be either `NONE`, by `DAY`, `WEEK`, or `MONTH`.
`REMARK` | Additional information that can be supplied to an elderly. It should only be used with elderlies, not tasks.
`ROOMNO` | Room number that an elderly is staying in (the Nursing Home). It should be a non-negative integer. 
`TAG` | Tag associated with an elderly. It should only be used with elderlies, not tasks. 
`TIME` | Time that a task is scheduled to occur from. It should be in the format of hh:mm.

--------------------------------------------------------------------------------------------------------------------

## 4. Features

This section contains the documentation on NurseyBook's features and commands. It is split into the following subsections:

1. Elderly commands
2. Task commands
3. Miscellaneous commands
4. Storage

### 4.1 Elderly commands

#### View all elderly: `viewElderly`

Shows the list of all added elderly.

Format: `viewElderly`

#### Add an elderly: `addElderly`

Adds an elderly to NurseyBook.

Format: `addElderly en/ELDERLY_NAME a/AGE g/GENDER r/ROOMNO [t/TAG]…​ [nn/NOK_NAME] [rs/NOK_RELATIONSHIP] [p/NOK_PHONE_NUMBER] [e/NOK_EMAIL] [addr/NOK_ADDRESS]`

<div markdown="block" class="alert alert-info">

:information_source: **Information:**
* An elderly can have any number of tags (including 0).
* You can specify a Next-of-Kin's (NOK) details for each elderly as well, when adding an elderly.

</div>

Examples:
* `addElderly en/Khong Guan a/80 g/M r/201 nn/Gong Kuan rs/Brother p/91234567 e/guanbro@gmail.com addr/London Street 11`
* `addElderly en/John a/77 g/M r/420 t/diabetes`
* `addElderly en/John a/77 g/M r/420 t/diabetes nn/Timothy rs/Son`

![](images/userGuide/add_elderly_0.png)
![](images/userGuide/add_elderly_1.png)


#### Delete an elderly : `deleteElderly`

Deletes an elderly from NurseyBook.

Format: `deleteElderly INDEX`   

<div markdown="block" class="alert alert-info">

:information_source: **Information:**
* Deletes the elderly at the specified `INDEX`.
* The index refers to the index number shown in the displayed elderly list.
* The index **must be a positive integer** 1, 2, 3, …​

</div>

Examples:
* `viewElderly` followed by `deleteElderly 2` deletes the 2nd elderly in NurseyBook.


#### Edit an elderly's details: `editElderly`

Edits the details of a specific elderly.

Format: `editElderly INDEX [en/ELDERLY_NAME] [a/AGE] [g/GENDER] [r/ROOMNO] [t/TAG]…​ [nn/NOK_NAME] [rs/NOK_RELATIONSHIP] [p/NOK_PHONE_NUMBER] [e/NOK_EMAIL] [addr/NOK_ADDRESS]`

<div markdown="block" class="alert alert-info">

:information_source: **Information:**
* Any number of tags is acceptable (including 0).

</div>

<div markdown="block" class="alert alert-primary">

:bulb: **Tip:**  
You can remove a remark by leaving the remark input empty!  
e.g. `editElderly 1 re/`

</div>

<div markdown="block" class="alert alert-warning">

:exclamation: **Caution**
* By leaving the tag input empty, you will remove all the tags currently assigned to the elderly.
* If you want to add a tag with `editElderly`, you have to include all the existing tags of the elderly in your command.

e.g. John has an existing tag `cancer`, and he is at index 1 in the current list of elderly displayed. To add a tag to John, your command should be `editElderly 1 t/overweight t/covid`.

* For more accessible tag related commands, do refer to the `addTag` and `deleteTag` commands.
</div>


#### Find elderly: `findElderly`

Finds elderlies whose names contain any of the given keywords.

Format: `findElderly KEYWORD [MORE_KEYWORDS]`

<div markdown="block" class="alert alert-info">

:information_source: **Information:**
* The search is case-insensitive. e.g. `elena` will match `Elena`
* The order of the keywords does not matter. e.g. `Elena Kro` will match `Kro Elena`
* Only the name is searched.
* Only full words will be matched. e.g. `Ele` will not match `Elena`
* Persons matching at least one keyword will be returned (i.e. `OR` search). e.g. `Elena Kro` will return `Elena Grob`, `Kro Stanly`

</div>

Examples:
* `findElderly Elena` returns `elena` and `Elena Kro`
* `findElderly Elena Jav` returns `Elena Kro`, `Jav Marsh`


#### View full details of an elderly: `viewDetails`

Displays full details of a specific elderly

Format: `viewDetails INDEX`

<div markdown="block" class="alert alert-info">

:information_source: **Information:**
* Shows the full details of the elderly at the specified `INDEX`.
* The index refers to the index number shown in the displayed elderly list.
* The index **must be a positive integer** 1, 2, 3, …​
* After executing another command that is not a `viewDetails` command, the details panel will close.

</div>

![](images/userGuide/view_details.png)


#### Delete an elderly's NoK details : `deleteNok`

Deletes an elderly's Next-of-Kin details from NurseyBook.

Format: `deleteNok INDEX`

<div markdown="block" class="alert alert-info">

:information_source: **Information:**
* Deletes the NoK details of the elderly at the specified `INDEX`.
* The index refers to the index number shown in the displayed elderly list.
* The index **must be a positive integer** 1, 2, 3, …​

</div>

Examples:
* `viewElderly` followed by `deleteNok 2` deletes the NoK details of the 2nd elderly in NurseyBook.


#### Add tags to elderly: `addTag`

Adds one or more tags to a specific elderly.

Format: `addTag INDEX t/TAG [t/TAG]…​`

<div markdown="block" class="alert alert-info">

:information_source: **Information:**
* There should be at least one tag.
* Tags should be alphanumeric.
* The index refers to the index number shown in the displayed elderly list.
* The index **must be a positive integer** 1, 2, 3, …​

</div>

Example:
* `addTag 1 t/covid`


#### Delete tags of elderly: `deleteTag`

Deletes one or more tags of a specific elderly.

Format: `deleteTag INDEX t/TAG [t/TAG]…​`

<div markdown="block" class="alert alert-info">

:information_source: **Information:**
* There should be at least one tag.
* Tags should be alphanumeric.
* The index refers to the index number shown in the displayed elderly list.
* The index **must be a positive integer** 1, 2, 3, …​

</div>

Example:
* `deleteTag 1 t/covid`


#### Filter elderly: `filter`

Filters elderly based on one or more tags.

Format: `filter t/TAG [t/TAG]…​`

<div markdown="block" class="alert alert-info">

:information_source: **Information:**
* There should be at least one tag.
* Tags should be alphanumeric.

</div>

Example:
* `filter t/covid t/diabetes`

![](images/userGuide/filter.png)


#### Add remark to elderly: `remark`

Adds a remark to a specific elderly.

Format: `remark INDEX re/REMARK`

<div markdown="block" class="alert alert-info">

:information_source: **Information:**
* The index refers to the index number shown in the displayed elderly list.
* The index **must be a positive integer** 1, 2, 3, …​

</div>

Example:
* `remark 1 re/Medicine seems to be ineffective`

<div markdown="block" class="alert alert-primary">

:bulb: **Tip:**  
You can remove a remark by leaving the remark input empty!  
e.g. `remark 1 re/`

</div>


### 4.2 Task commands

#### View all tasks: `viewTasks`

Shows a list of all your tasks in NurseyBook.

Format: `viewTasks`


#### Add a task: `addTask`

Adds a task to the task list.

<div markdown="block" class="alert alert-primary">

:bulb: **Tip:**
You can add a recurring task to the list! <br>
There are a few recurring options available namely: `NONE`, `DAY`, `WEEK` and `MONTH` (4 weeks later from the previous date). Tasks that have passed their original date will have their date automatically updated to the new date based on the recurrence type of the task.

If you want to create a non-recurring task, you can exclude the `recur` field. NurseyBook will automatically assume that the task is non-recurring.

</div>

<div markdown="block" class="alert alert-info">

:information_source: **Information:**

* Executing the command will automatically change the display view to your task list, so that you can see the task you added.

</div>

Format: `addTask [en/ELDERLY_NAME]... desc/DESCRIPTION date/DATE time/TIME [recur/RECURRENCE_TYPE]`  

Example:
`addTask en/John desc/check insulin level date/2022-01-25 time/19:22 recur/week`

![](images/userGuide/add_task_0.png)
![](images/userGuide/add_task_1.png)

#### Delete a task: `deleteTask`

Deletes a particular task in the task list from NurseyBook.

Format: `deleteTask INDEX`

<div markdown="block" class="alert alert-info">

:information_source: **Information:**

* Deletes the task at the specified `INDEX`.
* The index refers to the index number shown in the displayed task list.
* The index **must be a positive integer** 1, 2, 3, …​

</div>

Examples:
* `viewTasks` followed by `deleteTask 2` deletes the 2nd task shown by NurseyBook.


#### Edit a task: `editTask`

Edits the details of a specific task.

Format: `editTask INDEX [en/ELDERLY_NAME]... [desc/DESCRIPTION] [date/DATE] [time/TIME] [recur/RECURRENCE_TYPE]`

<div markdown="block" class="alert alert-info">

:information_source: **Information**

* If the date/recurrence type of a recurring task is edited, the updated date generated will be based on the most recently edited date.

e.g. Date of task is `2022-01-01` with `daily` recurrence. On `2022-01-03`, the task is edited to be `weekly` recurring. When the next occurrence of the task is generated by NurseyBook, the date of the task will be `2022-01-10`.

</div>

Example:
* `viewTasks` followed by `editTask 1 date/2022-01-30` changes the date of the 1st task shown by NurseyBook to 30 January 2022.


#### Find a task: `findTask`

Finds tasks whose description contain any of the given keywords.

Format: `findTask KEYWORD [MORE_KEYWORDS]`

<div markdown="block" class="alert alert-info">

information_source: **Information:**

* The search is case-insensitive. e.g. `shift` will match `Shift`
* The order of the keywords does not matter. e.g. `Day shift` will match `shift Day`
* Only the description is searched.
* Only full words will be matched. e.g. `Sh` will not match `Shift`
* Task matching at least one keyword will be returned (i.e. `OR` search). e.g. `Day shift` will return `Day routine`, `Shift items`

</div>

Examples:
* `findTask Day` returns `day` and `Day routine`
* `findTask Day shift` returns `Day routine`, `Shift items`

![](images/userGuide/find_task.png)


#### Mark a task as completed: `doneTask`

Marks a particular task in the task list as completed.

Format: `doneTask INDEX`

<div markdown="block" class="alert alert-info">

:information_source: **Information:**

* Marks the task at the specified `INDEX` as done.
* The index refers to the index number shown in the displayed task list.
* The index **must be a positive integer** 1, 2, 3, …​

</div>

Examples:
* `viewTasks` followed by `doneTask 2` marks the 2nd task shown by NurseyBook as completed.


#### View reminders: `remind`

Shows the list of upcoming tasks (that are coming up in the next three days), such as the required medical needs for
those under your care.

Format: `remind`

![](images/userGuide/remind.png)


#### View task schedule: `viewSchedule`

Displays the list of tasks set to occur on the specified date.
Future occurrences of recurring tasks that will take place on that date is also included.

Format: `viewSchedule DATE`

<div markdown="block" class="alert alert-info">

:information_source: **Information:**
* Date must be in yyyy-mm-dd form.
* You can view schedule for up to 12 weeks in advance from the current date.
* For recurring tasks, this is simply a tool to preview its future occurrences. You cannot modify (e.g. delete/edit/mark as done/etc.) any such future occurrences of recurring tasks shown in the task list. These future occurrences will be deleted from task view upon entering any next valid/invalid input.

</div>

Example:
`viewSchedule 2022-02-14`

![](images/userGuide/viewSchedule.png)


<div markdown="span" class="alert alert-warning">

:exclamation: **Caution**
* NurseyBook will not automatically refresh the displayed task list to reflect
instantaneous changes, such as overdue tasks and new dates of recurring tasks.
* However, you can manually trigger this refresh, and one way is to enter
 `viewTasks` in the command box.
* This will update the overdue status of all tasks and new dates of all
recurring tasks.
  * E.g. If the time now is 9.01pm and there is an undone task which is due at
    9.00pm the same day, you can enter `viewTasks`,
    otherwise the red overdue tag will not show automatically.
  * E.g. If the time now is 9.01pm and there is a recurring task due at 9.00pm,
    you can enter `viewTasks`, otherwise the task's date will remain
    unchanged.

</div>


### 4.3 Miscellaneous commands

#### Clearing all entries : `clear`

Clears all entries from NurseyBook.

Format: `clear`


#### Undo previous command : `undo`

Undoes the previous undoable command executed on the NurseyBook.

Format: `undo`

* Undoable commands(i.e. any command that modifies NurseyBook's data): `addElderly`, `editElderly`, `deleteElderly`, `deleteNok`, `addTag`, `deleteTag`, `addTask`, `editTask`, `deleteTask`, `doneTask`, `clear`
* Non-undoable commands: `findElderly`, `filter`, `viewDetails`, `viewElderly`, `findTask`, `remind`, `viewTasks`, `viewSchedule`
* If there are no undoable commands executed previously, the undo command will fail and an error message will be shown.

Example:
* `deleteElderly 1` followed by `undo` causes the `deleteElderly 1` command to be undone and no elderly is deleted from the NurseyBook.


#### Redo previously undone command : `redo`

Reverses the previous undo command executed on the NurseyBook.

Format: `redo`

* If there are no undo commands executed previously, the redo command will fail and an error message will be shown.

Example:
* `deleteElderly 1` followed by `undo` causes the `deleteElderly 1` command to be undone and no elderly is deleted from the NurseyBook.
Entering `redo` will reverse the previous undo command, causing the elderly to be deleted again.


#### Exiting the program : `exit`

Exits the program.

Format: `exit`


### 4.4 Storage

#### Saving the data

NurseyBook's data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.


#### Editing the data file

NurseyBook data are saved as a JSON file `[JAR file location]/data/nurseybook.json`. If you are technologically savvy, you
are also welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">

:exclamation: **Caution:**
If your changes to the data file makes its format invalid, NurseyBook will discard all data and start with an empty data file at the next run.

</div>

--------------------------------------------------------------------------------------------------------------------

## 5. FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file NurseyBook creates with the file, with the file
that contains the data of your previous NurseyBook home folder.

**Q**: How do I save my data?<br>
**A**: NurseyBook's data is saved in the hard disk automatically after any command that changes the data.
There is no need to save manually.

--------------------------------------------------------------------------------------------------------------------

## 6. Command summary

### 6.1 Elderly

Action | Format, Examples
--------|------------------
**Add an elderly** | `addElderly en/ELDERLY_NAME a/AGE r/ROOMNO g/GENDER [t/TAG]…​ [nn/NOK_NAME] [rs/NOK_RELATIONSHIP] [p/NOK_PHONE_NUMBER] [e/NOK_EMAIL] [addr/NOK_ADDRESS]` <br> <br> e.g., `addElderly en/Khong Guan a/80 g/M r/201 nn/Gong Kuan rs/Brother p/91234567 e/guanbro@gmail.com addr/London Street 11`
**Delete an elderly** | `deleteElderly INDEX` <br> <br> e.g., `deleteElderly 3`
**Edit an elderly** | `editElderly INDEX [en/ELDERLY_NAME] [a/AGE] [g/GENDER] [r/ROOMNO] [t/TAG]…​ [nn/NOK_NAME] [rs/NOK_RELATIONSHIP] [p/NOK_PHONE_NUMBER] [e/NOK_EMAIL] [addr/NOK_ADDRESS] [re/REMARK]`
**Find an elderly** | `findElderly KEYWORD [MORE_KEYWORDS]`
**Delete next-of-kin of elderly** | `deleteNok INDEX`<br> <br> e.g., `deleteNok 3`
**Add tag(s)** | `addTagINDEX t/TAG [t/TAG]…​` <br> <br> e.g., `addTag 1 t/diabetes`
**Delete tag(s)** | `deleteTag INDEX t/TAG [t/TAG]…​`
**Filter** | `filter t/TAG [t/TAG]…​`
**Remark** | `remark INDEX re/REMARK`
**View elderly details** | `viewDetails INDEX`<br> <br> e.g., `viewDetails 2`
**View all elderly** | `viewElderly`


### 6.2 Task

Action | Format, Examples
--------|------------------
**Add a task** | `addTask [en/ELDERLY_NAME] desc/DESCRIPTION date/DATE time/TIME [recur/RECURRENCE_TYPE]` <br> <br> e.g., `addTask en/John desc/check insulin level date/2022-01-25 time/10:00 recur/week`
**Delete a task** | `deleteTask INDEX`<br> e.g., `deleteTask 3`
**Edit a task** | `editTask INDEX [en/ELDERLY_NAME] [desc/DESCRIPTION] [date/DATE] [time/TIME] [recur/RECURRENCE_TYPE]` <br> <br> e.g., `editTask 2 desc/Meeting with head nurse`
**Find a task** | `findTask KEYWORD [MORE_KEYWORDS]`
**Mark a task as complete** | `doneTask INDEX`<br> e.g., `doneTask 3`
**Remind** | `remind`
**View Schedule** | `viewSchedule DATE` <br> e.g., `viewSchedule 2022-02-14`
**View all tasks** | `viewTasks`


### 6.3 Miscellaneous

Action | Format, Examples
--------|------------------
**Clear** | `clear`
**Undo** | `undo`
**Redo** | `redo`
**Exit** | `exit`


## 7. Glossary

Term | Definition
--------|------------------
**Command Line Interface (CLI)** | Command line interface where users interact with the system by typing in commands. <br> <br> e.g., Terminal
**Graphical User Interface (GUI)** | Graphical user interface where users interact with the system through visual representations. <br> <br> e.g., Microsoft Windows Desktop
**JAR** | A file format that contains all bundled Java files (relevant to NurseyBook).
**Java 11** | The Java Platform, Standard Edition 11 Development Kit (JDK 11) is a feature release of the Java SE platform.
**Javascript Object Notation (JSON)** | JSON is a lightweight text format for storing and transporting data.            
