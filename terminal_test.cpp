#include <stdio.h>
#include <stdlib.h>
#include <bits/stdc++.h>
using namespace std;

int main(){


    const char *command = raspistill -o test.jpg -w 1280 -h 624 -3d sbs;
    system(command);

    cout << "\nRunning file ";

    return 0;
    

}