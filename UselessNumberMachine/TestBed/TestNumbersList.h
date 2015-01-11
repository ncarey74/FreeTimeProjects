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

//------------------------------------------------------------------
// Executive test function for NumbersList
//------------------------------------------------------------------
void numbersListTestUnit();

//------------------------------------------------------------------
// TestID: IntegerNumbersListTestSet
// Test object methods with integers.
//------------------------------------------------------------------
void intTestSet();

//------------------------------------------------------------------
// TestID: FractionNumbersListTestSet
// Test object methods with fractions.
//------------------------------------------------------------------
void floatTestSet();

//------------------------------------------------------------------
// TestID: RealNumbersListTestSet
// Test object methods with real numbers.
//------------------------------------------------------------------
void mixedTestSet();

//------------------------------------------------------------------
// TestID: ConstructNumbersListTestSet
// Test constructors and destructors.
//------------------------------------------------------------------
void constructTestSet();

#endif