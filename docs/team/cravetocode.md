---
layout: page
title: CraveToCode's Project Portfolio Page
---

### Project: NurseyBook

#### Overview

NurseyBook is a desktop application made for nurses in nursing homes to aid them in managing contacts and tasks in their busy lives. The user interacts with it using Command Line Interface (CLI),
and it still has the benefits of a Graphical User Interface (GUI) created with JavaFX. It is written in Java, and has about 17 kLoC.

#### Summary of Contributions

Given below are my contributions to the project.

* **New Feature**: Added the ability to view a preview of tasks that will be occurring on a future date.
  * What it does: Allows the user to check their schedule of tasks on a specific date in the future, inclusive of all potential future occurrences of recurring tasks.
  * Justification: This feature is a huge improvement to the user experience, as it allows a user to check all tasks on a particular date, so that they can better plan their schedule. This makes the entire Task Management system more meaningful, as without this, the user would have to traverse task by task to map out their schedule.
  * Highlights: This enhancement is unique in that it automatically calculates all future occurrences of recurring tasks. This was the primary motivating command that necessitated the need to refactor `Task` objects into `RealTask` and `GhostTask` objects.


* **New Feature**: Added the ability to store GhostTasks in Nurseybook.
  * What it does: Internally allows developers to store Tasks in the form of a `GhostTask`, that is hidden from the view of users unless explicitly allowed.
  * Justification: Prior to this, all `Task` objects were visible to users, and it was not possible to store hidden task objects internally in a sensible way. Introduction of `GhostTasks` allows for that, so that future occurrences of recurring tasks, which have not occurred yet and hence cannot be considered as `RealTasks`, can still be stored and played around with.
  * Highlights: This enhancement necessitated the need to entirely refactor `Task` objects into `RealTask` and `GhostTask` objects. An in-depth analysis of design alternatives had to be considered before implementation, as detailed in the Developer Guide. This also sets the stage for more complex commands in the future that work with recurring tasks and their yet-to-occur recurrences.


* **New Feature**: Added the ability to delete Next-of-Kin (NoK) details
  * What it does: Allows the user to quickly wipe all Next-of-Kin details by using a shortcut command.
  * Justification: A good Quality-Of-Life feature for users to instantly get rid of outdated NoK details, without having to go through the hassle of editting field by field.


* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2122s1.github.io/tp-dashboard/?search=&sort=groupTitle&sortWithin=title&since=2021-09-17&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=false&tabOpen=true&tabType=authorship&tabAuthor=CraveToCode&tabRepo=AY2122S1-CS2103T-F13-2%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code~other&authorshipIsBinaryFileTypeChecked=false)

* **Project management**:
  * Managed releases `v1.1` - `v1.4` (4 releases) on GitHub

* **Enhancements to existing features**:
  * Partly responsible for refactoring `Person` into an abstract class with concrete `Elderly` and `Nok` subclasses.
  * Added the ability to store `RoomNumber` information in `Person` objects. (Pull request [\#63](https://github.com/AY2122S1-CS2103T-F13-2/tp/pull/63))

* **Documentation**:
  * User Guide:
    * Added documentation for the features `addElderly`, `deleteElderly`, and `viewSchedule`. [\#72]()
    * //DELETED_LATER id cosmetic tweaks to existing documentation of features `clear`, `exit`: [\#74]()
  * Developer Guide:
    * Added implementation details of the `viewSchedule` and `deleteNok` features.

* **Community**:
  * PRs reviewed (with non-trivial review comments): [\#12](), [\#32](), [\#19](), [\#42]()
  * Contributed to forum discussions (examples: [1](), [2](), [3](), [4]())
  * Reported bugs and suggestions for other teams in the class (examples: [1](), [2](), [3]())
  * Some parts of the history feature I added was adopted by several other class mates ([1](), [2]())

* _{you can add/remove categories in the list above}_
