package com.example.assignment_2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    // Define variables
    DatabaseReference dbref;
    User user;
    Float systolic;
    Float diastolic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText name_edit_txt = (EditText) findViewById(R.id.name);
        final EditText systolic_input = (EditText) findViewById(R.id.systolic_reading);
        final EditText diastolic_input = (EditText) findViewById(R.id.diastolic_reading);
        Button saveButton = (Button) findViewById(R.id.save_button);
        String name = name_edit_txt.getText().toString().trim();

        dbref = FirebaseDatabase.getInstance().getReference().child(name);
        user = new User();
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
                user.setSystolic(systolic);
                user.setDiastolic(diastolic);

                dbref.push().setValue(user);
            }
        });

    }
}
