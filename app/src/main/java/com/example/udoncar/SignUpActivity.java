package com.example.udoncar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class SignUpActivity extends AppCompatActivity {
    private static final String TAG = "SignUpActivity";

    // FirebaseAuth의 인스턴스 선언
    private FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private EditText idEdittext;
    private EditText pwEdittext;
    private Button doublecheckBtn;
    private EditText doublepwEdittext;
    private EditText nameEdittext;
    private Spinner regionSpinner1;
    private Spinner regionSpinner2;
    private Spinner regionSpinner3;
    private ArrayAdapter<String> regionSpinner1Adapter;
    private RadioGroup sexRadioGroup;
    private RadioButton sexRadioBtn;
    private Spinner agesSpinner;
    private ArrayAdapter agesSpinnerAdapter;
    private Button signupBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // FirebaseAuth 인스턴스 초기화
        mAuth = FirebaseAuth.getInstance();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        idEdittext = (EditText)findViewById(R.id.id_edittext);
        doublecheckBtn = (Button)findViewById(R.id.doublecheck_btn);
        doublecheckBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        pwEdittext = (EditText)findViewById(R.id.pw_edittext);
        doublepwEdittext = (EditText)findViewById(R.id.doublepw_edittext);
        nameEdittext = (EditText)findViewById(R.id.name_edittext);

        // 지역부분
        regionSpinner1 = (Spinner) findViewById(R.id.spinner_1);
        regionSpinner1Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, (String[]) getResources().getStringArray(R.array.spinner_region));
        regionSpinner1Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        regionSpinner1.setAdapter(regionSpinner1Adapter);

        regionSpinner2 = (Spinner) findViewById(R.id.spinner_2);
        regionSpinner3 = (Spinner) findViewById(R.id.spinner_3);

        initAddressSpinner();

        // 성별 부분
        sexRadioGroup = (RadioGroup) findViewById(R.id.sex_radio);
        sexRadioBtn = findViewById(sexRadioGroup.getCheckedRadioButtonId());


        // 나이 부분
        agesSpinner = (Spinner) findViewById(R.id.ages_spinner);
        agesSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.ages, android.R.layout.simple_spinner_item);
        agesSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        agesSpinner.setAdapter(agesSpinnerAdapter);

        // 회원가입 버튼 부분
        signupBtn = (Button)findViewById(R.id.signup_btn);
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ID, PW, PW확인, 닉네임이 공백인 경우
                if(idEdittext.getText().toString().equals("") || pwEdittext.getText().toString().equals("") || doublepwEdittext.getText().toString().equals("") || nameEdittext.getText().toString().equals("")){
                    Toast.makeText(SignUpActivity.this, "필수 정보를 입력해주세요.", Toast.LENGTH_LONG).show();
                }
                // PW, PW확인이 일치하지 않을 경우
                else if(!pwEdittext.getText().toString().equals(doublepwEdittext.getText().toString())){
                    Toast.makeText(SignUpActivity.this, "PW와 PW확인이 일치하지 않습니다.", Toast.LENGTH_LONG).show();
                }
                // 다 입력했을 경우
                else {
                    createUser(idEdittext.getText().toString(), pwEdittext.getText().toString(), nameEdittext.getText().toString(), regionSpinner1, regionSpinner2, regionSpinner3, sexRadioBtn, agesSpinner);
                }
            }
        });
    }

    private void initAddressSpinner() {
        regionSpinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // 시군구, 동의 스피너를 초기화한다.
                switch (position) {
                    case 0:
                        regionSpinner2.setAdapter(null);
                        break;
                    case 1:
                        setspinner_2AdapterItem(R.array.spinner_region_seoul);
                        break;
                    case 2:
                        setspinner_2AdapterItem(R.array.spinner_region_busan);
                        break;
                    case 3:
                        setspinner_2AdapterItem(R.array.spinner_region_daegu);
                        break;
                    case 4:
                        setspinner_2AdapterItem(R.array.spinner_region_incheon);
                        break;
                    case 5:
                        setspinner_2AdapterItem(R.array.spinner_region_gwangju);
                        break;
                    case 6:
                        setspinner_2AdapterItem(R.array.spinner_region_daejeon);
                        break;
                    case 7:
                        setspinner_2AdapterItem(R.array.spinner_region_ulsan);
                        break;
                    case 8:
                        setspinner_2AdapterItem(R.array.spinner_region_sejong);
                        break;
                    case 9:
                        setspinner_2AdapterItem(R.array.spinner_region_gyeonggi);
                        break;
                    case 10:
                        setspinner_2AdapterItem(R.array.spinner_region_gangwon);
                        break;
                    case 11:
                        setspinner_2AdapterItem(R.array.spinner_region_chung_buk);
                        break;
                    case 12:
                        setspinner_2AdapterItem(R.array.spinner_region_chung_nam);
                        break;
                    case 13:
                        setspinner_2AdapterItem(R.array.spinner_region_jeon_buk);
                        break;
                    case 14:
                        setspinner_2AdapterItem(R.array.spinner_region_jeon_nam);
                        break;
                    case 15:
                        setspinner_2AdapterItem(R.array.spinner_region_gyeong_buk);
                        break;
                    case 16:
                        setspinner_2AdapterItem(R.array.spinner_region_gyeong_nam);
                        break;
                    case 17:
                        setspinner_2AdapterItem(R.array.spinner_region_jeju);
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        regionSpinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // 서울특별시 선택시
                if (regionSpinner2.getSelectedItemPosition() == 1 && regionSpinner2.getSelectedItemPosition() > -1) {
                    switch (position) {
                        //25
                        case 0:
                            setSpinner_3AdapterItem(R.array.spinner_region_seoul_gangnam);
                            break;
                        case 1:
                            setSpinner_3AdapterItem(R.array.spinner_region_seoul_gangdong);
                            break;
                        case 2:
                            setSpinner_3AdapterItem(R.array.spinner_region_seoul_gangbuk);
                            break;
                        case 3:
                            setSpinner_3AdapterItem(R.array.spinner_region_seoul_gangseo);
                            break;
                        case 4:
                            setSpinner_3AdapterItem(R.array.spinner_region_seoul_gwanak);
                            break;
                        case 5:
                            setSpinner_3AdapterItem(R.array.spinner_region_seoul_gwangjin);
                            break;
                        case 6:
                            setSpinner_3AdapterItem(R.array.spinner_region_seoul_guro);
                            break;
                        case 7:
                            setSpinner_3AdapterItem(R.array.spinner_region_seoul_geumcheon);
                            break;
                        case 8:
                            setSpinner_3AdapterItem(R.array.spinner_region_seoul_nowon);
                            break;
                        case 9:
                            setSpinner_3AdapterItem(R.array.spinner_region_seoul_dobong);
                            break;
                        case 10:
                            setSpinner_3AdapterItem(R.array.spinner_region_seoul_dongdaemun);
                            break;
                        case 11:
                            setSpinner_3AdapterItem(R.array.spinner_region_seoul_dongjag);
                            break;
                        case 12:
                            setSpinner_3AdapterItem(R.array.spinner_region_seoul_mapo);
                            break;
                        case 13:
                            setSpinner_3AdapterItem(R.array.spinner_region_seoul_seodaemun);
                            break;
                        case 14:
                            setSpinner_3AdapterItem(R.array.spinner_region_seoul_seocho);
                            break;
                        case 15:
                            setSpinner_3AdapterItem(R.array.spinner_region_seoul_seongdong);
                            break;
                        case 16:
                            setSpinner_3AdapterItem(R.array.spinner_region_seoul_seongbuk);
                            break;
                        case 17:
                            setSpinner_3AdapterItem(R.array.spinner_region_seoul_songpa);
                            break;
                        case 18:
                            setSpinner_3AdapterItem(R.array.spinner_region_seoul_yangcheon);
                            break;
                        case 19:
                            setSpinner_3AdapterItem(R.array.spinner_region_seoul_yeongdeungpo);
                            break;
                        case 20:
                            setSpinner_3AdapterItem(R.array.spinner_region_seoul_yongsan);
                            break;
                        case 21:
                            setSpinner_3AdapterItem(R.array.spinner_region_seoul_eunpyeong);
                            break;
                        case 22:
                            setSpinner_3AdapterItem(R.array.spinner_region_seoul_jongno);
                            break;
                        case 23:
                            setSpinner_3AdapterItem(R.array.spinner_region_seoul_jung);
                            break;
                        case 24:
                            setSpinner_3AdapterItem(R.array.spinner_region_seoul_jungnanggu);
                            break;
                    }
                } else {

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void setspinner_2AdapterItem(int array_resource) {
        if (regionSpinner1Adapter != null) {
            regionSpinner2.setAdapter(null);
            regionSpinner1Adapter = null;
        }

        if (regionSpinner1.getSelectedItemPosition() > 1) {
            regionSpinner3.setAdapter(null);
        }

        regionSpinner1Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, (String[]) getResources().getStringArray(array_resource));
        regionSpinner1Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        regionSpinner2.setAdapter(regionSpinner1Adapter);
    }

    private void setSpinner_3AdapterItem(int array_resource) {
        if (regionSpinner1Adapter != null) {
            regionSpinner3.setAdapter(null);
            regionSpinner1Adapter = null;
        }

        regionSpinner1Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, (String[]) getResources().getStringArray(array_resource));
        regionSpinner1Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        regionSpinner3.setAdapter(regionSpinner1Adapter);
    }

    // 성별 부분
    RadioGroup.OnCheckedChangeListener radioGroupButtonChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            if (i == R.id.sex_man) { // 남자일 경우
                Toast.makeText(SignUpActivity.this, "남자를 눌렸습니다.", Toast.LENGTH_SHORT).show();
            } else if (i == R.id.sex_woman) { // 여자일 경우

                Toast.makeText(SignUpActivity.this, "여자를 눌렸습니다.", Toast.LENGTH_SHORT).show();
            }
        }
    };




    private void createUser(String email, String password, String name, Spinner region1, Spinner region2, Spinner region3, RadioButton sex, Spinner age) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        List<String> region = Arrays.asList(region1.getSelectedItem().toString(), region2.getSelectedItem().toString(), region3.getSelectedItem().toString());
        User user = new User(email, password, name, region, sex.getText().toString(), age.getSelectedItem().toString());
        db.collection("users").document(email).set(user);
    }



}