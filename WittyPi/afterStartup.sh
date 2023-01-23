#!/bin/bash
# file: afterStartup.sh
#
# This script will be executed in background after Witty Pi 3 gets initialized.
# If you want to run your commands after boot, you can place them here.
#

NOW=`date +"%D %T"`

FILE=/home/pi/Desktop/msft-imagedataprocessing/WittyPi/WittyPi_log.txt

#Capture picture
/home/pi/Desktop/msft-imagedataprocessing/AGICamCapture/capture /home/pi/AGICamImages 1 5 &

#Wait until pictures are taken
wait

#Upload to database
java -jar /home/pi/Desktop/msft-imagedataprocessing/AGICamUpload/build/libs/AGICamUpload-1.0-SNAPSHOT.jar /home/pi/AGICamImages 'mongodb+srv://<username>:<password>@agicam-store.dsxer1a.mongodb.net/?retryWrites=true&w=majority' agicam QCZgJ97ledg5cXbf

#Wait until upload is done are taken
wait

printf "System powered off at %s\n" "$NOW" >> "$FILE"