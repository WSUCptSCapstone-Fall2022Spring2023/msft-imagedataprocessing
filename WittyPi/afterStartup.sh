#!/bin/bash
# file: afterStartup.sh
#
# This script will be executed in background after Witty Pi 3 gets initialized.
# If you want to run your commands after boot, you can place them here.
#

NOW=`date +"%D %T"`

FILE=/home/pi/Desktop/msft-imagedataprocessing/WittyPi/WittyPi_log.txt



printf "System powered on at %s\n" "$NOW" >> "$FILE"