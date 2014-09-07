#define _CRTDBG_MAP_ALLOC
#include <stdlib.h>
#include <crtdbg.h>

#include "TestNumbersList.h"
#include "TestUselessMath.h"
#include <iostream>
using std::cout;
using std::cin;

void main()
{
   char exitKey;

   cout << "###BEGIN TESTING###\n\n";
   TestNumbersList::testUnit();
   TestUselessMath::testUnit();
   cout << "\n###END TESTING###\n";
   cout << "Press any key to exit the test suite.\n";
   cin >> exitKey;
}