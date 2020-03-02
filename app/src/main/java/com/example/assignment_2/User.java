package com.example.assignment_2;

import java.util.HashMap;
import java.util.Map;

public class User {

    private String personalHealthCareNo;
    private String name;

    Map<String, Reading> Readings = new HashMap<>();

    public String getPersonalHealthCareNo() {
        return personalHealthCareNo;
    }

    public void setPersonalHealthCareNo(String personalHealthCareNo) {
        this.personalHealthCareNo = personalHealthCareNo;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Reading> getReadings() {
        return Readings;
    }

    public void setReadings(Map<String, Reading> readings) {
        Readings = readings;
    }

}
