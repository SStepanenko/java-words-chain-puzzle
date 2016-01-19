/***************************************************************************************************
 * @file ListCursor.java
 * @author Sergey Stepanenko (sergey.stepanenko.27@gmail.com)
 * @description Contains implementation of the ListCursor class.
 **************************************************************************************************/

package com.gmail.stepanenko.sergey27.elephant_from_fly;

import java.util.List;
import java.util.NoSuchElementException;

/** Represents list cursor which keeps position of element in List<T> and used for iteration through list.
 *  This class was implemented because Java Iterator<T> class doesn't allow getting reference to current element
 *  at which it is pointed to.
 * @param <T> Type of element in list.
 * */
public final class ListCursor<T> {

    // Private fields.

    private List<T> mList; // List.

    private int mPosition; // Current position (should be inside valid range).

    private T mElement; // Element in current position.

    // Public methods.

    /** Constructor.
     *  @param list List of elements of T type.
     *  @param position Valid position of element in list.
     *  @exception IllegalArgumentException List is null or position is out of range.
     * */
    public ListCursor(List<T> list, int position){
        // Check list.
        if(list == null){
            throw new IllegalArgumentException("list");
        }

        // Check position.
        if(position < 0 || position >= list.size()){
            throw new IllegalArgumentException("position");
        }

        mList = list;
        mPosition = position;
        mElement = mList.get(mPosition);
    }

    /** Gets current position. */
    public synchronized int getPosition(){
        return mPosition;
    }

    /** Gets current element from list */
    public synchronized T getElement(){
        return mElement;
    }

    /** Gets element at given position.
     *  @param position Position of element in list.
     *  @exception IndexOutOfBoundsException Position is out of valid range.
     *  */
    public synchronized T getElementAt(int position){
        return mList.get(position); // exception
    }

    /** Checks if current element is not the last. */
    public synchronized boolean hasNext(){
        boolean result = _validatePosition(mPosition + 1);

        return result;
    }

    /** Checks if current element is not the first. */
    public synchronized boolean hasPrevious(){
        boolean result = _validatePosition(mPosition - 1);

        return result;
    }

    /** Sets cursor to the next element and returns reference to this element.
     * @return Reference to next element.
     * @exception NoSuchElementException Current element is the last element in list.
     */
    public synchronized T next(){
        if(!hasNext()){
            throw new NoSuchElementException();
        }

        mPosition++;
        mElement = mList.get(mPosition);

        return mElement;
    }

    /** Sets cursor to the previous element and returns reference to this element.
     * @return Reference to the previous element.
     * @exception NoSuchElementException Current element is the first element in list.
     */
    public synchronized T previous(){
        if(!hasPrevious()){
            throw new NoSuchElementException();
        }

        mPosition--;
        mElement = mList.get(mPosition);

        return mElement;
    }

    // Private methods.

    /** Checks if position is valid.
     * @param position Position to be checked.
     * @return True - if position is valid, otherwise - false.
     * */
    private boolean _validatePosition(int position){
        boolean result = (position >=0 && position < mList.size());

        return result;
    }
} // class ListCursor<T>
