package com.example.udoncar;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

public class MainDialogActiviy extends Dialog {
    Spinner dest1Spn, dest2Spn, dest3Spn;
    Button pos1Btn, pos2Btn, pos3Btn;
    Button sex1Btn, sex2Btn;
    Spinner ageSpn;
    Button isre1Btn, isreB2tn;
    Button cancelBtn, submitBtn;

//    private ArrayAdapter ageAdapter;

    public MainDialogActiviy(Context context) {
        super(context);
        //다이얼로그 배경을 투명으로
        //왜인지 없으면 안 돌아감
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.dialog_main);

        dest1Spn = findViewById(R.id.dialogdest_spn1);
        dest2Spn = findViewById(R.id.dialogdest_spn2);
        dest3Spn = findViewById(R.id.dialogdest_spn3);
        pos1Btn = findViewById(R.id.dialogposition_btn1);
        pos2Btn = findViewById(R.id.dialogposition_btn2);
        pos3Btn = findViewById(R.id.dialogposition_btn3);
        sex1Btn = findViewById(R.id.dialogsex_btn1);
        sex2Btn = findViewById(R.id.dialogsex_btn2);
        ageSpn = findViewById(R.id.dialogage_spn);
        isre1Btn = findViewById(R.id.dialogisrepeat_btn1);
        isreB2tn = findViewById(R.id.dialogisrepeat_btn2);
        cancelBtn = findViewById(R.id.dialogcancel_btn);
        submitBtn = findViewById(R.id.dialogsubmit_btn);

//        ageAdapter = ArrayAdapter.createFromResource(this,
//                R.array.age,
//                android.R.layout.simple_spinner_dropdown_item);
//        ageSpn.setAdapter(ageAdapter);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //필터링
                //눌린 값들을 post에 저장해서
                //DB에 있는 post중 다 같은것만 가져와서
                //메인화면에 보여주기 <-?
                dismiss();
            }
        });
    }
}