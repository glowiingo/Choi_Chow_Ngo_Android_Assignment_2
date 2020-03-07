package com.example.assignment_2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class EditReading extends AppCompatActivity {

    String systolicReading = "";
    String diastolicReading = "";
    String readingID = "";
    String userName = "";
    DatabaseReference ref;
    Float systolic;
    Float diastolic;
    HypertensiveFragment hf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_reading);


        final String user_db_key = this.getResources().getString(R.string.users_db_key);

        TextView systolicOldReading = (TextView) findViewById(R.id.systolic_display);
        TextView diastolicOldReading = (TextView) findViewById(R.id.diastolic_display);

        final TextView new_systolic_input = (TextView) findViewById(R.id.new_systolic_input);
        final TextView new_diastolic_input = (TextView) findViewById(R.id.new_diastolic_input);

        systolicReading = (String) Objects.requireNonNull(getIntent().getExtras()).get(this.getResources().getString(R.string.systolic_label));
        diastolicReading = (String) getIntent().getExtras().get(this.getResources().getString(R.string.diastolic_label));
        readingID = (String) getIntent().getExtras().get(this.getResources().getString(R.string.readingID));
        userName = (String) getIntent().getExtras().get(this.getResources().getString(R.string.username));

        systolicOldReading.setText(systolicReading);
        diastolicOldReading.setText(diastolicReading);

        Button edit_data = (Button) findViewById(R.id.edit_button_new_reading);
        edit_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String systolic_string = new_systolic_input.getText().toString().trim();
                    systolic = Float.parseFloat(systolic_string);
                } catch (NumberFormatException e) {
                    Toast.makeText(
                            getApplicationContext(),
                            EditReading.this.getResources().getString(R.string.systolic_error),
                            Toast.LENGTH_SHORT).show();
                }
                try {
                    String diastolic_string = new_diastolic_input.getText().toString().trim();
                    diastolic = Float.parseFloat(diastolic_string);
                } catch (NumberFormatException e) {
                    Toast.makeText(
                            getApplicationContext(),
                            EditReading.this.getResources().getString(R.string.diastolic_error),
                            Toast.LENGTH_SHORT).show();
                }

                if (diastolic != null && systolic != null) {
                    ref = FirebaseDatabase.getInstance().getReference();
                    DatabaseReference readingRef = ref.child(user_db_key).child(userName).child(readingID);

                    // create the new reading
                    Reading newReading = new Reading();
                    newReading.setSystolic(systolic);
                    newReading.setDiastolic(diastolic);
                    String condition = newReading.determineCondition(systolic, diastolic);
                    Log.i("condition", condition);
                    newReading.setCondition(condition);
                    Log.i("ReadingCondition", newReading.getCondition());

                    try {
                        // update the reading based on new values
                        Map<String, Object> taskMap = new HashMap<>();
                        taskMap.put(EditReading.this.getResources().getString(R.string.systolic_key), newReading.getSystolic());
                        taskMap.put(EditReading.this.getResources().getString(R.string.diastolic_key), newReading.getDiastolic());
                        taskMap.put(EditReading.this.getResources().getString(R.string.time_key), newReading.getTime());
                        taskMap.put(EditReading.this.getResources().getString(R.string.date_key), newReading.getDate());
                        taskMap.put(EditReading.this.getResources().getString(R.string.condition_key), newReading.getCondition());
                        readingRef.updateChildren(taskMap);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (!status()) {
                        Intent returnToMain = new Intent(EditReading.this, MainActivity.class);
                        startActivity(returnToMain);
                    }
                }
            }
        });

    }

    public boolean status() {
        if(systolic >= 180 || diastolic >= 120) {
            hf = new HypertensiveFragment();
            hf.show(getSupportFragmentManager(), this.getResources().getString(R.string.dialog));
            return true;
        } else {
            return false;
        }
    }
}
