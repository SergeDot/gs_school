package org.example;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

// sources
// https://tanbt.medium.com/memory-leak-and-memory-consumption-test-in-java-8f144955f1ab
// https://stackoverflow.com/questions/19785290/java-unit-testing-how-to-measure-memory-footprint-for-method-call
// https://www.vogella.com/tutorials/JavaPerformance/article.html

public class CustomListPerformanceTest {
    private static final int BULK_SIZE = 1_000_000;
    private static final int ADD_REMOVE_SIZE = 10_000;

    private static final Runtime runtime = Runtime.getRuntime();

    private static long getCurrentUsedMemoryInKB() {
        return (runtime.totalMemory() - runtime.freeMemory()) / 1024L;
    }

    private static long deltaCalculator(long comparable, long base) {
        return -100 * (base - comparable)/base;
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
    
    private Result testObjectBulkAddition(String name, List<Object> list, int count) {
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

    private Result testIntBulkAddition(String name, List<Integer> list, int count) {
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
    void performanceReport() {
        System.out.println("\n--- Bulk Addition Test (1,000,000 integers) ---");
        Result customListResultInt = testIntBulkAddition("CustomList", new CustomList<>(), BULK_SIZE);
        Result[] bulkResultsInt = new Result[] {
                testIntBulkAddition("LinkedList", new LinkedList<>(), BULK_SIZE),
                testIntBulkAddition("ArrayList", new ArrayList<>(), BULK_SIZE)
        };
        printResults(bulkResultsInt, customListResultInt);

        System.out.println("\n--- Bulk Addition Test (1,000,000 objects) ---");
        Result customListResultObj = testObjectBulkAddition("CustomList", new CustomList<>(), BULK_SIZE);
        Result[] bulkResultsObj = new Result[] {
                testObjectBulkAddition("LinkedList", new LinkedList<>(), BULK_SIZE),
                testObjectBulkAddition("ArrayList", new ArrayList<>(), BULK_SIZE)
        };
        printResults(bulkResultsObj, customListResultObj);

        System.out.println("\n--- Add/Remove Test (10,000 add, 10,000 remove) ---");
        Result customListAddRemoveResult = testAddRemove("CustomList", new CustomList<>(), ADD_REMOVE_SIZE);
        Result[] addRemoveResults = new Result[] {
                testAddRemove("LinkedList", new LinkedList<>(), ADD_REMOVE_SIZE),
                testAddRemove("ArrayList", new ArrayList<>(), ADD_REMOVE_SIZE)
        };
        printResults(addRemoveResults, customListAddRemoveResult);
    }
}
