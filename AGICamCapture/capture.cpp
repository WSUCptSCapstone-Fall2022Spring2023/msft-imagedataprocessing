#include <iostream>
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
    strftime(buf, sizeof(buf), "%Y-%m-%d-%H", &time_struct);
    return buf;
}

/**
 *
 * Parameters to pass in
 * Arg[0] = path to output
 * Arg[1] = Camera #
 * Arg[2] = Amount of pictures to take
 *
 * @param argc count of arguments
 * @param args args to pass into executable
 * @return 0 on success
 */
int main(int argc, char**args) {

    // Make sure we have enough arguments
    if(argc < 3)
    {
        std::cout << "Please provide the following arguments [path to output] [camera #] [amount of stills]" << std::endl;
        return 1;
    }

    // Collect argument strings
    char* directory = args[1];
    char* camera_number_str = args[2];
    char* amount_pics_str = args[3];

    // Convert arguments to their proper form
    int camera_number = atoi(camera_number_str);
    int amount_pics = atoi(amount_pics_str);

    // Take 5 pictures
    for (int i = 0; i < amount_pics; ++i) {
        std::ostringstream os;
        os << "raspistill -o " // Handle output location
        << directory << "/"
        << camera_number << "-"
        << currentDateTime() << "-"
        << i
        << ".png"
        << " -w 1280 -h 624 -3d sbs"; // Handle image settins
        std::cout << os.str() << std::endl;
        std::system(os.str().c_str());
        this_thread::sleep_for(chrono::milliseconds(7000));
    }
    return 0;
}
