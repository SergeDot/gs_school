package org.example;

import java.io.IOException;

import lombok.SneakyThrows;

public class ExampleSneakyThrows {
    @SneakyThrows(IOException.class)
    public void readFile() {
        // Simulating code that might throw IOException
        if (true) {
            throw new IOException("Simulated IOException");
        }
    }

    public void exampleMethod() { // No need for try-catch or "throws" declaration
        readFile();
    }

    public static void main(String[] args) {
        ExampleSneakyThrows exampleSneakyThrows = new ExampleSneakyThrows();
        exampleSneakyThrows.exampleMethod();
    }
}
