/***************************************************************************************************
 * @file VocabularyTest.java
 * @author Sergey Stepanenko (sergey.stepanenko.27@gmail.com)
 * @description Contains implementation of the VocabularyTest class.
 **************************************************************************************************/

package com.gmail.stepanenko.sergey27.elephant_from_fly;

import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Unit Test for Vocabulary class.
 */
public class VocabularyTest {

    // Private static constants.

    private static final String VOCABULARY_FILE_PATH = "unit_tests_data\\Vocabulary.txt";

    // Public methods.

    @Test
    public void test_constructor(){
        Vocabulary vocabulary = new Vocabulary();

        Assert.assertTrue(vocabulary.getWordsSet() != null);
        Assert.assertTrue(vocabulary.getWordsSet().isEmpty());
    }

    @Test
    public void test_contains(){
        Vocabulary vocabulary = new Vocabulary();

        String newWord1 = "elephant";
        Assert.assertTrue(vocabulary.getWordsSet().isEmpty());
        Assert.assertTrue(!vocabulary.contains(newWord1));

        vocabulary.getWordsSet().add(newWord1);
        Assert.assertTrue(vocabulary.contains(newWord1));
        Assert.assertEquals(1, vocabulary.getWordsSet().size());

        vocabulary.getWordsSet().add(newWord1);
        Assert.assertTrue(vocabulary.contains(newWord1));
        Assert.assertEquals(1, vocabulary.getWordsSet().size());

        String newWord2 = "fly";
        Assert.assertTrue(!vocabulary.contains(newWord2));

        vocabulary.getWordsSet().add(newWord2);
        Assert.assertTrue(vocabulary.contains(newWord2));
        Assert.assertTrue(vocabulary.contains(newWord1));
        Assert.assertEquals(2, vocabulary.getWordsSet().size());

        vocabulary.getWordsSet().clear();
        Assert.assertTrue(!vocabulary.contains(newWord1));
        Assert.assertTrue(!vocabulary.contains(newWord2));
        Assert.assertTrue(vocabulary.getWordsSet().isEmpty());
    }

    @Test
    public void test_loadFromFile_invalidFileName() {
        Vocabulary vocabulary = new Vocabulary();

        boolean isExceptionThrown = false;

        try {
            vocabulary.loadFromFile("@#WrongFileName#@", 5); // exception
        } catch (PuzzleException exception){
            Assert.assertEquals(PuzzleException.ErrorCode.READ_VOCABULARY_FILE_ERROR, exception.getErrorCode());
            Assert.assertTrue(exception.getCause() instanceof IOException);
            isExceptionThrown = true;
        }

        Assert.assertTrue(isExceptionThrown);
    }

    @Test
    public void test_loadFromFile() throws IOException, PuzzleException {
        int maxWordLengthInVocabulary = _test_loadFromFile(VOCABULARY_FILE_PATH, 1); // exception

        _test_loadFromFile(VOCABULARY_FILE_PATH, 3); // exception

        _test_loadFromFile(VOCABULARY_FILE_PATH, 5); // exception

        _test_loadFromFile(VOCABULARY_FILE_PATH, maxWordLengthInVocabulary); // exception

        _test_loadFromFile(VOCABULARY_FILE_PATH, maxWordLengthInVocabulary + 1); // exception
    }

    // Private methods.

    private int _loadVocabularyFromFile(String fileName, int wordsLength, Set<String> wordsSet) throws IOException {
        assert fileName != null;
        assert wordsLength > 0;
        assert wordsSet != null;

        wordsSet.clear();

        File vocabularyFile = new File(fileName);

        int maxWordLength = 0;

        try(
                FileReader fileReader = new FileReader(vocabularyFile); // exception
                BufferedReader bufferedReader = new BufferedReader(fileReader);
        ) {
            String word;

            while((word = bufferedReader.readLine()) != null){ // exception
                // Trim word and convert to lower case.
                word = word.trim().toLowerCase();

                maxWordLength = (word.length() > maxWordLength ? word.length() : maxWordLength);

                // Add only words of required length.
                if(word.length() == wordsLength){
                    wordsSet.add(word);
                }
            }
        }

        return maxWordLength;
    }

    private int _test_loadFromFile(String fileName, int wordsLength) throws IOException, PuzzleException {
        Set<String> expectedWordsSet = new HashSet<>();

        int maxWordLength = _loadVocabularyFromFile(fileName, wordsLength, expectedWordsSet); // exception

        Vocabulary vocabulary = new Vocabulary();
        vocabulary.loadFromFile(fileName, wordsLength); // exception

        // Verify that words set are equal.
        Assert.assertEquals(expectedWordsSet, vocabulary.getWordsSet());

        return maxWordLength;
    }
} // class VocabularyTest
