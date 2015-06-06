//###########################################################################
// Simple Math Program Main
// FILE DESC HERE
// 
// Software Interface Specifications:
//    IN:         N/A
//    OUT:        N/A
//
// Author:        Carey Norslien
// Created:       06/06/2015
// Last Modified: 06/06/2015
//###########################################################################
#ifndef     __SMAP_NUMBER_H
#define     __SMAP_NUMBER_H

//**FILE INCLUDES************************************************************
#include    <climits>

class SMaP_Number
{
public:
    bool dataRangeHi;       //exceeds VC++ int upper limit
    __int64 dataRangeHiVal;
    bool dataRangeLo;       //exceeds VC++ int lower limit
    __int64 dataRangeLoVal;
    bool dataRange;         //exceeds 64 bits

    //-----------------------------------------------------------------------
    // FUNC NAME
    // FUNC DESC
    // param:   N/A
    // return:  N/A
    //-----------------------------------------------------------------------
    SMaP_Number();

    //-----------------------------------------------------------------------
    // FUNC NAME
    // FUNC DESC
    // param:   N/A
    // return:  N/A
    //-----------------------------------------------------------------------
    SMaP_Number(int inNum);

    //-----------------------------------------------------------------------
    // FUNC NAME
    // FUNC DESC
    // param:   N/A
    // return:  N/A
    //-----------------------------------------------------------------------
    SMaP_Number& operator=(const SMaP_Number &rhs);

    //-----------------------------------------------------------------------
    // FUNC NAME
    // FUNC DESC
    // param:   N/A
    // return:  N/A
    //-----------------------------------------------------------------------
    friend SMaP_Number operator+(const SMaP_Number &lhs, const SMaP_Number &rhs);

private:
    const __int32 MAX_INT = LONG_MAX; //"redefine" macro for Visual C++ limits
    const __int32 MIN_INT = LONG_MIN; //"redefine" macro for Visual C++ limits

    __int32 val;
};

#endif // !__SMAP_OPERAND_h