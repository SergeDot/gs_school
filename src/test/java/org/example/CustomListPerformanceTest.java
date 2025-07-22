package org.example;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import static org.example.CustomTestAnnotations.*;

// sources
// https://tanbt.medium.com/memory-leak-and-memory-consumption-test-in-java-8f144955f1ab
// https://stackoverflow.com/questions/19785290/java-unit-testing-how-to-measure-memory-footprint-for-method-call
// https://www.vogella.com/tutorials/JavaPerformance/article.html

public class CustomListPerformanceTest {
    private static final int BULK_SIZE = 10_000_000;
    private static final int ADD_REMOVE_SIZE = 200_000;

    private static final Runtime runtime = Runtime.getRuntime();

    private static long getCurrentUsedMemoryInKB() {
        return (runtime.totalMemory() - runtime.freeMemory()) / 1024L;
    }

    private static long deltaCalculator(long comparable, long base) {
        return base == 0 ? -100 : -100 * (base - comparable) / base;
//        return -100 * (base - comparable) / base;
    }

    private static class Result {
        String name;
        long timeMs;
        long memoryKB;

        Result(String name, long timeMs, long memoryKB) {
            this.name = name;
            this.timeMs = timeMs;
            this.memoryKB = memoryKB;
        }
    }

    private Result testAddObjectBulkAddition(String name, List<Object> list, int count) {
        System.gc();
        Object object = new Object();
        long beforeMem = getCurrentUsedMemoryInKB();
        long start = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            list.add(object);
        }
        long end = System.currentTimeMillis();
        long afterMem = getCurrentUsedMemoryInKB();
        return new Result(name, end - start, (afterMem - beforeMem));
    }

    private Result testPushObjectBulkAddition(String name, CustomLinkedList<Object> list, int count) {
        System.gc();
        Object object = new Object();
        long beforeMem = getCurrentUsedMemoryInKB();
        long start = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            list.add(object);
        }
        long end = System.currentTimeMillis();
        long afterMem = getCurrentUsedMemoryInKB();
        return new Result(name, end - start, (afterMem - beforeMem));
    }

    private Result testEnqueueObjectBulkAddition(String name, CustomQueue<Object> list, int count) {
        System.gc();
        Object object = new Object();
        long beforeMem = getCurrentUsedMemoryInKB();
        long start = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            list.add(object);
        }
        long end = System.currentTimeMillis();
        long afterMem = getCurrentUsedMemoryInKB();
        return new Result(name, end - start, (afterMem - beforeMem));
    }

    private Result testAddIntBulkAddition(String name, List<Integer> list, int count) {
        System.gc();
        long beforeMem = getCurrentUsedMemoryInKB();
        long start = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            list.add(i);
        }
        long end = System.currentTimeMillis();
        long afterMem = getCurrentUsedMemoryInKB();
        return new Result(name, end - start, (afterMem - beforeMem));
    }

    private Result testPushIntBulkAddition(String name, CustomLinkedList<Integer> list, int count) {
        System.gc();
        long beforeMem = getCurrentUsedMemoryInKB();
        long start = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            list.add(i);
        }
        long end = System.currentTimeMillis();
        long afterMem = getCurrentUsedMemoryInKB();
        return new Result(name, end - start, (afterMem - beforeMem));
    }

    private Result testEnqueueIntBulkAddition(String name, CustomQueue<Integer> list, int count) {
        System.gc();
        long beforeMem = getCurrentUsedMemoryInKB();
        long start = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            list.add(i);
        }
        long end = System.currentTimeMillis();
        long afterMem = getCurrentUsedMemoryInKB();
        return new Result(name, end - start, (afterMem - beforeMem));
    }

    private Result testAddRemove(String name, List<Integer> list, int count) {
        System.gc();
        long beforeMem = getCurrentUsedMemoryInKB();
        long start = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            list.add(i);
        }
        for (int i = 0; i < count; i++) {
            list.remove(0);
        }
        long end = System.currentTimeMillis();
        long afterMem = getCurrentUsedMemoryInKB();
        return new Result(name, end - start, (afterMem - beforeMem));
    }

    private Result testPushPop(String name, CustomLinkedList<Integer> list, int count) {
        System.gc();
        long beforeMem = getCurrentUsedMemoryInKB();
        long start = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            list.push(i);
        }
        for (int i = 0; i < count; i++) {
            list.pop();
        }
        long end = System.currentTimeMillis();
        long afterMem = getCurrentUsedMemoryInKB();
        return new Result(name, end - start, (afterMem - beforeMem));
    }

    private Result testEnqueueDequeue(String name, CustomQueue<Integer> list, int count) {
        System.gc();
        long beforeMem = getCurrentUsedMemoryInKB();
        long start = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            list.enqueue(i);
        }
        for (int i = 0; i < count; i++) {
            list.dequeue();
        }
        long end = System.currentTimeMillis();
        long afterMem = getCurrentUsedMemoryInKB();
        return new Result(name, end - start, (afterMem - beforeMem));
    }

    private void printResults(Result[] results, Result customListResult) {
        System.out.printf(
                "%-20s | %-12s | %-12s | %-12s | %-12s %n",
                "Collection",
                "Time (ms)",
                "Memory (KB)",
                "Time Delta (%)",
                "Memory Delta (%)"
        );
        System.out.println("------------------------------------------------------------------------------------------");
        for (Result r : results) {
            System.out.printf(
                    "%-20s | %-12d | %,-12d | %-12d%% | %-12d%% %n",
                    r.name,
                    r.timeMs,
                    r.memoryKB,
                    deltaCalculator(customListResult.timeMs, r.timeMs),
                    deltaCalculator(customListResult.memoryKB, r.memoryKB)
            );
        }
        System.out.printf(
                "%-20s | %-12d | %,-12d | %-12s  | %-12s %n",
                customListResult.name,
                customListResult.timeMs,
                customListResult.memoryKB,
                "",
                ""
        );

        System.out.println();
    }

    @Test
    @ThisIsTest
    void addPerformanceReport() {
        String additionTestName = "Bulk Regular Addition Test using add()";
        String addRemoveTestName = "Regular Add/Remove Test using add() + remove()";

        System.out.printf("\n--- %s (%,d integers) ---\n", additionTestName, BULK_SIZE);
        Result customListResultInt = testAddIntBulkAddition("CustomList", new CustomList<>(), BULK_SIZE);
        Result[] bulkResultsInt = new Result[]{
                testAddIntBulkAddition("ArrayList", new ArrayList<>(), BULK_SIZE),
                testAddIntBulkAddition("LinkedList", new LinkedList<>(), BULK_SIZE),
                testAddIntBulkAddition("CustomLinkedList", new CustomLinkedList<>(), BULK_SIZE),
                testAddIntBulkAddition("Stack", new Stack<>(), BULK_SIZE),
                testAddIntBulkAddition("CustomStack", new CustomStack<>(), BULK_SIZE),
                testAddIntBulkAddition("CustomQueue", new CustomQueue<>(), BULK_SIZE),
        };
        printResults(bulkResultsInt, customListResultInt);

        System.out.printf("\n--- %s (%,d objects) ---\n", additionTestName, BULK_SIZE);
        Result customListResultObj = testAddObjectBulkAddition("CustomList", new CustomList<>(), BULK_SIZE);
        Result[] bulkResultsObj = new Result[]{
                testAddObjectBulkAddition("ArrayList", new ArrayList<>(), BULK_SIZE),
                testAddObjectBulkAddition("LinkedList", new LinkedList<>(), BULK_SIZE),
                testAddObjectBulkAddition("CustomLinkedList", new CustomLinkedList<>(), BULK_SIZE),
                testAddObjectBulkAddition("Stack", new Stack<>(), BULK_SIZE),
                testAddObjectBulkAddition("CustomStack", new CustomStack<>(), BULK_SIZE),
                testAddObjectBulkAddition("CustomQueue", new CustomQueue<>(), BULK_SIZE)
        };
        printResults(bulkResultsObj, customListResultObj);

        System.out.printf("\n--- %s (%,d add, %,d remove) ---\n", addRemoveTestName, ADD_REMOVE_SIZE, ADD_REMOVE_SIZE);
        Result customListAddRemoveResult = testAddRemove("CustomList", new CustomList<>(), ADD_REMOVE_SIZE);
        Result[] addRemoveResults = new Result[]{
                testAddRemove("ArrayList", new ArrayList<>(), ADD_REMOVE_SIZE),
                testAddRemove("LinkedList", new LinkedList<>(), ADD_REMOVE_SIZE),
                testAddRemove("CustomLinkedList", new CustomLinkedList<>(), ADD_REMOVE_SIZE),
                testAddRemove("Stack", new Stack<>(), ADD_REMOVE_SIZE),
                testAddRemove("CustomStack", new CustomStack<>(), ADD_REMOVE_SIZE),
                testAddRemove("CustomQueue", new CustomQueue<>(), ADD_REMOVE_SIZE)
        };
        printResults(addRemoveResults, customListAddRemoveResult);
    }

    @Test
    @ThisIsTest
    void altAddPerformanceReport() {
        String additionTestName = "Bulk Alt Addition Test using dedicated methods";
        String addRemoveTestName = "Alt Add/Remove Test using dedicated methods";

        System.out.printf("\n--- %s (%,d integers) ---\n", additionTestName, BULK_SIZE);
        Result customListResultInt = testAddIntBulkAddition("CustomList", new CustomList<>(), BULK_SIZE);
        Result[] bulkResultsInt = new Result[]{
                testPushIntBulkAddition("CustomLinkedList", new CustomLinkedList<>(), BULK_SIZE),
                testPushIntBulkAddition("CustomStack", new CustomStack<>(), BULK_SIZE),
                testEnqueueIntBulkAddition("CustomQueue", new CustomQueue<>(), BULK_SIZE),
        };
        printResults(bulkResultsInt, customListResultInt);

        System.out.printf("\n--- %s (%,d objects) ---\n", additionTestName, BULK_SIZE);
        Result customListResultObj = testAddObjectBulkAddition("CustomList", new CustomList<>(), BULK_SIZE);
        Result[] bulkResultsObj = new Result[]{
                testPushObjectBulkAddition("CustomLinkedList", new CustomLinkedList<>(), BULK_SIZE),
                testPushObjectBulkAddition("CustomStack", new CustomStack<>(), BULK_SIZE),
                testEnqueueObjectBulkAddition("CustomQueue", new CustomQueue<>(), BULK_SIZE)
        };
        printResults(bulkResultsObj, customListResultObj);

        System.out.printf("\n--- %s (%,d add, %,d remove) ---\n", addRemoveTestName, ADD_REMOVE_SIZE, ADD_REMOVE_SIZE);
        Result customListAddRemoveResult = testAddRemove("CustomList", new CustomList<>(), ADD_REMOVE_SIZE);
        Result[] addRemoveResults = new Result[]{
                testPushPop("CustomLinkedList", new CustomLinkedList<>(), ADD_REMOVE_SIZE),
                testPushPop("CustomStack", new CustomStack<>(), ADD_REMOVE_SIZE),
                testEnqueueDequeue("CustomQueue", new CustomQueue<>(), ADD_REMOVE_SIZE)
        };
        printResults(addRemoveResults, customListAddRemoveResult);
    }
}
