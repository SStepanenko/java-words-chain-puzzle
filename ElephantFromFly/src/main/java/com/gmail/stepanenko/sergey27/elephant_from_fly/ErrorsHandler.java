/***************************************************************************************************
 * @file ErrorsHandler.java
 * @author Sergey Stepanenko (sergey.stepanenko.27@gmail.com)
 * @description Contains implementation of the ErrorsHandler class.
 **************************************************************************************************/

package com.gmail.stepanenko.sergey27.elephant_from_fly;

import java.util.HashMap;
import java.util.Map;

/**
 * Errors handler: provides error messages for error codes.
 * */
public final class ErrorsHandler {

    // Private static constants.

    // Mapping of error codes to error messages.
    private static final Map<PuzzleException.ErrorCode, String> mErrorsMap;

    static {
        mErrorsMap = new HashMap<>(PuzzleException.ErrorCode.values().length);

        mErrorsMap.put(PuzzleException.ErrorCode.WRONG_INPUTS_COUNT, "Wrong count of input parameters, " +
                "expected 4 inputs: \n 1) File with start and end word; \n 2) File with vocabulary; \n " +
                "3) Timeout value in minutes; \n 4) Max words chain length");

        mErrorsMap.put(PuzzleException.ErrorCode.INVALID_MAX_WORDS_CHAIN_LENGTH_PARAM_VALUE,
                "Invalid value of maximum words chain length parameter (it is integer value and should be more than zero)");

        mErrorsMap.put(PuzzleException.ErrorCode.INVALID_TIMEOUT_PARAM_VALUE,
                "Invalid value of timeout parameter (it is integer value in minutes and should be more than zero)");

        mErrorsMap.put(PuzzleException.ErrorCode.READ_INPUT_WORDS_FILE_ERROR,
                "Failed to read file with start and end words");

        mErrorsMap.put(PuzzleException.ErrorCode.READ_START_WORD_ERROR, "Failed to read start word");

        mErrorsMap.put(PuzzleException.ErrorCode.READ_END_WORD_ERROR, "Failed to read end word");

        mErrorsMap.put(PuzzleException.ErrorCode.READ_VOCABULARY_FILE_ERROR, "Failed to read vocabulary file");

        mErrorsMap.put(PuzzleException.ErrorCode.EMPTY_START_WORD, "Start word is empty");

        mErrorsMap.put(PuzzleException.ErrorCode.EMPTY_END_WORD, "End word is empty");

        mErrorsMap.put(PuzzleException.ErrorCode.DIFFERENT_LENGTH_OF_START_AND_END_WORD,
                "Invalid input parameters: length of start word is not equal to length of end word");

        mErrorsMap.put(PuzzleException.ErrorCode.START_WORD_EQUALS_TO_END_WORD,
                "Start word is the same as end word");

        mErrorsMap.put(PuzzleException.ErrorCode.END_WORD_IS_ABSENT_IN_VOCABULARY,
                "End word is absent in vocabulary");

        mErrorsMap.put(PuzzleException.ErrorCode.UNKNOWN_ERROR, "Unknown error");
    }

    // Public static methods.

    /** Gets error message for given error code.
     *  @pram errorCode Error code.
     *  @return Error message.
     * */
    public static String getErrorMessage(PuzzleException.ErrorCode errorCode){
        assert errorCode != null;

        return mErrorsMap.get(errorCode);
    }

    /** Handles error and prints error message and stack trace if necessary.
     * */
    public static  void handleError(Throwable error){
        assert error != null;

        if(error instanceof PuzzleException) {
            PuzzleException puzzleException = (PuzzleException)error;

            String errorMessage = getErrorMessage(puzzleException.getErrorCode());

            System.out.println(errorMessage);
        } else {
            // Handle unknown error.

            if(error.getCause() != null){
                error.getCause().printStackTrace(System.out);
            } else {
                error.printStackTrace(System.out);
            }
        }

    }
} // class ErrorsHandler