//###########################################################################
// Simple Math Program Main
// FILE DESC HERE
// 
// Software Interface Specifications:
//    IN:         N/A
//    OUT:        N/A
//
// Author:        Carey Norslien
// Created:       06/05/2015
// Last Modified: 06/06/2015
//###########################################################################

//**FILE INCLUDES************************************************************
#include    "SMaP_Number.h"


//**PUBLIC FUNCTION DEFINITIONS**********************************************
SMaP_Number::SMaP_Number()
{
    dataRangeHi     = false;
    dataRangeHiVal  = 0;
    dataRangeLo     = false;
    dataRangeLoVal  = 0;
    dataRange       = false;
    val             = 0;
}

SMaP_Number::SMaP_Number(int inNum)
{
    dataRangeHi     = false;
    dataRangeHiVal  = 0;
    dataRangeLo     = false;
    dataRangeLoVal  = 0;
    dataRange       = false;
    val             = inNum;
}

SMaP_Number& SMaP_Number::operator=(const SMaP_Number &rhs)
{
    if (this != &rhs)
    {
        dataRangeHi     = rhs.dataRangeHi;
        dataRangeHiVal  = rhs.dataRangeHiVal;
        dataRangeLo     = rhs.dataRangeLo;
        dataRangeLoVal  = rhs.dataRangeLoVal;
        dataRange       = rhs.dataRange;
        val             = rhs.val;
    }
    return *this;
}

SMaP_Number operator+(const SMaP_Number &lhs, const SMaP_Number &rhs)
{
    SMaP_Number result;
    __int32 num1 = lhs.val;
    __int32 num2 = rhs.val;

    if ((num2 > 0) && (num1 > (result.MAX_INT - num2)))
    {
        result.dataRangeHi = true;
    }
    else if ((num2 < 0) && (num1 < (result.MIN_INT - num2)))
    {
        result.dataRangeLo = true;
    }
    else
    {
        result = SMaP_Number(num1 + num2);
    }

    return result;
}