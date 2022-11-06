#include <iostream>
#include <fstream>
#include <ctime>
using namespace std;


int main()
{
    ofstream logStream;
    time_t startTime = time(0);
    char* startTimeStr = ctime(&startTime);


    logStream.open("log.txt");
    logStream << "Program started at: " << startTimeStr << endl;


    logStream.close();
    return 0;
}