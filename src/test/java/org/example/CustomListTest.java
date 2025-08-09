package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;
import java.util.stream.Stream;

import static org.example.TestUtils.*;

import static org.example.CustomTestAnnotations.*;

class CustomListTest {
    private static CustomList customList;
    private static List arrayList;
    private static LinkedList linkedList;
    private static CustomLinkedList customLinkedList;
    private static Stack stack;
    private static CustomStack customStack;
    private static CustomQueue customQueue;
    private static final String[] stringTestData = {"a", "b", "c", "d", "e"};
    private static final int intListSize = 5;
    private static final int[] intTestData = {1, 2, 3, 4, 5, 6};

    @BeforeEach
    @ThisIsArrangeBeforeEach
    void setUp() {
        this.customList = new CustomList(1);
        this.arrayList = new ArrayList(1);
        this.customLinkedList = new CustomLinkedList(Arrays.asList(1));
        this.linkedList = new LinkedList(Arrays.asList(1));
        this.stack = new Stack<>();
        this.customStack = new CustomStack(1);
    }

    static CustomList create_empty_custom_list() {
        return new CustomList();
    }

    static List create_empty_array_list() {
        return new ArrayList();
    }

    static LinkedList create_empty_linked_list() {
        return new LinkedList();
    }

    static CustomLinkedList create_empty_custom_linked_list() {
        return new CustomLinkedList();
    }

    static Stack create_empty_stack() {
        return new Stack();
    }

    static CustomStack create_empty_custom_stack() {
        return new CustomStack();
    }

    static CustomQueue create_empty_custom_queue() {
        return new CustomQueue();
    }

    static Stream<Arguments> generateEmptyLists() {
        CustomList customList = create_empty_custom_list();
        List arrayList = create_empty_array_list();
        LinkedList linkedList = create_empty_linked_list();
        CustomLinkedList customLinkedList = create_empty_custom_linked_list();
        Stack stack = create_empty_stack();
        CustomStack customStack = create_empty_custom_stack();
        CustomQueue customQueue = create_empty_custom_queue();
        return Stream.of(
                Arguments.of(customList),
                Arguments.of(arrayList),
                Arguments.of(linkedList),
                Arguments.of(customLinkedList),
                Arguments.of(stack),
                Arguments.of(customStack),
                Arguments.of(customQueue)
        );
    }

    static Stream<Arguments> generateStringLists() {
        CustomList customList = new CustomList();
        List arrayList = new ArrayList();
        LinkedList linkedList = new LinkedList();
        CustomLinkedList customLinkedList = new CustomLinkedList();
        Stack stack = new Stack();
        CustomStack customStack = new CustomStack();
        CustomQueue customQueue = new CustomQueue();
        for (int i = 0; i < stringTestData.length; i++) {
            customList.add(stringTestData[i]);
            arrayList.add(stringTestData[i]);
            linkedList.add(stringTestData[i]);
            customLinkedList.add(stringTestData[i]);
            stack.add(stringTestData[i]);
            customStack.push(stringTestData[i]);
            customQueue.enqueue(stringTestData[i]);
        }
        return Stream.of(
                Arguments.of(customList),
                Arguments.of(arrayList),
                Arguments.of(linkedList),
                Arguments.of(customLinkedList),
                Arguments.of(stack),
                Arguments.of(customStack),
                Arguments.of(customQueue)
        );
    }

    @Test
    @ThisIsTest
    void should_create_empty_list() {
        CustomList emptyCustomList = create_empty_custom_list();
        CustomLinkedList emptyCustomLinkedList = create_empty_custom_linked_list();
        CustomStack emptyCustomStack = create_empty_custom_stack();
        CustomQueue emptyCustomQueue = create_empty_custom_queue();

        String customListClassName = emptyCustomList.getClass().toString();
        String customLinkedListClassName = emptyCustomLinkedList.getClass().toString();
        String customStackClassName = emptyCustomStack.getClass().toString();
        String customQueueClassName = emptyCustomQueue.getClass().toString();

        Assertions.assertTrue(customListClassName.endsWith("CustomList"));
        Assertions.assertTrue(customLinkedListClassName.endsWith("CustomLinkedList"));
        Assertions.assertTrue(customStackClassName.endsWith("CustomStack"));
        Assertions.assertTrue(customQueueClassName.endsWith("CustomQueue"));
    }

    @ParameterizedTest
    @MethodSource("generateEmptyLists")
    void should_have_zero_elements(List list) {
        Assertions.assertTrue(list.isEmpty());
        Assertions.assertEquals(0, list.size());
    }

    @ParameterizedTest
    @MethodSource("generateEmptyLists")
    void should_not_get_element_empty_list(List list) {
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> list.get(0));
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> list.get(1));
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> list.get(2));
    }

    @ParameterizedTest
    @MethodSource("generateEmptyLists")
    void should_not_get_negative_index_empty_list(List list) {
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> list.get(-2));
    }

    @ParameterizedTest
    @MethodSource("generateStringLists")
    void should_not_get_negative_index(List list) {
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> list.get(-2));
    }

    @ParameterizedTest
    @MethodSource("generateEmptyLists")
    void should_add_elements(List list) {
        int expectedArrayListSize = 5;
        for (int i = 0; i < expectedArrayListSize; i++) {
            list.add(i);
        }

        Assertions.assertEquals(expectedArrayListSize, list.size());
        Assertions.assertFalse(list.isEmpty());
    }

    @ParameterizedTest
    @MethodSource("generateEmptyLists")
    void should_add_null_elements(List list) {
        int expectedArrayListSize = 5;
        for (int i = 0; i < expectedArrayListSize; i++) {
            if (isCustomLinkedList(list) || isCustomStack(list))
                ((CustomLinkedList<?>) list).push(null);
            else if (isCustomQueue(list))
                ((CustomQueue<?>) list).enqueue(null);
            else
                list.add(null);
        }

        Assertions.assertEquals(expectedArrayListSize, list.size());
        Assertions.assertFalse(list.isEmpty());
    }

    @ParameterizedTest
    @MethodSource("generateStringLists")
    void should_get_elements(List list) {
        Assertions.assertEquals(stringTestData[0], list.get(0));
        Assertions.assertEquals(stringTestData[1], list.get(1));
        Assertions.assertEquals(stringTestData[2], list.get(2));
        Assertions.assertEquals(stringTestData[3], list.get(3));
        Assertions.assertEquals(stringTestData[4], list.get(4));
    }

    @ParameterizedTest
    @MethodSource("generateStringLists")
    void should_set_elements(List list) {
        String newElement = "q";
        int elementToReplaceIx = 3;
        list.set(elementToReplaceIx, newElement);

        Assertions.assertEquals(newElement, list.get(elementToReplaceIx));
    }

    @ParameterizedTest
    @MethodSource("generateStringLists")
    void should_set_elements_keep_same_size(List list) {
        String newElement = "q";
        int elementToReplaceIx = 3;
        int originalListLength = list.size();
        list.set(elementToReplaceIx, newElement);
        int newListLength = list.size();

        Assertions.assertEquals(originalListLength, newListLength);
    }

    @ParameterizedTest
    @MethodSource("generateEmptyLists")
    void should_not_set_negative_index(List list) {
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> list.set(-1, "B"));
    }

    @ParameterizedTest
    @MethodSource("generateStringLists")
    void should_remove_elements(List list) {
        int elementToRemoveIx = 3;
        int originalListLength = list.size();
        Object removedElement = list.remove(elementToRemoveIx);
        int newListLength = list.size();

        Assertions.assertEquals(stringTestData[elementToRemoveIx], removedElement);
        Assertions.assertNotEquals(originalListLength, newListLength);
        Assertions.assertFalse(list.contains(stringTestData[elementToRemoveIx]));
    }

    @ParameterizedTest
    @MethodSource("generateStringLists")
    void should_get_correct_size(List list) {
        Assertions.assertEquals(stringTestData.length, list.size());
    }

    @ParameterizedTest
    @MethodSource("generateStringLists")
    void should_clear_list(List list) {
        int fullListSize = list.size();
        list.clear();
        int clearedListSize = list.size();

        Assertions.assertNotEquals(fullListSize, clearedListSize);
        Assertions.assertTrue(list.isEmpty());
        Assertions.assertEquals(0, list.size());
    }

    @Test
    @ThisIsTest
    void should_increase_capacity() {
        CustomList customList = new CustomList();
        int originalCapacity = customList.elementData.length;
        for (int i = 0; i < stringTestData.length; i++) {
            customList.add(stringTestData[i]);
        }
        int newCapacity = customList.elementData.length;

        Assertions.assertNotEquals(originalCapacity, newCapacity);
        Assertions.assertEquals(10, newCapacity);
    }
}
