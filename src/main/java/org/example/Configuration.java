package org.example;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class Configuration {
    private Person person;
    private House house;
    private Car car;
    private Product product;
    private Book book;
}
