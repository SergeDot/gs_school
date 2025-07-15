package org.example;

import java.util.*;

public class Fibonacci {

    // Recursive implementation
    public static long fibonacciRecursive(int n) {
        if (n <= 1) {
            return n;
        }
        return fibonacciRecursive(n - 1) + fibonacciRecursive(n - 2);
    }

    // Memoized implementation
    private List<Long> fibCacheList = new ArrayList<>(Arrays.asList(0l, 1l));

    public long fibonacciMemoized(int n) {
        if (n <= 1) {
            return n;
        }

        if (n < fibCacheList.size()) {
            return fibCacheList.get(n);
        }

        long fibPrev = fibonacciMemoized(n - 1);
        long fibPrevPrev = fibonacciMemoized(n - 2);
        long result = fibPrev + fibPrevPrev;
        fibCacheList.add(n, result);

        return result;
    }

    // Iterative implementation
    public static long fibonacciIterative(int n) {
        if (n <= 1) {
            return n;
        }

        int prev = 0, curr = 1;
        for (int i = 2; i <= n; i++) {
            int next = prev + curr;
            prev = curr;
            curr = next;
        }

        return curr;
    }

    // List the first 45 Fibonacci numbers
    public static Map<Integer, Long> listFirstFibonacci() {
        Map<Integer, Long> benchmarkFibonacciMap = new HashMap<>();
        benchmarkFibonacciMap.put(0, 0L);
        benchmarkFibonacciMap.put(1, 1L);
        benchmarkFibonacciMap.put(2, 1L);
        benchmarkFibonacciMap.put(3, 2L);
        benchmarkFibonacciMap.put(4, 3L);
        benchmarkFibonacciMap.put(5, 5L);
        benchmarkFibonacciMap.put(6, 8L);
        benchmarkFibonacciMap.put(7, 13L);
        benchmarkFibonacciMap.put(8, 21L);
        benchmarkFibonacciMap.put(9, 34L);
        benchmarkFibonacciMap.put(10, 55L);
        benchmarkFibonacciMap.put(11, 89L);
        benchmarkFibonacciMap.put(12, 144L);
        benchmarkFibonacciMap.put(13, 233L);
        benchmarkFibonacciMap.put(14, 377L);
        benchmarkFibonacciMap.put(15, 610L);
        benchmarkFibonacciMap.put(16, 987L);
        benchmarkFibonacciMap.put(17, 1597L);
        benchmarkFibonacciMap.put(18, 2584L);
        benchmarkFibonacciMap.put(19, 4181L);
        benchmarkFibonacciMap.put(20, 6765L);
        benchmarkFibonacciMap.put(21, 10946L);
        benchmarkFibonacciMap.put(22, 17711L);
        benchmarkFibonacciMap.put(23, 28657L);
        benchmarkFibonacciMap.put(24, 46368L);
        benchmarkFibonacciMap.put(25, 75025L);
        benchmarkFibonacciMap.put(26, 121393L);
        benchmarkFibonacciMap.put(27, 196418L);
        benchmarkFibonacciMap.put(28, 317811L);
        benchmarkFibonacciMap.put(29, 514229L);
        benchmarkFibonacciMap.put(30, 832040L);
        benchmarkFibonacciMap.put(31, 1346269L);
        benchmarkFibonacciMap.put(32, 2178309L);
        benchmarkFibonacciMap.put(33, 3524578L);
        benchmarkFibonacciMap.put(34, 5702887L);
        benchmarkFibonacciMap.put(35, 9227465L);
        benchmarkFibonacciMap.put(36, 14930352L);
        benchmarkFibonacciMap.put(37, 24157817L);
        benchmarkFibonacciMap.put(38, 39088169L);
        benchmarkFibonacciMap.put(39, 63245986L);
        benchmarkFibonacciMap.put(40, 102334155L);
        benchmarkFibonacciMap.put(41, 165580141L);
        benchmarkFibonacciMap.put(42, 267914296L);
        benchmarkFibonacciMap.put(43, 433494437L);
        benchmarkFibonacciMap.put(44, 701408733L);
        benchmarkFibonacciMap.put(45, 1134903170L);
        return benchmarkFibonacciMap;
    }
}
