# Appcaca
CSUCI Fall 2019 COMP 350 Project

## Getting Started
Clone this repository onto your local machine.

### Android Studio
You can download Android Studio [directly](https://developer.android.com/studio) or in the [JetBrains Toolbox App](https://www.jetbrains.com/toolbox-app/).

## Repository Structure
Please maintain the following structure in th repository
* production - The current version of the application
  * testing - The branch that will contain code currently being tested before going to production
  * development - The code that is currently being worked on
    * sprintN - The branch for the nth sprint that is being worked on
      * Trello Task N - This is where you will be actually be working! 

## Workflow

### Before starting a sprint
1. @JoseRivas1998 will make a branch off of the development branch for that sprint

### Before working on a specific feature
1. Message @JoseRivas1998 and he will create a branch for the trello feature
2. Update your local repo with the following commands:
```
git branch -r | grep -v '\->' | while read remote; do git branch --track "${remote#origin/}" "$remote"; done
git fetch --all
git pull --all
```
### When working on a feature
1. Update your local repository by checking out the correct branch, and pulling that branch
2. Do your work.
3. Stage all changes and commit your work to your local repo
4. Pull the branch you are working on and resolve conflicts
5. If any confilicts needed to be resolved, commit those changes
6. Push your changes to the correct branch.

### When a feature is done
1. Create a pull request from your feature branch into sprint branch
2. @JoseRivas1998 will perform code review and give feedback on anything that needs to be changed
3. If changes need to be made, do those changes (see above) and comment on the PR to review again.
4. Once code review is done, the branch will be merged into the sprint and you can move on!

### When Rebasing
Rebasing is basically updating your feature code with code in the sprint branch that could have been pushed.
1. Create a pull request from the sprint branch to your feature branch.
2. Merge the pull request and resolve merge conflicts.
3. Commit merge results.

## Authors
* **Jose de Jesus Rodriguez Rivas** - [@JoseRivas1998](https://github.com/JoseRivas1998)
* **Teagan Walsh** - [@tea98](https://github.com/tea98)
* **Lee Solomon** - [@LeeSolomon](https://github.com/LeeSolomon)
* **Forrest Hampton** - [@ForrestHampton](https://github.com/ForrestHampton)
