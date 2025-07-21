package org.example;

import lombok.SneakyThrows;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.List;

public class CustomTestRunner {
    static String packageName = "org.example";
    private static int totalTests = 0;
    private static int passedTests;
    private static int failedTests;
    private static long totalTime = 0;
    private static List<String> failedTestList = new CustomList<>();
    private static List<String> errorTestList = new CustomList<>();

    public static void main(String[] args) {
        runAllTests();
    }

    @SneakyThrows(Exception.class)
    public static void runAllTests() {
        List<Class<?>> classes = getClasses(packageName);

        for (Class<?> testableClass : classes) {
            List<Method> beforeEachMethods = new CustomList<>();
            List<Method> afterEachMethods = new CustomList<>();
            List<Method> testMethods = new CustomList<>();

            for (Method method : testableClass.getDeclaredMethods()) {
                if (method.isAnnotationPresent(CustomTestAnnotations.ThisIsArrangeBeforeEach.class)) {
                    beforeEachMethods.add(method);
                }
                if (method.isAnnotationPresent(CustomTestAnnotations.ThisIsDismantleAfterEach.class)) {
                    afterEachMethods.add(method);
                }
                if (method.isAnnotationPresent(CustomTestAnnotations.ThisIsTest.class)) {
                    testMethods.add(method);
                }
            }
            for (Method testMethod : testMethods) {
                totalTests++;
                runTest(testMethod, testableClass, beforeEachMethods, afterEachMethods);
            }
        }

        System.out.println("\nTest run complete.");
        System.out.printf("Total tests: %d, Passed: %d, Failed: %d\n", totalTests, passedTests, failedTests);
        System.out.printf("Total time: %.2f ms\n", totalTime / 1_000.0);
        if (!failedTestList.isEmpty()) {
            System.out.println("Failed tests:");
            for (String test : failedTestList) {
                System.out.println(" - " + test);
            }
        }
        if (!errorTestList.isEmpty()) {
            System.out.println("Error executing tests:");
            for (String test : errorTestList) {
                System.out.println(" - " + test);
            }
        }
    }

    private static void runTest(
            Method method,
            Class<?> testableClass,
            List<Method> beforeEachMethods,
            List<Method> afterEachMethods
    ) {
        Object instance = null;
        try {
            if (!java.lang.reflect.Modifier.isStatic(method.getModifiers())) {
                instance = testableClass.getDeclaredConstructor().newInstance();
            }
            long start = System.currentTimeMillis();

            for (Method beforeEachMethod : beforeEachMethods) {
                beforeEachMethod.invoke(instance);
            }
            try {
                method.setAccessible(true);
                method.invoke(instance);
                long duration = System.currentTimeMillis() - start;
                totalTime += duration;
                System.out.printf("[PASS] %s.%s (%.2f ms)\n", testableClass.getSimpleName(), method.getName(), duration / 1_000.0);
                passedTests++;
            } catch (Exception e) {
                long duration = System.currentTimeMillis() - start;
                totalTime += duration;
                System.out.printf("[FAIL] %s.%s (%.2f ms) - %s\n", testableClass.getSimpleName(), method.getName(), duration / 1_000.0, e.getCause());
                failedTests++;
                failedTestList.add(testableClass.getSimpleName() + "." + method.getName());
            }
            for (Method afterEachMethod : afterEachMethods) {
                afterEachMethod.invoke(instance);
            }
        } catch (Exception e) {
            errorTestList.add("Error executing test: " + testableClass.getSimpleName() + "." + method.getName() + "\n" + e.getCause());
        }
    }

    private static List<Class<?>> getClasses(String packageName) throws Exception {
        String path = packageName.replace('.', '/');
        URL resource = Thread.currentThread().getContextClassLoader().getResource(path);
        if (resource == null) return new CustomList<>();
        File directory = new File(resource.toURI());
        List<Class<?>> classes = new CustomList<>();
        for (String file : directory.list()) {
            if (file.endsWith(".class")) {
                String className = packageName + '.' + file.substring(0, file.length() - 6);
                classes.add(Class.forName(className));
            }
        }
        return classes;
    }
}
