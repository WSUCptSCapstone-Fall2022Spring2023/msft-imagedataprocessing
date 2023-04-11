# AGIcam Image Data Processing

### Agricultural IOT Camera System


![alt_text](Images/microsoft-logo.png "Microsoft Logo")

**Mentor:**

Dr. Sindhuja Sankaran | Washington State University

**Development Team:**

Abhilash Ambati
Zachary Hall
Jordan Muehlbauer



# Table of Contents

**I. Introduction**  
**II. Team Members - Bios and Project Roles**  
**III. Project Requirements Specification**  
	1. Project Stakeholders  
	2. Use Cases  
	3. Functional Requirements  
	4. Non-Functional Requirements  
**IV. Software Design**  
	1. Architecture Design  
		1.1 Overview  
		1.2 Subsystem Decomposition  
	2. Data Design  
	3. API Design  
**V. Test Case Specifications and Results**  
	1. Testing Overview  
	2. Environment Requirements  
	3. Test Results  
**VI. Projects and Tools Used**  
**VII. Description of Final Prototype**  
**VIII. Product Delivery Status**  
**IX. Conclusions and Future Work**  
	1. Limitations and Recommendations  
	2. Future Work  
**X. Acknowledgements**  
**XI. Glossary**  
**XII. References**  
**XIII. Appendix A - Team Information**  
**XIV. Appendix B - Example Testing Strategy Reporting**  
**XV. Appendix C - Project Management**  

# I. Introduction

Originating from Pullman, WA, Washington State University Phenomics lab developed an Raspberry Pi-based IOT camera system known as AGIcam. The goal of this was to allow for remote crop monitoring. While what they developed worked, they wanted to make it better. AgroDevs is a team of three WSU computer science students whose main tasks were improving power consumption on the sensor, improving data transfer from the sensor, and creating a convienent way to store and search information gathered by the sensor.  Through the completion of these tasks, ArgoDevs allowed for AGIcam to provide a better user experience and reduced time spent in the field by farmers/researchers. Our team worked directly with Dr. Sankaran to meet these goals and advance the prosperity of AGIcam.

The broader goal of this project was to allow farmers to remotely monitor their crops and minimize time thier time spent in the field. This was achieved by programing the sensor to be remotely configurable, sending these pictures to a database, and improving power consumption thereby reducing time needed to travel and recharge the sensor. Idealy, this will allow for better crop management and lower cost of crop production.

# II. Team Members - Bios and Project Roles
- Zachary Hall's roll in this project was team lead. The main responsiblity of this was communicatoin between the client and the development team. He is a computer science major at Washington State University. His prior experiences include building a linux file system, a flask based web application, and a Mandelbrot Set picture generator. He has helped throughout the project but mostly in programing the WittyPi module, remote configuration, and monitoring the sensor.

 - Jordan Muehlbauer is a computer science student attending Washington State University. His prior projects and experience include orchastrating large game servers, doing website development and contributing to embedded operating systems. He is well versed in system design, Java, C++ and Python. For this project he has been responsible for creating the database, creating the capture and upload executable and providing support for database interaction to other team members while working on their parts.

- Abhilash Ambati is a computer science student attending Washington State University. His prior project and experiences include web development, sms spam filter and data minning. He is well versed in c++, python and HTML.For this project he has been responsible for convertinf the code from python to c++ and helped capturing multiple images and completed part of Remote Configuration.


# III. Project Requirements Specification

## 1. Project Stakeholders

There are three main groups of stakeholders. Professors and graduate students of the Biological Systems Engineering department at Washington State University, farmers (wheat farmers), and the development team. The following is a breakdown of each stakholder's needs.

* WSU Professors and Graduate Students
	* In the scope of this project, professors and grad students share the same needs. For the sensor, they want it to be easy to set up, accurately capture a picture containing both NoIR and RGB images and report the NDVI of specified areas, be remotely configurable, have a long battery life, and work well in areas of low conection. For the API, they want something that is easy to understand and easy to build a website from. It should also be able to get important data from the database and be able to ajust the remote configuation of the sensor.
* Farmers (mainly wheat farmers)
	* Sensor accurancy and battery life are the most important things for the farmer. They want to be able to observe their crops remotely without spending time in the fields. This includes being able to remotely configure the sensor.
* Development Team
	* The main goal of the development team is meet the goals of the client while also meeting the goals of their capstone project. If the client is happy then we are happy. However, the team will have to manage client needs with what is doable with in the time allotted for the capstone project.

## 2. Use Cases

For the purposes of this project, WSU professors, grad students, farmers all shared the same use cases. Therefore, we combined them into one actor. Below is the project's use case diagram.

![alt_text](Images/Capstone_UseCaseDiagram.jpg "Use Cases")

## 3. Functional Requirements

The following are the functional requirements of each subsystem. In order to extend battery life of the sensor, we minimalized the functional requirements of the sensor by moving the requirements to other subsystems.

### 3.1. Sensor

#### 3.1.1. Allow for Remote Configuration
* In order for the farmer or researcher to spend minimal time in the field, the sensor must be remoltly configurable. This includes both the picture schedule and how many pictures to be taken.
* Priority Level: 1

#### 3.1.2. Take a Picture Containing Both NoIR and RGB Images
* Capturing a picture containing both NoIR and RGB images is the main purpose of the sensor.
* Priority Level: 0

#### 3.1.3. Send Pictures to Database
* The sensor should be able to connect to the database and send pictures taken to the database.
* Priority Level: 0

#### 3.1.4. Take Pictures at Specified Times
* The sensor should take pictures at specified times as seen in the Witty Pi schedule.
* Priority Level: 0

#### 3.1.5. Support Lossess Uploading (Stored Uploads)
* Durring the upload process, if connection is dropped then pictures should remain on the device until upload can continue.
* Priority Level: 1

### 3.2. Database

#### 3.2.1. Store Picture, NDVI, and Configuration Data
* The main pupose of the database is to store the necessary picture and configuration data.
* Priority Level: 0

#### 3.2.2. Give Access to the Data to Other Subsystems
* The database should allow for other subsystems to access the data stored.
* Priority Level: 0

### 3.3. AGICamProcessor

### 3.3.1 Download Images from the database
* AGICamProcessor must be able to download images from the database so that it can process newly stored images.
* Priority Level: 1

### 3.3.2 Upload results to the database
* AGICamProcessor must be able to upload its NDVI results to the database so that the user can view the results
* Priority Level: 1

### 3.3.3 Perform NDVI image analysis on specific quadrilateral plots
* AGICamProcessor must be able to process the NDVI value for specific quarilateral plots on an image.
* Priority Level: 2

### 3.4. API

#### 3.4.1. Configure capture time for specific sensors
* The API must allow the user to configure what time the sensor will capture images throughout the day.
* Priority Level: 0

#### 3.4.2. Query NDVI processed results by time for a sensors
* The API must provide an endpoint for the user to query the processed NDVI values for an image based on the sensor# and range of time to query inbetween.
* Priority Level: 1

#### 3.4.3 Query captured images
* The API must provide an endpoint to query captured images.
* Priority Level: 1

#### 3.4.4 Configure NDVI process plots for specific sensors
* The API must provide an endpoint to allow for the configuration of plots for each image. A plot is a quadrilateral region on an image where image-processing will be done.
* Priority Level: 1

#### 3.4.5 Query configuration for specific sensor
* The API must provide an endpoint for querying the entire configuration on a sensor.
* Priority Level: 1

##  4. Non-Functional Requirements 

### Scaleable (Easy to add new sensors to a system)
Our system should make it easy to add new sensors to a system of sensors. It should be as easy as running AGICamConfigure with the specified database information.

### Economically Extensible (Compare battery life)
Our system should have better battery life or improved battery life from the previous edition of AGICam so that it is cheaper to deploy sensors into the system.

### Maintainable (Well documented)
Our system should be well documented and design choices should be easily explained. This will make the project maintainable for future developers.

### Extendible (Project needs to be in a good state for future development)
Our project should be well documented and in a good position so that future developers can contribute and expand upon it easily.

# IV. Software Design

The following subsections will detail the software design and the reasoning behind it. The software was designed keeping use cases, functional requirements, and non-functional requirements in minde. We hope the following best meets all requirements.

## 1. Architecture Design

### 1.1 Overview

There are four main subsystems within this project: the sensor module, database, remote server, and API. One of the main focuses of this design was to minimize the use of the sensor thus extending the sensor's battery life. This design also allows for minimal time spent by the user in the field. The sensor module uses the cameras to take pictures, the database stores these pictures, the remote server calculates the NDVI for these pictures and returns these values back to the database, and finally the API gets information from the database to present to the user. The following diagram is a generalized representation of how the subsystems interact.

![alt_text](Images/Generalized_Subsystem_Diagram.jpg "Use Cases")

### 1.2 Subsystem Decomposition

The following is a further breakdown of the subsystem structure.

![alt_text](Images/SubsystemDecompositionDesign.jpg)

## 2. Data Design

Our product has two inputs that are collected during a snapshot. NOIR camera data and RGB camera data. AGICamera’s will be installed in an environment and will work within a network to provide overview of the current state of their farm to end-users. This is how our documents in our database will be formatted. It is important to note that the compressed data will be stored in binary form and will need to be decompressed when used on the front-end.

AGICam Snapshot Design: Fields: Camera ID, Snapshot timestamp, Compressed NOIR data, Compressed RGB data Primary key: (Camera ID, Snapshot timestamp)

Internally on the end-user side of things they will be querying our database on ranges of Camera ID and latest snapshot. It will be possible for users to view specific regions of their farm as well as viewing the entirety of snapshots across their farm at once.

This data will be uploaded from our devices to our cloud SQL server where it can be queried by the end user through our interface. Currently our end-clients have their own interface to use this data with but in the case where we supply an end-user interface we will determine what local data structures will be needed to visually process this data.

## 3. API Design


# V. Test Case Specifications and Results

## 1. Testing Overview

## 2. Environment Requirements

Ideally, we would be able to test code on the sensor module down to the scale of unit tests, however, we only have one sensor and we can’t all use it at the same time. To solve this, we will only be doing integration testing on the sensor. Unit testing will be done individually by team members on their own machines.

When a team member pulls into the main branch, they will also pull main into the sensor’s test branch. This branch will then be pulled into the sensor. There, they will preform the required integration testing. They will document the tests done and results of the test in a test report. If there are issues, they will try to resolve them. The code and results will then be pushed back into the test branch which will merged back to main. This will ensure that code within main remains functional on the sensor.

## 3. Tests/Results

### 3.1 Unit Tests/Results

| Test Name | Aspect Tested (Functional) | Expected Result | Observed Result | Test Result | Test Requirements |
| --------- | ------------- | --------------- | --------------- | ----------- | ----------------- |
| Capture Test | 3.1.2. Sensor Take a Picture Containing Both NoIR and RGB Images | The correct number of images are in the specified folder, and each image contains RGB (on left) and NoIR (on right) pictures | The specified folder contained the correct number of images and the images where correct | PASS | (See 3.1.1. Capture Test Requirements/Steps) |
| Lossless Uploading Test | 3.1.3. Send Pictures to Database, and 3.1.5. Support Lossess Uploading (Stored Uploads) | After first run of AGICamUpload, picture should still be in test directory. After second run, it should be in the database. | After first run the picture remained in the test folder, after second run it was not there but was in the database | PASS | (See 3.1.2. Lossless Uploading Test Requirements/Steps) |

#### 3.1.1. Capture Test Requirements/Steps
1. Follow AGICamCapture build steps.
2. Follow AGICamInit build steps.
3. Use the command line to run AGICamCapture.exe with desired output and number of pictures arguments. For example: /home/pi/Desktop/msft-imagedataprocessing/AGICamCapture/capture /home/pi/AGICamImages 5
4. Go to output location (for example /home/pi/AGICamImages) and view pictures making sure the number of pictures in correct and the pictures themselves contain both RGB and NoIR images.
5. Manually delete pictures to return folder to inital state. 

#### 3.1.2. Lossless Uploading Test Requirements/Steps
1. Follow AGICamUpload build steps.
2. Create a directory named test.
3. Place an image named "data-1.png" into the test directory.
4. Switch off (or unplug) the sensor's internet connection.
5. Run AGICamUpload with the following command java -jar /home/pi/Desktop/msft-imagedataprocessing/AGICamUpload/build/libs/AGICamUpload-1.0-SNAPSHOT.jar [location of test directory] 'mongodb+srv://<username>:<password>@agicam-store.dsxer1a.mongodb.net/?retryWrites=true&w=majority' agicam QCZgJ97ledg5cXbf .
6. See that picture is still in test dicrectory.
7. Switch on (or plug in) the sensor's internet connection.
8. Re-run the command in step 5.
9. Observe that the test directory is empty and the image is in database.

### 3.2 Integration Tests/Results

| Test Name | Aspect Tested (Functional) | Expected Result | Observed Result | Test Result | Test Requirements |
| --------- | ------------- | --------------- | --------------- | ----------- | ----------------- | 
| Capture and Upload at Specified Times Test | 3.1.4 Take Pictures at Specified Times, and 3.2.2. Give Access to the Data to Other Subsystems | There will be pictures stored in the database that correspond to the desired capture times | Pictures were not in the database. | FAIL | (See 3.2.1 Cature and Upload at Specified Times Test Requirements/Steps) |
| Configure capture time for specific sensors | 3.4.1 Configure capture time for specific sensors | {result success}, {result success} | N/A | PASS | Follow steps |
| Query NDVI processed results by time for a sensors | 3.4.2 Query NDVI processed results by time for a sensors| Map of times to double | N/A | PASS | Follow steps |
| Query captured images | 3.4.3 Query captured images | Image from the database captured by a sensor | N/A | PASS | Follow steps |
| Configure NDVI process plots for specific sensors | 3.4.4 Configure NDVI process plots for specific sensors | Plot with two quadrilateral's (0,10), (10,0), (10,10) , (0,0) and (5,5), (5,10), (10,5), (10,10) | N/A | PASS | Follow steps |
| Query configuration for specific sensor | 3.4.5 Query configuration for specific sensor | Configuration JSON which contains time & plots for a specific sensor | N/A | PASS | Follow steps |
| Confirm functionality of AGICamProcessor | 3.3.1, 3.3.2, 3.3.3. All AGICamProcessor requirements | No results, Map of Time->NDVI values | N/A | PASS | Follow steps |

#### 3.2.1 Cature and Upload at Specified Times Test Requirements/Steps
1. ** Follow the appropriate build steps **
2. Set Witty Pi to run desired off/on schedule.
3. Make sure AGICamCapture.exe is being run in either afterStartup.sh or beforeShutdown.sh. For example: /home/pi/Desktop/msft-imagedataprocessing/AGICamCapture/capture /home/pi/AGICamImages 5
4. Make sure AGICamUpload is being run after AGICamCapture.exe. For example: java -jar /home/pi/Desktop/msft-imagedataprocessing/AGICamUpload/build/libs/AGICamUpload-1.0-SNAPSHOT.jar /home/pi/AGICamImages 'mongodb+srv://<username>:<password>@agicam-store.dsxer1a.mongodb.net/?retryWrites=true&w=majority' agicam QCZgJ97ledg5cXbf
5. Wait until the last specified time in the WittyPi schedule passes.
6. Log into MongoDB and obsver in the pictures exist with the corisponding

#### 3.2.2. Configure capture time for specific sensors
1. Pick a camera # which has been initialized on our system
2. Build AGICamEndpoint
3. Run this application
5. curl -X POST -d "camNum=3&time=890" http://localhost:4567/time-add
6. Expect to see result success
7. curl -X POST -d "camNum=3&time=890" http://localhost:4567/time-del
8. Expect to see result success

#### 3.2.3. Query NDVI processed results by time for a sensors
1. Build AGICamEndpoint
2. Pick a camera that has been initialized on our system and has been running for a period of time
3. Pick two dates within the range that it has been running for
4. Open browser and go to the following URL
5. http://localhost:4567/ndvi?camNum=NUM&startDate=X&endDate=Y
6. View data.

#### 3.2.4 Query captured images
1. Follow the QUERY NDVI test and pick out a specific time from the map queried
2. Open browser and go to the following URL
3. http://localhost:4567/image-get?camNum=X&time=Y
4. View Image

#### 3.2.5 Configure NDVI process plots for specific sensors
1. Build AGICamEndpoint
2. Pick a camera that has been initialized on our system
3. curl -X POST -d "camNum=X&p1=10,0&p2=0,10&p3=10,10&p4=0,0" http://localhost:4567/plot-add
4. curl -X POST -d "camNum=Y&p1=5,5&p2=10,5&p3=5,5&p4=10,10" http://localhost:4567/plot-add
5. Open browser and go to
6. http://localhost:4567/config?camNum=X - This will show you their plots

#### 3.2.6 Query configuration for specific sensor
1. Build AGICamEndpoint
2. Pick a camera which has been initialized within the system
3. Open browser
4. Go to this url
5. http://localhost:4567/config?camNum=X - This will show you their config.
	
#### 3.2.7 Confirm functionality of AGICamProcessor
1. Build AGICamCapture, AGiCamUpload, AGICamEndpoint, and AGICamProcessor
2. Run capture, then upload the images to the database
3. Start AGICamEndpoint to access the API
4. Query the previous day on the sensor which you ran capture for and expect to see no results
5. Run AGICamProcessor
6. Query the previous day on the sensor and now expect to see results of time->ndvi value.

### 3.3 System Tests/Results

| Test Name | Aspect Tested (Functional) | Expected Result | Observed Result | Test Result | Test Requirements |
| --------- | ------------- | --------------- | --------------- | ----------- | ----------------- |
| Remote Configuration of WittyPi Test | 3.1.1. Allow for Remote Configuration, 3.2.2. Give Access to the Data to Other Subsystems, and 3.4.1. Allow Users to Change Configuration Of a Cammera | The .wpi file inputed into sensor configuration call should resemble what the user desires. | The previously mentioned file matched what we expected | PASS | (See 3.3.1. Remote Configuration of WittyPi Test Requirements/Steps) | 

#### 3.3.1. Remote Configuration of WittyPi Test Requirements/Steps
1. **Follow the appropriate build steps **
2. Use the following API call to change the desired cammera's configuration: 
3. On the sensor, run the configuration file with desired inputs. For example: java -jar /home/pi/Desktop/msft-imagedataprocessing/AGICamCongifure/build/libs/AGICamConfigure-1.0-SNAPSHOT.jar /home/pi/wittypi/schedules/Capture_schedule.wpi 'mongodb+srv://agicam:QCZgJ97ledg5cXbf@agicam-store.dsxeria.mongodb.net/?retryWrites=true&w=majority'
4. Check to see if file at the given location was changed
	
### 3.4 Non-Functional Test/Results 
| Test Name | Aspect Tested (Non-Functional) | Expected Result | Observed Result | Test Result | Test Requirements |
| --------- | ------------- | --------------- | --------------- | ----------- | ----------------- |
| Scalibility Test | Scalibility (Non-Functional) | Documented AGICamConfigure code directory(How to build and requirements), Provided release JAR for AGICamConfigure. | Nothing | FAIL | Follow Steps |
| Battery Life Test | Economically Extensible (Non-Functional) | Current sensor should be within +/- 10% lifespan as the previous device. | N/A | N/A | Follow Steps |
| Maintainability Test | Maintainable (Non-Functional) | All components are well documented and provide either release executables or build files. | N/A | FAIL | Follow Steps |
| Future development test | Extendible (Non-Functional) | Report with short-comings and design choices, documentation on suggestions for future work | N/A | FAIL | Follow Steps |
	
#### 3.4.1 Scalability Test
1. View AGICamConfigure readme file
2. Confirm there is a release available through GitHub
	
#### 3.4.2 Battery Life Test
1. Configure two sensors, one with previous system (Written By Worasit), one with our current system.
2. Fully charge both sensors and remove power supply
3. Enable both sensors and recored the life-span of each sensor.
4. Compare results
	
#### 3.4.3 Maintainability test
1. Visit all component directories within Git Repository
2. Confirm there is an accurate README file
3. Confirm there is a release JAR or build file provided
4. Record results.
	
#### 3.4.4 Future development test
1. View Report of short-comings and design choices
2. View Document with suggestions for future work
3. Confirm both are adequate.
	
# VI. Projects and Tools Used

# VII. Description of Final Prototype
![alt_text](Images/AGICamDiagram.png)
	
## Sensor Components
	
### AGICamConfigure

### AGICamCapture

### AGICamUpload

### WittyPI

## Remote Server Components
	
### AGICamProcessor
	
### AGICamEndpoint
	
### MongoDB
	
	
# VIII. Product Delivery Status

# IX. Conclusions and Future Work

## 1. Limitations and Recommendations
There are two factors that limit the workflow. The first is with the camera modules that are being used. We are unsure of how long the camera modules will run. These camera modules are set up to take pictures in a timely manner if they do not last as long as we expect them then we will run into issues that would delay the process and increase the overall time for the process. 

The second issue we are facing is with the processing of NDVI on images taken. The main issue is that we cannot hardcode the location of the wheat fields and they have to be manually added for every image, which is increasing the time for processing each image. The reason we cannot hard code is that the camera’s positions may change and due to this we cannot predict where the wheat fields are going to be in the image when the camera is adjusted.

A few recommendations we have are to fix the camera in a specific location and connect it to an electric port so they do not have to be moved. By doing this  We can locate exactly where the wheat plants are growing and get NDVI data for these sites by placing the cameras in one spot. This will increase the flow of the system and reduce the time for processing the images. 


## 2. Future Work
We plan on updating the sensors by adding temperature and humidity sensors. This will help in understanding the environment that influences plant growth. To make it easier for the user a user-friendly interface will also be developed, by doing this users will have better access to the data that is being processed. The results will also get better in the future since we plan on upgrading the color filter that is being used in the NDVI calculation. These upgrades will help improve plant growth by making the system faster and more accurate.

# X. Acknowledgements

# XI. Glossary

**C**

Compiled language - Programming language where the source code is directly converted into machine code for the 
processor to execute.<sup>[11]</sup>

**E**

Edge computing – Handling some computation and processing on the client to avoid unnecessary data transfers [4].

**I**

Interpreted language - Programming language where the source code is not directly translated by the target machine. 
The interpreted reads the code and executes it on the target device<sup>[11]</sup>
	
**L**

Local network - Collection of devices connected together in one physical location.<sup>[13]</sup>

**N**

Normalized difference vegetation index (NDVI) – Calculated from near-infrared and visible light reflected by vegetation. 
Healthy vegetation absorbs most of the visible light and reflects most near-infrared light. Unhealthy vegetation does the opposite [7].

NoIR image/camera – A camera without an infrared filter allows for images to be produced in a dark environment [6].
	
**M**

Microprocessor - A miniature electronic device that contains the arithmetic, logic, and control circuitry necessary to 
perform the functions of a digital computers central processing unit<sup>[10]</sup>

Micro controller - Chip optimized to control electronic devices. It is used in a single integrated circuit which is 
dedicated to performing a particular task and executing one specific application<sup>[10]</sup>

**O**

OpenCV – An open-source machine learning software library [8].

**P**

Plant phenotyping – The analysis of plant life based on physical/easily observed traits [1].

Picamera – Python library that provides a pure Python interface to the Raspberry Pi camera module [5]. 

**R**

RGB image/camera – Visible light spectrum image. An RGB camera produces these images.
	
**T**

TV white space - unused TV channels between the active ones in the VHF and UHF spectrum.<sup>[12]</sup>

**V**

Vegetation index (VI) – A calculated single value that is used to show the amount of plant material in an image [7].

# XII. References


[1]	C. Costa, U. Schurr, F. Loreto, P. Menesatti, and S. Carpentier, “Plant Phenotyping Research Trends, a Science Mapping Approach,” _Front. Plant Sci._, [Online]. Available: https://www.frontiersin.org/articles/10.3389/fpls.2018.01933/full#:~:text=A%20more%20recent%20and%20comprehensive,complex%20trait%20assessment%20(Li%20et


[2]	B. J. Stucky, R. Guralnick, J. Deck, E. G. Denny, K. Bolmgren, and R. Walls, “The Plant Phenology Ontology: A New Informatics Resource for Large-Scale Integration of Plant Phenology Data,” _Front. Plant Sci._, [Online]. Available: https://www.frontiersin.org/articles/10.3389/fpls.2018.00517/full


[3]	W. Sangjan, A. H. Carter, M. O. Pumphrey, V. Jitov, and S. Sankaran, “Development of a Raspberry Pi-Based Sensor System for Automated In-Field Monitoring to Support Crop Breeding Programs,” _Invent._, vol. 6, no. 2, pp. 42, Jun., 2021. [Online]. Available: https://www.mdpi.com/2411-5134/6/2/42/htm.


[4]	S. J. Bigelow. “What is Edge Computing? Everything You Need to Know.”  https://www.techtarget.com/searchdatacenter/definition/edge-computing (accessed Sept. 21, 2022).


[5]	“Picamera.” https://picamera.readthedocs.io/en/release-1.13/ (accessed Sept. 21, 2022).


[6]	“Raspberry Pi Camera Module 2 NoIR.” https://www.raspberrypi.com/products/pi-noir-camera-v2/ (accessed Sept. 21, 2022).


[7]	“Measuring Vegetation (NDVI & EVI): Normalized Difference Vegetation Index (NDVI).” https://earthobservatory.nasa.gov/features/MeasuringVegetation/measuring_vegetation_2.php (accessed Sept. 21, 2022). \



[8]	“OpenCV: About.” https://opencv.org/about/ (accessed Sept. 21, 2022).


<sup>[9]	</sup>D. Cassel, “Which programming languages use the least electricity?,” _The New Stack_,  20-May-2018. [Online]. Available:    https://thenewstack.io/which-programming-languages-use-the-least-electricity/. [Accessed: 28-Sep-2022]. 


<sup>[10]	</sup>L. Williams, “Difference between microprocessor and Microcontroller,” _Guru99_, 29-Aug-2022. [Online]. Available: https://www.guru99.com/difference-between-microprocessor-and-microcontroller.html#:~:text=Microprocessor%20vs%20Microcontroller%3A%20Key%20Difference,used%20in%20an%20embedded%20system. [Accessed: 28-Sep-2022]. 


<sup>[11]	</sup>“Interpreted vs compiled programming languages: What's the difference?,” _freeCodeCamp.org_, 28-Apr-2021. [Online]. Available: https://www.freecodecamp.org/news/compiled-versus-interpreted-languages/#:~:text=In%20an%20interpreted%20language%2C%20the,reads%20and%20executes%20the%20code. [Accessed: 28-Sep-2022]. 


<sup>[12]	</sup>“TV white space fact page,” Carlson Wireless Technologies, 11-Jun-2015. [Online]. Available: https://carlsonwireless.com/tv-white-space/. [Accessed: 28-Sep-2022]. 


<sup>[13]</sup>	“What is a lan? Local Area Network,” _Cisco_, 16-Sep-2022. [Online]. Available: https://www.cisco.com/c/en/us/products/switches/what-is-a-lan-local-area-network.html. [Accessed: 28-Sep-2022]. 
	

# XIII. Appendix A - Team Information 

# XIV. Appendix B - Example Testing Strategy Reporting

# XV. Appendix C - Project Management
