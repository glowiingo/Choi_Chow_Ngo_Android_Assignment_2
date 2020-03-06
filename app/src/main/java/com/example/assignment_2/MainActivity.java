package com.example.assignment_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getName();
    private JSONObject jsonDBObject;
    private String url = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button addReading = (Button) findViewById(R.id.add_reading);
        Button deleteButton = (Button) findViewById(R.id.go_to_delete);
        Button listButton = (Button) findViewById(R.id.go_to_list);
        Button averageButton = (Button) findViewById(R.id.average_button);


        addReading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_intent(v);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete_intent(v);
            }
        });

        listButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list_intent(v);
            }
        });

        averageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                average_intent(v);
            }
        });

    }

    private void average_intent(View v) {
        Intent i = new Intent(this, AverageReadings.class);
        startActivity(i);
    }

    public void add_intent(View v) {
        Intent i = new Intent(this, AddPatientActivity.class);
        startActivity(i);
    }

    public void delete_intent(View v) {
        Intent i = new Intent(this, DeleteActivity.class);
        startActivity(i);
    }

    public void list_intent(View v) {
        url = this.getResources().getString(R.string.users_db_json_url);
        sendAndRequestResponse();

    }

    private void sendAndRequestResponse() {
        //RequestQueue initialized
        RequestQueue mRequestQueue = Volley.newRequestQueue(this);

        //String Request initialized
        JsonObjectRequest mJsonRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                jsonDBObject = response;
                Intent intentSuggestions = new Intent(MainActivity.this, ListUsersActivity.class);
                intentSuggestions.putExtra(MainActivity.this.getResources().getString(R.string.jsonFullString), jsonDBObject.toString());
                startActivity(intentSuggestions);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG, "Error :" + error.toString());
            }
        });
        mRequestQueue.add(mJsonRequest);
    }
}
