/***************************************************************************************************
 * @file InputWords.java
 * @author Sergey Stepanenko (sergey.stepanenko.27@gmail.com)
 * @description Contains implementation of the InputWords class.
 **************************************************************************************************/

package com.gmail.stepanenko.sergey27.elephant_from_fly;

/** Class contains start and end word of words chain.
 *  Constraints: words can't be empty and must have equal length, also words must be different.
 * */
public final class InputWords {

    // Private fields.

    private String mStartWord; // Start word.

    private String mEndWord; // End word.

    // Public methods.

    /** Constructor.
     *  @param startWord Start word.
     *  @param endWord End word.
     *  @exception PuzzleException Incorrect input parameters (empty words, equal words or words of different length).
     * */
    public InputWords(String startWord, String endWord) throws PuzzleException {
        assert startWord != null;
        assert  endWord != null;

        // Check that start word is not empty.
        if(startWord.isEmpty()){
            throw new PuzzleException(PuzzleException.ErrorCode.EMPTY_START_WORD);
        }

        // Check that end word is not empty.
        if(endWord.isEmpty()){
            throw new PuzzleException(PuzzleException.ErrorCode.EMPTY_END_WORD);
        }

        // Check that input and output words have equal length.
        if(startWord.length() != endWord.length()){
            throw new PuzzleException(PuzzleException.ErrorCode.DIFFERENT_LENGTH_OF_START_AND_END_WORD);
        }

        // Check that start word is not the same as end word.
        if(startWord.equals(endWord)){
            throw new PuzzleException(PuzzleException.ErrorCode.START_WORD_EQUALS_TO_END_WORD);
        }

        mStartWord = startWord;
        mEndWord = endWord;
    }

    /** Gets start word. */
    public String getStartWord(){
        return mStartWord;
    }

    /** Gets end word. */
    public String getEndWord(){
        return mEndWord;
    }

    /** Gets length of words (start and end words have equal length). */
    public int getLength(){
        assert mStartWord.length() == mEndWord.length();

        return mStartWord.length();
    }
} // class InputWords
