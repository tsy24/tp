---
layout: page
title: tsy24's Project Portfolio Page
---

### Project: NurseyBook

NurseyBook is a desktop application made for nurses in nursing homes to aid them in managing contacts and tasks in their busy lives.
It is part of a team project for CS2103T Software Engineering principles. The user interacts with it using a CLI, and it has a GUI created with JavaFX. It is written in Java, and has about 19 kLoC.

Given below are my contributions to the project.

* **New Feature**: Added the ability to undo/redo previous commands.
    * What it does: allows the user to undo all previous commands one at a time. Preceding undo commands can be reversed by using the redo command.
    * Justification: This feature improves the product significantly because a user can make mistakes in commands and the app should provide a convenient way to rectify them.
    * Highlights: This enhancement affects existing commands and commands to be added in future. It required an in-depth analysis of design alternatives. The implementation too was challenging as it required changes to existing commands.
    * Credits: Solution was adapted from https://github.com/se-edu/addressbook-level4 and then implemented with modifications

* **New Feature**: Added the ability to add and delete tags
    * What it does: allows the user add or delete multiple tags
    * Justification: This feature improves the product significantly because edit tags through editElderly clears all existing tags. `addTag` allows users to add tags on top of existing ones and `deleteTag` allows users to only delete specified tags.

* **New Feature**: Added the ability to filter the elderly list by tags
    * What it does: allows the user to look for elderly based on one or more tags
    * Justification: This feature increases the efficiency of searching for elderly on NurseyBook as the user can search for all elderly with the same tags.
    * Highlights: This feature offers users an alternative way to search for an elderly on top of searching for an elderly by name.

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2122s1.github.io/tp-dashboard/?search=&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2021-09-17&tabOpen=true&tabType=authorship&tabAuthor=tsy24&tabRepo=AY2122S1-CS2103T-F13-2%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false)

* **Enhancements to existing features**:
    * Updated the GUI color scheme (Pull requests [\#33](), [\#34]())
    * Wrote additional tests for existing features to increase coverage from 88% to 92% (Pull requests [\#36](), [\#38]())

* **Documentation**:
    * User Guide:
        * Added documentation for the features `filter`, `addTag`, `deleteTag`, `undo` and `redo` [\#72]()
    * Developer Guide:
        * Added implementation details of the `undo` and `redo` feature. 
        * Added implementation details of the `filter` feature.
        * Added glossary

* **Community**:
    * PRs reviewed (with non-trivial review comments): [\#12](), [\#32](), [\#19](), [\#42]()
    * Reported bugs and suggestions for other teams in the class (examples: [1](), [2](), [3]())
    * Some parts of the history feature I added was adopted by several other class mates ([1](), [2]())

* **Tools**:
    * Integrated a third party library (Natty) to the project ([\#42]())
    * Integrated a new Github plugin (CircleCI) to the team repo
