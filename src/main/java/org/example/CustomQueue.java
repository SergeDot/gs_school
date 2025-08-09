package org.example;

import java.util.*;

public class CustomQueue<S> extends CustomLinkedList<S> implements Queue<S> {

    /*TODO ************** DISTINCTIVE OPERATIONS **************/

    //TODO: enqueue()

    /**
     * Inserts the specified element at the tail (end) of this list.
     *
     * @param s the element to add
     */
    public void enqueue(S s) {
        addLast(s);
    }

    //TODO: dequeue()

    /**
     * Removes and returns the first element from this list.
     *
     * @return the last element from this list
     * @throws NoSuchElementException if this list is empty
     */
    public S dequeue() {
        return removeFirst();
    }

    //TODO: front()

    /**
     * Retrieves, but does not remove, the head (first element) of this list.
     *
     * @return the head of this list, or {@code null} if this list is empty
     * @since 1.5
     */
    public S front() {
        return peek();
    }

    //TODO: crop()

    /**
     * Retrieves and removes the head (first element) of this list.
     *
     * @return the head of this list, or {@code null} if this list is empty
     * @since 1.5
     */
    public S crop() {
        return poll();
    }

    //TODO: peek() - inherited

    /*TODO ************** STANDARD OPERATIONS **************/
    //TODO: size() - inherited
    //TODO: isEmpty() - inherited
    //TODO: contains() - inherited
}
