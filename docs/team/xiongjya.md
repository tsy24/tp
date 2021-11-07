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

* **Code contributed**: Around 5 kLoC contribution to the project. [Reposense link](https://nus-cs2103-ay2122s1.github.io/tp-dashboard/?search=f13&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2021-09-17&tabOpen=true&tabType=authorship&tabAuthor=xiongjya&tabRepo=AY2122S1-CS2103T-F13-2%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code~other&authorshipIsBinaryFileTypeChecked=false)



* **New Feature**: Added the ability to add remarks to elderly contacts.
    * What it does: Allows the user to add additional notes in the form of a remark, to each elderly contact that has been added into NurseyBook.
    * Justification: Instead of using other applications/pen or paper to take down additional notes for each elderly, the user can keep track of such information within the application itself. There will be no need for the user to switch between different applications.


* **New Feature**: Sort newly added tasks into NurseyBook in chronological order.
    * What it does: When the user adds a task into NurseyBook, the task is positioned in the list according to its date and time, such that the tasks in the list are always chronologically ordered.
    * Justification: As the user will be more likely to start on tasks that are due sooner, he/she will prefer to view such tasks in a more accessible manner. Thus, tasks that are due sooner are displayed at the top of the task list. 
  

* **New Feature**: Added a remind command that allows the user to view the tasks coming up in the next 3 days.
    * What it does: Detects tasks added to NurseyBook that are scheduled within the next 3 days (in comparison to the time of running the command), and displays the result to the user.
    * Justification: This feature improves the user experience. If a user has a task list cluttered up with multiple irrelevant and undeleted tasks, the command assists the user in detecting tasks of higher priority (in less than a second).
    * Highlights: This implementation marks the start of incorporating the usage of time and duration into NurseyBook.


* **New Feature**: Added the ability to detect overdue tasks.
    * What it does: Detects whether a task is overdue by comparing the indicated date and time of the task with the current time. If the task is overdue and not yet completed, a tag is added to its display in NurseyBook.
    * Justification: This feature similarly adds to the user experience. A user can quickly identify the tasks that should be completed as soon as possible. This way, a user can prioritise the right tasks to focus on. 
    * Highlights: This implementation alters how a task is represented within NurseyBook. Each task has an additional overdue status. Furthermore, it required working in the UI package (compared to certain other feature implementations that only touch the model and logic packages).
  

* **New Feature**: Added the ability to find tasks within NurseyBook. 
    * What it does: Allows the user to look for tasks with matching keywords in the task description.
    * Justification: This feature boosts the speed and efficiency of the user. The user no longer has to scroll through multiple tasks to search for the one they need, but simply type in a keyword to filter out the unrelated tasks. 
    

* **Documentation**:
    * User Guide:
        * Added documentation for the `viewElderly`, `remind`, `editTask` and `findTask` features (Pull requests [\#41](https://github.com/AY2122S1-CS2103T-F13-2/tp/pull/41), [\#118](https://github.com/AY2122S1-CS2103T-F13-2/tp/pull/118)).
        * Wrote the whole section, 'About' (Pull request [\#219](https://github.com/AY2122S1-CS2103T-F13-2/tp/pull/219)).
        * Included more screenshots for better user viewing (Pull request [\#219](https://github.com/AY2122S1-CS2103T-F13-2/tp/pull/219)).
    * Developer Guide:
        * Added implementation details of the `viewElderly` and `findTask` features (Pull requests [\#99](https://github.com/AY2122S1-CS2103T-F13-2/tp/pull/99), [\#107](https://github.com/AY2122S1-CS2103T-F13-2/tp/pull/107)).
        * Added user stories and use cases. (Pull requests [\#42](https://github.com/AY2122S1-CS2103T-F13-2/tp/pull/42), [\#95](https://github.com/AY2122S1-CS2103T-F13-2/tp/pull/95)).
        

* **Community**:
    * PRs reviewed (with non-trivial review comments): [\#61](https://github.com/AY2122S1-CS2103T-F13-2/tp/pull/61), [\#111](https://github.com/AY2122S1-CS2103T-F13-2/tp/pull/111), [\#115](https://github.com/AY2122S1-CS2103T-F13-2/tp/pull/115), [\#202](https://github.com/AY2122S1-CS2103T-F13-2/tp/pull/202), [\#237](https://github.com/AY2122S1-CS2103T-F13-2/tp/pull/237), [\#242](https://github.com/AY2122S1-CS2103T-F13-2/tp/pull/242)
    * Reported bugs and suggestions for other teams during mock practical examination (repository: [ped](https://github.com/xiongjya/ped))
