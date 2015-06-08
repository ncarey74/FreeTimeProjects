//###########################################################################
// Simple Math Program Main
// A simple console calculator designed for the Windows environment, thus 
// VC++ data types are used instead of the regular C++ data types. It can
// add, subtract, multiply, and divide two integers. There is no support
// for fractional numbers.
// 
// Software Interface Specifications:
//    IN:           None
//    OUT:          None
//    IN/OUT:       SMaP_Number Objects. These objects are data abstractions
//                  for the user entered numbers and the result of the 
//                  arithmetic operation on those user entered numbers
//
// Author:          Carey Norslien
// Created:         06/05/2015
// Last Modified:   06/07/2015
//###########################################################################

//**LIBRARY INCLUDES*********************************************************
#include    <iostream>
#include    <string>


//**FILE INCLUDES************************************************************
#include    "SMaP_Number.h"


//**FILE CONSTANTS***********************************************************
const int   CMD_LENGTH          = 4u;


//**CUSTOM TYPES*************************************************************
typedef unsigned char t_MathType;

enum Errors
{
    e_ERR_NONE = 0,
    e_ERR_PARSE_CMD,
    e_ERR_DATA_RANGE
};

enum Arithmetic
{
    e_NONE = 0,
    e_ADDITION,
    e_SUBTRACTION,
    e_MULTIPLICATION,
    e_DIVISION
};


//**FILE DATA****************************************************************
struct t_appData
{
    t_MathType  arithmetic  = e_NONE;
    SMaP_Number number1     = SMaP_Number();
    SMaP_Number number2     = SMaP_Number();
    SMaP_Number result      = SMaP_Number();
} g_appData;

struct t_argumentErrors
{
    bool argumentCountHi    = false;
    bool argumentCountLo    = false;
} g_argumentErrors;


//**FUNCTION DECLARATIONS****************************************************
//---------------------------------------------------------------------------
// FUNC NAME
// FUNC DESC
// param:   N/A
// return:  N/A
//---------------------------------------------------------------------------
void printErrorReport();

//---------------------------------------------------------------------------
// Parse Data
// FUNC DESC
//
// Known Deficiency:
//      All operands share the same fault reporting mechanism. 
//      As such, a single fault may be triggered by more than one operand, so
//      the latest operand with that fault will be reported without mention of
//      previous operands with that fault.
//
// param:   (IN) operand    ...
//          (OUT) error     ...
// return:  Integer value of operand if it can fit in a 32 bit number, SAFE_DATA
//          if the operand can't fit. SAFE_DATA must be used in conjunction with
//          e_ERR_DATA_RANGE to properly handle erroneous data.
//---------------------------------------------------------------------------
int parseData(const char* operand, int& error);

//---------------------------------------------------------------------------
// FUNC NAME
// FUNC DESC
// param:   N/A
// return:  N/A
//---------------------------------------------------------------------------
int parseCommands(int argc, char* argv[]);


//**FUNCTION DEFINITIONS*****************************************************
void main(int argc, char* argv[])
{
    if (parseCommands(argc, argv) == e_ERR_NONE)
    {
        if (g_appData.arithmetic == e_ADDITION)
        {
            g_appData.result = g_appData.number1 + g_appData.number2;
        }
        else if (g_appData.arithmetic == e_SUBTRACTION)
        {
            g_appData.result = g_appData.number1 - g_appData.number2;
        }
        else if (g_appData.arithmetic == e_MULTIPLICATION)
        {
            g_appData.result = g_appData.number1 * g_appData.number2;
        }
        else if (g_appData.arithmetic == e_DIVISION)
        {
            g_appData.result = g_appData.number1 / g_appData.number2;
        }

        if (g_appData.result.isInFault() == false)
        {
            std::cout << g_appData.result << "\n";
        }
    }
    printErrorReport();
}

void printErrorReport()
{
    if (g_argumentErrors.argumentCountHi)
    {
        std::cout << "\nThere are too many arguments\n";
    }
    if (g_argumentErrors.argumentCountLo)
    {
        std::cout << "\nThere are too few arguments\n";
    }
    g_appData.result.printErrorReport();
    g_appData.number1.printErrorReport();
    g_appData.number2.printErrorReport();
}

int parseCommands(int argc, char* argv[])
{
    int isReadSuccessful = e_ERR_NONE;

    if (argc < CMD_LENGTH)
    {
        g_argumentErrors.argumentCountLo = true;
        isReadSuccessful = e_ERR_PARSE_CMD;
    }
    else if (argc > CMD_LENGTH)
    {
        g_argumentErrors.argumentCountHi = true;
        isReadSuccessful = e_ERR_PARSE_CMD;
    }
    else
    {
        if (strcmp(argv[1], "-add") == 0)
        {
            g_appData.arithmetic = e_ADDITION;
        }
        else if (strcmp(argv[1], "-subtract") == 0)
        {
            g_appData.arithmetic = e_SUBTRACTION;
        }
        else if (strcmp(argv[1], "-multiply") == 0)
        {
            g_appData.arithmetic = e_MULTIPLICATION;
        }
        else if (strcmp(argv[1], "-divide") == 0)
        {
            g_appData.arithmetic = e_DIVISION;
        }
        g_appData.number1 = SMaP_Number(argv[2]);
        g_appData.number2 = SMaP_Number(argv[3]);
        /* see constructor documentation for the justification of the
        following IF test. */
        if (!g_appData.number1.isFalsePositive() ||
            !g_appData.number2.isFalsePositive())
        {
            isReadSuccessful = e_ERR_PARSE_CMD;
        }
    }

    return isReadSuccessful;
}