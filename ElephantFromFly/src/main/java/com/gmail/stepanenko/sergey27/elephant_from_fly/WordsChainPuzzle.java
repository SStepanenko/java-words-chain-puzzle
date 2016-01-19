/***************************************************************************************************
 * @file WordsChainPuzzle.java
 * @author Sergey Stepanenko (sergey.stepanenko.27@gmail.com)
 * @description Contains implementation of the WordsChainPuzzle class.
 **************************************************************************************************/

package com.gmail.stepanenko.sergey27.elephant_from_fly;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Words chain puzzle algorithm: looks for the shortest words chain from start word to end word using given vocabulary.
 * Each word in chain must be one character different from previous.
 */
public final class WordsChainPuzzle {

    // Nested classes.

    /**
     * Result of words chain search.
     */
    public static final class Result {

        // Private fields.

        private InputWords mInputWords; // Input words.

        private List<String> mWordsChain; // Words chain.

        private boolean mInterruptionByTimeoutFlag; // Flag defines if search process was interrupted by timeout.

        // Public methods.

        /** Constructor.
         *  @param inputWords Input words.
         * */
        public Result(InputWords inputWords){
            assert inputWords != null;

            mInputWords = inputWords;
            mWordsChain = new ArrayList<>();
            mInterruptionByTimeoutFlag = false;
        }

        /** Gets input words. */
        public InputWords getInputWords(){
            return mInputWords;
        }

        /** Gets words chain. */
        public List<String> getWordsChain(){
            return mWordsChain;
        }

        /** Check if result is empty. */
        public boolean isEmpty(){
            return mWordsChain.isEmpty();
        }

        /** Checks if search process was interrupted by timeout. */
        public boolean isInterruptedByTimeoutFlag(){
            return mInterruptionByTimeoutFlag;
        }

        /** Sets interruption by timeout flag. */
        public void setInterruptionByTimeoutFlag(boolean value){
            mInterruptionByTimeoutFlag = value;
        }
    } // class Result

    // Private fields.

    private InputWords mInputWords; // Input words.

    private Vocabulary mVocabulary; // Vocabulary.

    private int mMaxWordsChainLength; // Maximum words chain length.

    private long mTimeoutMinutes; // Time in minutes after which search process should be interrupted.

    // List of of WordInfo objects.
    // Words are sorted in difference with target word ascending order.
    private List<WordInfo> mVocabularyWordsInfo;

    // Double ended queue of ListCursor<WordInfo> objects.
    // It is built in the process of search. WordInfo of start word is not added.
    // Index of element in deque represents position of  word in words chain.
    // When search is started first element is added to deque: cursor which is pointed to the first element
    // in mVocabularyWordsInfo list.
    // On each stage cursor is moved forward while appropriate (1-character length from previous and not used)
    // word is not found. When such word is found then algorithm goes to next stage and new cursor is added to queue
    // which is pointed to the first element in mVocabularyWordsInfo list.
    // When on current stage search reaches dead end algorithms rolls back to previous stage and search is continued.
    // Search is completed when mWordsChainDeque contains one cursor which is pointed to the last element
    // in the mVocabularyWordsInfo list (pr it can be interrupted by timeout).
    private Deque<ListCursor<WordInfo>> mWordsChainDeque;

    // Public methods.

    /** Constructor. */
    public WordsChainPuzzle(){
    }

    /** Solves words chain puzzle.
     *  @param inputWords Input words.
     *  @param vocabulary Vocabulary.
     *  @param maxWordsChainLength Maximum length of words chain.
     *  @param timeoutMinutes Time in minutes after which search process should be interrupted.
     *  @return Result of words chain search.
     *  @exception PuzzleException Incorrect input parameters.
     * */
    public Result solve(InputWords inputWords, Vocabulary vocabulary, int maxWordsChainLength,
                        long timeoutMinutes) throws PuzzleException {
        assert inputWords != null;
        assert vocabulary != null;
        assert maxWordsChainLength > 0;
        assert timeoutMinutes > 0;

        // Check that end word is in vocabulary.
        if(!vocabulary.contains(inputWords.getEndWord())){
            throw new PuzzleException(PuzzleException.ErrorCode.END_WORD_IS_ABSENT_IN_VOCABULARY);
        }

        // Store inputs.
        mInputWords = inputWords;
        mVocabulary = vocabulary;
        mMaxWordsChainLength = maxWordsChainLength;
        mTimeoutMinutes = timeoutMinutes;

        // Prepare vocabulary words info: sort words in difference with end word ascending order.
        _prepareVocabularyWordsInfo();

        // look for shortest words chain.
        Result searchResult = _lookForShortestWordsChain();

        return searchResult;
    }

    // Private static methods.

    /** Calculate difference between two words (count of different characters in appropriate positions). */
    private static int _calculateWordsDifference(String word1, String word2){
        assert word1 != null;
        assert word2 != null;
        assert word1.length() == word2.length();

        int difference = 0;

        for(int i = 0; i < word1.length(); i++){
            if(word1.charAt(i) != word2.charAt(i)){
                difference++;
            }
        }

        return difference;
    }

    // Private methods.

    /**  Prepare vocabulary words info: sort words in difference with end word ascending order. */
    private void _prepareVocabularyWordsInfo() {
        Set<String> wordsSet = mVocabulary.getWordsSet();

        mVocabularyWordsInfo = new ArrayList<>(wordsSet.size());

        // Create list of WordInfo objects from vocabulary.
        for(String word : wordsSet){
            if(!word.equals(mInputWords.getStartWord())) {
                // Word is not equal to start word.

                int differenceWithEndWord = _calculateWordsDifference(word, mInputWords.getEndWord());

                WordInfo wordInfo = new WordInfo(word, differenceWithEndWord);

                mVocabularyWordsInfo.add(wordInfo);
            } // else skip start word
        } // for

        // Comparator used to sort words in difference with end word ascending order.
        Comparator<WordInfo> wordInfoComparator = new Comparator<WordInfo>() {
            @Override
            public int compare(WordInfo wordInfo1, WordInfo wordInfo2) {
                int result = 0;

                if(wordInfo1.getDifferenceWithTargetWord() < wordInfo2.getDifferenceWithTargetWord()){
                    result = -1;
                } else if(wordInfo1.getDifferenceWithTargetWord() > wordInfo2.getDifferenceWithTargetWord()){
                    result = 1;
                } else {
                    result = wordInfo1.getWord().compareTo(wordInfo2.getWord());
                }

                return result;
            }
        };

        // Sort words in difference with end word ascending order.
        Collections.sort(mVocabularyWordsInfo, wordInfoComparator);
    }

    /** Gets previous word for the current stage of search.
     *  On the 1-st stage previous word is a start word. */
    private String _getPreviousWord(){
        // Create backward iterator pointed to the end of words chain deque.
        Iterator<ListCursor<WordInfo>> descendingIterator = mWordsChainDeque.descendingIterator();

        String previousWord;

        if(descendingIterator.hasNext()){
            descendingIterator.next();

            if(descendingIterator.hasNext()){
                WordInfo previousWordInfo = descendingIterator.next().getElement();
                previousWord = previousWordInfo.getWord();
            } else {
                previousWord = mInputWords.getStartWord();
            }

        } else {
            previousWord = mInputWords.getStartWord();
        }

        return previousWord;
    }

    /** Gets current word fo this moment of search. */
    private String _getCurrentWord(){
        ListCursor<WordInfo> wordsInfoListCursor = mWordsChainDeque.getLast();

        String currentWord = wordsInfoListCursor.getElement().getWord();

        return currentWord;
    }

    /** Checks if in this moment of search cursor points to the word which is one character different
     * from previous word in chain. */
    private boolean _isCursorAtWordOneCharDifferentFromPrevious(){
        assert mWordsChainDeque.size() > 0;

        String previousWord = _getPreviousWord();

        String currentWord = _getCurrentWord();

        int wordsDifference = _calculateWordsDifference(previousWord, currentWord);

        return (wordsDifference == 1);
    }

    /** Checks if search reached target word. */
    private boolean _isEndWordInChainReached(){
        String currentWord = _getCurrentWord();

        boolean result = currentWord.equals(mInputWords.getEndWord());

        return result;
    }

    /** Gets current words chain length. */
    private int _getCurrentWordsChainLength(){
        int length = mWordsChainDeque.size() + 1;

        return length;
    }

    /** Stores current words chain to search result.
     *  @param searchResult Words chain search result. */
    private void _storeCurrentWordsChain(Result searchResult){
        assert searchResult != null;
        assert _isEndWordInChainReached();

        List<String> wordsChain = searchResult.getWordsChain();

        wordsChain.clear();
        wordsChain.add(mInputWords.getStartWord());

        for(ListCursor<WordInfo> wordInfoListCursor : mWordsChainDeque){
            assert wordInfoListCursor.getElement().getUsageFlag();

            wordsChain.add(wordInfoListCursor.getElement().getWord());
        }
    }

    /** Advances cursor to the first word which is one character different from previous word in chain.
     *  Note: function also sets usage flag for this word to true.
     *  @param cursor Words list cursor.
     * */
    private boolean _advanceCursorToTheFirstProperWord(ListCursor<WordInfo> cursor){
        assert cursor != null;

        String previousWord = _getPreviousWord();

        boolean isSearchCompleted = false;
        boolean isWordFound = false;

        while(!isSearchCompleted){
            WordInfo currentWordInfo = cursor.getElement();

            // If current word is not used.
            if(!currentWordInfo.getUsageFlag()){
                int wordsDifference = _calculateWordsDifference(previousWord, currentWordInfo.getWord());

                if(wordsDifference == 1){
                    isWordFound = true;
                    currentWordInfo.setUsageFlag(true);
                }
            } // else skip this word

            if(!isWordFound && cursor.hasNext()){
                cursor.next();
            } else {
                isSearchCompleted = true;
            }
        }

        return isWordFound;
    }

    /** Rolls back search process. It is called in the following cases:
     *      1) End word is reached.
     *      2) Current words chain length violates constraint or not optimal.
     *      3) Dead end is reached.
     *  @param minStepsCount Minimum roll back steps count.
     *  @param modifyCurrentWordUsage Flag defines if current word's usage flag should be modified.
     *  @return Result of roll back: true - roll back successful, false - failed to roll back, it means that
     *          search process is completed.
     * */
    private boolean _rollBack(int minStepsCount, boolean modifyCurrentWordUsage){
        assert minStepsCount >= 1;
        assert mWordsChainDeque.size() > 0;

        // Result of roll back
        boolean rollBackResult = false;

        // Remained count of roll back steps.
        int remainedStepsCount = minStepsCount;

        // Roll back stop condition.
        boolean isRollBackCompleted = false;

        while(!isRollBackCompleted){
            // Extract last element from words chain deque.
            ListCursor<WordInfo> currentWordListCursor = mWordsChainDeque.pollLast();

            // Decrement remained steps count.
            if(remainedStepsCount > 0){
                remainedStepsCount--;
            }

            // Modify current word's usage flag if necessary.
            if(modifyCurrentWordUsage){
                currentWordListCursor.getElement().setUsageFlag(false);
            }

            if(mWordsChainDeque.size() > 0){
                // Words chain deque is not empty.

                if(remainedStepsCount == 0) {
                    // Required minimum roll back steps performed.

                    // Get last element from words chain deque.
                    currentWordListCursor = mWordsChainDeque.getLast();

                    if(currentWordListCursor.hasNext()) {
                        // Current cursor has next element.

                        // Reset usage flag for current word.
                        currentWordListCursor.getElement().setUsageFlag(false);

                        // Move cursor.
                        currentWordListCursor.next();

                        isRollBackCompleted = true;
                        rollBackResult = true;
                    } else {
                        // Cursor is pointed to the last element: so it is necessary to continue roll back.
                        modifyCurrentWordUsage = true;
                    }
                } else {
                    // Need to continue roll back.

                    modifyCurrentWordUsage = true;
                }
            } else {
                // Words chain deque is empty.

                isRollBackCompleted = true;
                rollBackResult = false;
            }
        } // while

        return rollBackResult;
    }

    /** Checks if current words chain built by this moment is optimal relative to words chain found earlier
     *  and doesn't violate max words chain length constraint.
     *  @param searchResult Words chain search result.
     *  @return True - if words chain is still optimal and there is a sense to continue process forward,
     *          false - currently built chain is not optimal and search process should be rolled back.
     * */
    private boolean _isCurrentWordsChainLengthOptimal(Result searchResult){
        assert searchResult != null;

        boolean isOptimal = false;

        int currentWordsChainLength = _getCurrentWordsChainLength();

        if(!_isEndWordInChainReached()){
            // Words chain is not completed therefore completed words chain will be at least one word longer.
            currentWordsChainLength++;
        }

        if(searchResult.isEmpty()){
            // No words chain were found.

            // Check maximum words chain length constraint.
            isOptimal = (currentWordsChainLength <= mMaxWordsChainLength);
        } else {
            // Word chain already was built.

            // Check maximum words chain length constraint and check that words chain length is optimal relative to
            // already built words chain.
            isOptimal = ((currentWordsChainLength <= mMaxWordsChainLength) &&
                    (currentWordsChainLength < searchResult.getWordsChain().size()));
        }

        return isOptimal;
    }

    /** Augments currently built words chain and transits search to the next stage. */
    private void _augmentWordsChain(){
        mWordsChainDeque.addLast(new ListCursor<>(mVocabularyWordsInfo, 0));
    }

    /** Implements words chain puzzle search algorithm: looks for shortest words chain.
     *  @return Words chain puzzle search result.
     * */
    private Result _lookForShortestWordsChain() {
        assert mVocabularyWordsInfo.size() > 0;

        TimeCounter executionTimeCounter = TimeCounter.start();

        mWordsChainDeque = new LinkedList<>();

        // Augment words chain and go to the 1-st stage of search.
        _augmentWordsChain();

        // Search algorithm result.
        Result searchResult = new Result(mInputWords);

        boolean isSearchCompleted = false;

        while(!isSearchCompleted){
            // Get cursor for the current stage of search.
            ListCursor<WordInfo> wordsInfoListCursor = mWordsChainDeque.getLast();

            if(_advanceCursorToTheFirstProperWord(wordsInfoListCursor)){
                // Now cursor is set to the word which is 1-character different from previous word in chain.
                assert _isCursorAtWordOneCharDifferentFromPrevious();

                if(_isCurrentWordsChainLengthOptimal(searchResult)){
                    // Current words chain length is optimal (less than currently found minimum words chain length).

                    if(_isEndWordInChainReached()){
                        // End word in chain has been reached.

                        // Store current words chain.
                        _storeCurrentWordsChain(searchResult);

                        // Roll back to search another words chain.
                        // If we roll one step back we won't be able to get shorter words chain, therefore
                        // it we should make at least two steps back to improve result.
                        isSearchCompleted = !_rollBack(2, true);
                    } else {
                        // End word in chain is not reached.

                        // Augment words chain and go to the next stage of search.
                        _augmentWordsChain();
                    }
                } else {
                    // Current words chain length is not optimal therefore we should skip it
                    // and roll back to build another chain.
                    isSearchCompleted = !_rollBack(1, true);
                }
            } else {
                // Now cursor is set to the last word in vocabulary and this word can't be added to chain.
                // Therefore we need replace previous word in chain and continue search.
                isSearchCompleted = !_rollBack(1, false);
            }

            // Check if timeout expired and search should be interrupted.
            if(executionTimeCounter.passedTime(TimeUnit.MINUTES) >= mTimeoutMinutes){
                searchResult.setInterruptionByTimeoutFlag(true);
                isSearchCompleted = true;
            }
        } // while

        return searchResult;
    }
} // class WordsChainPuzzle
