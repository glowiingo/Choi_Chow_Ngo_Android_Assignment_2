package com.example.assignment_2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

public class HypertensiveFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Please call the hospital, your are in a state of Hypertensive Crisis!")
                .setPositiveButton(getResources().getText(R.string.ok), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intentMain = new Intent(getContext(), MainActivity.class);
                        startActivity(intentMain);
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
