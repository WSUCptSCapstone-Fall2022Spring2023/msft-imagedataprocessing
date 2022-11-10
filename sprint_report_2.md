# Sprint x Report (8/26/21 - 9/24/2021)

## What's New (User Facing)
 * Nothing is new on the user facing side of things

## Work Summary (Developer Facing)
This spring we were able to navigate through a plethora of problems with creating software on our target software (Raspibian). We have successfully moved from using directory to store our output to using a simple uploader that propogates all images to our MongoDB cluster. Configuration of camera capturing is still in progress but has been worked on. One of the main issues we ran into with compiling software ontop of Raspibian was that its a unique target and not all softwares support compiling ontop of it. Initially the plan for the Mongo Uploader was to have it written in C++ which requires the compilation of MongoC and MongoCxx drivers. However this was not possible as once we got the very last stage of linking MongoC to our device we got a device not supported error. Possibly we could do some sort of lying to the compiler to get this to work but instead we switched from using the MongoC/Cxx drivers to using the Mongo Java Drivers. 

## Unfinished Work
Our team was not able to complete camera capture and camera scheduling this sprint as our team had significant outside responsibilities which led to a lack of time to complete these features. Instead both of these features have been researched extensively and will be easily completed at the start of Sprint 3.

## Completed Issues/User Stories
Here are links to the issues that we completed in this sprint:

 * URL of issue 1
 * URL of issue 2
 * URL of issue n

 Reminders (Remove this section when you save the file):
  * Each issue should be assigned to a milestone
  * Each completed issue should be assigned to a pull request
  * Each completed pull request should include a link to a "Before and After" video
  * All team members who contributed to the issue should be assigned to it on GitHub
  * Each issue should be assigned story points using a label
  * Story points contribution of each team member should be indicated in a comment
 
 ## Incomplete Issues/User Stories
 Here are links to issues we worked on but did not complete in this sprint:
 
 * URL of issue 1 <<One sentence explanation of why issue was not completed>>
 * URL of issue 2 <<One sentence explanation of why issue was not completed>>
 * URL of issue n <<One sentence explanation of why issue was not completed>>
 
 Examples of explanations (Remove this section when you save the file):
  * "We ran into a complication we did not anticipate (explain briefly)." 
  * "We decided that the feature did not add sufficient value for us to work on it in this sprint (explain briefly)."
  * "We could not reproduce the bug" (explain briefly).
  * "We did not get to this issue because..." (explain briefly)

## Code Files for Review
Please review the following code files, which were actively developed during this sprint, for quality:
 * [Main.Cpp -AGICamUploader](https://github.com/WSUCptSCapstone-Fall2022Spring2023/msft-imagedataprocessing/blob/feature/create-mongo-uploader/src/main/java/org/agicam/Main.java)
 
## Retrospective Summary
Here's what went well:
  * Quickly transitioning when problems arose
  * Client communication
  * Database setup
  * Device setup and remote access
 
Here's what we'd like to improve:
   * Team availability
   * Code workflow
   * Team communication
  
Here are changes we plan to implement in the next sprint:
   * Shift meetings to be more implementation based allowing for us to work together better when writing code
   * Planning each week of each sprint instead of solely sprint to improve speed of project
   * Defined expectations for each team member
