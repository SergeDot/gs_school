package org.example;

import org.junit.jupiter.api.Test;

import static org.example.TestUtils.PerformanceResult;
import static org.example.TestUtils.CorrectnessResult;
import static org.example.TestUtils.*;
import static org.example.CustomTestAnnotations.*;

public class FibonacciTest {

    @Test
    @ThisIsTest
    void testReport() {
        int[] allTestArray = new int[]{10, 20, 30, 35, 45};
        for (int n : allTestArray) {
            Fibonacci fibb = new Fibonacci();
            PerformanceResult[] bulkPerformanceResults = new PerformanceResult[]{
                    testPerformance("fibonacciRecursive", Fibonacci::fibonacciRecursive, n),
                    testPerformance("fibonacciIterative", Fibonacci::fibonacciIterative, n),
                    testPerformance("fibonacciMemoized", fibb::fibonacciMemoized, n)
            };
            printPerformanceResults(bulkPerformanceResults);
        }

        int[] efficientMethodTestArray = new int[]{1000, 2000, 3000, 3500, 4500};
        for (int n : efficientMethodTestArray) {
            Fibonacci fibb = new Fibonacci();
            PerformanceResult[] bulkPerformanceResults = new PerformanceResult[]{
                    testPerformance("fibonacciIterative", Fibonacci::fibonacciIterative, n),
                    testPerformance("fibonacciMemoized", fibb::fibonacciMemoized, n)
            };
            printPerformanceResults(bulkPerformanceResults);
        }

        Fibonacci fibb = new Fibonacci();
        CorrectnessResult[] bulkCorrectnessResults = new TestUtils.CorrectnessResult[]{
                testCorrectness("fibonacciRecursive", Fibonacci::fibonacciRecursive),
                testCorrectness("fibonacciIterative", Fibonacci::fibonacciIterative),
                testCorrectness("fibonacciMemoized", fibb::fibonacciMemoized)
        };
        printCorrectnessResults(bulkCorrectnessResults);
    }
}
