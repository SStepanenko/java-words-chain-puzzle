/***************************************************************************************************
 * @file PuzzleException.java
 * @author Sergey Stepanenko (sergey.stepanenko.27@gmail.com)
 * @description Contains implementation of the PuzzleException class.
 **************************************************************************************************/

package com.gmail.stepanenko.sergey27.elephant_from_fly;

/**
 * Exception of Words Chain Puzzle: contains info about all possible errors related to words chain puzzle.
 */
public final class PuzzleException extends Exception {

    // Nested enumerations.

    /**
     * Error codes enumeration.
     */
    public enum ErrorCode {
        // Wrong count of input parameters passed to program.
        WRONG_INPUTS_COUNT,

        // Invalid value of max words chain length parameter.
        INVALID_MAX_WORDS_CHAIN_LENGTH_PARAM_VALUE,

        // Invalid value of timeout parameter.
        INVALID_TIMEOUT_PARAM_VALUE,

        // Failed to read file with start and end words.
        READ_INPUT_WORDS_FILE_ERROR,

        // Failed to read start word.
        READ_START_WORD_ERROR,

        // Failed to read end word.
        READ_END_WORD_ERROR,

        // Failed to read vocabulary file.
        READ_VOCABULARY_FILE_ERROR,

        // Start word is empty.
        EMPTY_START_WORD,

        // Start word is empty.
        EMPTY_END_WORD,

        // Different length of start and end word.
        DIFFERENT_LENGTH_OF_START_AND_END_WORD,

        // Start word is the same as end word.
        START_WORD_EQUALS_TO_END_WORD,

        // End word is absent in vocabulary.
        END_WORD_IS_ABSENT_IN_VOCABULARY,

        // Unknown error.
        UNKNOWN_ERROR
    } // enum ErrorCode

    // Private fields.

    private ErrorCode mErrorCode; // Error code.

    // Public methods.

    /** Default constructor. */
    public PuzzleException(){
        mErrorCode = ErrorCode.UNKNOWN_ERROR;
    }

    /** Constructor.
     * @param errorCode Error code.
     * */
    public PuzzleException(ErrorCode errorCode){
        assert errorCode != null;

        mErrorCode = errorCode;
    }

    /** Constructor.
     * @param message Error message.
     * @param errorCode Error code.
     * */
    public PuzzleException(String message, ErrorCode errorCode){
        super(message);

        assert errorCode != null;

        mErrorCode = errorCode;
    }

    /** Constructor.
     * @param cause Inner exception.
     * @param errorCode Error code.
     * */
    public PuzzleException(Throwable cause, ErrorCode errorCode){
        super(cause);

        assert errorCode != null;

        mErrorCode = errorCode;
    }

    /** Constructor.
     * @param message Error message.
     * @param cause Inner exception.
     * @param errorCode Error code.
     * */
    public PuzzleException(String message, Throwable cause, ErrorCode errorCode){
        super(message, cause);

        assert errorCode != null;

        mErrorCode = errorCode;
    }

    /** Gets error code. */
    public ErrorCode getErrorCode(){
        return mErrorCode;
    }
} // class PuzzleException
