#include "TestNumbersList.h"
#include "UselessMath.h"
#include <iostream>
using namespace std;

void main()
{
   char exitKey;

   cout << "###BEGIN TESTING###\n";
   TestNumbersList::testUnit();
   cout << "###END TESTING###\n";
   cout << "Press any key to exit the test suite.\n";
   cin >> exitKey;
}

