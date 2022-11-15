package com.example.udoncar;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainDialogActivity extends AppCompatActivity implements View.OnClickListener {
    Button filterBtn;

    AlertDialog dialog;
    private DialogInterface.OnClickListener dialogListener;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_main_dialog);

        filterBtn = findViewById(R.id.btn_filter);

        filterBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == filterBtn) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            View dialogView = inflater.inflate(R.layout.activity_main_dialog, null);
            builder.setView(dialogView);
            builder.setPositiveButton("SUBMIT", dialogListener);
            builder.setNegativeButton("CANCEL", null);
            dialog = builder.create();
            dialog.show();
        }
    }
}