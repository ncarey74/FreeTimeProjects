//---------------------------------------------------------------------
// Tests the NumbersList class.
// 
// Author: Carey Norslien
//---------------------------------------------------------------------
#include "NumbersList.h"
#ifndef TESTNUMBERSLIST_h
#define TESTNUMBERSLIST_h

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

};
#endif