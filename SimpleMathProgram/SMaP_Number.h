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

#ifndef     __SMAP_NUMBER_H
#define     __SMAP_NUMBER_H

//**LIBRARY INCLUDES*********************************************************
#include    <iostream>
#include    <iomanip>
#include    <string>
#include    <climits>
#include    <cerrno>

using std::string;

class SMaP_Number
{
public:
    // following variables are faults
    bool    dataRangeHi;        //exceeds VC++ int upper limit
    string  dataRangeHiVal;     //value that exceeds VC++ upper limit
    bool    dataRangeLo;        //exceeds VC++ int lower limit
    string dataRangeLoVal;     //value that exceeds VC++ lower limit
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
    // param:   (IN) inNum      -value of the new SMaP Number
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
    // Adds two SMaP Numbers together while recording any faults as a result of
    // the addition.
    // param:   (IN) lhs        -left hand side
    //          (IN) rhs        -right hand side
    // return:  SMaP Number representing the sum of lhs and rhs with faults
    //-----------------------------------------------------------------------
    friend SMaP_Number operator+(const SMaP_Number &lhs, const SMaP_Number &rhs);

    //-----------------------------------------------------------------------
    // Binary Subtraction Operator Overload
    // Subtracts two SMaP numbers together while recording any faults as a 
    // result of the subtraction.
    // param:   (IN) lhs        -left hand side
    //          (IN) rhs        -right hand side
    // return:  SMaP Number representing the difference of lhs and rhs with faults
    //-----------------------------------------------------------------------
    friend SMaP_Number operator-(const SMaP_Number &lhs, const SMaP_Number &rhs);

    //-----------------------------------------------------------------------
    // Binary Multiplication Operator Overload
    // Multiplies two SMaP numbers together while recording any faults as a 
    // result of the multiplication.
    // param:   (IN) lhs        -left hand side
    //          (IN) rhs        -right hand side
    // return:  SMaP Number representing the product of lhs and rhs with faults
    //-----------------------------------------------------------------------
    friend SMaP_Number operator*(const SMaP_Number &lhs, const SMaP_Number &rhs);

    //-----------------------------------------------------------------------
    // Binary Division Operator Overload
    // Divides two SMaP numbers together while recording any faults as a 
    // result of the multiplication. This uses integer division.
    // param:   (IN) lhs        -left hand side
    //          (IN) rhs        -right hand side
    // return:  SMaP Number representing the quotient of lhs and rhs with faults
    //-----------------------------------------------------------------------
    friend SMaP_Number operator/(const SMaP_Number &lhs, const SMaP_Number &rhs);

    //-----------------------------------------------------------------------
    // Outstream Operator Overload
    // Its functionality is obvious.
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
    // Is In Fault
    // Determines if a SMaP number is in fault.
    // A fault is one or more of the following:
    //      - Exceeds maximum 32 bit signed number
    //      - Exceeds minimum 32 bit signed number
    //      - General overflow
    //      - Divide by zero
    // param:   none
    // return:  true if a SMaP number is in fault, false otherwise.
    //-----------------------------------------------------------------------
    bool isInFault() const;

    //------------------------------- ----------------------------------------
    // Print Error Report
    // If the SMaP number is in fault, print all faults.
    // param:   none
    // return:  none
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