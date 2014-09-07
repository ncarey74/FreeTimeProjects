//---------------------------------------------------------------------
// Tests the NumbersList class.
// 
// Author: Carey Norslien
//---------------------------------------------------------------------
#ifndef TESTNUMBERSLIST_h
#define TESTNUMBERSLIST_h
#include "NumbersList.h"
#include <cassert>
#include <iostream>
using std::cin;
using std::cout;

class TestNumbersList
{
public:
   static void testUnit();
private:
   //------------------------------------------------------------------
   // TestID: IntegerNumbersListTestSet
   // Test object methods with integers.
   //------------------------------------------------------------------
   static void intTestSet();

   //------------------------------------------------------------------
   // TestID: FractionNumbersListTestSet
   // Test object methods with fractions.
   //------------------------------------------------------------------
   static void floatTestSet();

   //------------------------------------------------------------------
   // TestID: RealNumbersListTestSet
   // Test object methods with real numbers.
   //------------------------------------------------------------------
   static void mixedTestSet();

   //------------------------------------------------------------------
   // TestID: ConstructNumbersListTestSet
   // Test constructors and destructors.
   //------------------------------------------------------------------
   static void constructTestSet();
};
#endif