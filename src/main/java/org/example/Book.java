package org.example;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Book {
    private String title;
    private String author;
    private String pages;
    private String price;
}
