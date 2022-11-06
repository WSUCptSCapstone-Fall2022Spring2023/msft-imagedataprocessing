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


int takePicture()
{
    raspicam::RaspiCam Camera; //Camera object

    //ofstream picFileStream;

    //picFileStream.open("Test_picture");

    if ( !Camera.open()) 
    {
        cerr<<"Error opening camera"<<endl;return -1;
    }

    sleep(3);

    Camera.grab();
    //allocate memory
    unsigned char *data=new unsigned char[  Camera.getImageTypeSize ( raspicam::RASPICAM_FORMAT_RGB )];

    Camera.retrieve ( data,raspicam::RASPICAM_FORMAT_RGB );

    ofstream outFile ( "raspicam_image.ppm",std::ios::binary );

    outFile<<"P6\n"<<Camera.getWidth() <<" "<<Camera.getHeight() <<" 255\n";

    //picFileStream.close();

    outFile.write ( ( char* ) data, Camera.getImageTypeSize ( raspicam::RASPICAM_FORMAT_RGB ) );

    delete data;

    return 0;
}
