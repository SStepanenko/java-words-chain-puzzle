/***************************************************************************************************
 * @file WordInfo.java
 * @author Sergey Stepanenko (sergey.stepanenko.27@gmail.com)
 * @description Contains implementation of the WordInfo class.
 **************************************************************************************************/

package com.gmail.stepanenko.sergey27.elephant_from_fly;

/**
 * Class contains word information.
 * This class is used in the process of words chain search.
 */
public final class WordInfo {

    // Private fields.

    private String mWord; // Word.

    private int mDifferenceWithTargetWord; // Difference with target word.

    private boolean mUsageFlag; // Flag defines if this word is used in words chain.

    // Public methods.

    /** Constructor.
     *  @param word Word.
     *  @param differenceWithTargetWord Difference with target word.
     * */
    public WordInfo(String word, int differenceWithTargetWord){
        assert word != null;
        assert differenceWithTargetWord >= 0;

        mWord = word;
        mDifferenceWithTargetWord = differenceWithTargetWord;
    }

    /** Gets word. */
    public String getWord(){
        return mWord;
    }

    /** Gets difference with target word. */
    public int getDifferenceWithTargetWord(){
        return mDifferenceWithTargetWord;
    }

    /** Gets usage flag. */
    public boolean getUsageFlag(){
        return mUsageFlag;
    }

    /** Sets usage flag. */
    public void setUsageFlag(boolean value){
        mUsageFlag = value;
    }
} // class WordInfo
