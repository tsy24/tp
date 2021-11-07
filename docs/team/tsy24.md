---
layout: page
title: tsy24's Project Portfolio Page
---

### Project: NurseyBook

#### Overview

NurseyBook is a desktop application made for nurses in nursing homes to aid them in managing contacts and tasks in their busy lives. The user interacts with it using Command Line Interface (CLI),
and it still has the benefits of a Graphical User Interface (GUI) created with JavaFX. It is written in Java, and has about 17 kLoC.

#### Summary of Contributions

Given below are my contributions to the project.

* **New Feature**: Added the ability to undo/redo previous commands.
    * What it does: Allows the user to undo previous commands one at a time. Preceding undo commands can be reversed by using the redo command.
    * Justification: This feature improves the product significantly because a user can make mistakes in commands and the app should provide a convenient way to rectify them.
    * Highlights: This enhancement affects existing commands and commands to be added in the future. It required an in-depth analysis of design alternatives. The implementation too was challenging as it required changes to existing commands and architecture. Copies of objects had to be created to save previous states.
    * Credits: Solution was adapted from https://github.com/se-edu/addressbook-level4 and implemented with modifications

* **New Feature**: Added the ability to add and delete tags
    * What it does: Allows the user to add or delete one or more tags
    * Justification: This feature improves the product significantly because editing tags through `editElderly` clears all existing tags. `addTag` allows users to add tags on top of existing ones and `deleteTag` allows users to only delete specified tags.

* **New Feature**: Added the ability to filter the elderly list by tags
    * What it does: Allows the user to look for elderly based on one or more tags
    * Justification: This feature increases the efficiency of searching for elderly on NurseyBook as the user can search for all elderly with the same tags or search for an elderly based on his tags.
    * Highlights: This feature required a new way to test the `Elderly` objects. It required an in-depth analysis of design alternatives on how to store tags.

* **Code contributed**: Around 4kLoC contribution to the project [RepoSense link](https://nus-cs2103-ay2122s1.github.io/tp-dashboard/?search=&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2021-09-17&tabOpen=true&tabType=authorship&tabAuthor=tsy24&tabRepo=AY2122S1-CS2103T-F13-2%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false)

* **Enhancements to existing features**:
    * Partly responsible for major refactoring of `AddressBook` to `NurseyBook` in project (Pull request [\#202](https://github.com/AY2122S1-CS2103T-F13-2/tp/pull/202))

* **Documentation**:
    * User Guide:
        * Added documentation for the features `filter`, `addTag`, `deleteTag`, `undo` and `redo` (Pull requests [\#43](https://github.com/AY2122S1-CS2103T-F13-2/tp/pull/43), [\#113](https://github.com/AY2122S1-CS2103T-F13-2/tp/pull/113))
        * Did cosmetic tweaks to existing documentation (Pull requests [\#233](https://github.com/AY2122S1-CS2103T-F13-2/tp/pull/233), [\#240](https://github.com/AY2122S1-CS2103T-F13-2/tp/pull/240))
    * Developer Guide:
        * Added implementation details of the `filter`, `undo` and `redo` feature. (Pull requests [\#102](https://github.com/AY2122S1-CS2103T-F13-2/tp/pull/102), [\#113](https://github.com/AY2122S1-CS2103T-F13-2/tp/pull/113))
        * Updated the UML diagrams and description of the architecture of the application (Pull request [\#236](https://github.com/AY2122S1-CS2103T-F13-2/tp/pull/236))
        * Reorganised and updated use cases (Pull request [\#236](https://github.com/AY2122S1-CS2103T-F13-2/tp/pull/236))
        * Added glossary
        * Added manual testing instructions for `filter`, `addTag`, `deleteTag`, `undo` and `redo` (Pull request [#\261](https://github.com/AY2122S1-CS2103T-F13-2/tp/pull/261))

* **Community**:
    * PRs reviewed (with non-trivial review comments): [\#69](https://github.com/AY2122S1-CS2103T-F13-2/tp/pull/69), [\#114](https://github.com/AY2122S1-CS2103T-F13-2/tp/pull/114), [\#210](https://github.com/AY2122S1-CS2103T-F13-2/tp/pull/210), [\#224](https://github.com/AY2122S1-CS2103T-F13-2/tp/pull/224), [#/254](https://github.com/AY2122S1-CS2103T-F13-2/tp/pull/254)
    * Reported bugs and suggestions for other teams during the mock practical examination (repository: [ped](https://github.com/tsy24/ped/issues))

