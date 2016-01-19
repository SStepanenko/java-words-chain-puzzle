/***************************************************************************************************
 * @file PuzzleExceptionTest.java
 * @author Sergey Stepanenko (sergey.stepanenko.27@gmail.com)
 * @description Contains implementation of the PuzzleExceptionTest class.
 **************************************************************************************************/

package com.gmail.stepanenko.sergey27.elephant_from_fly;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

/**
 * Unit Test for PuzzleException class.
 */
public class PuzzleExceptionTest {

    @Test
    public void test_constructor(){
        _test_constructor(PuzzleException.ErrorCode.WRONG_INPUTS_COUNT);
        _test_constructor(PuzzleException.ErrorCode.READ_INPUT_WORDS_FILE_ERROR);

        _test_constructor("Some message", PuzzleException.ErrorCode.READ_START_WORD_ERROR);
        _test_constructor("Some another message", PuzzleException.ErrorCode.READ_END_WORD_ERROR);

        _test_constructor(new Exception(), PuzzleException.ErrorCode.READ_VOCABULARY_FILE_ERROR);
        _test_constructor(new IOException(), PuzzleException.ErrorCode.EMPTY_START_WORD);

        _test_constructor("Illegal argument", new IllegalArgumentException(),
                PuzzleException.ErrorCode.START_WORD_EQUALS_TO_END_WORD);
        _test_constructor("Read file error", new IOException(), PuzzleException.ErrorCode.READ_VOCABULARY_FILE_ERROR);
    }

    // Private methods.

    private void _test_constructor(PuzzleException.ErrorCode errorCode){
        PuzzleException exception = new PuzzleException(errorCode);

        Assert.assertEquals(errorCode, exception.getErrorCode());
    }

    private void _test_constructor(String message, PuzzleException.ErrorCode errorCode){
        PuzzleException exception = new PuzzleException(message, errorCode);

        Assert.assertEquals(message, exception.getMessage());
        Assert.assertEquals(errorCode, exception.getErrorCode());
    }

    private void _test_constructor(Throwable cause, PuzzleException.ErrorCode errorCode){
        PuzzleException exception = new PuzzleException(cause, errorCode);

        Assert.assertEquals(cause, exception.getCause());
        Assert.assertEquals(errorCode, exception.getErrorCode());
    }

    private void _test_constructor(String message, Throwable cause, PuzzleException.ErrorCode errorCode){
        PuzzleException exception = new PuzzleException(message, cause, errorCode);

        Assert.assertEquals(message, exception.getMessage());
        Assert.assertEquals(cause, exception.getCause());
        Assert.assertEquals(errorCode, exception.getErrorCode());
    }
} // class PuzzleExceptionTest
