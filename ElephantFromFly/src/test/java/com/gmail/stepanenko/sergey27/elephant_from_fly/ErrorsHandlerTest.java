/***************************************************************************************************
 * @file ErrorsHandlerTest.java
 * @author Sergey Stepanenko (sergey.stepanenko.27@gmail.com)
 * @description Contains implementation of the ErrorsHandlerTest class.
 **************************************************************************************************/

package com.gmail.stepanenko.sergey27.elephant_from_fly;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Unit Test for ErrorsHandler class.
 */
public class ErrorsHandlerTest {

    @Test
    public void test_getErrorMessage(){
        Map<PuzzleException.ErrorCode, String> errorsMapExpected =
                new HashMap<>(PuzzleException.ErrorCode.values().length);

        errorsMapExpected.put(PuzzleException.ErrorCode.WRONG_INPUTS_COUNT, "Wrong count of input parameters, " +
                "expected 4 inputs: \n 1) name of file with start and end word; \n 2) name of file with vocabulary;" +
                "3) timeout value in minutes \n 4) Max words chain length");
        errorsMapExpected.put(PuzzleException.ErrorCode.INVALID_MAX_WORDS_CHAIN_LENGTH_PARAM_VALUE,
                "Invalid value of maximum words chain length parameter (it is integer value and should be more than zero)");
        errorsMapExpected.put(PuzzleException.ErrorCode.INVALID_TIMEOUT_PARAM_VALUE,
                "Invalid value of timeout parameter (it is integer value in minutes and should be more than zero)");
        errorsMapExpected.put(PuzzleException.ErrorCode.READ_INPUT_WORDS_FILE_ERROR,
                "Failed to read file with start and end words");
        errorsMapExpected.put(PuzzleException.ErrorCode.READ_START_WORD_ERROR, "Failed to read start word");
        errorsMapExpected.put(PuzzleException.ErrorCode.READ_END_WORD_ERROR, "Failed to read end word");
        errorsMapExpected.put(PuzzleException.ErrorCode.READ_VOCABULARY_FILE_ERROR, "Failed to read vocabulary file");
        errorsMapExpected.put(PuzzleException.ErrorCode.EMPTY_START_WORD, "Start word is empty");
        errorsMapExpected.put(PuzzleException.ErrorCode.EMPTY_END_WORD, "End word is empty");
        errorsMapExpected.put(PuzzleException.ErrorCode.DIFFERENT_LENGTH_OF_START_AND_END_WORD,
                "Invalid input parameters: length of start word is not equal to length of end word");
        errorsMapExpected.put(PuzzleException.ErrorCode.START_WORD_EQUALS_TO_END_WORD,
                "Start word is the same as end word");
        errorsMapExpected.put(PuzzleException.ErrorCode.END_WORD_IS_ABSENT_IN_VOCABULARY,
                "End word is absent in vocabulary");
        errorsMapExpected.put(PuzzleException.ErrorCode.UNKNOWN_ERROR, "Unknown error");

        for(PuzzleException.ErrorCode errorCode : PuzzleException.ErrorCode.values()){
             Assert.assertEquals(errorsMapExpected.get(errorCode), ErrorsHandler.getErrorMessage(errorCode));
        }
    }
} // class ErrorsHandlerTest
