#include "TestUselessMath.h"

void TestUselessMath::testUnit()
{
   double test1;
   char ch;
   test1 = addition(NumbersList());

   if (test1 == 0)
      cout << "Things work\n";
   else
      cout << "Things don't work\n";
   cout << "Press any key to do something else\n";
   cin >> ch;
}