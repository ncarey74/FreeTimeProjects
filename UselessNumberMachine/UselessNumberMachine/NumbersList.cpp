#include "NumbersList.h"
#include <new>
using namespace std;

NumbersList::NumbersList()
{
   numbers = new (nothrow) double[DEFAULT_SIZE];
   listLength = DEFAULT_SIZE;
   listSize = 0;
}

NumbersList::NumbersList(int size)
{
   if (size <= DEFAULT_SIZE)
   {
      numbers = new (nothrow) double[DEFAULT_SIZE];
      listLength = DEFAULT_SIZE;
   }
   else
   {
      numbers = new (nothrow) double[size];
      listLength = size;
   }
   listSize = 0;
}

NumbersList::~NumbersList()
{
   delete[] numbers;
}

void NumbersList::addToList(double inNum)
{
   if (numbers == nullptr)
      return; //TBD ERROR HANDLING
   else if (listSize >= listLength)
      return; //TBD ERROR HANDLING
   else if (inNum == 0)
      return; //TBD ERROR HANDLING
   else
   {
      numbers[listSize] = inNum;
      listSize++;
   }
}

bool NumbersList::removeFromList(double targetIndex)
{
   int index = 0;

   if (numbers == nullptr)
      return false; //TBD ERROR HANDLING
   else if (listSize <= 0)
      return false; //TBD ERROR HANDLING
   else
   {
      for (int i = targetIndex; i < listSize; i++)
      {
         numbers[i] = i + 1;
      }
      listSize--;
   }
}

//The following functions are for debugging purposes only!
#ifdef DEBUG_FUNC
#include <iostream>
void NumbersList::print()
{
   for (int i = 0; i < listSize; i++)
      cout << numbers[i] << " ";
   cout << "\n";
}
#endif