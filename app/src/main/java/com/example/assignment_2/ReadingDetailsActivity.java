package com.example.assignment_2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Objects;

public class ReadingDetailsActivity extends AppCompatActivity {

    // Define variables
    DatabaseReference ref;

    private JSONObject jsonReadingsObject;
    private int readingIndex = -1;
    private JSONObject jsonReading;
    private String userName;
    private String readingID;
    String date = "";
    String time = "";
    String condition = "";
    String systolic = "";
    String diastolic = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading_details);

        final String user_db_key = this.getResources().getString(R.string.users_db_key);
        final String delete_tag = this.getResources().getString(R.string.delete_reading_button_label);

        String jsonReadingsObjectString = "";
        jsonReadingsObjectString = (String) Objects.requireNonNull(getIntent().getExtras()).get(this.getResources().getString(R.string.jsonReadings));
        readingIndex = (int) getIntent().getExtras().get(this.getResources().getString(R.string.index));
        userName = (String) getIntent().getExtras().get(this.getResources().getString(R.string.username));
        assert userName != null;
        String[] username = userName.split("-", 2);
        String phno = username[0];
        String name = username[1];

        try {
            assert jsonReadingsObjectString != null;
            jsonReadingsObject = new JSONObject(jsonReadingsObjectString);
            Log.i("jsonReadingsObject", jsonReadingsObjectString);
        } catch (JSONException e) {
            Log.e("Error", e.toString());
        }

        populatePage();

        TextView phnoTextView = (TextView) findViewById(R.id.phnoTextView);
        String phnoTextViewText = this.getResources().getString(R.string.phno) + phno;
        phnoTextView.setText(phnoTextViewText);

        TextView nameTextView = (TextView) findViewById(R.id.nameTextView);
        String nameTextViewText = this.getResources().getString(R.string.name_label) + name;
        nameTextView.setText(nameTextViewText);

        TextView dateTextView = (TextView) findViewById(R.id.dateRecordedTextView);
        String dateTextViewText = this.getResources().getString(R.string.date_label) + date;
        dateTextView.setText(dateTextViewText);

        TextView timeTextView = (TextView) findViewById(R.id.timeTextView);
        String timeTextViewText = this.getResources().getString(R.string.time_label) + time;
        timeTextView.setText(timeTextViewText);

        TextView systolicTextView = (TextView) findViewById(R.id.systolicTextView);
        String systolicTextViewText = this.getResources().getString(R.string.systolic_label) + systolic;
        systolicTextView.setText(systolicTextViewText);

        TextView diastolicTextView = (TextView) findViewById(R.id.diastolicTextView);
        String diastolicTextViewText = this.getResources().getString(R.string.diastolic_label) + diastolic;
        diastolicTextView.setText(diastolicTextViewText);

        TextView conditionTextView = (TextView) findViewById(R.id.conditionTextView);
        String conditionTextViewText = this.getResources().getString(R.string.condition_label) + condition;
        conditionTextView.setText(conditionTextViewText);

        Button deleteButton = (Button) findViewById(R.id.reading_delete_button);
        Button editButton = (Button) findViewById(R.id.reading_edit_button);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // delete node from firebase
                ref = FirebaseDatabase.getInstance().getReference();
                Query readingQuery = ref.child(user_db_key).child(userName).child(readingID);
                readingQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snap: dataSnapshot.getChildren()) {
                            snap.getRef().removeValue();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.e(delete_tag, delete_tag, databaseError.toException());
                    }
                });

                Intent backToMainIntent = new Intent(ReadingDetailsActivity.this, MainActivity.class);
                startActivity(backToMainIntent);
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // go to new ui activity to input new data
                Intent getMonthAverageIntent = new Intent(ReadingDetailsActivity.this, EditReading.class);
                startActivity(getMonthAverageIntent);


            }
        });
    }

    public void populatePage() {
        // get which reading was selected
        try {
            JSONArray readingKeysList = jsonReadingsObject.names();
            assert readingKeysList != null;
            readingID = readingKeysList.get(readingIndex).toString();
            Log.i("ReadingID: ", readingID);
            jsonReading = jsonReadingsObject.getJSONObject(readingID);
            Log.i("ReadingObject: ", jsonReading.toString());
            date = jsonReading.getString(this.getResources().getString(R.string.date_key));
            time = jsonReading.getString(this.getResources().getString(R.string.time_key));
            condition = jsonReading.getString(this.getResources().getString(R.string.condition_key));
            systolic = jsonReading.getString(this.getResources().getString(R.string.systolic_key));
            diastolic = jsonReading.getString(this.getResources().getString(R.string.diastolic_key));
        } catch (JSONException e) {
            Log.e("userListConversion: ", e.toString());
        }
    }
}
