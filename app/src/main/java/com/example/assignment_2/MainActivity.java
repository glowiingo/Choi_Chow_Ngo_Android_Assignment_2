package com.example.assignment_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
    Reading reading;
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

        String phno = personalHealthCareNo.getText().toString().trim();

        dbref = FirebaseDatabase.getInstance().getReference().child(phno);
        user = new User();
        reading = new Reading();
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    systolic = Float.parseFloat(systolic_input.getText().toString().trim());
                    diastolic = Float.parseFloat(diastolic_input.getText().toString().trim());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                String name = name_edit_txt.getText().toString().trim();
                user.setName(name);
                reading.setSystolic(systolic);
                reading.setDiastolic(diastolic);
                user.getReadings().put("Reading", reading);

                dbref.push().setValue(user);
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
