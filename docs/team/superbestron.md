---
layout: page
title: Tze Henn's Project Portfolio Page
---

### Project: AddressBook Level 3

NurseyBook is a desktop address book application designed to help nurses manage their contacts and tasks. The user primarily interacts with it using a CLI, and it has a GUI created with JavaFX. It is written in Java, and has about 20 kLoC.

Given below are my contributions to the project.

* **New Feature**: Added the ability to add recurring tasks.
  * What it does: allows the user to create tasks with different recurrence types: none, daily, weekly or monthly.
  * Justification: This feature improves the product significantly because it gives a user a lot of flexibility in creating variations of task, which suits different needs of a user.
  * Highlights: This enhancement is an important feature of NurseyBook. This required implementing an auxiliary function to update the task’s current date once its date has passed, which varies based on its recurrence type. The integration of this function with the app was challenging as now, all commands executed will have this side effect of updating the task's current date. As a result, testing for this feature needed to be more comprehensive.

* **New Feature**: Added the ability to edit tasks.
  * What it does: allows the user to make changes to any details of a task.
  * Justification: This feature improves the user experience greatly as a user does not have to go through the troublesome motion of deleting and adding a task again should a user decides to change the task’s details, or editing all tasks which contain the same elderly name.
  * Highlights: Similar to the first feature mentioned above, integration of this feature was challenging as this command have the side effect of updating the elderly names of other tasks. As a result, testing for this feature was also more comprehensive than usual.

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2122s1.github.io/tp-dashboard/#breakdown=true&search=superbestron)

* **Project management**:
  * Managed releases `v1.1` - `v1.4` (4 releases) on GitHub

<div style="page-break-after: always;"></div>
* **Enhancements to existing features**:
  * Performed a major refactor of `Person` class to include `Elderly` and `Nok` classes. (Pull requests [\#64](https://github.com/AY2122S1-CS2103T-F13-2/tp/pull/64), [\#78](https://github.com/AY2122S1-CS2103T-F13-2/tp/pull/78))
  * Made the relationships between elderly and task more intuitive. (Pull requests [\#220](https://github.com/AY2122S1-CS2103T-F13-2/tp/pull/220))
    * Edits and deletions of elderly names performed by `editCommand` and `deleteCommand` will cause the relevant changes to be observed in tasks that contain that name
    * Disallowed unregistered `Elderly` in `Task`. Commands such as `addTask` and `editTask` included checks as to whether the new/edited elderly currently exists in the database.

* **Documentation**:
  * User Guide:
    * Added documentation for the features `editElderly`, `viewDetails`, `editElderly`, `deleteElderly`, `add`addTask`, `editTask` (Pull requests [\#40](https://github.com/AY2122S1-CS2103T-F13-2/tp/pull/40), [\#212](https://github.com/AY2122S1-CS2103T-F13-2/tp/pull/212), [\#232](https://github.com/AY2122S1-CS2103T-F13-2/tp/pull/232))
  * Developer Guide:
    * Updated use cases (Pull requests [\#46](https://github.com/AY2122S1-CS2103T-F13-2/tp/pull/46), [\#243](https://github.com/AY2122S1-CS2103T-F13-2/tp/pull/243))
    * Added implementation details of the sections "Add Recurring Task feature" and "Handling of Overdue and Recurring Tasks" with the activtiy diagram HandleOverdueAndRecurringTasksActivityDiagram.png. (Pull requests [\#212](https://github.com/AY2122S1-CS2103T-F13-2/tp/pull/212)) 
    * Improved section on Model architecture with new model class diagrams ModelClassDiagram.png and DetailedModelClassDiagram.png. (Pull requests [\#104](https://github.com/AY2122S1-CS2103T-F13-2/tp/pull/104), [\#105](https://github.com/AY2122S1-CS2103T-F13-2/tp/pull/105))

* **Community**:
  * PRs reviewed (with non-trivial review comments): (Pull requests [\#77](https://github.com/AY2122S1-CS2103T-F13-2/tp/pull/77), [\#107](https://github.com/AY2122S1-CS2103T-F13-2/tp/pull/107))
