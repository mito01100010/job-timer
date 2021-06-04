package com.example.jobtimer.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.jobtimer.R;

public class WorkFinishedDialog extends AppCompatDialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_work_finished_dialog, null);

        builder.setView(view)
                .setTitle("NOTE")
                .setNegativeButton("Cancel", (dialog, which) -> {

                })
                .setPositiveButton("Save", (dialog, which) -> {
                    //add note in the db
                });
        return builder.create();
    }
}
