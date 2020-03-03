package com.example.assignment_2;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button addReading = (Button) findViewById(R.id.add_reading);
        Button deleteButton = (Button) findViewById(R.id.go_to_delete);
        Button listButton = (Button) findViewById(R.id.go_to_list);

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
        Intent i = new Intent(this, ListActivity.class);
        startActivity(i);
    }
}
