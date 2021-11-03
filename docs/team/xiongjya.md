---
layout: page
title: Xiong Jingya's Project Portfolio Page
---

### Project: NurseyBook

#### Overview

NurseyBook is a desktop application made for nurses in nursing homes to aid them in managing contacts and tasks in their busy lives. The user interacts with it using Command Line Interface (CLI), 
and it still has the benefits of a Graphical User Interface (GUI) created with JavaFX. It is written in Java, and has about 17 kLoC. 

#### Summary of Contributions

Given below are my contributions to the project.

* **Code contributed**: Around 4.5 kLoC contribution to the project.

* **New Feature**: Added a remind command that allows the user to view the tasks coming up in the next 3 days.
   * What it does: Detects whether the tasks added to NurseyBook have dates that are scheduled for the next 3 days (in comparison to the time of running the command), and displays the result to the user.
   * Justification: This feature similarly adds to the user experience. If a user has a task list cluttered up with multiple irrelevant and undeleted tasks, the command assists the user in detecting tasks of higher priority (in less than a second).
   * Highlights: This implementation marks the start of incorporating the usage of time and duration into NurseyBook.

* **New Feature**: Added the ability to detect overdue tasks.
    * What it does: Detects whether a task is overdue by comparing the indicated date and time of the task with the current time. If the task is overdue and not yet completed, a tag is added to its display in NurseyBook.
    * Justification: This feature improves the user experience, as a user can quickly identify the tasks that should be completed as soon as possible. This way, a user can prioritise the right tasks to focus on. 
    * Highlights: This implementation alters how a task is represented within NurseyBook. Each task has an additional overdue status. Furthermore, it required working in the UI package (compared to certain other feature implementations that only touch the model and logic packages).

* **New Feature**: Added the ability to add remarks to elderly contacts.
    * What it does: Allows the user to add additional notes in the form of a remark, to each elderly contact that has been added into NurseyBook.
    
* **Project management**:
    * Managed releases `v1.1` - `v1.4` (4 releases) on GitHub

* **Enhancements to existing features**:
    * Updated the GUI color scheme (Pull requests [\#33](), [\#34]())
    * Wrote additional tests for existing features to increase coverage from 88% to 92% (Pull requests [\#36](), [\#38]())

* **Documentation**:
    * User Guide:
        * Added documentation for the features `delete` and `find` [\#72]()
        * Did cosmetic tweaks to existing documentation of features `clear`, `exit`: [\#74]()
    * Developer Guide:
        * Added implementation details of the `delete` feature.

* **Community**:
    * PRs reviewed (with non-trivial review comments): [\#12](), [\#32](), [\#19](), [\#42]()
    * Contributed to forum discussions (examples: [1](), [2](), [3](), [4]())
    * Reported bugs and suggestions for other teams in the class (examples: [1](), [2](), [3]())
    * Some parts of the history feature I added was adopted by several other class mates ([1](), [2]())

* **Tools**:
    * Integrated a third party library (Natty) to the project ([\#42]())
    * Integrated a new Github plugin (CircleCI) to the team repo

* _{you can add/remove categories in the list above}_
