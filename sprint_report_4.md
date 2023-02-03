# Sprint x Report (1/8/23 - 2/3/23)

## What's New (User Facing)
 * Implemented Image Processing Engine 
 * Implemented Auto Camera number generation

## Work Summary (Developer Facing)
During the sprint we split off and tried to divide and conquer. Abhi and Jordan worked on the image processing engine and the integration into the databse while Zach worked on generating camera numbers on first call of AGICamConfigure. Creating an image processor for the first time was a huge learning curve with lots of technical issues we never expected to encounter. At first when working on the processor we were getting wildly inaccurate readings that obviously were not properly using the color channels to determine the results. After a few days of attempting to get that to work Abhi and Jordan were able to use an algorithm provided by Worasit who is a grad student for the client to be able to effectively process the images. While this was going on Zach was able to make large improvements to AGICamConfigure, one of the main issues zach had to overcome with this was that our MongoDB was not configured correctly to allow connections from his computer. To overcome this we all got into call and problem solved together.

## Unfinished Work
In this sprint we did not find time to add selection of time for image capture, however we will finish it next sprint.

## Completed Issues/User Stories
Here are links to the issues that we completed in this sprint:

 * https://github.com/WSUCptSCapstone-Fall2022Spring2023/msft-imagedataprocessing/issues/55
 * https://github.com/WSUCptSCapstone-Fall2022Spring2023/msft-imagedataprocessing/issues/79


 ## Incomplete Issues/User Stories
 Here are links to issues we worked on but did not complete in this sprint:
 
 * https://github.com/WSUCptSCapstone-Fall2022Spring2023/msft-imagedataprocessing/issues/77 - We ran out of time to get to this
 * https://github.com/WSUCptSCapstone-Fall2022Spring2023/msft-imagedataprocessing/issues/50 - We were not able to find a reason to do this fully yet.
 
## Code Files for Review
Please review the following code files, which were actively developed during this sprint, for quality:
 * [ConfigureRunner.java](https://github.com/WSUCptSCapstone-Fall2022Spring2023/msft-imagedataprocessing/blob/044763b45705e60c009c9e65ee83d4d67cd1ef09/AGICamConfigure/src/main/java/org/agicam/ConfigureRunner.java)
 * [NDVIProcessor.java](https://github.com/WSUCptSCapstone-Fall2022Spring2023/msft-imagedataprocessing/blob/main/AGICamProcessor/src/main/java/org/agicam/processor/NDVIProcessor.java)
 * [ProcessorRunner.java](https://github.com/WSUCptSCapstone-Fall2022Spring2023/msft-imagedataprocessing/blob/main/AGICamProcessor/src/main/java/org/agicam/processor/ProcessorRunner.java)
 * [Couple.java](https://github.com/WSUCptSCapstone-Fall2022Spring2023/msft-imagedataprocessing/blob/main/AGICamProcessor/src/main/java/org/agicam/processor/util/Couple.java)
 * [Plot.java](https://github.com/WSUCptSCapstone-Fall2022Spring2023/msft-imagedataprocessing/blob/main/AGICamProcessor/src/main/java/org/agicam/processor/util/Plot.java)
 
## Retrospective Summary
Here's what went well:
  * Our team worked together efficiently and provided great support to one another
  * Our system has finally come full circle
  * Our clients appear to be happy with the current progress.
 
Here's what we'd like to improve:
   * Proactiveness, this sprint due to scheduling issues our team had a slow start. We would like to be proactive for the next sprint.
   * Issue tracking, Our team could benefit from more detailed issue tracking.
   * Inplace testing, Currently our project lacks testing inside of our code base. Doing this would be useful
  
Here are changes we plan to implement in the next sprint:
   * Setting a plan from the start of the sprint on what need to be completed from an implementation standpoint.
   * Define all issues at the start of the sprint and frequently update them
   * Determine a testing route at the start of the sprint to ensure testing makes progress.
