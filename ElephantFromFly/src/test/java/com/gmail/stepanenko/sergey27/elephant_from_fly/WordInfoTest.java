/***************************************************************************************************
 * @file WordInfoTest.java
 * @author Sergey Stepanenko (sergey.stepanenko.27@gmail.com)
 * @description Contains implementation of the WordInfoTest class.
 **************************************************************************************************/

package com.gmail.stepanenko.sergey27.elephant_from_fly;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit Test for WordInfo class.
 */
public class WordInfoTest {

    @Test
    public void test_wholeClass(){
        _test_wholeClass("Word1", 0, false);

        _test_wholeClass("Word2", 10, true);
    }

    // Private methods.

    private void _test_wholeClass(String word, int difference, boolean usage){
        assert word != null;
        assert difference >= 0;

        WordInfo wordInfo = new WordInfo(word, difference);
        Assert.assertEquals(word, wordInfo.getWord());
        Assert.assertEquals(difference, wordInfo.getDifferenceWithTargetWord());
        Assert.assertFalse(wordInfo.getUsageFlag());

        wordInfo.setUsageFlag(usage);
        Assert.assertEquals(usage, wordInfo.getUsageFlag());
    }
} // class WordInfoTest
