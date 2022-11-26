package com.example.udoncar;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainDialogActivity extends AppCompatActivity implements View.OnClickListener {
    Button filterBtn;

    AlertDialog myDialog;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_main);

        filterBtn = findViewById(R.id.filter_btn);

        filterBtn.setOnClickListener(this);
    }

    private void showToast(String message){
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    DialogInterface.OnClickListener dialogListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            if (dialog == myDialog && which == DialogInterface.BUTTON_POSITIVE) {
                showToast("myDialog 확인 click...");
            }
        }
    };

    @Override
    public void onClick(View view) {
        if(view == filterBtn) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            View dialogView = inflater.inflate(R.layout.activity_main_dialog, null);
            builder.setView(dialogView);
            builder.setPositiveButton("SUBMIT", dialogListener);
            builder.setNegativeButton("CANCEL", null);
            myDialog = builder.create();
            myDialog.show();
        }
    }
}