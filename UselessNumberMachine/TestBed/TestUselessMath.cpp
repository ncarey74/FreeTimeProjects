#include "TestUselessMath.h"

void uselessMathTestUnit()
{
   char ch;
   cout << "Execute TestUselessMath Y/N?\n";
   cin >> ch;
   if (ch == 'Y' || ch == 'y')
   {
      cout << "***BEGIN TestUselessMath***\n";
      emptyNumbersList();
      sumTestSet();
      diffTestSet();
      prodTestSet();
      quotTestSet();
      cout << "***END TestUselessMath***\n";
   }
}

//expand this function for all math functions
void emptyNumbersList()
{
   char ch;
   double sumTest, diffTest, prodTest, quotTest;

   cout << "\nBEGIN: EmptyNumbersListTestSet\n";

   sumTest = sum(NumbersList());
   diffTest = difference(NumbersList());
   prodTest = product(NumbersList());
   quotTest = quotient(NumbersList());

   if (sumTest == 0 && diffTest == 0 && prodTest == 0 && quotTest == 0)
      cout << "Things work. BTW, divide by zero!\n";
   else
      cout << "Things don't work\n";


   cout << "\nEND: EmptyNumbersListTestSet\nPress c to continue\n\n";
   cin >> ch;
}

void sumTestSet()
{
   NumbersList list1, list2, list3, list4, list5, list6;
   double positiveIntTest, negativeIntTest, naturalIntTest, positiveDecTest, 
          negativeDecTest, mixedDecTest;
   char ch;

   createPopulatedNumberLists(list1, list2, list3, list4, list5, list6);
   
   cout << "\nBEGIN: SumTestSet\n";

   positiveIntTest = sum(list1); //sum of positive integers
   negativeIntTest = sum(list2); //sum of negative integers
   naturalIntTest = sum(list3);  //sum of positive and negative integers
   positiveDecTest = sum(list4); //sum of positive decimal numbers
   negativeDecTest = sum(list5); //sum of negative decimal numbers
   mixedDecTest = sum(list6);    //sum of positive and negative numbers.

   if (positiveIntTest == 12345)
      cout << "positive integers correctly summed\n";
   else
      cout << "positive integers incorrectly summed\n";

   if (negativeIntTest == -12345)
      cout << "negative integers correctly summed\n";
   else
      cout << "negative integers incorrectly summed\n";

   if (naturalIntTest == -101)
      cout << "positive and negative integers correctly summed\n";
   else
      cout << "positive and negative integers incorrectly summed\n";

   if (positiveDecTest == 0.5432)
      cout << "positive decimals correctly summed\n";
   else
      cout << "positive decimals incorrectly summed\n";

   if (negativeDecTest == -0.5432)
      cout << "negative decimals correctly summed\n";
   else
      cout << "negative decimals incorrectly summed\n";

   if (mixedDecTest == -0.101)
      cout << "positive and negative decimals correctly summed\n";
   else
      cout << "positive and negative decimals incorrectly summed\n";

   cout << "\nEND: SumTestSet\nPress c to continue\n\n";
}

void diffTestSet()
{
   cout << "does nothing yet\n";
}

void prodTestSet()
{
   cout << "does nothing yet\n";
}

void quotTestSet()
{
   cout << "does nothing yet\n";
}
//doesn't want to return the numbers list. memory problem.
NumbersList positiveIntNumbersList()
{
   NumbersList numList;

   numList.addToList(1);
   numList.addToList(11);
   numList.addToList(111);
   numList.addToList(1111);
   numList.addToList(11111);

   return numList;
}

NumbersList negativeIntNumbersList()
{
   NumbersList numList;

   numList.addToList(-1);
   numList.addToList(-11);
   numList.addToList(-111);
   numList.addToList(-1111);
   numList.addToList(-11111);

   return numList;
}

NumbersList naturalNumNumbersList()
{
   NumbersList numList;

   numList.addToList(-1);
   numList.addToList(11);
   numList.addToList(-111);
   numList.addToList(1111);
   numList.addToList(-11111);

   return numList;
}

NumbersList positveDecNumbersList()
{
   NumbersList numList;

   numList.addToList(0.1);
   numList.addToList(0.11);
   numList.addToList(0.111);
   numList.addToList(0.1111);
   numList.addToList(0.11111);

   return numList;
}

NumbersList negativeDecNumbersList()
{
   NumbersList numList;

   numList.addToList(-0.1);
   numList.addToList(-0.11);
   numList.addToList(-0.111);
   numList.addToList(-0.1111);
   numList.addToList(-0.11111);

   return numList;
}

NumbersList mizedDecNumbersList()
{
   NumbersList numList;

   numList.addToList(-0.1);
   numList.addToList(0.11);
   numList.addToList(-0.111);
   numList.addToList(0.1111);
   numList.addToList(-0.11111);

   return numList;
}

void createPopulatedNumberLists(NumbersList& list1, NumbersList& list2,
                                 NumbersList& list3, NumbersList& list4,
                                 NumbersList& list5, NumbersList& list6)
{
   list1 = positiveIntNumbersList();
   list2 = negativeIntNumbersList();
   list3 = naturalNumNumbersList();
   list4 = positveDecNumbersList();
   list5 = negativeDecNumbersList();
   list6 = mizedDecNumbersList();
}