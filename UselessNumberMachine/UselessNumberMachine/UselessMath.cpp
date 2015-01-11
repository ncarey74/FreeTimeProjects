#include "UselessMath.h"

//Simple Math----------------------------------------------------------------
double sum(NumbersList numbers)
{
   double sum = 0;

   for (int i = 0; i < numbers.getListSize(); i++)
      sum += numbers.elementAt(i);

   return sum;
}

double difference(NumbersList numbers)
{
   double difference = 0;

   for (int i = 0; i < numbers.getListSize(); i++)
      difference -= numbers.elementAt(i);

   return difference;
}

double product(NumbersList numbers)
{
   double product = 0;

   for (int i = 0; i < numbers.getListSize(); i++)
      product *= numbers.elementAt(i);

   return product;
}

double quotient(NumbersList numbers)
{
   double quotient = 0;

   for (int i = 0; i < numbers.getListSize(); i++)
   {
      //prevent pending divide by zero errors
      if (numbers.elementAt(i) != 0)
         quotient /= numbers.elementAt(i);
      else
         return 0;
   }
   return quotient;
}

double reciprocalSum(NumbersList numbers)
{
   double reciprocal = 0;

   for (int i = 0; i < numbers.getListSize(); i++)
      reciprocal += (1 / numbers.elementAt(i));

   return 1 / reciprocal;
}

double reciprocalDifference(NumbersList numbers)
{
   double reciprocal = 0;

   for (int i = 0; i < numbers.getListSize(); i++)
      reciprocal -= (1 / numbers.elementAt(i));

   return 1 / reciprocal;
}

double reciprocalProduct(NumbersList numbers)
{
   double reciprocal = 0;

   for (int i = 0; i < numbers.getListSize(); i++)
      reciprocal *= (1 / numbers.elementAt(i));

   return 1 / reciprocal;
}

double reciprocalQuotient(NumbersList numbers)
{
   double reciprocal = 0;

   for (int i = 0; i < numbers.getListSize(); i++)
   {
      //prevent pending divide by zero error
      if (numbers.elementAt(i) != 0)
         reciprocal /= (1 / numbers.elementAt(i));
      else
         return 0;
   }
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

double rollingOperations() 
{ 
   return 0;
}

double modulus()
{ 
   return 0;
}

//Statistics-----------------------------------------------------------------
double largestNumber() 
{ 
   return 0; 
}

double smallestNumber() 
{ 
   return 0;
}

double averageNumber() 
{ 
   return 0;
}

double medianNumber()
{ 
   return 0;
}

double standardDeviation() 
{ 
   return 0;
}

NumbersList orderByValue() 
{ 
   return 0;
}

//Prime Numbers--------------------------------------------------------------
NumbersList primeNumber() 
{ 
   return 0;
}

//Exponential Numbers--------------------------------------------------------
double sqrtSum() 
{ 
   return 0;
}

double sqrtDifference() 
{ 
   return 0;
}

double sqrtProduct() 
{ 
   return 0;
}

double sqrtQuotient() 
{ 
   return 0;
}