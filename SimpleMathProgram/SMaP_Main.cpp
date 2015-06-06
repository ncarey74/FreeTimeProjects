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

//**FILE INCLUDES************************************************************
#include    <iostream>
#include    <string>
#include    <climits>
#include    <cerrno>

//**FILE CONSTANTS***********************************************************
const int   CMD_LENGTH          = 4u;

const int   MAX_INT             = LONG_MAX; //"redefine" macro for Visual C++ limits
const int   MIN_INT             = LONG_MIN; //"redefine" macro for Visual C++ limits

const int   SAFE_DATA           = 0;        //when used in conjuction with another condition,
                                            //  it can replace erroneous data

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
    __int32 number1;
    __int32 number2;
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
    if (parseCommands(argc, argv) == e_ERR_NONE)
    {
        addition(g_userData.number1, g_userData.number2);
    }
    else
    {
        printErrorReport();
    }
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
            g_userData.number1 = parseData(argv[2], isReadSuccessful);
            g_userData.number2 = parseData(argv[3], isReadSuccessful);
            /* see parseData() documentation for the justification of the 
               following if test. */
            if ((g_userData.number1 != SAFE_DATA && g_userData.number2 != SAFE_DATA) ||
                 isReadSuccessful != e_ERR_DATA_RANGE)
            {
                isReadSuccessful = e_ERR_NONE;
            }
        }
    }

    return isReadSuccessful;
}

//WARNING: assumes arguments are integers
//TODO: typechecking
int parseData(const char* data, int& error)
{
    //long long is 64 bit signed number
    _int64 operand = strtoll(data, NULL, 10);

    if (error == e_ERR_NONE)
    {
        if (errno == ERANGE)
        {
            g_operandsDataBoundError.dataRange = true;
            error = e_ERR_DATA_RANGE;
        }
        else if (operand > MAX_INT)
        {
            g_operandsDataBoundError.dataRangeHi = true;
            g_operandsDataBoundError.dataRangeHiVal = operand;
            error = e_ERR_DATA_RANGE;
        }
        else if (operand < MIN_INT)
        {

            g_operandsDataBoundError.dataRangeLo = true;
            g_operandsDataBoundError.dataRangeLoVal = operand;
            error = e_ERR_DATA_RANGE;
        }
        else
        {
            error = e_ERR_NONE;
        }

        if (g_operandsDataBoundError.dataRange || g_operandsDataBoundError.dataRangeHi ||
            g_operandsDataBoundError.dataRangeLo)
        {
            operand = SAFE_DATA;
        }
    }

    return operand;
}


bool addition(int num1, int num2)
{
    bool isInRange = true;
    int result = 0;

    result = num1 + num2;

    if ((num2 > 0) && (num1 > (MAX_INT - num2)))
    {
        g_answerDataBoundError.dataRangeHi = true;
    }
    else if ((num2 < 0) && (num1 < (MIN_INT - num2)))
    {
        g_answerDataBoundError.dataRangeLo = true;
    }
    else
    {
        std::cout << "\nSum: " << result << "\n";
        isInRange = true;
    }

    return isInRange;
}