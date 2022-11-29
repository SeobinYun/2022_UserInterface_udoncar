package com.example.udoncar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.udoncar.model.Post;

public class WriteActivity extends AppCompatActivity {
    Button writeBtn;
    EditText titleEt, startEt, destEt, contEt;
    RadioGroup positionRg, isrepeatRg, sexRg;
    RadioButton positionRb1, positionRb2, positionRb3, isrepeatRb1, isrepeatRb2, sexRb1, sexRb2;
    String pos, isre, sex;
    Spinner ageSpn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_write);

        writeBtn = findViewById(R.id.write_btn);
        titleEt = findViewById(R.id.writetitle_et);
        startEt = findViewById(R.id.writestart_et);
        destEt = findViewById(R.id.writedest_et);
        contEt = findViewById(R.id.writecont_et);
        positionRg = findViewById(R.id.writepos_rg);
        positionRb1 = findViewById(R.id.writepos_btn1);
        positionRb2 = findViewById(R.id.writepos_btn2);
        positionRb3 = findViewById(R.id.writepos_btn3);
        isrepeatRg = findViewById(R.id.writeisre_rg);
        isrepeatRb1 = findViewById(R.id.writeisre_btn1);
        isrepeatRb2 = findViewById(R.id.writeisre_btn2);
        sexRg = findViewById(R.id.writesex_rg);
        sexRb1 = findViewById(R.id.writesex_btn1);
        sexRb2 = findViewById(R.id.writesex_btn2);
        ageSpn = findViewById(R.id.writeage_spn);

        writeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //postid생성
                //userid/닉네임/성별/나이 DB에서 가져오기
                //제목
                edittextToString(titleEt);
                //목적지spn
                //목적지
                edittextToString(destEt);
                //날짜?? -> DATE
                //position radio
                positionRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        if (i == R.id.writepos_btn1)
                            pos = positionRb1.getText().toString();
                        else if (i == R.id.writepos_btn2)
                            pos = positionRb2.getText().toString();
                        else
                            pos = positionRb3.getText().toString();
                    }
                });
                //isrepeat radio
                isrepeatRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        if (i == R.id.writeisre_btn1) {
                            isre = isrepeatRb1.getText().toString();
                        } else {
                            isre = isrepeatRb2.getText().toString();
                        }
                    }
                });
                //sex radio
                sexRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        if (i == R.id.writesex_btn1) {
                            sex = sexRb1.getText().toString();
                        } else {
                            sex = sexRb2.getText().toString();
                        }
                    }
                });
                //age spinner -> String
                spinnerToString(ageSpn);
                edittextToString(contEt);

                //Post post = new Post();

                //post를 DB Post에 저장

                //메인화면 전환
            }
        });


//        monthSpn = findViewById(R.id.writemonth_spn);
//        monthAdapter = ArrayAdapter.createFromResource(this,
//                R.array.month,
//                android.R.layout.simple_spinner_dropdown_item);
//        monthSpn.setAdapter(monthAdapter);
    }

    private String edittextToString(EditText sentence) {
        return sentence.getText().toString();
    }

    private String spinnerToString(Spinner spinner) {
        return spinner.getSelectedItem().toString();
    }
}