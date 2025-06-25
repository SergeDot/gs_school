package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CustomListTest {
    private CustomList list;
    final private String[] stringTestData = {"a", "b", "c", "d", "e"};
    final private int[] intTestData = {1, 2, 3, 4, 5, 6};


    @BeforeEach
    void setUp() {
        this.list = new CustomList(1);
    }

    CustomList create_empty_list() {
        return new CustomList();
    }
    CustomList create_test_string_list() {
        CustomList stringList = new CustomList();
        for (int i = 0; i < stringTestData.length; i++) {
            list.add(stringTestData[i]);
        }
        return stringList;
    }

    @Test
    void should_create_empty_customlist() {
        CustomList emptyList = create_empty_list();
        String className = emptyList.getClass().toString();
        Assertions.assertTrue(className.endsWith("CustomList"));
        Assertions.assertEquals(0, emptyList.size());
    }

    @Test
    void should_not_get_element_empty_list() {
        CustomList emptyList = create_empty_list();
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> emptyList.get(0));
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> emptyList.get(1));
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> emptyList.get(2));
    }

    @Test
    void should_not_get_negative_index() {
        CustomList emptyList = create_empty_list();
        CustomList stringList = create_test_string_list();

        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> emptyList.get(-2));
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> stringList.get(-2));
    }

    @Test
    void should_add_elements() {
        int expectedArrayListSize = 5;
        for (int i = 0; i < expectedArrayListSize; i++) {
            list.add(i);
        }
        Assertions.assertEquals(expectedArrayListSize, list.size());
        Assertions.assertFalse(list.isEmpty());
    }

    @Test
    void should_add_null_elements() {
        int expectedArrayListSize = 5;
        for (int i = 0; i < expectedArrayListSize; i++) {
            list.add(null);
        }
        Assertions.assertEquals(expectedArrayListSize, list.size());
        Assertions.assertFalse(list.isEmpty());
    }

    @Test
    void should_get_elements() {
        for (int i = 0; i < stringTestData.length; i++) {
            list.add(stringTestData[i]);
        }
        Assertions.assertEquals(stringTestData[0], list.get(0));
        Assertions.assertEquals(stringTestData[1], list.get(1));
        Assertions.assertEquals(stringTestData[2], list.get(2));
        Assertions.assertEquals(stringTestData[3], list.get(3));
        Assertions.assertEquals(stringTestData[4], list.get(4));
    }

    @Test
    void should_set_elements() {
        String newElement = "q";
        int elementToReplaceIx = 3;
        for (int i = 0; i < stringTestData.length; i++) {
            list.add(stringTestData[i]);
        }
        list.set(elementToReplaceIx, newElement);
        Assertions.assertEquals(newElement, list.get(elementToReplaceIx));
    }

    @Test
    void should_set_elements_keep_same_size() {
        String newElement = "q";
        int elementToReplaceIx = 3;
        for (int i = 0; i < stringTestData.length; i++) {
            list.add(stringTestData[i]);
        }
        int originalListLength = list.size();
        list.set(elementToReplaceIx, newElement);
        int newListLength = list.size();
        Assertions.assertEquals(originalListLength, newListLength);
    }

    @Test
    void should_not_set_negative_index() {
        CustomList emptyList = create_empty_list();
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> emptyList.set(-1, "B"));
    }

    @Test
    void should_remove_elements() {
        int elementToRemoveIx = 3;
        for (int i = 0; i < stringTestData.length; i++) {
            list.add(stringTestData[i]);
        }
        int originalListLength = list.size();
        Object removedElement = list.remove(elementToRemoveIx);
        int newListLength = list.size();
        Assertions.assertEquals(stringTestData[elementToRemoveIx], removedElement);
        Assertions.assertNotEquals(originalListLength, newListLength);
    }

    @Test
    void should_get_size() {
        for (int i = 0; i < stringTestData.length; i++) {
            list.add(stringTestData[i]);
        }
        Assertions.assertEquals(stringTestData.length, list.size());
    }

    @Test
    void should_clear_list() {
        for (int i = 0; i < stringTestData.length; i++) {
            list.add(stringTestData[i]);
        }
        int fullListSize = list.size();
        list.clear();
        int clearedListSize = list.size();
        Assertions.assertNotEquals(fullListSize, clearedListSize);
        Assertions.assertTrue(list.isEmpty());
    }

    @Test
    void should_increase_capacity() {
        int originalCapacity = list.elementData.length;
        for (int i = 0; i < stringTestData.length; i++) {
            list.add(stringTestData[i]);
        }
        int newCapacity = list.elementData.length;

        for (int i = 0; i < stringTestData.length; i++) {
            list.add(stringTestData[i]);
        }
        int newNewCapacity = list.elementData.length;

        Assertions.assertNotEquals(originalCapacity, newCapacity);
        Assertions.assertNotEquals(newCapacity, newNewCapacity);
    }
}
