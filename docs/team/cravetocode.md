---
layout: page
title: Ramkumar Madur Malliah's Project Portfolio Page
---

### Project: NurseyBook

#### Overview

NurseyBook is a desktop application made for nurses in nursing homes to aid them in managing contacts and tasks in their busy lives. The user interacts with it using Command Line Interface (CLI),
and it still has the benefits of a Graphical User Interface (GUI) created with JavaFX. It is written in Java, and has about 20 kLoC.

#### Summary of Contributions

Given below are my key contributions to the project.

* **New Feature**: Added the `viewSchedule` command to allow a previewing of tasks that will be occurring on a future date
  * What it does: Allows the user to check their schedule of tasks on a specific date in the future, inclusive of all potential future occurrences of recurring tasks.
  * Justification: This feature is a huge improvement to the user experience, as it allows a user to check all tasks on a particular date, so that they can better plan their schedule. This makes the entire Task Management system more meaningful, as without this, the user would have to traverse task by task to map out their schedule.
  * Highlights: This enhancement is unique in that it automatically calculates all future occurrences of recurring tasks. This was the primary motivating command that necessitated the need to refactor `Task` objects into `RealTask` and `GhostTask` objects.


* **New Feature**: Added the ability to store GhostTasks in Nurseybook through a major refactor of the `Task` class.
  * What it does: Internally allows developers to store Tasks in the form of a `GhostTask`, that is hidden from the view of users unless explicitly allowed.
  * Justification: Prior to this, all `Task` objects were visible to users, and it was not possible to store hidden task objects internally in a sensible way. Introduction of `GhostTasks` allows for that, so that future occurrences of recurring tasks, which have not occurred yet and hence cannot be considered as `RealTasks`, can still be stored and played around with.
  * Highlights: This enhancement necessitated the need to entirely refactor the `Task` class. An in-depth analysis of design alternatives had to be considered before implementation, as detailed in the Developer Guide. This also sets the stage for future commands that involve recurring tasks and their future recurrences.


* **New Feature**: Added the ability to delete Next-of-Kin (NoK) details
  * What it does: Allows the user to quickly wipe all Next-of-Kin details by using a shortcut command.
  * Justification: A good Quality-Of-Life feature for users to instantly get rid of outdated NoK details, without having to go through the hassle of editting field by field.
  * Highlights: Instead of merely deleting NoK fields, the command sets all NoK fields to a default value. This means in the future, developers can choose a different default value they would like to set each NoK field to, or even open this functionality to the user through a new command that enables users to set default values.  
  
* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2122s1.github.io/tp-dashboard/?search=&sort=groupTitle&sortWithin=title&since=2021-09-17&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=false&tabOpen=true&tabType=authorship&tabAuthor=CraveToCode&tabRepo=AY2122S1-CS2103T-F13-2%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code~other&authorshipIsBinaryFileTypeChecked=false)

* **Project management**:
  * Managed releases `v1.1` - `v1.4b` (5 releases) on GitHub
  * Setup GitHub Team Organisation and Repository
  * Setup Project Website: Set up GitHub pages, updated settings, changed names and links from AddressBook to NurseyBook
  * Maintained issue tracker: Handled creation of new issues for milestones, managed milestones and assignees, cleaned up closed issues

* **Enhancements to existing features**:
  * Involved in refactoring `Person` into an abstract class with concrete `Elderly` and `Nok` subclasses. (Pull requests [\#73](https://github.com/AY2122S1-CS2103T-F13-2/tp/pull/73), [\#76](https://github.com/AY2122S1-CS2103T-F13-2/tp/pull/76), [\#79](https://github.com/AY2122S1-CS2103T-F13-2/tp/pull/79))
  * Added the ability to store `RoomNumber` information in `Person` objects. (Pull request [\#63](https://github.com/AY2122S1-CS2103T-F13-2/tp/pull/63))

* **Documentation**:
  * User Guide:
    * Added documentation for the features `viewSchedule`, `deleteNok`, `addElderly`, and `deleteElderly`. (Pull Requests [\#117](https://github.com/AY2122S1-CS2103T-F13-2/tp/pull/117), [\#79](https://github.com/AY2122S1-CS2103T-F13-2/tp/pull/79), [\#48](https://github.com/AY2122S1-CS2103T-F13-2/tp/pull/48))
  * Developer Guide:
    * Added implementation details for the features `viewSchedule`, `deleteNok`, `addElderly`, and `deleteElderly`. (Pull Requests [\#265](https://github.com/AY2122S1-CS2103T-F13-2/tp/pull/265), [\#106](https://github.com/AY2122S1-CS2103T-F13-2/tp/pull/106), [\#47](https://github.com/AY2122S1-CS2103T-F13-2/tp/pull/47))
    * Added class diagram for better representation of `Task` objects, sequence diagram to illustrate how `deleteNok` command is executed, and an
    activity diagram to visualize the addition of GhostTasks during `viewSchedule` command (Pull Requests [\#265](https://github.com/AY2122S1-CS2103T-F13-2/tp/pull/265), [\#106](https://github.com/AY2122S1-CS2103T-F13-2/tp/pull/106))
* **Community**:
  * PRs reviewed (with non-trivial review comments): (Pull Requests [\#203](https://github.com/AY2122S1-CS2103T-F13-2/tp/pull/203), [\#93](https://github.com/AY2122S1-CS2103T-F13-2/tp/pull/93))
  * Reported bugs and suggestions for other teams in the class during PE-D. (Issues created: [PED](https://github.com/CraveToCode/ped/issues))
