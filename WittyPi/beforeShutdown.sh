#!/bin/bash
# file: beforeShutdown.sh
#
# This script will be executed after Witty Pi 3 receives shutdown command (GPIO-4 gets pulled down).
# If you want to run your commands before turnning of your Raspberry Pi, you can place them here.
# Raspberry Pi will not shutdown until all commands here are executed.
#

#Cofigure Sensor
java -jar /home/pi/Desktop/msft-imagedataprocessing/AGICamConfigure/build/libs/AGICamConfigure-1.0-SNAPSHOT.jar /home/pi/wittypi/schedules/Capture_Schedule.wpi 'mongodb+srv://agicam:QCZgJ97ledg5cXbf@agicam-store.dsxer1a.mongodb.net/?retryWrites=true&w=majority'

wait

#Capture picture
/home/pi/Desktop/msft-imagedataprocessing/AGICamCapture/capture /home/pi/AGICamImages 5

wait

#Upload to database
java -jar /home/pi/Desktop/msft-imagedataprocessing/AGICamUpload/build/libs/AGICamUpload-1.0-SNAPSHOT.jar /home/pi/AGICamImages 'mongodb+srv://<username>:<password>@agicam-store.dsxer1a.mongodb.net/?retryWrites=true&w=majority' agicam QCZgJ97ledg5cXbf

wait