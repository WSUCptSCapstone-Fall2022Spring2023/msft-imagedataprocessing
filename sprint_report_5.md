# Sprint 5 Report (2/2/23 - 3/2/2023)

## What's New (User Facing)
 * REST API Is ready
 * Quadrilateral plot processing is ready

## Work Summary (Developer Facing)
This sprint our team worked efficiently to create the REST API for our system. This rest API was the majority of our work this sprint and allows for future contributors to the project to easily interface our current system. We did not run into any issues while working on the API. While this was being completed Zach was able to update some sensor code inorder for it have optimized scheduling. Quadrilateral processing of plots is now ready for our system which will allow for more dynamic selection of plots on our sensors. Our team benefited greatly this sprint by laying out all issues at the start of the sprint and starting work early instead of waiting.

## Unfinished Work
We were not able to finish our documentation or close the NDVI rest-api endpoint. These are trivial issues and will be addressed at the start of the next sprint quickly.

## Completed Issues/User Stories
Here are links to the issues that we completed in this sprint:

 * https://github.com/WSUCptSCapstone-Fall2022Spring2023/msft-imagedataprocessing/issues/77
 * https://github.com/WSUCptSCapstone-Fall2022Spring2023/msft-imagedataprocessing/issues/85
 * https://github.com/WSUCptSCapstone-Fall2022Spring2023/msft-imagedataprocessing/issues/86
 * https://github.com/WSUCptSCapstone-Fall2022Spring2023/msft-imagedataprocessing/issues/87
 * https://github.com/WSUCptSCapstone-Fall2022Spring2023/msft-imagedataprocessing/issues/89
 * https://github.com/WSUCptSCapstone-Fall2022Spring2023/msft-imagedataprocessing/issues/91
 * https://github.com/WSUCptSCapstone-Fall2022Spring2023/msft-imagedataprocessing/issues/97
 * https://github.com/WSUCptSCapstone-Fall2022Spring2023/msft-imagedataprocessing/issues/107
 
 ## Incomplete Issues/User Stories
 Here are links to issues we worked on but did not complete in this sprint:
 
 * https://github.com/WSUCptSCapstone-Fall2022Spring2023/msft-imagedataprocessing/issues/78 - We ran out of time to verify the final rest-api endpoint.
 * https://github.com/WSUCptSCapstone-Fall2022Spring2023/msft-imagedataprocessing/issues/84 - We ran out of time to validate this issue however it is believed to be almost ready.
 * https://github.com/WSUCptSCapstone-Fall2022Spring2023/msft-imagedataprocessing/issues/88 - We ran out of time to test this issue. The code is written though.
 * https://github.com/WSUCptSCapstone-Fall2022Spring2023/msft-imagedataprocessing/issues/90 - We started this issue however we ran out of time to validate it.
 * https://github.com/WSUCptSCapstone-Fall2022Spring2023/msft-imagedataprocessing/issues/102 - We did not work on this issue as we did not test this sprint. Next sprint is our testing sprint and this will be address at the start.
 
## Code Files for Review
Please review the following code files, which were actively developed during this sprint, for quality:
 * [EndpointRunner.java](AGICamEndpoint/src/main/java/org/agicam/endpoint/EndpointRunner.java)
 * [ConfigController.java](AGICamEndpoint/src/main/java/org/agicam/endpoint/controllers/ConfigController.java)
 * [Main.java](AGICamUpload/src/main/java/org/agicam/Main.java)
 * [ImageController.java](AGICamEndpoint/src/main/java/org/agicam/endpoint/controllers/ImageController.java)
 * [Point.java](AGICamEndpoint/src/main/java/org/agicam/endpoint/util/Point.java)
 * [MongoDB.java](AGICamEndpoint/src/main/java/org/agicam/endpoint/MongoDB.java)
 * [ImageColl.java](AGICamEndpoint/src/main/java/org/agicam/endpoint/collections/ImageColl.java)
 * [ConfigColl.java](AGICamEndpoint/src/main/java/org/agicam/endpoint/collections/ConfigColl.java)
 * [NDVIProcessor.java](AGICamProcessor/src/main/java/org/agicam/processor/NDVIProcessor.java)
 * [ProcessorRunner.java](AGICamProcessor/src/main/java/org/agicam/processor/ProcessorRunner.java)
 * [Plot.java](AGICamProcessor/src/main/java/org/agicam/processor/util/Plot.java)
 * [ConfigureRunner.java](AGICamConfigure/src/main/java/org/agicam/ConfigureRunner.java)
 * [capture.cpp](AGICamCapture/capture.cpp)
 
## Retrospective Summary
Here's what went well:
  * Planning, Our team did a great job of planning early and getting ahead.
  * Coding, Our team managed to use different technologies we haven't used before effectively to ensure that the coding part of our project was completed efficiently.
  * Teamwork, This sprint our team really bound together and did a good job making sure issues got solved.
 
Here's what we'd like to improve:
   * Documentation, Our product is mainly done now we need to make sure we start documenting how everything works so future contributors can work on this project.
   * Usability, We need to start testing and making sure that our product is usable and flexible to the clients needs.
   * Consistency, We need to make sure we maintain this level of efficiency throughout next sprint.
  
Here are changes we plan to implement in the next sprint:
   * Set a due date for issues internally so that we remain on task.
   * Define the process for each issue we create so that any one can pick up an issue at anytime without the need for a team meeting.
   * Determine what the client needs testing wise so that we can make sure we fufill their desires this sprint.
