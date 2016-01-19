/***************************************************************************************************
 * @file ListCursorTest.java
 * @author Sergey Stepanenko (sergey.stepanenko.27@gmail.com)
 * @description Contains implementation of the ListCursorTest class.
 **************************************************************************************************/

package com.gmail.stepanenko.sergey27.elephant_from_fly;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Unit Test for ListCursor<T> class.
 */
public class ListCursorTest {

    @Test(expected = IllegalArgumentException.class)
    public void test_constructor_nullList() {
        new ListCursor<Integer>(null, 0); // exception
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_constructor_emptyList() {
        List<Integer> emptyList = new ArrayList<Integer>();

        new ListCursor<Integer>(emptyList, 0); // exception
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_constructor_negativePosition() {
        List<Integer> emptyList = new ArrayList<Integer>();

        new ListCursor<Integer>(emptyList, -1); // exception
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_constructor_positionEqualsToListSize() {
        List<Integer> list = new ArrayList<Integer>(Arrays.asList(0, 1, 2, 3));


        new ListCursor<Integer>(list, list.size()); // exception
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_constructor_positionGreaterThanListSize() {
        List<Integer> list = new ArrayList<Integer>(Arrays.asList(0, 1, 2, 3));


        new ListCursor<Integer>(list, list.size() + 1); // exception
    }

    @Test
    public void test_constructor_validArguments() {

        List<Integer> list;

        // List with 1 element.
        list = new ArrayList<Integer>(Arrays.asList(0));
        _test_constructor_validArguments(list, 0);

        // List with 3 elements.
        list = new ArrayList<Integer>(Arrays.asList(0, 1, 2));
        for(int i = 0; i < list.size(); i++){
            _test_constructor_validArguments(list, i);
        }
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void test_getElementAt_negativePosition() {
        List<Integer> list = new ArrayList<Integer>(Arrays.asList(0));


        ListCursor<Integer> listCursor = new ListCursor<Integer>(list, 0);

        listCursor.getElementAt(-1); // exception
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void test_getElementAt_positionEqualsToListSize() {
        List<Integer> list = new ArrayList<Integer>(Arrays.asList(0, 1, 2));

        ListCursor<Integer> listCursor = new ListCursor<Integer>(list, 0);

        listCursor.getElementAt(list.size()); // exception
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void test_getElementAt_positionMoreThanListSize() {
        List<Integer> list = new ArrayList<Integer>(Arrays.asList(0, 1, 2));

        ListCursor<Integer> listCursor = new ListCursor<Integer>(list, 0);

        listCursor.getElementAt(list.size() + 1); // exception
    }

    @Test(expected = NoSuchElementException.class)
    public void test_next_noSuchElement() {
        List<Integer> list = new ArrayList<Integer>(Arrays.asList(0));

        ListCursor<Integer> listCursor = new ListCursor<Integer>(list, 0);

        listCursor.next(); // exception
    }

    @Test(expected = NoSuchElementException.class)
    public void test_previous_noSuchElement() {
        List<Integer> list = new ArrayList<Integer>(Arrays.asList(0));

        ListCursor<Integer> listCursor = new ListCursor<Integer>(list, 0);

        listCursor.previous(); // exception
    }

    @Test
    public void test_forwardIteration() {
        List<Integer> list = new ArrayList<Integer>(Arrays.asList(0, 1, 2, 3, 4, 5));

        ListCursor<Integer> listCursor = new ListCursor<Integer>(list, 0);

        for(int i = 0; i < list.size(); i++){
            Assert.assertEquals(list.get(i), listCursor.getElementAt(i));
            Assert.assertEquals(list.get(i), listCursor.getElement());
            Assert.assertEquals(i, listCursor.getPosition());

            boolean isFirstElement = (i == 0);
            Assert.assertEquals(!isFirstElement, listCursor.hasPrevious());

            boolean isLastElement = (i == list.size() - 1);
            Assert.assertEquals(!isLastElement, listCursor.hasNext());

            if(!isLastElement){
                Integer nextElement = listCursor.next();
                Assert.assertEquals(list.get(i + 1), nextElement);
            }
        }
    }

    @Test
    public void test_backwardIteration() {
        List<Integer> list = new ArrayList<Integer>(Arrays.asList(0, 1, 2, 3, 4, 5));

        ListCursor<Integer> listCursor = new ListCursor<Integer>(list, list.size() - 1);

        for(int i = list.size() - 1; i >= 0; i--){
            Assert.assertEquals(list.get(i), listCursor.getElementAt(i));
            Assert.assertEquals(list.get(i), listCursor.getElement());
            Assert.assertEquals(i, listCursor.getPosition());

            boolean isFirstElement = (i == 0);
            Assert.assertEquals(!isFirstElement, listCursor.hasPrevious());

            boolean isLastElement = (i == list.size() - 1);
            Assert.assertEquals(!isLastElement, listCursor.hasNext());

            if(!isFirstElement){
                Integer previousElement = listCursor.previous();
                Assert.assertEquals(list.get(i - 1), previousElement);
            }
        }
    }

    // Private methods.

    private <T> void _test_constructor_validArguments(List<T> list, int position){
        ListCursor<T> listCursor = new ListCursor<>(list, position);

        Assert.assertEquals(position, listCursor.getPosition());
    }
} // class ListCursorTest
