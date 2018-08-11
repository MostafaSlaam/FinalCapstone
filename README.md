# Fantasy El Premier League
## Description
Fantasy El Premier League represents English Premier League matches, their details at each Game week (goal scored, assisted) and display Fixtures for each team.
User can Comment on the current game week.
## Intended User
this an app for English Premier League fans around the whole world.
Features
## List the main features of your app. For example:
##### • Enable Sign in using google (Gmail)
##### • Retrieve API from Network
##### • Display Current Game Week
##### • User can explore game weeks and players
##### • User can set players at a watchlist
##### • make a widget next game week
##### • User can make comments and see other comments


## Key Considerations
### How will your app handle data persistence?
I will use a Content Provider to save players at the watchlist and use Firebase Realtime
Database to make comments and share it among others
### Describe any edge or corner cases in the UX.
In fixtures there are two button
Next: display the next game week
Previous: display the previous game week
And in option menu there is a watchlist for players saved in SQLite
### Describe any libraries you’ll be using and share your reasoning for including
them.
Picasso to handle the loading of images.
Describe how you will implement Google Play Services or other external
services.
I will use firebase authentication to enables sign in with google email
I will use firebase database to store Comments
## Next Steps: Required Tasks
This is the section where you can take the main features of your app (declared above)
and break them down into tangible technical tasks that you can complete one at a time
until you have a finished app.
### Task 1: Project Setup
Write out the steps you will take to setup and/or configure this project. See previous
implementation guides for an example.
You may want to list the subtasks. For example:
##### • Configure libraries
##### • Something else
If it helps, imagine you are describing these tasks to a friend who wants to follow along
and build this app with you.
##### 1: set up firebase authentication and database
##### 2: add dependency of used libraries
##### 3: prepare used API
##### 4: app uses an AsyncTask to pull from API
### Task 2: Implement UI for Each Activity and Fragment
List the subtasks
##### • Build UI for Sign in Activity
##### • Build UI for MainActivity
##### • Build UI for Detailed Activity
##### • Build UI for section Fragment
### Task 3: Your Next Task
Describe the next task. For example, “Implement Google Play Services,” or “Handle
Error Cases,” or “Create Build Variant.”
Describe the next task. List the subtasks.
##### • Build SQLite Database and Content Provider
##### • In google Sign in in case not access any email the app not crash, still in sign in
activity to enable user to set a google account (Gmail)
##### • Build player Dialog display his data ( image,club,..)
### Task 4: Your Next Task
In detail Activity will appear when user select a specific match get started already and
display goal scored, assisted and some details
Make functionality that enable user to select a specific player and see his performance
during the game
And add him to a watchlist
### Task 5: Your Next Task
Build watchlist activity that display stored players
build logout from google account
