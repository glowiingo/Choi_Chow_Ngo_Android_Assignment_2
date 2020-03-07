package com.example.assignment_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListReadingsActivity extends AppCompatActivity {

    private JSONObject jsonUsersObject;
    private int listLength = 0;
    private int userIndex = -1;
    private JSONObject userReadings;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_readings);

        String jsonUserObjectString = "";
        jsonUserObjectString = (String) Objects.requireNonNull(getIntent().getExtras()).get(this.getResources().getString(R.string.jsonUsers));
        userIndex = (int) getIntent().getExtras().get(this.getResources().getString(R.string.index));
        try {
            assert jsonUserObjectString != null;
            jsonUsersObject = new JSONObject(jsonUserObjectString);
            Log.i("jsonUsersObject", jsonUserObjectString);
        } catch (JSONException e) {
            Log.e("Error", e.toString());
        }

        Button averageButton = (Button) findViewById(R.id.average_readings_button);
        averageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getMonthAverageIntent = new Intent(ListReadingsActivity.this, AverageReadings.class);
                startActivity(getMonthAverageIntent);
            }
        });
        populatePage();

        // Listener for ListReadings to display ReadingDetailsActivity
        ListView userListView = findViewById(R.id.list_of_reading);
        userListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
                if (i >= 0 && i < listLength) {
                    Intent readingDetailsIntent = new Intent(ListReadingsActivity.this, ReadingDetailsActivity.class);
                    readingDetailsIntent.putExtra(ListReadingsActivity.this.getResources().getString(R.string.jsonReadings), userReadings.toString());
                    readingDetailsIntent.putExtra(ListReadingsActivity.this.getResources().getString(R.string.username), userName);
                    Log.i("Username", userName);
                    Log.i("UserReadings: ", userReadings.toString());
                    readingDetailsIntent.putExtra(ListReadingsActivity.this.getResources().getString(R.string.index), i);
                    startActivity(readingDetailsIntent);
                }
            }
        });

    }


    public void populatePage() {
        try {
            JSONArray userJsonArray = jsonUsersObject.names();
            assert userJsonArray != null;
            userName = userJsonArray.get(userIndex).toString();
            Log.i("userName", userName);

            userReadings = (JSONObject) jsonUsersObject.get(userName);
            JSONArray userReadingsArray = userReadings.names();
            Log.i("userReadings", userReadings.toString());
            assert userReadingsArray != null;
            Log.i("userReadingsArray", userReadingsArray.toString());
            listLength = userReadingsArray.length();
            List<String> listOfReadings = new ArrayList<>(listLength);
            for (int i = 0; i < userReadingsArray.length(); i++) {
                String reading = userReadingsArray.get(i).toString();
                listOfReadings.add("ReadingID: " + reading);
            }
            ListView readingListView = findViewById(R.id.list_of_reading);
            readingListView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listOfReadings));
        } catch (JSONException e) {
            Log.e("userListConversion: ", e.toString());
        }
    }
}
