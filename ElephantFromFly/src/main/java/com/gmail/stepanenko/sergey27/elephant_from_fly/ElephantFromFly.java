/***************************************************************************************************
 * @file ElephantFromFly.java
 * @author Sergey Stepanenko (sergey.stepanenko.27@gmail.com)
 * @description Contains implementation of the ElephantFromFly class and application entry point.
 **************************************************************************************************/

package com.gmail.stepanenko.sergey27.elephant_from_fly;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Class contains application entry point: main() function.
 */
public final class ElephantFromFly {

    // Private static constants.

    // Required count of inputs to program.
    private static final int REQUIRED_INPUTS_COUNT = 4;

    // Index of the "Input words" parameter.
    private static final int INPUT_WORDS_FILE_NAME_PARAM_INDEX = 0;

    // Index of the "Vocabulary file" parameter.
    private static final int VOCABULARY_FILE_NAME_PARAM_INDEX = 1;

    // Index of the "Maximum words chain length" parameter.
    private static final int MAX_WORDS_CHAIN_LENGTH_PARAM_INDEX = 2;

    // Index of the "Timeout" parameter.
    private static final int TIMEOUT_PARAM_INDEX = 3;

    private static final String WORDS_CHAIN_NOT_FOUND_FORMAT = "Words chain wasn't found: %s -> ... -> %s";

    private static final String SEARCH_INTERRUPTION_ON_TIMEOUT = "Search was interrupted by timeout";

    // Format of message with words chain length.
    private static final String WORDS_CHAIN_LENGTH_FORMAT = "Words chain length: %d";

    private static final String START_WORD = "Start word: ";

    private static final String END_WORD = "End word: ";

    private static final String VOCABULARY_SIZE = "Vocabulary size (count of words of required length): ";

    private static final String MAX_WORDS_CHAIN_LENGTH = "Maximum words chain length: ";

    private static final String TIMEOUT_MINUTES = "Timeout (minutes): ";

    private static final String SEARCH_RESULTS = "Search results:";

    private static final String EXECUTION_TIME = "Execution time: ";

    // Public static methods.

    /** Application entry point.
     *  @param args Array of input arguments:
     *              args[0] - Input words file.
     *              args[1] - Vocabulary file.
     *              args[2] - Max words chain length.
     *              args[3] - Timeout value in minutes.
     * */
    public static void main(String[] args) {
        try{
            // Execution time counter.
            TimeCounter executionTimeCounter = TimeCounter.start();

            // Check arguments count.
            if(args.length < REQUIRED_INPUTS_COUNT){
                throw new PuzzleException(PuzzleException.ErrorCode.WRONG_INPUTS_COUNT);
            }

            // Get input words file name parameter.
            String inputWordsFileName = args[INPUT_WORDS_FILE_NAME_PARAM_INDEX];

            // Load input words from file.
            InputWords inputWords = _loadInputWords(inputWordsFileName); // exception

            // Get vocabulary file name parameter.
            String vocabularyFileName = args[VOCABULARY_FILE_NAME_PARAM_INDEX];

            // Load vocabulary from file.
            Vocabulary vocabulary = new Vocabulary();
            vocabulary.loadFromFile(vocabularyFileName, inputWords.getLength()); // exception

            // Get max words chain length parameter.
            int maxWordsChainLength = _getMaxWordsChainLengthParamValue(args); // exception

            // Gets value of timeout parameter.
            long timeoutValueMinutes = _getTimeoutPramValue(args); // exception

            // Output info about input parameters.
            _outputInputParamsInfo(inputWords, vocabulary.getWordsSet().size(), maxWordsChainLength,
                    timeoutValueMinutes);

            // Solve words chain puzzle.
            WordsChainPuzzle wordsChainPuzzle = new WordsChainPuzzle();
            WordsChainPuzzle.Result puzzleResult = wordsChainPuzzle.solve(
                    inputWords, vocabulary, maxWordsChainLength, timeoutValueMinutes); // exception

            // Output results.
            _outputWordsChainPuzzleResult(puzzleResult);

            // Print execution time: hh:m:ss.SSS.
            String executionTimeStr = executionTimeCounter.getPassedTimeAsString();
            System.out.println("");
            System.out.println(EXECUTION_TIME + executionTimeStr);

        } catch(Throwable error){
            // Handle error.
            ErrorsHandler.handleError(error);
        }
    }

    // Private static methods.

    /** Loads input words from file.
     * @param fileName Full file name of input words file with path.
     * @return Input words loaded from file.
     * @exception PuzzleException Failed to load input words.
     * */
    private static InputWords _loadInputWords(String fileName) throws PuzzleException {
        assert fileName != null;

        File inputWordsFile = new File(fileName);

        InputWords inputWords;

        try(
                FileReader fileReader = new FileReader(inputWordsFile); // exception
                BufferedReader bufferedReader = new BufferedReader(fileReader);
        ) {

            // Read start word.
            String startWord = bufferedReader.readLine(); // exception

            if(startWord == null){
                throw new PuzzleException(PuzzleException.ErrorCode.READ_START_WORD_ERROR);
            }

            // Convert word lo lower case and trim leading and trailing spaces.
            startWord = startWord.trim().toLowerCase();

            // Read end word.
            String endWord = bufferedReader.readLine(); // exception

            if(endWord == null){
                throw new PuzzleException(PuzzleException.ErrorCode.READ_END_WORD_ERROR);
            }

            // Convert word lo lower case and trim leading and trailing spaces.
            endWord = endWord.trim().toLowerCase();

            inputWords = new InputWords(startWord, endWord); // exception
        } catch(IOException ioException){
            throw  new PuzzleException(ioException, PuzzleException.ErrorCode.READ_INPUT_WORDS_FILE_ERROR);
        }

        return inputWords;
    }

    /** Gets value of maximum words chain length parameter.
     *  @param args Array of input parameters passed to program.
     *  @return Value of maximum words chain length parameter.
     *  @exception PuzzleException Invalid value of maximum words chain length parameter.
     * */
    private static int _getMaxWordsChainLengthParamValue(String[] args) throws PuzzleException {
        assert args != null;

        int maxWordsChainLength = 0;

        try {

            String maxWordsChainLengthStr = args[MAX_WORDS_CHAIN_LENGTH_PARAM_INDEX];

            maxWordsChainLength = Integer.valueOf(maxWordsChainLengthStr); // exception

            if(maxWordsChainLength <= 0){
                throw new PuzzleException(PuzzleException.ErrorCode.INVALID_MAX_WORDS_CHAIN_LENGTH_PARAM_VALUE);
            }

        } catch (NumberFormatException exception){
            throw new PuzzleException(exception, PuzzleException.ErrorCode.INVALID_MAX_WORDS_CHAIN_LENGTH_PARAM_VALUE);
        }

        return maxWordsChainLength;
    }

    /** Gets value of timeout parameter in minutes.
     *  @param args Array of input parameters passed to program.
     *  @return Value of timeout parameter in minutes.
     *  @exception PuzzleException Invalid value of timeout parameter.
     * */
    private static long _getTimeoutPramValue(String[] args) throws PuzzleException {
        assert args != null;

        long timeoutValue = 0;

        try {
            String timeoutStr = args[TIMEOUT_PARAM_INDEX];

            timeoutValue = Long.valueOf(timeoutStr); // exception

            if(timeoutValue <= 0){
                throw new PuzzleException(PuzzleException.ErrorCode.INVALID_TIMEOUT_PARAM_VALUE);
            }
        } catch (NumberFormatException exception){
            throw new PuzzleException(exception, PuzzleException.ErrorCode.INVALID_TIMEOUT_PARAM_VALUE);
        }

        return timeoutValue;
    }

    /** Outputs info about input parameters.
     *  @param inputWords Input words.
     *  @param vocabularySize Vocabulary size.
     *  @param maxWordsChainLength Maximum words chain length.
     *  @param timeout Value of timeout in minutes.
     * */
    private static void _outputInputParamsInfo(InputWords inputWords, int vocabularySize,
                                               int maxWordsChainLength, long timeout) {
        assert inputWords != null;
        assert vocabularySize > 0;
        assert maxWordsChainLength > 0;
        assert timeout > 0;

        String startWordText = START_WORD + inputWords.getStartWord();
        System.out.println(startWordText);

        String endWordText = END_WORD + inputWords.getEndWord();
        System.out.println(endWordText);

        String vocabularySizeText = VOCABULARY_SIZE + vocabularySize;
        System.out.println(vocabularySizeText);

        String maxWordsChainLengthText = MAX_WORDS_CHAIN_LENGTH + maxWordsChainLength;
        System.out.println(maxWordsChainLengthText);

        String timeoutText = TIMEOUT_MINUTES + timeout;
        System.out.println(timeoutText);

        System.out.println("");
    }

    /** Outputs words chain puzzle results.
     *  @param result Words chain puzzle result.
     * */
    private static void _outputWordsChainPuzzleResult(WordsChainPuzzle.Result result){
        assert result != null;

        System.out.println(SEARCH_RESULTS);
        System.out.println("");

        if(result.isInterruptedByTimeoutFlag()){
            System.out.println(SEARCH_INTERRUPTION_ON_TIMEOUT);
        }

        if(result.isEmpty()){
            // Output info that words chain wasn't found.
            String emptyResult = String.format(WORDS_CHAIN_NOT_FOUND_FORMAT, result.getInputWords().getStartWord(),
                    result.getInputWords().getEndWord());

            System.out.println(emptyResult);
        } else {
            // Print words chain length.
            String wordsChainLengthStr = String.format(WORDS_CHAIN_LENGTH_FORMAT, result.getWordsChain().size());
            System.out.println(wordsChainLengthStr);

            System.out.println("");

            // Print list of words in chain.
            for(String word : result.getWordsChain()){
                System.out.println(word);
            }
        }
    }
} // class ElephantFromFly
