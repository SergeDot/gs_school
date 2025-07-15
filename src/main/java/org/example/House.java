package org.example;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.util.List;

@Builder
@Data
public class House {
    private String address;
    @Singular
    private List<String> rooms;
    private String area;
    private String price;
}
