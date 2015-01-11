//---------------------------------------------------------------------
// Tests the NumbersList class.
// 
// Author: Carey Norslien
//---------------------------------------------------------------------
#ifndef TESTUSELESSMATH_h
#define TESTUSELESSMATH_h

#include "NumbersList.h"
#include "UselessMath.h"
#include <iostream>
using std::cin;
using std::cout;

//---------------------------------------------------------------------------
// Executive test function for UselessMath.
//---------------------------------------------------------------------------
void uselessMathTestUnit();

//---------------------------------------------------------------------------
// TestID: EmptyNumbersListTestSet
// Test all functions with an emtpy numbers list.
//---------------------------------------------------------------------------
void emptyNumbersList();

//---------------------------------------------------------------------------
// TestID: SumTestSet
// Test all functions with an emtpy numbers list.
//---------------------------------------------------------------------------
void sumTestSet();

//---------------------------------------------------------------------------
// TestID: DifferenceTestSet
// For several number lists of the same size, subtract all values.
//---------------------------------------------------------------------------
void diffTestSet();

//---------------------------------------------------------------------------
// TestID: ProductTestSet
// For several number lists of the same size, multiply all values.
//---------------------------------------------------------------------------
void prodTestSet();

//---------------------------------------------------------------------------
// TestID: QuotientTestSet
// For several number lists of the same size, divide all values.Also check 
// for divide by zero errors.
//---------------------------------------------------------------------------
void quotTestSet();

//---------------------------------------------------------------------------
// Creates a populated Numbers List with commonly used positive integer 
// values.
//---------------------------------------------------------------------------
NumbersList positiveIntNumbersList();

//---------------------------------------------------------------------------
// Creates a populated Numbers List with commonly used negative integer 
// values.
//---------------------------------------------------------------------------
NumbersList negativeIntNumbersList();

//---------------------------------------------------------------------------
// Creates a populated Numbers List with commonly used positive and negative 
// integer values.
//---------------------------------------------------------------------------
NumbersList naturalNumNumbersList();

//---------------------------------------------------------------------------
// Creates a populated Numbers List with commonly used positive decimal values.
//---------------------------------------------------------------------------
NumbersList positveDecNumbersList();

//---------------------------------------------------------------------------
// Creates a populated Numbers List with commonly used negative decimal values.
//---------------------------------------------------------------------------
NumbersList negativeDecNumbersList();

//---------------------------------------------------------------------------
// Creates a populated Numbers List with commonly used positive and negative
// decimal values.
//---------------------------------------------------------------------------
NumbersList mizedDecNumbersList();

//---------------------------------------------------------------------------
// Creates the common Numbers Lists for the test sets.
//---------------------------------------------------------------------------
void createPopulatedNumberLists(NumbersList& list1, NumbersList& list2,
                                NumbersList& list3, NumbersList& list4,
                                NumbersList& list5, NumbersList& list6);

#endif