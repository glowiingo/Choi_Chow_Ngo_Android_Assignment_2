package com.example.assignment_2;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    // Define variables
    DatabaseReference userRef;
    User user;
    Reading Reading;
    Float systolic;
    Float diastolic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText name_edit_txt = (EditText) findViewById(R.id.name);
        final EditText systolic_input = (EditText) findViewById(R.id.systolic_reading);
        final EditText diastolic_input = (EditText) findViewById(R.id.diastolic_reading);
        final EditText personalHealthCareNo = (EditText) findViewById(R.id.personal_healthcare_no_input);
        Button saveButton = (Button) findViewById(R.id.save_button);
        String Name = name_edit_txt.getText().toString().trim();

        userRef = FirebaseDatabase.getInstance().getReference().child("Users");

        user = new User();
        Reading = new Reading();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                try {
                    systolic = Float.parseFloat(systolic_input.getText().toString().trim());
                    diastolic = Float.parseFloat(diastolic_input.getText().toString().trim());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                String Name = name_edit_txt.getText().toString().trim();
                String phno = personalHealthCareNo.getText().toString().trim();

                Map<String, User> users = new HashMap<>();

                String postKey = userRef.push().getKey();
                DatabaseReference phnoRef = userRef.child('/' + phno + '/' + Name + '/');
                phnoRef.push().setValue(Reading);
            }
        });
    }
    public void onClick(View v) {
        Intent i = new Intent(this, DeleteActivity.class);
        startActivity(i);
    }

    public void onListViewClick(View v) {
        Intent i = new Intent(this, ListActivity.class);
        startActivity(i);
    }
}
