package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.example.ArrayOperations.*;
import static org.example.ArrayOperations.shiftRightArrayCopy;
import static org.example.TestUtils.*;
import static org.example.CustomTestAnnotations.*;

public class ArrayOperationsTest {

    private int[][] arraysArray;
    private int[] positionsArray;
    private final int DEFAULT_OVERWRITE_VALUE = 0;


    @BeforeEach
    @ThisIsArrangeBeforeEach
    public void setUp() {
        this.arraysArray = new int[][]{
                createIntArray(1000),
                createIntArray(10000),
                createIntArray(100000),
                createIntArray(1000000)
        };
        this.positionsArray = new int[]{1, 10, 100, 1000};
    }

    @Test
    @ThisIsTest
    void testCorrectness() {
        List<String> failedResults = new CustomList<>();

        for (int array = 0; array < arraysArray.length; array++) {
            for (int position = 0; position < positionsArray.length; position++) {
                int[] finalArray = arraysArray[array];
                int finalPosition = positionsArray[position];
                int[] origArr = finalArray.clone();
                shiftRightArrayCopy(finalArray, finalPosition);

                for (int i = 0; i < finalArray.length; i++) {
                    int comparable = i <= finalPosition ? DEFAULT_OVERWRITE_VALUE : origArr[i - finalPosition];
                    if (finalArray[i] != comparable) {
                        failedResults.add("i " + i + " arr[i] " + finalArray[i] + " comparable " + comparable);
                    }
                }
            }
        }
        Assertions.assertTrue(failedResults.size() == 0);
    }

    @Test
    @ThisIsTest
    void testArrayOperationsPerformance() {
        for (int array = 0; array < arraysArray.length; array++) {
            PerformanceResult[] bulkPerformanceResults = new PerformanceResult[2 * arraysArray.length];
            System.out.println("------------------------Array of " + arraysArray[array].length + "-----------------------------");
            for (int position = 0; position < positionsArray.length; position++) {
                int[] finalArray = arraysArray[array];
                int finalPosition = positionsArray[position];

                Runnable shiftRightArrayCopyTestable = () -> shiftRightArrayCopy(finalArray, finalPosition);
                Runnable shiftRightManualTestable = () -> shiftRightManual(finalArray, finalPosition);
                bulkPerformanceResults[2 * position] =
                        testPerformance("shiftRightArrayCopy", shiftRightArrayCopyTestable, finalPosition);
                bulkPerformanceResults[2 * position + 1] =
                        testPerformance("shiftRightManual", shiftRightManualTestable, finalPosition);
            }
            printPerformanceResults(bulkPerformanceResults);
        }
    }
}
