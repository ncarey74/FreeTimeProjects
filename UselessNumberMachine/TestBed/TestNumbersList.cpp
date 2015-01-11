#include "TestNumbersList.h"

void numbersListTestUnit()
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

void intTestSet()
{
   NumbersList testObject;
   char ch;

   cout << "\nBEGIN: IntegerNumbersListTestSet\n";

   //Add the numbers to the list w/o crashing
   testObject.addToList(-2);
   testObject.addToList(0);
   testObject.addToList(157);
   testObject.addToList(-2998);

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

void floatTestSet()
{
   NumbersList testObject;
   char ch;

   cout << "\nBEGIN: FractionNumbersListTestSet\n";

   //Add the numbers to the list w/o crashing
   testObject.addToList(-2.1);
   testObject.addToList(0);
   testObject.addToList(157.22);
   testObject.addToList(-2998.456);
   testObject.addToList(0.3);

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

void mixedTestSet()
{
   NumbersList testObject;
   char ch;

   cout << "\nBEGIN: FractionNumbersListTestSet\n";

   //Add the numbers to the list w/o crashing
   testObject.addToList(-2.1);
   testObject.addToList(0);
   testObject.addToList(157.22);
   testObject.addToList(-2998.456);
   testObject.addToList(0.3);
   testObject.addToList(2);
   testObject.addToList(-78);

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

void constructTestSet()
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
