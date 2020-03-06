package com.example.assignment_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListReadingsActivity extends AppCompatActivity {

    private JSONObject jsonUsersObject;
    private int listLength = 0;
    private int userIndex = -1;
    private List<String> listOfReadings;
    private JSONObject userReadings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_readings);

        String jsonUserObjectString = "";
        jsonUserObjectString = (String) getIntent().getExtras().get(this.getResources().getString(R.string.jsonUsers));
        userIndex = (int) getIntent().getExtras().get(this.getResources().getString(R.string.index));
        try {
            jsonUsersObject = new JSONObject(jsonUserObjectString);
            Log.i("jsonUsersObject", jsonUserObjectString);
        } catch (JSONException e) {
            Log.e("Error", e.toString());
        }
        populatePage();

        // Listener for ListReadings to display ReadingDetailsActivity
        ListView userListView = findViewById(R.id.list_of_reading);
        userListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
                if (i >= 0 && i < listLength) {
                    Intent readingDetailsIntent = new Intent(ListReadingsActivity.this, ReadingDetailsActivity.class);
                    readingDetailsIntent.putExtra(ListReadingsActivity.this.getResources().getString(R.string.jsonReadings), userReadings.toString());
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
            String userName = userJsonArray.get(userIndex).toString();
            Log.i("userName", userName);

            userReadings = (JSONObject) jsonUsersObject.get(userName);
            JSONArray userReadingsArray = userReadings.names();
            Log.i("userReadings", userReadings.toString());
            assert userReadingsArray != null;
            Log.i("userReadingsArray", userReadingsArray.toString());
            listLength = userReadingsArray.length();
            listOfReadings = new ArrayList<>(listLength);
            for (int i = 0; i < userReadingsArray.length(); i++) {
                String reading = userReadingsArray.get(i).toString();
                listOfReadings.add("Reading number " + (i + 1) + ": " + reading);
            }
            ListView readingListView = findViewById(R.id.list_of_reading);
            readingListView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listOfReadings));
        } catch (JSONException e) {
            Log.e("userListConversion: ", e.toString());
        }
    }
}
