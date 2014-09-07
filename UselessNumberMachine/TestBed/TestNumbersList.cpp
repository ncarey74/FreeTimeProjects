#include "TestNumbersList.h"

void TestNumbersList::testUnit()
{
   char ch;
   cout << "Execute TestNumbersList Y/N?\n";
   cin >> ch;
   if (ch == 'Y' || ch == 'y')
   {
      cout << "***BEGIN TestNumbersList***\n"; 
      intTestSet();
      floatTestSet();
      mixedTestSet();
      constructTestSet();
      cout << "***END TestNumbersList***\n"; 
   }
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

   if (testObject.getListSize() == 3)
      cout << "getListSize() passes\n";
   else
      cout << "getListSize() fails\n";

   for (int i = 0; i < testObject.getListSize(); i++)
      cout << testObject.elementAt(i) << " ";
   cout << "\n";

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

   if (testObject.getListSize() == 4)
      cout << "getListSize() passes\n";
   else
      cout << "getListSize() fails\n";

   for (int i = 0; i < testObject.getListSize(); i++)
      cout << testObject.elementAt(i) << " ";
   cout << "\n";

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

   if (testObject.getListSize() == 6)
      cout << "getListSize() passes\n";
   else
      cout << "getListSize() fails\n";

   for (int i = 0; i < testObject.getListSize(); i++)
      cout << testObject.elementAt(i) << " ";
   cout << "\n";

   cout << "END: FractionNumbersListTestSet\nPress c to continue\n";
   cin >> ch;
}

void TestNumbersList::constructTestSet()
{
   NumbersList testObject;
   NumbersList *pointer = new NumbersList();

   cout << "\nBEGIN: ConstructNumbersListTestSet\n";
#ifdef DEBUG_FUNC
   testObject.print();
   pointer->print();
#endif

   delete pointer;
   pointer = new NumbersList(15);

#ifdef DEBUG_FUNC
   pointer->print();
#endif

   cout << "\nEND: ConstructNumbersListTestSet\n";
}
