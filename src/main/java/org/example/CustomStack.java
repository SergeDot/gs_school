package org.example;

import lombok.ToString;

import java.util.*;
import java.util.function.Predicate;

@ToString
public class CustomStack<S> extends CustomLinkedList<S> {

    /**
     * The number of valid components in this {@code Vector} object.
     * Components {@code elementData[0]} through
     * {@code elementData[elementCount-1]} are the actual items.
     *
     * @serial
     */
    protected Object[] elementData;

    protected int capacityIncrement;

    /**
     * Default initial capacity.
     */
    protected static final int DEFAULT_INITIAL_CAPACITY = 10;

    /**
     * A soft maximum array length imposed by array growth computations.
     * Some JVMs (such as HotSpot) have an implementation limit that will cause
     * <p>
     * OutOfMemoryError("Requested array size exceeds VM limit")
     * <p>
     * to be thrown if a request is made to allocate an array of some length near
     * Integer.MAX_VALUE, even if there is sufficient heap available. The actual
     * limit might depend on some JVM implementation-specific characteristics such
     * as the object header size. The soft maximum value is chosen conservatively so
     * as to be smaller than any implementation limit that is likely to be encountered.
     */
    public static final int SOFT_MAX_ARRAY_LENGTH = Integer.MAX_VALUE - 8;

    public CustomStack() {
        this(DEFAULT_INITIAL_CAPACITY);
    }

    public CustomStack(int initialCapacity) {
        this(initialCapacity, 0);
    }

    public CustomStack(int initialCapacity, int capacityIncrement) {
        if (initialCapacity < 0)
            throw new IllegalArgumentException("Serge says: Illegal Capacity: " +
                    initialCapacity);
        this.elementData = new Object[initialCapacity];
        this.capacityIncrement = capacityIncrement;
    }

    public CustomStack(Collection<? extends S> c) {
        Object[] a = c.toArray();
        size = a.length;
        if (c.getClass() == ArrayList.class) {
            elementData = a;
        } else {
            elementData = Arrays.copyOf(a, size, Object[].class);
        }
    }

    /*TODO ************** DISTINCTIVE OPERATIONS **************/

    /**
     * Pushes an item onto the top of this stack. This has exactly
     * the same effect as:
     * <blockquote><pre>
     * addElement(item)</pre></blockquote>
     *
     * @param e the item to be pushed onto this stack.
     * @return the {@code item} argument.
     * @see java.util.Vector#addElement
     */
    @Override
    public void push(S e) {
        addElement(e);
    }

    /**
     * Removes the object at the top of this stack and returns that
     * object as the value of this function.
     *
     * @return The object at the top of this stack (the last item
     * of the {@code Vector} object).
     * @throws EmptyStackException if this stack is empty.
     */
    public S pop() {
        S obj;
        int len = size();

        obj = peek();
        removeElementAt(len - 1);

        return obj;
    }

    /**
     * Looks at the object at the top of this stack without removing it
     * from the stack.
     *
     * @return the object at the top of this stack (the last item
     * of the {@code Vector} object).
     * @throws EmptyStackException if this stack is empty.
     */
    public synchronized S peek() {
        int len = size();

        if (len == 0)
            throw new EmptyStackException();
        return elementAt(len - 1);
    }


    /**
     * Deletes the component at the specified index. Each component in
     * this vector with an index greater or equal to the specified
     * {@code index} is shifted downward to have an index one
     * smaller than the value it had previously. The size of this vector
     * is decreased by {@code 1}.
     *
     * <p>The index must be a value greater than or equal to {@code 0}
     * and less than the current size of the vector.
     *
     * <p>This method is identical in functionality to the {@link #remove(int)}
     * method (which is part of the {@link List} interface).  Note that the
     * {@code remove} method returns the old value that was stored at the
     * specified position.
     *
     * @param index the index of the object to remove
     * @throws ArrayIndexOutOfBoundsException if the index is out of range
     *                                        ({@code index < 0 || index >= size()})
     */
    public void removeElementAt(int index) {
        if (index >= size) {
            throw new ArrayIndexOutOfBoundsException(index + " >= " +
                    size);
        } else if (index < 0) {
            throw new ArrayIndexOutOfBoundsException(index);
        }
        int j = size - index - 1;
        if (j > 0) {
            System.arraycopy(elementData, index + 1, elementData, index, j);
        }
        size--;
        elementData[size] = null; /* to let gc do its work */
    }

    /*TODO ************** STANDARD OPERATIONS **************/

    //TODO: add()

    /**
     * Inserts the specified object as a component in this vector at the
     * specified {@code index}. Each component in this vector with
     * an index greater or equal to the specified {@code index} is
     * shifted upward to have an index one greater than the value it had
     * previously.
     *
     * <p>The index must be a value greater than or equal to {@code 0}
     * and less than or equal to the current size of the vector. (If the
     * index is equal to the current size of the vector, the new element
     * is appended to the Vector.)
     *
     * <p>This method is identical in functionality to the
     * {@link #add(int, Object) add(int, E)}
     * method (which is part of the {@link List} interface).  Note that the
     * {@code add} method reverses the order of the parameters, to more closely
     * match array usage.
     *
     * @param element the component to insert
     * @param index   where to insert the new component
     * @param index   index at which the specified element is to be inserted
     * @param element element to be inserted
     * @throws ArrayIndexOutOfBoundsException if the index is out of range
     *                                        ({@code index < 0 || index > size()})
     *                                        <p>
     *                                        <p>
     *                                        Inserts the specified element at the specified position in this Vector.
     *                                        Shifts the element currently at that position (if any) and any
     *                                        subsequent elements to the right (adds one to their indices).
     * @throws ArrayIndexOutOfBoundsException if the index is out of range
     *                                        ({@code index < 0 || index > size()})
     * @since 1.2
     */
    @Override
    public void add(int index, S element) {
        if (index > size || index < 0) {
            throw new ArrayIndexOutOfBoundsException("Serge says: " + index
                    + " > " + size);
        }
        final int s = size;
        Object[] elementData = this.elementData;
        if (s == elementData.length)
            elementData = grow();
        System.arraycopy(elementData, index,
                elementData, index + 1,
                s - index);
        elementData[index] = element;
        size = s + 1;
    }


    /**
     * Appends the specified element to the end of this Vector.
     *
     * @param e element to be appended to this Vector
     * @return {@code true} (as specified by {@link Collection#add})
     * @since 1.2
     */
    @Override
    public boolean add(S e) {
        add(e, elementData, size);
        return true;
    }

    /**
     * This helper method split out from add(E) to keep method
     * bytecode size under 35 (the -XX:MaxInlineSize default value),
     * which helps when add(E) is called in a C1-compiled loop.
     */
    private void add(S e, Object[] elementData, int s) {
        if (s == elementData.length)
            elementData = grow();
        elementData[s] = e;
        size = s + 1;
    }

    /**
     * Adds the specified component to the end of this vector,
     * increasing its size by one. The capacity of this vector is
     * increased if its size becomes greater than its capacity.
     *
     * <p>This method is identical in functionality to the
     * {@link #add(Object) add(E)}
     * method (which is part of the {@link List} interface).
     *
     * @param obj the component to be added
     */
    public void addElement(S obj) {
        add(obj, elementData, size);
    }

    /**
     * Appends all of the elements in the specified Collection to the end of
     * this Vector, in the order that they are returned by the specified
     * Collection's Iterator.  The behavior of this operation is undefined if
     * the specified Collection is modified while the operation is in progress.
     * (This implies that the behavior of this call is undefined if the
     * specified Collection is this Vector, and this Vector is nonempty.)
     *
     * @param c elements to be inserted into this Vector
     * @return {@code true} if this Vector changed as a result of the call
     * @throws NullPointerException if the specified collection is null
     * @since 1.2
     */
    @Override
    public boolean addAll(Collection<? extends S> c) {
        Object[] a = c.toArray();
        int numNew = a.length;
        if (numNew == 0)
            return false;
        synchronized (this) {
            Object[] elementData = this.elementData;
            final int s = size;
            if (numNew > elementData.length - s)
                elementData = grow(s + numNew);
            System.arraycopy(a, 0, elementData, s, numNew);
            size = s + numNew;
            return true;
        }
    }

    /**
     * Inserts all of the elements in the specified Collection into this
     * Vector at the specified position.  Shifts the element currently at
     * that position (if any) and any subsequent elements to the right
     * (increases their indices).  The new elements will appear in the Vector
     * in the order that they are returned by the specified Collection's
     * iterator.
     *
     * @param index index at which to insert the first element from the
     *              specified collection
     * @param c     elements to be inserted into this Vector
     * @return {@code true} if this Vector changed as a result of the call
     * @throws ArrayIndexOutOfBoundsException if the index is out of range
     *                                        ({@code index < 0 || index > size()})
     * @throws NullPointerException           if the specified collection is null
     * @since 1.2
     */
    @Override
    public boolean addAll(int index, Collection<? extends S> c) {
        if (index < 0 || index > size)
            throw new ArrayIndexOutOfBoundsException(index);

        Object[] a = c.toArray();
        int numNew = a.length;
        if (numNew == 0)
            return false;
        Object[] elementData = this.elementData;
        final int s = size;
        if (numNew > elementData.length - s)
            elementData = grow(s + numNew);

        int numMoved = s - index;
        if (numMoved > 0)
            System.arraycopy(elementData, index,
                    elementData, index + numNew,
                    numMoved);
        System.arraycopy(a, 0, elementData, index, numNew);
        size = s + numNew;
        return true;
    }

    private Object[] grow() {
        return grow(size + 1);
    }

    /**
     * Increases the capacity to ensure that it can hold at least the
     * number of elements specified by the minimum capacity argument.
     *
     * @param minCapacity the desired minimum capacity
     * @throws OutOfMemoryError if minCapacity is less than zero
     */
    private Object[] grow(int minCapacity) {
        int oldCapacity = elementData.length;
        int newCapacity;
        int minGrowth = minCapacity - oldCapacity;
        int prefGrowth = capacityIncrement > 0 ? capacityIncrement : oldCapacity;
        int prefLength = oldCapacity + Math.max(minGrowth, prefGrowth);

        if (0 < prefLength && prefLength <= SOFT_MAX_ARRAY_LENGTH) {
            newCapacity = prefLength;
        } else {
            // put code cold in a separate method
            int minLength = oldCapacity + minGrowth;
            if (minLength < 0) { // overflow
                throw new OutOfMemoryError(
                        "Serge says: Required array length " + oldCapacity + " + " + minGrowth + " is too large");
            } else if (minLength <= SOFT_MAX_ARRAY_LENGTH) {
                newCapacity = SOFT_MAX_ARRAY_LENGTH;
            } else {
                newCapacity = minLength;
            }
        }
        return elementData = Arrays.copyOf(elementData, newCapacity);
    }

    // TODO: Positional Access Operations

    //TODO: get()

    /**
     * Returns the element at the specified position in this Vector.
     *
     * @param index index of the element to return
     * @return object at the specified index
     * @throws ArrayIndexOutOfBoundsException if the index is out of range
     *                                        ({@code index < 0 || index >= size()})
     * @since 1.2
     */
    @Override
    public S get(int index) {
        if (index >= size)
            throw new ArrayIndexOutOfBoundsException(index);

        return elementData(index);
    }

    //TODO: set()

    /**
     * Replaces the element at the specified position in this Vector with the
     * specified element.
     *
     * @param index   index of the element to replace
     * @param element element to be stored at the specified position
     * @return the element previously at the specified position
     * @throws ArrayIndexOutOfBoundsException if the index is out of range
     *                                        ({@code index < 0 || index >= size()})
     * @since 1.2
     */
    @Override
    public S set(int index, S element) {
        if (index >= size)
            throw new ArrayIndexOutOfBoundsException(index);

        S oldValue = elementData(index);
        elementData[index] = element;
        return oldValue;
    }

    //TODO: remove()

    /**
     * Removes the first occurrence of the specified element in this Vector
     * If the Vector does not contain the element, it is unchanged.  More
     * formally, removes the element with the lowest index i such that
     * {@code Objects.equals(o, get(i))} (if such
     * an element exists).
     *
     * @param o element to be removed from this Vector, if present
     * @return true if the Vector contained the specified element
     * @since 1.2
     */
    @Override
    public boolean remove(Object o) {
        return removeElement(o);
    }

    /**
     * Removes the element at the specified position in this Vector.
     * Shifts any subsequent elements to the left (subtracts one from their
     * indices).  Returns the element that was removed from the Vector.
     *
     * @param index the index of the element to be removed
     * @return element that was removed
     * @throws ArrayIndexOutOfBoundsException if the index is out of range
     *                                        ({@code index < 0 || index >= size()})
     * @since 1.2
     */
    @Override
    public S remove(int index) {
        if (index >= size)
            throw new ArrayIndexOutOfBoundsException(index);
        S oldValue = elementData(index);

        int numMoved = size - index - 1;
        if (numMoved > 0)
            System.arraycopy(elementData, index + 1, elementData, index,
                    numMoved);
        elementData[--size] = null; // Let gc do its work

        return oldValue;
    }

    /**
     * Removes the first (lowest-indexed) occurrence of the argument
     * from this vector. If the object is found in this vector, each
     * component in the vector with an index greater or equal to the
     * object's index is shifted downward to have an index one smaller
     * than the value it had previously.
     *
     * <p>This method is identical in functionality to the
     * {@link #remove(Object)} method (which is part of the
     * {@link List} interface).
     *
     * @param obj the component to be removed
     * @return {@code true} if the argument was a component of this
     * vector; {@code false} otherwise.
     */
    public boolean removeElement(Object obj) {
        int i = indexOf(obj);
        if (i >= 0) {
            removeElementAt(i);
            return true;
        }
        return false;
    }

    /**
     * Removes from this Vector all of its elements that are contained in the
     * specified Collection.
     *
     * @param c a collection of elements to be removed from the Vector
     * @return true if this Vector changed as a result of the call
     * @throws ClassCastException   if the types of one or more elements
     *                              in this vector are incompatible with the specified
     *                              collection
     *                              (<a href="Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException if this vector contains one or more null
     *                              elements and the specified collection does not support null
     *                              elements
     *                              (<a href="Collection.html#optional-restrictions">optional</a>),
     *                              or if the specified collection is null
     * @since 1.2
     */
    @Override
    public boolean removeAll(Collection<?> c) {
        Objects.requireNonNull(c);
        return bulkRemove(e -> c.contains(e));
    }

    private boolean bulkRemove(Predicate<? super S> filter) {
        final Object[] es = elementData;
        final int end = size;
        int i;
        // Optimize for initial run of survivors
        for (i = 0; i < end && !filter.test(elementAt(es, i)); i++) ;

        // Tolerate predicates that reentrantly access the collection for
        // read (but writers still get CME), so traverse once to find
        // elements to delete, a second pass to physically expunge.
        if (i < end) {
            final int beg = i;
            final long[] deathRow = nBits(end - beg);
            deathRow[0] = 1L;   // set bit 0
            for (i = beg + 1; i < end; i++)
                if (filter.test(elementAt(es, i)))
                    setBit(deathRow, i - beg);
            int w = beg;
            for (i = beg; i < end; i++)
                if (isClear(deathRow, i - beg))
                    es[w++] = es[i];
            for (i = size = w; i < end; i++)
                es[i] = null;
            return true;
        }
        return false;
    }

    // A tiny bit set implementation
    private static long[] nBits(int n) {
        return new long[((n - 1) >> 6) + 1];
    }

    private static void setBit(long[] bits, int i) {
        bits[i >> 6] |= 1L << i;
    }

    private static boolean isClear(long[] bits, int i) {
        return (bits[i >> 6] & (1L << i)) == 0;
    }

    /**
     * Returns the component at the specified index.
     *
     * <p>This method is identical in functionality to the {@link #get(int)}
     * method (which is part of the {@link List} interface).
     *
     * @param index an index into this vector
     * @return the component at the specified index
     * @throws ArrayIndexOutOfBoundsException if the index is out of range
     *                                        ({@code index < 0 || index >= size()})
     */
    @SuppressWarnings("unchecked")
    S elementData(int index) {
        return (S) elementData[index];
    }

    @SuppressWarnings("unchecked")
    static <E> E elementAt(Object[] es, int index) {
        return (E) es[index];
    }


    /**
     * Returns the component at the specified index.
     *
     * <p>This method is identical in functionality to the {@link #get(int)}
     * method (which is part of the {@link List} interface).
     *
     * @param index an index into this vector
     * @return the component at the specified index
     * @throws ArrayIndexOutOfBoundsException if the index is out of range
     *                                        ({@code index < 0 || index >= size()})
     */
    public synchronized S elementAt(int index) {
        if (index >= size) {
            throw new ArrayIndexOutOfBoundsException(index + " >= " + size);
        }

        return elementData(index);
    }

    //TODO: Get size: size() - inherited
    //TODO: isEmpty - inherited

    //TODO: Clear all: clear()

    /**
     * Removes all of the elements from this Vector.  The Vector will
     * be empty after this call returns (unless it throws an exception).
     *
     * @since 1.2
     */
    @Override
    public void clear() {
        final Object[] es = elementData;
        for (int to = size, i = size = 0; i < to; i++)
            es[i] = null;
    }


    /**
     * Returns the 1-based position where an object is on this stack.
     * If the object {@code o} occurs as an item in this stack, this
     * method returns the distance from the top of the stack of the
     * occurrence nearest the top of the stack; the topmost item on the
     * stack is considered to be at distance {@code 1}. The {@code equals}
     * method is used to compare {@code o} to the
     * items in this stack.
     *
     * @param o the desired object.
     * @return the 1-based position from the top of the stack where
     * the object is located; the return value {@code -1}
     * indicates that the object is not on the stack.
     */
    public synchronized int search(Object o) {
        int i = lastIndexOf(o);

        if (i >= 0) {
            return size() - i;
        }
        return -1;
    }


    /*TODO ************** AUXILIARY BONUS METHODS **************/

    //TODO: contains()

    /**
     * Returns {@code true} if this vector contains the specified element.
     * More formally, returns {@code true} if and only if this vector
     * contains at least one element {@code e} such that
     * {@code Objects.equals(o, e)}.
     *
     * @param o element whose presence in this vector is to be tested
     * @return {@code true} if this vector contains the specified element
     */
    @Override
    public boolean contains(Object o) {
        return indexOf(o, 0) >= 0;
    }

    //TODO: containsAll() - inherited

    // Positional Access Operations

    /**
     * Returns the index of the first occurrence of the specified element
     * in this vector, or -1 if this vector does not contain the element.
     * More formally, returns the lowest index {@code i} such that
     * {@code Objects.equals(o, get(i))},
     * or -1 if there is no such index.
     *
     * @param o element to search for
     * @return the index of the first occurrence of the specified element in
     * this vector, or -1 if this vector does not contain the element
     */
    @Override
    public int indexOf(Object o) {
        return indexOf(o, 0);
    }

    /**
     * Returns the index of the first occurrence of the specified element in
     * this vector, searching forwards from {@code index}, or returns -1 if
     * the element is not found.
     * More formally, returns the lowest index {@code i} such that
     * {@code (i >= index && Objects.equals(o, get(i)))},
     * or -1 if there is no such index.
     *
     * @param o     element to search for
     * @param index index to start searching from
     * @return the index of the first occurrence of the element in
     * this vector at position {@code index} or later in the vector;
     * {@code -1} if the element is not found.
     * @throws IndexOutOfBoundsException if the specified index is negative
     * @see Object#equals(Object)
     */
    public int indexOf(Object o, int index) {
        if (o == null) {
            for (int i = index; i < size; i++)
                if (elementData[i] == null)
                    return i;
        } else {
            for (int i = index; i < size; i++)
                if (o.equals(elementData[i]))
                    return i;
        }
        return -1;
    }

    /**
     * Returns the index of the last occurrence of the specified element
     * in this vector, or -1 if this vector does not contain the element.
     * More formally, returns the highest index {@code i} such that
     * {@code Objects.equals(o, get(i))},
     * or -1 if there is no such index.
     *
     * @param o element to search for
     * @return the index of the last occurrence of the specified element in
     * this vector, or -1 if this vector does not contain the element
     */
    @Override
    public int lastIndexOf(Object o) {
        return lastIndexOf(o, size - 1);
    }

    /**
     * Returns the index of the last occurrence of the specified element in
     * this vector, searching backwards from {@code index}, or returns -1 if
     * the element is not found.
     * More formally, returns the highest index {@code i} such that
     * {@code (i <= index && Objects.equals(o, get(i)))},
     * or -1 if there is no such index.
     *
     * @param o     element to search for
     * @param index index to start searching backwards from
     * @return the index of the last occurrence of the element at position
     * less than or equal to {@code index} in this vector;
     * -1 if the element is not found.
     * @throws IndexOutOfBoundsException if the specified index is greater
     *                                   than or equal to the current size of this vector
     */
    public int lastIndexOf(Object o, int index) {
        if (index >= size)
            throw new IndexOutOfBoundsException(index + " >= " + size);

        if (o == null) {
            for (int i = index; i >= 0; i--)
                if (elementData[i] == null)
                    return i;
        } else {
            for (int i = index; i >= 0; i--)
                if (o.equals(elementData[i]))
                    return i;
        }
        return -1;
    }

    /**
     * Compares the specified Object with this Vector for equality.  Returns
     * true if and only if the specified Object is also a List, both Lists
     * have the same size, and all corresponding pairs of elements in the two
     * Lists are <em>equal</em>.  (Two elements {@code e1} and
     * {@code e2} are <em>equal</em> if {@code Objects.equals(e1, e2)}.)
     * In other words, two Lists are defined to be
     * equal if they contain the same elements in the same order.
     *
     * @param o the Object to be compared for equality with this Vector
     * @return true if the specified Object is equal to this Vector
     */
    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    /**
     * Returns the hash code value for this Vector.
     */
    @Override
    public int hashCode() {
        return super.hashCode();
    }

    // Bulk operations

    /**
     * Removes from this list all of the elements whose index is between
     * {@code fromIndex}, inclusive, and {@code toIndex}, exclusive.
     * Shifts any succeeding elements to the left (reduces their index).
     * This call shortens the list by {@code (toIndex - fromIndex)} elements.
     * (If {@code toIndex==fromIndex}, this operation has no effect.)
     */
    protected void removeRange(int fromIndex, int toIndex) {
        shiftTailOverGap(elementData, fromIndex, toIndex);
    }

    /**
     * Erases the gap from lo to hi, by sliding down following elements.
     */
    private void shiftTailOverGap(Object[] es, int lo, int hi) {
        System.arraycopy(es, hi, es, lo, size - hi);
        for (int to = size, i = (size -= hi - lo); i < to; i++)
            es[i] = null;
    }

    /**
     * Returns the first component (the item at index {@code 0}) of
     * this vector.
     *
     * @return the first component of this vector
     * @throws NoSuchElementException if this vector has no components
     */
    public S firstElement() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        return elementData(0);
    }

    /**
     * Trims the capacity of this vector to be the vector's current
     * size. If the capacity of this vector is larger than its current
     * size, then the capacity is changed to equal the size by replacing
     * its internal data array, kept in the field {@code elementData},
     * with a smaller one. An application can use this operation to
     * minimize the storage of a vector.
     */
    public void trimToSize() {
        if (size < elementData.length) {
            elementData = Arrays.copyOf(elementData, size);
        }
    }

    /**
     * Copies the components of this vector into the specified array.
     * The item at index {@code k} in this vector is copied into
     * component {@code k} of {@code anArray}.
     *
     * @param anArray the array into which the components get copied
     * @throws NullPointerException      if the given array is null
     * @throws IndexOutOfBoundsException if the specified array is not
     *                                   large enough to hold all the components of this vector
     * @throws ArrayStoreException       if a component of this vector is not of
     *                                   a runtime type that can be stored in the specified array
     * @see #toArray(Object[])
     */
    public void copyInto(Object[] anArray) {
        System.arraycopy(elementData, 0, anArray, 0, size);
    }

    /**
     * Increases the capacity of this vector, if necessary, to ensure
     * that it can hold at least the number of components specified by
     * the minimum capacity argument.
     *
     * <p>If the current capacity of this vector is less than
     * {@code minCapacity}, then its capacity is increased by replacing its
     * internal data array, kept in the field {@code elementData}, with a
     * larger one.  The size of the new data array will be the old size plus
     * {@code capacityIncrement}, unless the value of
     * {@code capacityIncrement} is less than or equal to zero, in which case
     * the new capacity will be twice the old capacity; but if this new size
     * is still smaller than {@code minCapacity}, then the new capacity will
     * be {@code minCapacity}.
     *
     * @param minCapacity the desired minimum capacity
     */
    public void ensureCapacity(int minCapacity) {
        if (minCapacity > elementData.length) grow(minCapacity);
    }

    /**
     * Sets the size of this vector. If the new size is greater than the
     * current size, new {@code null} items are added to the end of
     * the vector. If the new size is less than the current size, all
     * components at index {@code newSize} and greater are discarded.
     *
     * @param newSize the new size of this vector
     * @throws ArrayIndexOutOfBoundsException if the new size is negative
     */
    public void setSize(int newSize) {
        if (newSize > elementData.length)
            grow(newSize);
        final Object[] es = elementData;
        for (int to = size, i = newSize; i < to; i++)
            es[i] = null;
        size = newSize;
    }

    /**
     * Returns the current capacity of this vector.
     *
     * @return the current capacity (the length of its internal
     * data array, kept in the field {@code elementData}
     * of this vector)
     */
    public int capacity() {
        return elementData.length;
    }
}
