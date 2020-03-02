package com.example.assignment_2;

import java.util.HashMap;
import java.util.Map;

public class User {
    private int personalHealthCareNo;
    private String name;
    Map<String, Reading> Readings = new HashMap<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
