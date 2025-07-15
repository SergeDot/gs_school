package org.example;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class TestUtils {
    private static Map<Integer, Long> BENCHMARK = Fibonacci.listFirstFibonacci();

    @Data
    @AllArgsConstructor
    public static class PerformanceResult {
        String name;
        int methodInput;
        long timeMs;
        long memoryKB;
    }

    public static void printPerformanceResults(PerformanceResult[] results) {
        System.out.println("------------------------Performance Test-----------------------------");

        System.out.printf(
                "%-20s | %-20s | %-12s | %-12s | %n",
                "Method",
                "Method Input",
                "Time (ms)",
                "Memory (KB)"
        );

        System.out.println("---------------------------------------------------------------------");
        for (PerformanceResult r : results) {
            System.out.printf(
                    "%-20s | %-20d | %-12d | %,-12d | %n",
                    r.name,
                    r.methodInput,
                    r.timeMs,
                    r.memoryKB
            );
        }
        System.out.println();
    }

    private static final Runtime runtime = Runtime.getRuntime();

    private static long getCurrentUsedMemoryInKB() {
        return (runtime.totalMemory() - runtime.freeMemory()) / 1024L;
    }

    public static PerformanceResult testPerformance(String name, Function<Integer, Long> method, int input) {
        // Force garbage collection before test
        System.gc();

        // Get initial memory usage
        long beforeMem = getCurrentUsedMemoryInKB();
        // Measure execution time
        long startTime = System.currentTimeMillis();

        method.apply(input);

        long endTime = System.currentTimeMillis();
        long timeUsed = endTime - startTime;
        // Get final memory usage
        long afterMem = getCurrentUsedMemoryInKB();
        long memoryUsed = afterMem - beforeMem;

        return new PerformanceResult(name, input, timeUsed, memoryUsed);
    }

    public static PerformanceResult testPerformance(String name, Runnable method, int input) {
        // Force garbage collection before test
        System.gc();

        // Get initial memory usage
        long beforeMem = getCurrentUsedMemoryInKB();
        // Measure execution time
        long startTime = System.currentTimeMillis();

        method.run();

        long endTime = System.currentTimeMillis();
        long timeUsed = endTime - startTime;
        // Get final memory usage
        long afterMem = getCurrentUsedMemoryInKB();
        long memoryUsed = afterMem - beforeMem;

        return new PerformanceResult(name, input, timeUsed, memoryUsed);
    }

    @Data
    @AllArgsConstructor
    public static class CorrectnessFailedResult {
        int memberPos;
        long actualResult;
        long expectedResult;
    }

    @Data
    public static class CorrectnessResult {
        String name;
        List<CorrectnessFailedResult> failedResults;

        public CorrectnessResult(String name) {
            this.name = name;
            this.failedResults = new CustomList<>();
        }
    }

    public static void printCorrectnessResults(CorrectnessResult[] results) {
        System.out.println("------------------------Correctness Test-----------------------------");
        System.out.printf(
                "| %-20s | %-20s | %-20s %n",
                "Method",
                "Passed",
                "If failed"
        );
        System.out.println("---------------------------------------------------------------------");

        for (CorrectnessResult r : results) {
            boolean isPassed = r.failedResults == null || r.failedResults.size() == 0;
            System.out.printf(
                    "| %-20s | %-20s | %n",
                    r.name,
                    isPassed ? isPassed : ""
            );
            if (!isPassed) {
                for (CorrectnessFailedResult f : r.failedResults) {
                    System.out.printf("| %-20s | %-20s | Member Position: %-20s %n", "", isPassed, f.memberPos);
                    System.out.printf("| %-20s | %-20s | Actual Result: %-20s %n", "", "", f.actualResult);
                    System.out.printf("| %-20s | %-20s | Expected Result: %-20s %n", "", "", f.expectedResult);
                    System.out.printf("| %-20s | %-20s | %-20s %n", "", "", "");
                }
            }
        }
        System.out.println();
    }

    public static CorrectnessResult testCorrectness(String name, Function<Integer, Long> method) {
        int numberFibMembers = 35;
        CorrectnessResult result = new CorrectnessResult(name);
        for (int i = 0; i < numberFibMembers; i++) {
            long actualResult = method.apply(i);
            long expectedResult = BENCHMARK.get(i);
            if (actualResult != expectedResult) {
                result.failedResults.add(new CorrectnessFailedResult(i, actualResult, expectedResult));
            }
        }
        return result;
    }

    public static int[] createIntArray(int size) {
        int[] result = new int[size];
        for (int i = 0; i < size; i++) {
            result[i] = i;
        }
        return result;
    }
}
