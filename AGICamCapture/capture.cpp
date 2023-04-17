#include <iostream>
#include <fstream>
#include <sstream>
#include <chrono>
#include <ctime>
#include <thread>
#include <string>
using namespace std;
std::string currentDateTime();

/**
 * Return the current date and hour for output image name.
 * @return string with date and hour
 */
std::string currentDateTime() {
    time_t     now = time(nullptr);
    struct tm  time_struct;
    char       buf[80];
    time_struct = *localtime(&now);
    strftime(buf, sizeof(buf), "%Y-%m-%d-%H-%M", &time_struct);
    return buf;
}

/**
 *
 * Parameters to pass in
 * Arg[0] = path to output
 * Arg[1] = Amount of pictures to take Camera #
 * Arg[2] =
 *
 * @param argc count of arguments
 * @param args args to pass into executable
 * @return 0 on success
 */
int main(int argc, char**args) {

    ifstream file;
    // Make sure we have enough arguments
    if(argc < 2)
    {
        std::cout << "Please provide the following arguments [path to output] [amount of stills]" << std::endl;
        return 1;
    }

    //Get cam number
    file.open("./cameraNumber.txt", ifstream::in);

    if(!file.is_open())
    {
        cout << "Camera text file not found!";
        return 1;
    }

    // Collect argument strings
    char* directory = args[1];
    char* amount_pics_str = args[2];
    int camera_number;

    file >> camera_number;

    file.close();

    // Convert arguments to their proper form
    // int camera_number = atoi(camera_number_str);
    int amount_pics = atoi(amount_pics_str);

    // Take 5 pictures
    for (int i = 0; i < amount_pics; ++i) {
        std::ostringstream os;
        os << "raspistill -vf -hf -o " // Handle output location
        << directory << "/"
        << camera_number << "-"
        << currentDateTime() << "-"
        << i
        << ".png"
        << " -w 1280 -h 624 -3d sbs"; // Handle image settings
        std::cout << os.str() << std::endl;
        std::system(os.str().c_str());
        this_thread::sleep_for(chrono::milliseconds(7000));
    }
    return 0;
}
