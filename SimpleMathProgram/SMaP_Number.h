//###########################################################################
// Simple Math Program Number
// FILE DESC HERE
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
// Last Modified:   06/07/2015
//###########################################################################

#ifndef     __SMAP_NUMBER_H
#define     __SMAP_NUMBER_H

//**LIBRARY INCLUDES*********************************************************
#include    <iostream>
#include    <iomanip>
#include    <string>
#include    <climits>
#include    <cerrno>
#include    <climits>


class SMaP_Number
{
public:
    // following variables are faults
    bool    dataRangeHi;        //exceeds VC++ int upper limit
    __int64 dataRangeHiVal;     //value that exceeds VC++ upper limit
    bool    dataRangeLo;        //exceeds VC++ int lower limit
    __int64 dataRangeLoVal;     //value that exceeds VC++ lower limit
    bool    dataRange;          //overflow where too large/small can't be determined
    bool    divideByZero;

    //-----------------------------------------------------------------------
    // Default Constructor
    // Creates a default SMaP Number.
    // param:   none
    // return:  SMaP Number with value of 0 and no faults.
    //-----------------------------------------------------------------------
    SMaP_Number();

    //-----------------------------------------------------------------------
    // 32 Bit Number Constructor
    // Creates a SMaP Number using a 32 bit signed integer.
    // param:   (IN) inNum      -...
    // return:  SMaP Number with value of inNum and no faults.
    //-----------------------------------------------------------------------
    SMaP_Number(__int32 inNum);

    //-----------------------------------------------------------------------
    // Text Constructor
    // Creates a SMaP Number from command line arguments. It is possible that
    // the user input invalid information, thus human error is captured by
    // setting the appropriate faults.
    // param:   (IN) inText     -single argument from the command line
    // return:  SMaP Number representing inText and any faults from parsing
    //-----------------------------------------------------------------------
    SMaP_Number(char* inText);

    //-----------------------------------------------------------------------
    // Assignement Operator Overload
    // Its functionality is obvious.
    // param:   (IN) rhs       -right hand side
    // return:  SMaP Number with value and faults of the right hand side
    //-----------------------------------------------------------------------
    SMaP_Number& operator=(const SMaP_Number &rhs);

    //-----------------------------------------------------------------------
    // Binary Addition Operator Overload
    // Adds to SMaP Numbers together while recording any faults as a result of
    // the addition.
    // param:   (IN) lhs        -right hand side
    //          (IN) rhs        -left hand side
    // return:  SMaP Number representing the sum of lhs and rhs with faults
    //-----------------------------------------------------------------------
    friend SMaP_Number operator+(const SMaP_Number &lhs, const SMaP_Number &rhs);

    //-----------------------------------------------------------------------
    // FUNC NAME
    // FUNC DESC
    // param:   NA
    // return:  NA
    //-----------------------------------------------------------------------
    friend SMaP_Number operator-(const SMaP_Number &lhs, const SMaP_Number &rhs);

    //-----------------------------------------------------------------------
    // FUNC NAME
    // FUNC DESC
    // param:   NA
    // return:  NA
    //-----------------------------------------------------------------------
    friend SMaP_Number operator*(const SMaP_Number &lhs, const SMaP_Number &rhs);

    //-----------------------------------------------------------------------
    // FUNC NAME
    // FUNC DESC
    // param:   NA
    // return:  NA
    //-----------------------------------------------------------------------
    friend SMaP_Number operator/(const SMaP_Number &lhs, const SMaP_Number &rhs);

    //-----------------------------------------------------------------------
    // FUNC NAME
    // FUNC DESC
    // param:   NA
    // return:  NA
    //-----------------------------------------------------------------------
    friend std::ostream& operator<<(std::ostream &out, const SMaP_Number &num);

    //-----------------------------------------------------------------------
    // Is False Positive
    // To prevent an invalid argument from affecting the rest of the program,
    // the invalid argument is interpreted as SAFE_DATA. SAFE_DATA has a
    // numberical value that IS valid, so an argument that has the same value
    // as SAFE_DATA, but doesn't result from a fault, could be a false 
    // positive. This function is used to discover the false positive so 
    // corrective action can be taken.
    // param:   none
    // return:  true if a SMaP Number has the same value as SAFE_DATA, but 
    //          isn't the result of a fault.
    //-----------------------------------------------------------------------
    bool isFalsePositive() const;

    //-----------------------------------------------------------------------
    // FUNC NAME
    // FUNC DESC
    // param:   NA
    // return:  NA
    //-----------------------------------------------------------------------
    bool isInFault() const;

    //-----------------------------------------------------------------------
    // FUNC NAME
    // FUNC DESC
    // param:   NA
    // return:  NA
    //-----------------------------------------------------------------------
    void printErrorReport() const;

private:
    const __int32   MAX_VAL         = LONG_MAX; //"redefine" macro for Visual C++ limits
    const __int32   MIN_VAL         = LONG_MIN; //"redefine" macro for Visual C++ limits
    const __int8    CONVERSION_BASE = 10;
    const __int8    SAFE_DATA       = 0;        /* when used in conjuction with another condition,
                                                   it can replace erroneous data*/

    __int32 val;
};
#endif // !__SMAP_OPERAND_h