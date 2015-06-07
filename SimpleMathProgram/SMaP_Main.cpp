//###########################################################################
// Simple Math Program Main
// A simple console calculator designed for the Windows environment, thus 
// VC++ data types are used instead of the regular C++ data types
// 
// Software Interface Specifications:
//    IN:         N/A
//    OUT:        N/A
//
// Author:        Carey Norslien
// Created:       06/05/2015
// Last Modified: 06/06/2015
//###########################################################################

//**LIBRARY INCLUDES*********************************************************
#include    <iostream>
#include    <string>
#include    <climits>
#include    <cerrno>

//**FILE INCLUDES************************************************************
#include    "SMaP_Number.h"

//**FILE CONSTANTS***********************************************************
const int   CMD_LENGTH          = 4u;

//**CUSTOM TYPES*************************************************************
enum Errors
{
    e_ERR_NONE = 0,
    e_ERR_PARSE_CMD,
    e_ERR_DATA_RANGE,
};

//**FILE DATA****************************************************************
struct t_userInput
{
    SMaP_Number number1;
    SMaP_Number number2;
} g_userData;

struct t_argumentErrors
{
    bool argumentCountHi    = false;
    bool argumentCountLo    = false;
} g_argumentErrors;

//TODO: make a "standard" struct and an "enchanced" struct
struct t_dataBoundErrors 
{
    bool dataRangeHi        = false;    //exceeds VC++ int upper limit
    __int64 dataRangeHiVal     = 0;
    bool dataRangeLo        = false;    //exceeds VC++ int lower limit
    __int64 dataRangeLoVal = 0;
    bool dataRange          = false;    //exceeds 64 bits
} g_operandsDataBoundError, g_answerDataBoundError;


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

//---------------------------------------------------------------------------
// FUNC NAME
// FUNC DESC
// param:   N/A
// return:  N/A
//---------------------------------------------------------------------------
bool addition(int num1, int num2);


//**FUNCTION DEFINITIONS*****************************************************

void main(int argc, char* argv[])
{
    SMaP_Number num = SMaP_Number();
    if (parseCommands(argc, argv) == e_ERR_NONE)
    {
        num = g_userData.number1 + g_userData.number2;
        if (num.isInFault() == false)
        {
            std::cout << num << "\n";
        }
    }
    //printErrorReport();
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
    if (g_operandsDataBoundError.dataRangeHi)
    {
        std::cout << "\nNumber: " << g_operandsDataBoundError.dataRangeHiVal
            << " is too large.\n";
    }
    if (g_operandsDataBoundError.dataRangeLo)
    {
        std::cout << "\nNumber: " << g_operandsDataBoundError.dataRangeLoVal
            << " is too small.\n";
    }
    if (g_answerDataBoundError.dataRangeHi)
    {
        std::cout << "\nSum is too large.\n";
    }
    if (g_answerDataBoundError.dataRangeLo)
    {
        std::cout << "\nSum is too small.\n";
    }
    if (g_operandsDataBoundError.dataRange)
    {
        perror("The following error has occured");
    }
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
            g_userData.number1 = SMaP_Number(argv[2]);
            g_userData.number2 = SMaP_Number(argv[3]);
            /* see constructor documentation for the justification of the 
               following IF test. */
            if ((g_userData.number1).isFalsePositive() || 
                (g_userData.number2).isFalsePositive())
            {
                isReadSuccessful = e_ERR_NONE;
            }
        }
    }

    return isReadSuccessful;
}