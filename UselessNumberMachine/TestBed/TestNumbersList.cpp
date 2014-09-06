#include "TestNumbersList.h"
#include <cassert>
#include <iostream>
using namespace std;

void TestNumbersList::testUnit()
{
   intTestSet();
   floatTestSet();
   mixedTestSet();
}

void TestNumbersList::intTestSet()
{
   NumbersList testObject;
   int num1, num2, num3, num4, num5, num6, num7;
   char ch;

   num1 = -2;
   num2 = 0;
   num3 = 157;
   num4 = -2998;
   
   cout << "\nBEGIN: IntegerNumbersListTestSet\n";

   //Add the numbers to the list w/o crashing
   testObject.addToList(num1);
   testObject.addToList(num2);
   testObject.addToList(num3);
   testObject.addToList(num4);



#ifdef DEBUG_FUNC
   testObject.print();
#endif

   cout << "END: IntegerNumbersListTestSet\nPress c to continue\n\n";
   cin >> ch;
}

void TestNumbersList::floatTestSet()
{
   NumbersList testObject;
   float num1, num2, num3, num4, num5, num6, num7;
   char ch;

   num1 = -2.1;
   num2 = 0;
   num3 = 157.22;
   num4 = -2998.456;
   num5 = 0.3;
   
   cout << "\nBEGIN: FractionNumbersListTestSet\n";

   //Add the numbers to the list w/o crashing
   testObject.addToList(num1);
   testObject.addToList(num2);
   testObject.addToList(num3);
   testObject.addToList(num4);
   testObject.addToList(num5);


#ifdef DEBUG_FUNC
   testObject.print();
#endif

   cout << "END: FractionNumbersListTestSet\nPress c to continue\n";
   cin >> ch;
}

void TestNumbersList::mixedTestSet()
{
   NumbersList testObject;
   float num1, num2, num3, num4, num5;
   int num6, num7;
   char ch;

   num1 = -2.1;
   num2 = 0;
   num3 = 157.22;
   num4 = -2998.456;
   num5 = 0.3;
   num6 = 2;
   num7 = -78;
   
   cout << "\nBEGIN: FractionNumbersListTestSet\n";

   //Add the numbers to the list w/o crashing
   testObject.addToList(num1);
   testObject.addToList(num2);
   testObject.addToList(num3);
   testObject.addToList(num4);
   testObject.addToList(num5);
   testObject.addToList(num6);
   testObject.addToList(num7);


#ifdef DEBUG_FUNC
   testObject.print();
#endif

   cout << "END: FractionNumbersListTestSet\nPress c to continue\n";
   cin >> ch;
}