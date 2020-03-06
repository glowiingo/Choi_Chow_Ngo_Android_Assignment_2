package com.example.assignment_2;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddPatientActivity extends AppCompatActivity {

    // Define variables
    DatabaseReference userRef;
    User user;
    Reading Reading;
    Float systolic;
    Float diastolic;
    HypertensiveFragment hf;
    Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient);


        final EditText name_edit_txt = (EditText) findViewById(R.id.name);
        final EditText systolic_input = (EditText) findViewById(R.id.systolic_reading);
        final EditText diastolic_input = (EditText) findViewById(R.id.diastolic_reading);
        final EditText personalHealthCareNo = (EditText) findViewById(R.id.personal_healthcare_no_input);
        Button saveButton = (Button) findViewById(R.id.save_button);

        final String user_db_key = context.getResources().getString(R.string.users_db_key);

        userRef = FirebaseDatabase.getInstance().getReference().child(user_db_key);

        user = new User();
        Reading = new Reading();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                String Name = name_edit_txt.getText().toString().trim();
                String phno = personalHealthCareNo.getText().toString().trim();
                String diastolic_string = diastolic_input.getText().toString().trim();
                String systolic_string = systolic_input.getText().toString().trim();

                if (!phno.equals("") &&
                        !Name.equals("") &&
                        !diastolic_string.equals("") &&
                        !systolic_string.equals("")) {

                    try {
                        systolic = Float.parseFloat(systolic_string);
                    } catch (NumberFormatException e) {
                        Toast.makeText(
                                getApplicationContext(),
                                context.getResources().getString(R.string.systolic_error),
                                Toast.LENGTH_SHORT).show();
                    }

                    try {
                        diastolic = Float.parseFloat(diastolic_string);
                    } catch (NumberFormatException e) {
                        Toast.makeText(
                                getApplicationContext(),
                                context.getResources().getString(R.string.diastolic_error),
                                Toast.LENGTH_SHORT).show();
                    }

                    if (diastolic != null && systolic != null) {
                        Reading.setSystolic(systolic);
                        Reading.setDiastolic(diastolic);

                        String condition = Reading.determineCondition(systolic, diastolic);
                        Reading.setCondition(condition);

                        DatabaseReference phnoRef = userRef.child('/' + phno + '-' + Name + '/');
                        phnoRef.push().setValue(Reading);
                        Toast.makeText(
                                getApplicationContext(),
                                context.getResources().getString(R.string.add_db_reading_succes),
                                Toast.LENGTH_SHORT).show();
                        if (!status()) {
                            Intent intentMain = new Intent(AddPatientActivity.this, MainActivity.class);
                            startActivity(intentMain);
                        }
                    }

                } else if (phno.equals("")
                        || phno.contains(".")
                        || phno.contains("#")
                        || phno.contains("$")
                        || phno.contains("[")
                        || phno.contains("]")) {
                    Toast.makeText(
                            getApplicationContext(),
                            context.getResources().getString(R.string.phno_error),
                            Toast.LENGTH_SHORT).show();

                } else if (Name.equals("")
                        || Name.contains(".")
                        || Name.contains("#")
                        || Name.contains("$")
                        || Name.contains("[")
                        || Name.contains("]")) {
                    Toast.makeText(
                            getApplicationContext(),
                            context.getResources().getString(R.string.name_error),
                            Toast.LENGTH_SHORT).show();

                } else if (diastolic_string.equals("")) {
                    Toast.makeText(
                            getApplicationContext(),
                            context.getResources().getString(R.string.diastolic_error),
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(
                            getApplicationContext(),
                            context.getResources().getString(R.string.systolic_error),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public boolean status() {
        if(systolic >= 180 || diastolic >= 120) {
            hf = new HypertensiveFragment();
            hf.show(getSupportFragmentManager(), "Dialog");
            return true;
        } else {
            return false;
        }
    }
}
