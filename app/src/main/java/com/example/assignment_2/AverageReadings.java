package com.example.assignment_2;

import androidx.annotation.RequiresPermission;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AverageReadings extends AppCompatActivity {

    private JSONObject jsonReadingsObject;
    private int listLength = 0;
    private int userIndex = -1;
    private JSONObject userReadings;
    private String userName;
    String monthCode;
    int month_no;
    float total_avg_systolic;
    float total_avg_diastolic;
    String average_condition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_average_readings);

        String jsonReadingsString = (String) Objects.requireNonNull(getIntent().getExtras()).get(this.getResources().getString(R.string.jsonReadings));
        try {
            assert jsonReadingsString != null;
            jsonReadingsObject = new JSONObject(jsonReadingsString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        assert jsonReadingsObject != null;
        JSONArray readingKeysArray = jsonReadingsObject.names();
        assert readingKeysArray != null;
        Log.e("Keys", readingKeysArray.toString());

        userName = (String) getIntent().getExtras().get(this.getResources().getString(R.string.username));
        assert userName != null;
        String[] phno_name = userName.split("-", 2);


        // display average readings of the month from the first day of the month to current day
        TextView month_avg_txtView = (TextView) findViewById(R.id.month_average_title);
        Reading r = new Reading();
        String[] dateInfo = r.getDate().split("-");
        String month = dateInfo[1];
        month_no = Integer.parseInt(month);

        switch(month_no) {
            case 1:
                monthCode = this.getResources().getString(R.string.jan);
                break;
            case 2:
                monthCode = this.getResources().getString(R.string.feb);
                break;
            case 3:
                // code block
                monthCode = this.getResources().getString(R.string.mar);
                break;
            case 4:
                // code block
                monthCode = this.getResources().getString(R.string.apr);
                break;
            case 5:
                // code block
                monthCode = this.getResources().getString(R.string.may);
                break;
            case 6:
                // code block
                monthCode = this.getResources().getString(R.string.jun);
                break;
            case 7:
                // code block
                monthCode = this.getResources().getString(R.string.jul);
                break;
            case 8:
                // code block
                monthCode = this.getResources().getString(R.string.aug);
                break;
            case 9:
                // code block
                monthCode = this.getResources().getString(R.string.sep);
                break;
            case 10:
                // code block
                monthCode = this.getResources().getString(R.string.oct);
                break;
            case 11:
                // code block
                monthCode = this.getResources().getString(R.string.nov);
                break;
            case 12:
                // code block
                monthCode = this.getResources().getString(R.string.dec);
                break;
            default:
                // code block
        }
        String month_avg_txtView_Text = this.getResources().getString(R.string.average_title) + monthCode + dateInfo[0];
        month_avg_txtView.setText(month_avg_txtView_Text);

        TextView name_average_textView = (TextView) findViewById(R.id.name_average_label);
        String name_average_textView_text = this.getResources().getString(R.string.name_label) + phno_name[1];
        name_average_textView.setText(name_average_textView_text);

        // get json of all readings
        int length = readingKeysArray.length();
        JSONArray readingsList = new JSONArray();
        for (int i = 0; i < length; i++) {
            String key = "";
            JSONObject reading;
            try {
                key = (String) readingKeysArray.get(i);
                reading = jsonReadingsObject.getJSONObject(key);
                String date = reading.getString(AverageReadings.this.getResources().getString(R.string.date_key));
                String[] dateReadingInfo = date.split("-");
                String readingMonth = dateReadingInfo[1];
                if (month_no == Integer.parseInt(readingMonth)) {
                    readingsList.put(reading);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Log.e("ReadingList", readingsList.toString());
        if (readingsList.length() > 0) {
            float average_systolic = 0.0f;
            float average_diastolic = 0.0f;
            for (int i = 0; i < readingsList.length(); i++) {
                try {
                    JSONObject currentReading = (JSONObject) readingsList.get(i);
                    String systolic_float_str = currentReading.getString(this.getResources().getString(R.string.systolic_key));
                    average_systolic += Float.parseFloat(systolic_float_str);
                    String diastolic_float_str = currentReading.getString(this.getResources().getString(R.string.diastolic_key));
                    average_diastolic += Float.parseFloat(diastolic_float_str);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            total_avg_systolic = average_systolic / readingsList.length();
            total_avg_diastolic = average_diastolic / readingsList.length();
        }
        average_condition = r.determineCondition(total_avg_systolic, total_avg_diastolic);

        TextView monthAvg = (TextView) findViewById(R.id.month_average_condition);
        String monthAvgCondText = this.getResources().getString(R.string.avg_condition_label) + average_condition;
        monthAvg.setText(monthAvgCondText);

        TextView sys_avg = (TextView) findViewById(R.id.systolic_reading_average);
        String sys_avg_txt = this.getResources().getString(R.string.avg_systolic) + total_avg_systolic;
        sys_avg.setText(sys_avg_txt);

        TextView dia_avg = (TextView) findViewById(R.id.diastolic_reading_average);
        String dia_avg_txt = this.getResources().getString(R.string.avg_diastolic) + total_avg_diastolic;
        dia_avg.setText(dia_avg_txt);

        setColor(average_condition);
    }


    public void setColor(String condition) {
        if (condition.equals("Hypertensive Crisis")) {
            LinearLayout avg_layout = (LinearLayout) findViewById(R.id.average_readings_layout);
            avg_layout.setBackgroundColor(getResources().getColor(R.color.colorHypertensiveBP));
        } else if (condition.equals("High blood pressure (stage 2)")) {
            LinearLayout avg_layout = (LinearLayout) findViewById(R.id.average_readings_layout);
            avg_layout.setBackgroundColor(getResources().getColor(R.color.colorHigh2BP));
        } else if (condition.equals("High blood pressure (stage 1)")) {
            LinearLayout avg_layout = (LinearLayout) findViewById(R.id.average_readings_layout);
            avg_layout.setBackgroundColor(getResources().getColor(R.color.colorHigh1BP));
        } else if (condition.equals("Elevated")) {
            LinearLayout avg_layout = (LinearLayout) findViewById(R.id.average_readings_layout);
            avg_layout.setBackgroundColor(getResources().getColor(R.color.colorElevatedBP));
        } else {
            LinearLayout avg_layout = (LinearLayout) findViewById(R.id.average_readings_layout);
            avg_layout.setBackgroundColor(getResources().getColor(R.color.colorNormalBP));
        }
    }
}
