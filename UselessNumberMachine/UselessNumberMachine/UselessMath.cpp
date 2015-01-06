//---------------------------------------------------------------------
// Performs useless calculations on all elements a single input entry.
// Author: Carey Norslien
//---------------------------------------------------------------------
#include "UselessMath.h"

double addition(NumbersList numbers)
{
   double sum = 0;

   for (int i = 0; i < numbers.getListSize(); i++)
      sum += numbers.elementAt(i);

   return sum;
}

double subtraction(NumbersList numbers)
{
   double difference = 0;

   for (int i = 0; i < numbers.getListSize(); i++)
      difference -= numbers.elementAt(i);

   return difference;
}

double multiplication(NumbersList numbers)
{
   double product = 0;

   for (int i = 0; i < numbers.getListSize(); i++)
      product *= numbers.elementAt(i);

   return product;
}

double division(NumbersList numbers)
{
   double quotient = 0;

   for (int i = 0; i < numbers.getListSize(); i++)
      quotient /= numbers.elementAt(i);

   return quotient;
}

double reciprocalAddition(NumbersList numbers)
{
   double reciprocal = 0;

   for (int i = 0; i < numbers.getListSize(); i++)
      reciprocal += (1 / numbers.elementAt(i));

   return 1 / reciprocal;
}

double reciprocalSubtraction(NumbersList numbers)
{
   double reciprocal = 0;

   for (int i = 0; i < numbers.getListSize(); i++)
      reciprocal -= (1 / numbers.elementAt(i));

   return 1 / reciprocal;
}

double reciprocalMultiplication(NumbersList numbers)
{
   double reciprocal = 0;

   for (int i = 0; i < numbers.getListSize(); i++)
      reciprocal *= (1 / numbers.elementAt(i));

   return 1 / reciprocal;
}

double reciprocalDivision(NumbersList numbers)
{
   double reciprocal = 0;

   for (int i = 0; i < numbers.getListSize(); i++)
      reciprocal /= (1 / numbers.elementAt(i));

   return 1 / reciprocal;
}

//TBD - check if numbers list contains only integers.
//int modulus(NumbersList numbers)
//{
//   int result = 0;
//
//   for (int i = 0; i < numbers.getListSize(); i++)
//      result %= numbers.elementAt(i);
//
//   return result;
//}