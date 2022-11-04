import os
from pathlib import Path
import time
from datetime import datetime
import cv2 
import numpy as np
from statistics import mean

# Initial image size
img_width = 1280
img_height = 1248

# Improt image
img = cv2.imread('/media/pi/IOT4/image/06-07-2022_13-30-41_3.png')
img = img[100:900, img_width:2496] # Crop the image

# Find plot coordination
# Right click showing the coordination
# Left click showing the pixel's value

# Function to display the coordinates of the points clicked on the image
def click_event(event, x, y, flags, params):
    # checking for left mouse clicks 
    if event == cv2.EVENT_LBUTTONDOWN:
        print(x, ' ', y)
        font = cv2.FONT_HERSHEY_SIMPLEX
        cv2.putText(img, str(x) + ',' + str(y), (x,y), font, 1, (255, 0, 0), 2)
        cv2.imshow('img', img) 

# displaying the image 
cv2.imshow('img', img) 

# setting mouse hadler for the image and calling the click_event() function 
cv2.setMouseCallback('img', click_event) 

cv2.waitKey(0) 
cv2.destroyAllWindows()

# Display the image with plot (with color map)
# Initial values
black = (0, 0, 0) # Plot line color
# Plot 1 and 2 coordination
#pl1 = [(235, 275), (375, 275), (300, 360), (105, 360)]
#pl2 = [(455, 275), (600, 275), (600, 360), (400, 360)]
#pl3 = [(700, 285), (820, 285), (880, 380), (710, 380)]
#pl4 = [(910, 285), (1030, 285), (1140, 380), (985, 380)]

#pl1 = [(225, 245), (355, 255), (280, 350), (80, 340)]
#pl2 = [(450, 245), (600, 250), (590, 350), (385, 340)]
#pl3 = [(690, 260), (845, 260), (900, 345), (705, 340)]
#pl4 = [(900, 255), (1055, 250), (1175, 345), (985, 350)]

#pl1 = [(215, 175), (335, 175), (275, 255), (100, 250)]
#pl2 = [(435, 175), (590, 175), (595, 265), (405, 260)]
#pl3 = [(680, 185), (815, 185), (875, 275), (680, 275)]
#pl4 = [(925, 195), (1070, 195), (1175, 275), (1005, 275)]

pl1 = [(210, 165), (340, 165), (280, 235), (75, 235)]
pl2 = [(435, 165), (605, 165), (595, 235), (400, 235)]
pl3 = [(675, 170), (820, 170), (890, 250), (695, 250)]
pl4 = [(915, 185), (1035, 185), (1140, 260), (990, 260)]

# Display the image with the plot line
cv2.polylines(img, np.array([pl1]), True, black, 3)
cv2.polylines(img, np.array([pl2]), True, black, 3)
cv2.polylines(img, np.array([pl3]), True, black, 3)
cv2.polylines(img, np.array([pl4]), True, black, 3)
cv2.imshow('Plot', img)

cv2.waitKey(0)
cv2.destroyAllWindows()
        