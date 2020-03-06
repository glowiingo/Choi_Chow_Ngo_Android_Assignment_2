package com.example.assignment_2;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Objects;

public class ReadingDetailsActivity extends AppCompatActivity {

    private JSONObject jsonReadingsObject;
    private int readingIndex = -1;
    private JSONObject jsonReading;
    private String userName;
    String date = "";
    String time = "";
    String condition = "";
    String systolic = "";
    String diastolic = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading_details);

        String jsonReadingsObjectString = "";
        jsonReadingsObjectString = (String) Objects.requireNonNull(getIntent().getExtras()).get(this.getResources().getString(R.string.jsonReadings));
        readingIndex = (int) getIntent().getExtras().get(this.getResources().getString(R.string.index));
        userName = (String) getIntent().getExtras().get(this.getResources().getString(R.string.username));
        String[] username = userName.split("-", 2);
        String phno = username[0];
        String name = username[1];

        try {
            assert jsonReadingsObjectString != null;
            jsonReadingsObject = new JSONObject(jsonReadingsObjectString);
            Log.i("jsonReadingsObject", jsonReadingsObjectString);
        } catch (JSONException e) {
            Log.e("Error", e.toString());
        }

        populatePage();

        TextView phnoTextView = (TextView) findViewById(R.id.phnoTextView);
        String phnoTextViewText = this.getResources().getString(R.string.phno) + phno;
        phnoTextView.setText(phnoTextViewText);

        TextView nameTextView = (TextView) findViewById(R.id.nameTextView);
        String nameTextViewText = this.getResources().getString(R.string.name_label) + name;
        nameTextView.setText(nameTextViewText);

        TextView dateTextView = (TextView) findViewById(R.id.dateRecordedTextView);
        String dateTextViewText = this.getResources().getString(R.string.date_label) + date;
        dateTextView.setText(dateTextViewText);

        TextView timeTextView = (TextView) findViewById(R.id.timeTextView);
        String timeTextViewText = this.getResources().getString(R.string.time_label) + time;
        timeTextView.setText(timeTextViewText);

        TextView systolicTextView = (TextView) findViewById(R.id.systolicTextView);
        String systolicTextViewText = this.getResources().getString(R.string.systolic_label) + systolic;
        systolicTextView.setText(systolicTextViewText);

        TextView diastolicTextView = (TextView) findViewById(R.id.diastolicTextView);
        String diastolicTextViewText = this.getResources().getString(R.string.diastolic_label) + diastolic;
        diastolicTextView.setText(diastolicTextViewText);

        TextView conditionTextView = (TextView) findViewById(R.id.conditionTextView);
        String conditionTextViewText = this.getResources().getString(R.string.condition_label) + condition;
        conditionTextView.setText(conditionTextViewText);
    }

    public void populatePage() {
        // get which reading was selected
        try {
            JSONArray readingKeysList = jsonReadingsObject.names();
            assert readingKeysList != null;
            String readingID = readingKeysList.get(readingIndex).toString();
            Log.i("ReadingID: ", readingID);
            jsonReading = jsonReadingsObject.getJSONObject(readingID);
            Log.i("ReadingObject: ", jsonReading.toString());
            date = jsonReading.getString(this.getResources().getString(R.string.date_key));
            time = jsonReading.getString(this.getResources().getString(R.string.time_key));
            condition = jsonReading.getString(this.getResources().getString(R.string.condition_key));
            systolic = jsonReading.getString(this.getResources().getString(R.string.systolic_key));
            diastolic = jsonReading.getString(this.getResources().getString(R.string.diastolic_key));
        } catch (JSONException e) {
            Log.e("userListConversion: ", e.toString());
        }
    }
}
