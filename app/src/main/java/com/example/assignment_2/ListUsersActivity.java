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
import java.util.Iterator;
import java.util.List;

public class ListUsersActivity extends AppCompatActivity {

    private JSONObject jsonDBObject;
    private int listLength = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        String dbResponse = "";
        dbResponse = (String) getIntent().getExtras().get("jsonString");
        try {
            jsonDBObject = new JSONObject(dbResponse);
        } catch (JSONException e) {
            Log.e("Error", e.toString());
        }
        populatePage();

        ListView articleList = findViewById(R.id.list_of_user);
        final String response = dbResponse;
        articleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
                if (i >= 0 && i < listLength) {
//                    Intent intentDetails = new Intent(Suggestions.this, Details.class);
//                    intentDetails.putExtra("jsonString", response);
//                    intentDetails.putExtra("index", i);
//                    startActivity(intentDetails);
                }
            }
        });
    }


    public void populatePage() {
        try {
            JSONObject jsonUserObject = (JSONObject) jsonDBObject.get("Users");
            JSONArray userJsonArray = jsonUserObject.names();
            assert userJsonArray != null;
            // Log.i("Error", userJsonArray.toString());
            listLength = userJsonArray.length();
            List<String> articleTitleList = new ArrayList<>(listLength);
            for (int i = 0; i < userJsonArray.length(); i++) {
                String user = userJsonArray.get(i).toString();
                articleTitleList.add(user);
            }
            ListView users = findViewById(R.id.list_of_user);
            users.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, articleTitleList));
        } catch (JSONException e) {
            Log.e("Article Conversion: ", e.toString());
        }
    }

}
