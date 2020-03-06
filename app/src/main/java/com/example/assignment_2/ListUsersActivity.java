package com.example.assignment_2;

import android.app.Person;
import android.content.Intent;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListUsersActivity extends AppCompatActivity {

    private JSONObject jsonDBObject;
    private int listLength = 0;
    private List<String> listOfUsers;
    private JSONObject jsonUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        String dbResponse = "";
        dbResponse = (String) getIntent().getExtras().get(this.getResources().getString(R.string.jsonFullString));
        try {
            jsonDBObject = new JSONObject(dbResponse);
            Log.i("JsonDBResponse", dbResponse);
        } catch (JSONException e) {
            Log.e("Error", e.toString());
        }
        populatePage();

        // ListView listener for ListUsers to start ListReadings
        ListView userListView = findViewById(R.id.list_of_user);
        userListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
                if (i >= 0 && i < listLength) {
                    Intent listReadingsIntent = new Intent(ListUsersActivity.this, ListReadingsActivity.class);
                    listReadingsIntent.putExtra(ListUsersActivity.this.getResources().getString(R.string.jsonUsers), jsonUsers.toString());
                    listReadingsIntent.putExtra(ListUsersActivity.this.getResources().getString(R.string.index), i);
                    startActivity(listReadingsIntent);
                }
            }
        });
    }


    public void populatePage() {
        try {
            jsonUsers = (JSONObject) jsonDBObject.get(this.getResources().getString(R.string.users_db_key));
            JSONArray userJsonArray = jsonUsers.names();
            assert userJsonArray != null;
            Log.i("UserJsonArray", userJsonArray.toString());
            listLength = userJsonArray.length();
            listOfUsers = new ArrayList<>(listLength);
            for (int i = 0; i < userJsonArray.length(); i++) {
                String user = userJsonArray.get(i).toString();
                listOfUsers.add(user);
            }
            ListView usersListView = findViewById(R.id.list_of_user);
            usersListView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listOfUsers));
        } catch (JSONException e) {
            Log.e("userListConversion: ", e.toString());
        }
    }

}
