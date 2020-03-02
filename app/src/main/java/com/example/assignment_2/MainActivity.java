package com.example.assignment_2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    // Define variables
    DatabaseReference dbref;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText name_edit_txt = (EditText) findViewById(R.id.name);
        EditText systolic_input = (EditText) findViewById(R.id.systolic_reading);
        EditText diastolic_input = (EditText) findViewById(R.id.diastolic_reading);
        Button saveButton = (Button) findViewById(R.id.save_button);

        dbref = FirebaseDatabase.getInstance().getReference().child("User");
        user = new User();
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
