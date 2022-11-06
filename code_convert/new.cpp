#include <time.h>
#include <iostream>
#include <cstdlib>
#include <ctime>
#include <fstream>
#include <iostream>
#include <raspicam/raspicam.h>
#include <unistd.h>


using namespace std; 


int total_photos = 5;
int count_down = 15;

int cam_width = 1280;
int cam_height = 624;

//Final image capture settings
int scale_ratio = 2;

// Camera resolution height must be dividable by 16, and width by 32
int cam_width = int((cam_width+31)/32)*32;
int cam_height = int((cam_height+15)/16)*16;

// Buffer for captured image settings
int img_width = int (cam_width * scale_ratio);
int img_height = int (cam_height * scale_ratio);

//capture = np.zeros((img_height, img_width, 4), dtype=np.uint8)

raspicam::RaspiCam Camera; //Camera object


