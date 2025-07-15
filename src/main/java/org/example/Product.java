package org.example;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class Product {
    @Getter @Setter private String type;
    @Getter @Setter private String brand;
    @Getter @Setter(AccessLevel.PUBLIC) private String model;
    @Getter @Setter(AccessLevel.PROTECTED) private String cpuModel;
    @Getter @Setter(AccessLevel.PACKAGE) private double cpuSpeed;
    @Getter @Setter(AccessLevel.PRIVATE) private int cpuCoreNumber;
    @Getter @Setter private boolean isGPUDedicated;
    @Getter @Setter private int ramNumberOfSlots;
    @Getter @Setter private String ramtype;
    @Getter @Setter private int maxRamSize;
    @Getter @Setter private int defaultRamSize;
    @Getter @Setter private boolean isTouchscreen;
    @Getter @Setter private String color;
    @Getter @Setter private double displaySize;
}
