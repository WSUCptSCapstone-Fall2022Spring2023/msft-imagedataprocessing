import os
from pathlib import Path
import time
from datetime import datetime
import cv2 
import numpy as np
from statistics import mean
import json


t2 = datetime.now()
# Create a list of data in directory
in_path = '/media/pi/IOT4/image'
in_data = [img for img in os.listdir(in_path) if img.startswith(t2.strftime("%d-%m-%Y_%H"))]

# Image size
i_w = 1280
i_h = 1248

rep = []
replicate = ['rep1', 'rep2', 'rep3', 'rep4', 'rep5']

print('start')
# Loop to extract the vi
for file in in_data:
    img = cv2.imread(os.path.join(in_path, file)) # Read the image 
    img = img[100:900, i_w:2496] # Resize the image
    
    # Solit the color band
    b, g, r = cv2.split(img)
    # Calculate the ndvi
    ndvi = ((1.664*(b.astype(float))) / (0.953*(r.astype(float)))) - 1
    # Create black image for masking
    blank = np.zeros(ndvi.shape[:2], dtype='uint8')
    
    # Mask location on the image
    plot = [[(210, 165), (340, 165), (280, 235), (75, 235)], # !!! change
            [(435, 165), (605, 165), (595, 235), (400, 235)], # !!! change
            [(675, 170), (820, 170), (890, 250), (695, 250)], # !!! change
            [(915, 185), (1035, 185), (1140, 260), (990,260)]] # !!! change
    var = ['vr4_2', 'vr5_2', 'vr8_2', 'vr7_2']
    nd = []
 
    for pl in plot:
        # Mask the plot in left side
        pl_m = cv2.fillPoly(blank, np.array([pl]), 255)
        m = cv2.bitwise_and(ndvi, ndvi, mask=pl_m)
        m[m <= 0] = np.nan # Replace zero value to nan
        mean_m = round(np.nanmean(m), 5)
        median_m = round(np.nanmedian(m), 5)
        std_m = round(np.nanstd(m), 5)
        max_m = round(np.nanmax(m), 5)
        p95_m = round(np.nanpercentile(m, 95), 5)
        p90_m = round(np.nanpercentile(m, 90), 5)
        p85_m = round(np.nanpercentile(m, 85), 5)
        timestamp = t2.strftime("%d-%m-%Y_%H-%M-%S")
        # Make dictionary for ndvi of one plot
        data = [timestamp, mean_m, median_m, std_m, max_m, p95_m, p90_m, p85_m]
        header = ['timestamp', 'mean', 'median', 'std', 'max', 'p95', 'p90', 'p85']
        dict_plot = {i:j for i, j in zip(header, data)}
        # Combine ndvi data of all plots in lllllist datatype
        nd.append(dict_plot)
    # Match each ndvi dict with their plot's name
    ndvi_data = dict(zip(var, nd))
    # Combine ndvi of all images
    rep.append(ndvi_data)
# Match each ndvi dict with their plot's name
final_data = dict(zip(replicate, rep))
json_file = open('/home/pi/MScamera/ndvi.json', 'w')
json.dump(final_data, json_file, indent=6)
json_file.close()
print('finish')        

        





















