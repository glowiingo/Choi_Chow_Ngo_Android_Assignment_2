package com.example.assignment_2;
import android.os.Build;
import android.view.View;

import androidx.annotation.RequiresApi;

import java.time.LocalTime;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static java.time.LocalTime.*;

public class Reading {
    private Float systolic;
    private Float diastolic;
    private String condition;
    private String date;
    private String time;
    private HypertensiveFragment hf = new HypertensiveFragment();

    public Float getSystolic() {
        return systolic;
    }

    public void setSystolic(Float systolic) {
        this.systolic = systolic;
    }

    public Float getDiastolic() {
        return diastolic;
    }

    public void setDiastolic(Float diastolic) {
        this.diastolic = diastolic;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String getDate(){
        LocalDate ld = LocalDate.now();
        date = ld.toString();
        return date;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String getTime(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        time = LocalTime.now().toString();
        return time;
    }

    //this be gross
    public String determineCondition(float systolic, float diastolic){
        if (systolic >= 180)
            return "Hypertensive Crisis";
        if ((systolic >= 140 && systolic < 180) || (diastolic > 90 && diastolic < 20))
            return "High blood pressure (stage 2)";
        if ((systolic >= 130 && systolic < 140) || (diastolic > 80 && diastolic <= 90))
            return "High blood pressure (stage 1)";
        if ((systolic >= 129 && systolic < 130) && (diastolic <= 80))
            return "Elevated";
        else
            return "Normal";
    }

}
