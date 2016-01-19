/***************************************************************************************************
 * @file WordsChainPuzzleTest.java
 * @author Sergey Stepanenko (sergey.stepanenko.27@gmail.com)
 * @description Contains implementation of the WordsChainPuzzleTest class.
 **************************************************************************************************/

package com.gmail.stepanenko.sergey27.elephant_from_fly;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Unit Test for WordsChainPuzzle class.
 */
public class WordsChainPuzzleTest {

    // Private static constants.

    private static final long DEFAULT_TIMEOUT_VALUE_MINUTES = 1;

    // Public methods.

    @Test
    public void test_solve_emptyVocabulary() throws PuzzleException {
        InputWords inputWords = new InputWords("cat", "dog");
        Vocabulary vocabulary = new Vocabulary();

        WordsChainPuzzle wordsChainPuzzle = new WordsChainPuzzle();

        boolean isExceptionThrown = false;

        try {
            wordsChainPuzzle.solve(inputWords, vocabulary, 5, DEFAULT_TIMEOUT_VALUE_MINUTES); // exception
        } catch(PuzzleException exception){
            Assert.assertEquals(PuzzleException.ErrorCode.END_WORD_IS_ABSENT_IN_VOCABULARY, exception.getErrorCode());
            isExceptionThrown = true;
        }

        Assert.assertTrue(isExceptionThrown);
    }

    @Test
    public void test_solve() throws PuzzleException {
        InputWords inputWords;
        Vocabulary vocabulary;
        List<String> expectedWordsChain;

        vocabulary = new Vocabulary();

        // Words chain not found.
        inputWords = new InputWords("cat", "dog");
        vocabulary.getWordsSet().addAll(Arrays.asList("dog"));
        expectedWordsChain = new ArrayList<>();
        _test_solve(inputWords, vocabulary, 5, expectedWordsChain); // exception

        // Look for words chain: "cat" -> ... -> "dog"
        // Vocabulary doesn't contain start word (it is not necessary).
        // Several words chains exist:
        //     "cat" -> "cot" -> "cog" -> "dog"
        //     "cat" -> "cot" -> "cog" -> "cig" -> "dig" -> "dog"
        inputWords = new InputWords("cat", "dog");
        vocabulary.getWordsSet().clear();
        vocabulary.getWordsSet().addAll(Arrays.asList("dig", "gig", "cig", "dog", "cat", "cog", "cot"));
        expectedWordsChain = new ArrayList<>(Arrays.asList("cat", "cot", "cog", "dog"));
        _test_solve(inputWords, vocabulary, 7, expectedWordsChain); // exception
        _test_solve(inputWords, vocabulary, 4, expectedWordsChain); // exception

        // Test max words chain length constraint less than minimum possible words chain length.
        expectedWordsChain = new ArrayList<>();
        _test_solve(inputWords, vocabulary, 3, expectedWordsChain); // exception

        // Test reversed words chain search: "dog" -> "cog" -> "cot" -> "cat"
        vocabulary.getWordsSet().add("cat");
        inputWords = new InputWords("dog", "cat");
        expectedWordsChain = new ArrayList<>(Arrays.asList("dog", "cog", "cot", "cat"));
        _test_solve(inputWords, vocabulary, 4, expectedWordsChain); // exception

        // Look for not existent words chain: "fox" -> "cat"
        inputWords = new InputWords("fox", "cat");
        expectedWordsChain = new ArrayList<>();
        _test_solve(inputWords, vocabulary, 5, expectedWordsChain); // exception

        // Add 4-symbol length words to vocabulary.
        vocabulary.getWordsSet().clear();
        vocabulary.getWordsSet().addAll(Arrays.asList("gold", "good", "load", "lead", "coat", "goat", "goad"));

        // Look for words chain: "lead" -> "load" -> "goad" -> "gold"
        inputWords = new InputWords("lead", "gold");
        expectedWordsChain = new ArrayList<>(Arrays.asList("lead", "load", "goad", "gold"));
        _test_solve(inputWords, vocabulary, 4, expectedWordsChain); // exception

        // Look for not existent words chain: "lift" -> "goat"
        inputWords = new InputWords("lift", "goat");
        expectedWordsChain = new ArrayList<>();
        _test_solve(inputWords, vocabulary, 5, expectedWordsChain); // exception
    }

    // Private methods.

    private void _test_solve(InputWords inputWords, Vocabulary vocabulary, int maxWordsChainLength,
                             List<String> expectedWordsChain) throws PuzzleException {
        assert inputWords != null;
        assert vocabulary != null;
        assert expectedWordsChain != null;

        WordsChainPuzzle wordsChainPuzzle = new WordsChainPuzzle();

        WordsChainPuzzle.Result result = wordsChainPuzzle.solve(inputWords, vocabulary, maxWordsChainLength,
                DEFAULT_TIMEOUT_VALUE_MINUTES); // exception

        Assert.assertEquals(inputWords.getStartWord(), result.getInputWords().getStartWord());
        Assert.assertEquals(inputWords.getEndWord(), result.getInputWords().getEndWord());
        Assert.assertEquals(expectedWordsChain, result.getWordsChain());
        Assert.assertFalse(result.isInterruptedByTimeoutFlag());
    }
} // class WordsChainPuzzleTest
