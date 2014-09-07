//---------------------------------------------------------------------
//
//---------------------------------------------------------------------
#ifndef NUMBERSLIST_H
#define NUMBERSLIST_H

#include <new>
#include <iostream>
using std::cout;
using std::cin;
using std::nothrow;


class NumbersList
{
public:
   //------------------------------------------------------------------
   // Default constructor.
   //------------------------------------------------------------------
   NumbersList();

   //------------------------------------------------------------------
   // Constructs a numbers list of the given size.
   // param size: size of the new numbers list.
   //------------------------------------------------------------------
   NumbersList(int size);

   //------------------------------------------------------------------
   //
   //------------------------------------------------------------------
   virtual ~NumbersList();

   //------------------------------------------------------------------
   // Adds a single number to the list.
   // param inNum: number to add to the list.
   //------------------------------------------------------------------
   void addToList(double inNum);

   //------------------------------------------------------------------
   // Removes a single number from the list given its index.
   // param index: index of the number to remove from the list
   // return: true if the number was removed, false otherwise.
   //------------------------------------------------------------------
   bool removeFromList(double targetIndex);

   //------------------------------------------------------------------
   // Finds the index of the first instance of this number.
   // return: index of first instance of this number, -1 otherwise
   //------------------------------------------------------------------
   int find(double inNum);//TBD

   //------------------------------------------------------------------
   // Retrieves the element at a given index.
   // param index: index of element to retrieve.
   //------------------------------------------------------------------
   double elementAt(int index);

   int getListSize();

   void clearList();

#ifdef DEBUG_FUNC
   void print();
#endif

private:
   const static short DEFAULT_SIZE = 10;
   double *numbers;
   int listLength;
   int listSize;
};
#endif