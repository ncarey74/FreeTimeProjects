//###########################################################################
// Simple Math Program Number
// Data abstraction for the numbers that the SMaP can operate on. These
// numbers are 32 bit signed integers with some error handling and fault 
// reporting built in.
// 
// Software Interface Specifications:
//      IN:         N/A
//      OUT:        N/A
//      IN/OUT:     SMaP_Main. This class is the data abstraction
//                  for the user entered numbers and the result of the 
//                  arithmetic operation on those user entered numbers that
//                  the main files handles.
//
// Author:          Carey Norslien
// Created:         06/06/2015
// Last Modified:   06/08/2015
//###########################################################################

//**FILE INCLUDES************************************************************
#include    "SMaP_Number.h"

//**FILE CONSTANTS***********************************************************
const unsigned __int8 BYTE_SIZE = 8;
const unsigned __int8 VAL_SIZE = sizeof(__int32) * BYTE_SIZE;

//**FREE FUNCTION DECLARATIONS***********************************************
//---------------------------------------------------------------------------
// FUNC NAME
// FUNC DESC
// param:   NA
// return:  NA
//---------------------------------------------------------------------------
unsigned __int8 highestOneBitPosition(const __int32 inNum);


//**PUBLIC CLASS FUNCTION DEFINITIONS****************************************
SMaP_Number::SMaP_Number()
{
    dataRangeHi         = false;
    dataRangeHiVal      = "N/A";
    dataRangeLo         = false;
    dataRangeLoVal      = "N/A";
    dataRange           = false;
    divideByZero        = false;
    val                 = 0;
}

SMaP_Number::SMaP_Number(__int32 inNum)
{
    dataRangeHi         = false;
    dataRangeHiVal      = "N/A";
    dataRangeLo         = false;
    dataRangeLoVal      = "N/A";
    dataRange           = false;
    divideByZero        = false;
    val                 = inNum;
}

SMaP_Number::SMaP_Number(char *inText)
{
    dataRangeHi         = false;
    dataRangeHiVal      = "N/A";
    dataRangeLo         = false;
    dataRangeLoVal      = "N/A";
    dataRange           = false;
    divideByZero        = false;

    //long long is 64 bit signed number
    _int64 operand = strtoll(inText, NULL, CONVERSION_BASE);

    if (errno == ERANGE)
    {
        dataRange = true;
    }
    else if (operand > MAX_VAL)
    {
        dataRangeHi = true;
        dataRangeHiVal = inText;
    }
    else if (operand < MIN_VAL)
    {
        dataRangeLo = true;
        dataRangeLoVal = inText;
    }

    if (dataRange || dataRangeHi || dataRangeLo)
    {
        operand = SAFE_DATA;
    }

    val = (__int32)operand;
}

SMaP_Number& SMaP_Number::operator=(const SMaP_Number &rhs)
{
    if (this != &rhs)
    {
        dataRangeHi         = rhs.dataRangeHi;
        dataRangeHiVal      = rhs.dataRangeHiVal;
        dataRangeLo         = rhs.dataRangeLo;
        dataRangeLoVal      = rhs.dataRangeLoVal;
        dataRange           = rhs.dataRange;
        divideByZero        = rhs.divideByZero;
        val                 = rhs.val;
    }
    return *this;
}

SMaP_Number operator+(const SMaP_Number &lhs, const SMaP_Number &rhs)
{
    SMaP_Number result = SMaP_Number();
    __int32 lhsVal = lhs.val;
    __int32 rhsVal = rhs.val;

    if ((rhsVal > 0) && (lhsVal > (result.MAX_VAL - rhsVal)))
    {
        result.dataRangeHi = true;
        result.dataRangeHiVal = std::to_string(result.SAFE_DATA);
        result = SMaP_Number(result.SAFE_DATA);
    }
    else if ((rhsVal < 0) && (lhsVal < (result.MIN_VAL - rhsVal)))
    {
        result.dataRangeLo = true;
        result.dataRangeLoVal = std::to_string(result.SAFE_DATA);
        result = SMaP_Number(result.SAFE_DATA);
    }
    else
    {
        result = SMaP_Number(lhsVal + rhsVal);
    }

    return result;
}

SMaP_Number operator-(const SMaP_Number &lhs, const SMaP_Number &rhs)
{
    SMaP_Number result = SMaP_Number();
    __int32 lhsVal = lhs.val;
    __int32 rhsVal = rhs.val;

    //preconditional overflow test
    if ((lhsVal > 0 && rhsVal < 0) && (lhsVal > result.MAX_VAL + rhsVal))
    {
        result.dataRangeHi = true;
        result.dataRangeHiVal = std::to_string(result.SAFE_DATA);
        result = SMaP_Number(result.SAFE_DATA);
    }
    //TODO: fix logic to find case where result would be too small
    else if ((lhsVal >= 0 && rhsVal < 0) && (lhsVal < result.MAX_VAL + rhsVal))
    {
        result.dataRangeLo = true;
        result.dataRangeLoVal = std::to_string(result.SAFE_DATA);
        result = SMaP_Number(result.SAFE_DATA);
    }
    else
    {
        result = SMaP_Number(lhsVal - rhsVal);
    }

    return result;
}

SMaP_Number operator*(const SMaP_Number &lhs, const SMaP_Number &rhs)
{
    SMaP_Number result = SMaP_Number();
    __int32 lhsVal = lhs.val;
    __int32 rhsVal = rhs.val;

    //preconditional overflow test
    unsigned __int8 lhsBits = highestOneBitPosition(lhsVal);
    unsigned __int8 rhsBits = highestOneBitPosition(rhsVal);

    if (lhsBits + rhsBits > VAL_SIZE)
    {
        result.dataRange = true;
    }
    //TODO: get logic to find case where result would be too small
    else
    {
        result = SMaP_Number(lhsVal * rhsVal);
    }

    return result;
}

SMaP_Number operator/(const SMaP_Number &lhs, const SMaP_Number &rhs)
{
    SMaP_Number result = SMaP_Number();
    __int32 lhsVal = lhs.val;
    __int32 rhsVal = rhs.val;

    if (rhsVal == 0)
    {
        result.divideByZero = true;
    }
    else if (lhsVal == result.MIN_VAL && rhsVal == -1)
    {
        result.dataRange = true;
    }
    else
    {
        result = SMaP_Number(lhsVal / rhsVal);
    }

    return result;
}

std::ostream& operator<<(std::ostream &out, const SMaP_Number &num)
{
    if (num.isInFault() == false)
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
        (dataRange || dataRangeHi || dataRangeLo))
    {
        result = false;
    }

    return result;
}

bool SMaP_Number::isInFault() const
{
    bool result = false;

    if (dataRange || dataRangeHi || dataRangeLo || divideByZero)
    {
        result = true;
    }

    return result;
}

void SMaP_Number::printErrorReport() const
{
    /*If a SMaP Number has a fault and is not equal to SAFE_DATA, then
      it was determined that number was too large/small and the integer value
      was preserved. This SMaP Number should be an operand.*/
    if (dataRangeHiVal != std::to_string(SAFE_DATA))
    {
        if (dataRangeHi)
        {
            std::cout << "\nNumber: " << dataRangeHiVal << " is too large.\n";
        }
        if (dataRangeLo)
        {
            std::cout << "\nNumber: " << dataRangeLoVal << " is too small.\n";
        }
    }
    /*Else the number was too large/small and the integer value could not 
      be preserved. THis SMaP Number should be a result.*/
    else
    {
        if (dataRangeHi)
        {
            std::cout << "\nThe result is too large.\n";
        }
        if (dataRangeLo)
        {
            std::cout << "\nThe result is too small.\n";
        }
    }

    if (dataRange)
    {
        std::cout << "\nGeneral overflow error.\n";
    }
    if (divideByZero)
    {
        std::cout << "\nDivide by zero error.\n";
    }
}

//**FREE FUNCTION DEFINITIONS************************************************
unsigned __int8 highestOneBitPosition(const __int32 inNum)
{
    unsigned __int8 bits    = 0;
    __int8 bitShift         = inNum;
    
    while (bitShift != 0)
    {
        ++bits;
        bitShift >>= 1;
    }

    return bits;
}