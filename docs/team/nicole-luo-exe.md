---
layout: page
title: Siyi's Project Portfolio Page
---

### Project: NurseyBook

NurseyBook is a desktop address book application designed to help nurses manage their contacts and tasks. Users primarily interact with it using a CLI, and it has a GUI created with JavaFX. It is written in Java, and has about 17 kLoC.

Given below are my contributions to the project.

* **New Feature**: Added basic task functionalities.
    * What it does: allows the user to store tasks and execute basic commands on them - i.e. view, add, mark done, delete
    * Highlights: This feature serves as a basis for all future task features. This required careful forethought as the implementation should be flexible enough for future extensions to be made

* **New Feature**: Created separate views for task and elderlies.
  * What it does: allows the user to toggle between viewing task and elderly data
  * Highlights: This feature- especially the creation of the view for tasks, required dabbling in mockups in order to plan how best to display data to users that results in the best user experience

* **New Feature**: Separated elderly's key details from their full details in UI
  * What it does: Display only an elderly's key details in regular view and provides users with a way to view elderly's full details.
  
* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2122s1.github.io/tp-dashboard/?search=&sort=totalCommits%20dsc&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2021-09-17&tabOpen=true&tabType=authorship&zFR=false&tabAuthor=nicole-luo-exe&tabRepo=AY2122S1-CS2103T-F13-2%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false)

* **Enhancements to existing features**:
    * Revamped the entire GUI to match nurse theme and changed the color scheme to making more visually appealing (Pull requests [\#108](https://github.com/AY2122S1-CS2103T-F13-2/tp/pull/108), [\#110](https://github.com/AY2122S1-CS2103T-F13-2/tp/pull/110))
    * Improved help window to make more easy to navigate (Pull request [\#221](https://github.com/AY2122S1-CS2103T-F13-2/tp/pull/221))

* **Documentation**:
    * User Guide:
        * Added documentation for the task features `addTask`, `deleteTask`, `doneTask` and `viewTasks` [\#53](https://github.com/AY2122S1-CS2103T-F13-2/tp/pull/53)
        * Did cosmetic tweaks to existing documentation of elderly features [\#115](https://github.com/AY2122S1-CS2103T-F13-2/tp/pull/115)
    * Developer Guide:
        * Added implementation details of the `doneTask` feature. [\#98](https://github.com/AY2122S1-CS2103T-F13-2/tp/pull/98)
        * Added implementation details of the `viewDetails` feature. [\#269](https://github.com/AY2122S1-CS2103T-F13-2/tp/pull/269) 
        * Added manual testing section for `editElderly`, `findElderly` and `viewDetails`. [\#269](https://github.com/AY2122S1-CS2103T-F13-2/tp/pull/269)

* **Community**:
    * PRs reviewed (with non-trivial review comments): [\#112](https://github.com/AY2122S1-CS2103T-F13-2/tp/pull/112), [\#75](https://github.com/AY2122S1-CS2103T-F13-2/tp/pull/75), [\#201](https://github.com/AY2122S1-CS2103T-F13-2/tp/pull/201), [\#42]()
    * Contributed to forum discussions ([link](https://github.com/nus-cs2103-AY2122S1/forum/issues/231#issuecomment-927557868))
    * Reported bugs and suggestions for other teams in the class (repository: [ped](https://github.com/nicole-luo-exe/ped))
