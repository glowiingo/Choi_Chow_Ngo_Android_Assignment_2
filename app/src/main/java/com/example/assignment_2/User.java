package com.example.assignment_2;

import java.util.HashMap;
import java.util.Map;

public class User {

    private String personalHealthCareNo;
    private String Name;

    Map<String, Reading> Readings = new HashMap<>();

    public String getPersonalHealthCareNo() {
        return personalHealthCareNo;
    }

    public void setPersonalHealthCareNo(String personalHealthCareNo) {
        this.personalHealthCareNo = personalHealthCareNo;
    }
    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public Map<String, Reading> getReadings() {
        return Readings;
    }

    public void setReadings(Map<String, Reading> readings) {
        Readings = readings;
    }

}
