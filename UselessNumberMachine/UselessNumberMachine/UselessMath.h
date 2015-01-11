//---------------------------------------------------------------------
// Performs useless calculations on all elements a single input entry.
// Author: Carey Norslien
//---------------------------------------------------------------------
#include "NumbersList.h"

//Simple Math----------------------------------------------------------------
double sum(NumbersList numbers);
double difference(NumbersList numbers);
double product(NumbersList numbers);
double quotient(NumbersList numbers);  //returns 0 if there is a pending divide by zero error
double reciprocalSum(NumbersList numbers);
double reciprocalDifference(NumbersList numbers);  
double reciprocalProduct(NumbersList numbers);
double reciprocalQuotient(NumbersList numbers); //returns 0 if there is a pending divide by zero error
double rollingOperations();      //to be defined
double modulus();                //to be defined

//Statistics-----------------------------------------------------------------
double largestNumber();          //to be defined
double smallestNumber();        //to be defined
double averageNumber();          //to be defined
double medianNumber();           //to be defined
double standardDeviation();      //to be defined
NumbersList orderByValue();      //to be defined

//Prime Numbers--------------------------------------------------------------
NumbersList primeNumber();       //to be defined

//Exponential Numbers--------------------------------------------------------
double sqrtSum();
double sqrtDifference();
double sqrtProduct();
double sqrtQuotient();
