/***************************************************************************************************
 * @file InputWordsTest.java
 * @author Sergey Stepanenko (sergey.stepanenko.27@gmail.com)
 * @description Contains implementation of the InputWordsTest class.
 **************************************************************************************************/

package com.gmail.stepanenko.sergey27.elephant_from_fly;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit Test for InputWords class.
 */
public class InputWordsTest {

    @Test
    public void test_constructor_emptyStartWord(){
        _test_constructor_exception("", "EndWord", PuzzleException.ErrorCode.EMPTY_START_WORD);
    }

    @Test
    public void test_constructor_emptyEndWord(){
        _test_constructor_exception("StartWord", "", PuzzleException.ErrorCode.EMPTY_END_WORD);
    }

    @Test
    public void test_constructor_emptyWords(){
        _test_constructor_exception("", "", PuzzleException.ErrorCode.EMPTY_START_WORD);
    }

    @Test
    public void test_constructor_differentLengthOfStartAndEndWord(){
        _test_constructor_exception("Word", "Word2",
                PuzzleException.ErrorCode.DIFFERENT_LENGTH_OF_START_AND_END_WORD);
    }

    @Test
    public void test_constructor_startWordEqualsToEndWord(){
        _test_constructor_exception("Word", "Word",
                PuzzleException.ErrorCode.START_WORD_EQUALS_TO_END_WORD);
    }

    @Test
    public void test_constructorAndGetters() throws PuzzleException {
        _test_constructorAndGetters("a", "b");

        _test_constructorAndGetters("Word1", "Word2");
    }

    // Private methods.

    private void _test_constructor_exception(String startWord, String endWord,
                                             PuzzleException.ErrorCode expectedErrorCode) {
        boolean isExceptionThrown = false;

        try{
            new InputWords(startWord, endWord); // exception
        } catch(PuzzleException exception){
            Assert.assertEquals(expectedErrorCode, exception.getErrorCode());
            isExceptionThrown = true;
        }

        Assert.assertTrue(isExceptionThrown);
    }

    private void _test_constructorAndGetters(String startWord, String endWord) throws PuzzleException {
        InputWords inputWords = new InputWords(startWord, endWord);

        Assert.assertEquals(startWord, inputWords.getStartWord());

        Assert.assertEquals(endWord, inputWords.getEndWord());

        Assert.assertEquals(startWord.length(), inputWords.getLength());
    }
} // class InputWordsTest
