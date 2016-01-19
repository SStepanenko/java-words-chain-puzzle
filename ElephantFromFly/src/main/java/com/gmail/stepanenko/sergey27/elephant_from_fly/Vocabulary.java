/***************************************************************************************************
 * @file Vocabulary.java
 * @author Sergey Stepanenko (sergey.stepanenko.27@gmail.com)
 * @description Contains implementation of the Vocabulary class.
 **************************************************************************************************/

package com.gmail.stepanenko.sergey27.elephant_from_fly;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Class represents vocabulary of words.
 * Vocabulary is loaded fro file and anly words of specified length are loaded.
 * On loading all words are converted to lower case.
 */
public final class Vocabulary {

    // Private fields.

    private Set<String> mWordsSet; // Set of words.

    // Public methods.

    /** Constructor. */
    public Vocabulary(){
        mWordsSet = new HashSet<>();
    }

    /** Get words set. */
    public Set<String> getWordsSet(){
        return mWordsSet;
    }

    /** Checks if given word is contained tin vocabulary. */
    public boolean contains(String word){
        assert word != null;

        return mWordsSet.contains(word);
    }

    /** Loads vocabulary from file.
     *  After loading vocabulary will contain words of specified length converted to lower case.
     *  @param fileName Full vocabulary file name with path.
     *  @param wordsLength Length of words in vocabulary, words of other length are ignored.
     *  @exception PuzzleException Failed to load vocabulary from file.
     * */
    public void loadFromFile(String fileName, int wordsLength) throws PuzzleException {
        assert fileName != null;
        assert wordsLength > 0;

        File vocabularyFile = new File(fileName);

        try(
                FileReader fileReader = new FileReader(vocabularyFile); // exception
                BufferedReader bufferedReader = new BufferedReader(fileReader);
        ) {
            // Read vocabulary file.
            _loadVocabulary(bufferedReader, wordsLength);
        } catch(IOException ioException){
            throw  new PuzzleException(ioException, PuzzleException.ErrorCode.READ_VOCABULARY_FILE_ERROR);
        }
    }

    // Private methods.

    /** Loads vocabulary from file.
     *  After loading vocabulary will contain words of specified length converted to lower case.
     *  @param bufferedReader Buffered reader.
     *  @param wordsLength Length of words in vocabulary, words of other length are ignored.
     *  @exception IOException Failed to load vocabulary from file.
     * */
    private void _loadVocabulary(BufferedReader bufferedReader, int wordsLength) throws IOException {
        assert bufferedReader != null;
        assert wordsLength > 0;

        mWordsSet.clear();

        String word;

        while((word = bufferedReader.readLine()) != null){ // exception
            // Trim word and convert to lower case.
            word = word.trim().toLowerCase();

            // Add only words of required length.
            if(word.length() == wordsLength){
                mWordsSet.add(word);
            }
        }
    }
} // class Vocabulary
