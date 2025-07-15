package org.example;

import lombok.SneakyThrows;

public class ArrayOperations {

    private static String outOfBoundsMsg(int[] arr, int index) {
        return "Index: " + index + ", Size: " + arr.length;
    }

    @SneakyThrows
    private static void rangeCheck(int[] arr, int index) {
        if (index < 0 || arr == null)
            throw new IndexOutOfBoundsException(outOfBoundsMsg(arr, index));
    }

    // Shift elements to the right using System.arraycopy
    public static void shiftRightArrayCopy(int[] arr, int positions) {
        rangeCheck(arr, positions);
        int defaultValue = 0;

        if (positions < arr.length) {
            System.arraycopy(arr, 0, arr, positions, arr.length - positions);
        }
        int replaceables = Math.min(positions, arr.length);
        for (int i = 0; i < replaceables; i++) {
            arr[i] = defaultValue;
        }
    }

    // Shift elements to the right using manual for loop
    public static void shiftRightManual(int[] arr, int positions) {
        rangeCheck(arr, positions);
        int replaceables = Math.min(positions, arr.length);
        for (int i = arr.length - 1; i >= replaceables; i--) {
            arr[i] = arr[i - replaceables];
        }
        for (int i = 0; i < replaceables; i++) {
            arr[i] = 0;
        }
    }
}
