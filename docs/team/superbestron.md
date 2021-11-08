---
layout: page
title: Tze Henn's Project Portfolio Page
---

### Project: AddressBook Level 3

NurseyBook is a desktop address book application designed to help nurses manage their contacts and tasks. The user primarily interacts with it using a CLI, and it has a GUI created with JavaFX. It is written in Java, and has about 19 kLoC.

Given below are my contributions to the project.

* **New Feature**: Added the ability to add recurring tasks.
  * What it does: allows the user to create tasks with different recurrence types: none, daily, weekly or monthly.
  * Justification: This feature improves the product significantly because it gives a user a lot of flexibility in creating variations of task, which suits different needs of a user.
  * Highlights: This enhancement is an important feature of NurseyBook. This required implementing an auxiliary function to update the task’s current date once its date has passed, which varies based on its recurrence type. The integration of this function with the app was challenging as now, all commands executed will have this side effect of updating the task's current date. As a result, testing for this feature needed to be more comprehensive.

* **New Feature**: Added the ability to edit tasks.
  * What it does: allows the user to make changes to any details of a task, and specifically, any edits to the same elderly name will be reflected across all tasks.
  * Justification: This feature improves the user experience greatly as a user does not have to go through the troublesome motion of deleting and adding a task again should a user decides to change the task’s details, or editing all tasks which contain the same elderly name.
  * Highlights: Similar to the first feature mentioned above, integration of this feature was challenging as this command have the side effect of updating the elderly names of other tasks. As a result, testing for this feature was also more comprehensive than usual.

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2122s1.github.io/tp-dashboard/#breakdown=true&search=superbestron)

* **Project management**:
  * Managed releases `v1.1` - `v1.4` (4 releases) on GitHub

* **Enhancements to existing features**:
  * Performed a major refactor of `Person` class to include `Elderly` and `Nok` classes. (Pull requests [\#64](https://github.com/AY2122S1-CS2103T-F13-2/tp/pull/64), [\#78](https://github.com/AY2122S1-CS2103T-F13-2/tp/pull/78))

* **Documentation**: (not updated yet)
  * User Guide:
    * Added documentation for the features `delete` and `find` [\#72]()
    * Did cosmetic tweaks to existing documentation of features `clear`, `exit`: [\#74]()
  * Developer Guide:
    * Added implementation details of the `delete` feature.

* **Community**:
  * PRs reviewed (with non-trivial review comments): [\#107](https://github.com/AY2122S1-CS2103T-F13-2/tp/pull/107)
