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
    dataRangeHi         = false;
    dataRangeHiVal      = 0;
    dataRangeLo         = false;
    dataRangeLoVal      = 0;
    exceedAbsoluteLimit = false;
    val                 = 0;
}

SMaP_Number::SMaP_Number(__int32 inNum)
{
    dataRangeHi         = false;
    dataRangeHiVal      = 0;
    dataRangeLo         = false;
    dataRangeLoVal      = 0;
    exceedAbsoluteLimit = false;
    val                 = inNum;
}

SMaP_Number::SMaP_Number(char *inText)
{
    //long long is 64 bit signed number
    _int64 operand = strtoll(inText, NULL, CONVERSION_BASE);

    if (errno == ERANGE)
    {
        exceedAbsoluteLimit = true;
    }
    else if (operand > MAX_INT)
    {
        dataRangeHi = true;
        dataRangeHiVal = operand;
    }
    else if (operand < MIN_INT)
    {
        dataRangeLo = true;
        dataRangeLoVal = operand;
    }
    else
    {
        dataRangeHi = false;
        dataRangeHiVal = 0;
        dataRangeLo = false;
        dataRangeLoVal = 0;
        exceedAbsoluteLimit = false;
    }

    if (exceedAbsoluteLimit || dataRangeHi || dataRangeLo)
    {
        operand = SAFE_DATA;
    }

    val = (__int32)operand;
}

SMaP_Number& SMaP_Number::operator=(const SMaP_Number &rhs)
{
    if (this != &rhs)
    {
        dataRangeHi     = rhs.dataRangeHi;
        dataRangeHiVal  = rhs.dataRangeHiVal;
        dataRangeLo     = rhs.dataRangeLo;
        dataRangeLoVal  = rhs.dataRangeLoVal;
        exceedAbsoluteLimit       = rhs.exceedAbsoluteLimit;
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

std::ostream& operator<<(std::ostream &out, const SMaP_Number &num)
{
    if (!num.dataRangeHi && !num.dataRangeLo && !num.exceedAbsoluteLimit)
    {
        out << std::setiosflags(std::ios::left) << std::setw(30)
            << num.val << " ";
    }
    return out;
}

bool SMaP_Number::isFalsePositive() const
{
    bool result = true;

    if (val == SAFE_DATA && 
        (exceedAbsoluteLimit || dataRangeHi || dataRangeLo))
    {
        result = false;
    }

    return result;
}

bool SMaP_Number::isInFault() const
{
    bool result = false;

    if (exceedAbsoluteLimit || dataRangeHi || dataRangeLo)
    {
        result = true;
    }

    return result;
}