package com.example.udoncar;

import static android.content.ContentValues.TAG;

import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.activity.OnBackPressedDispatcherOwner;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MypageFragment extends Fragment{
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayAdapter<CharSequence> mySpinner1Adapter, mySpinner2Adapter, mySpinner3Adapter;
    EditText myIdEdittext;
    EditText myNameEdittext;

    Spinner mySpinner1;
    Spinner mySpinner2;
    Spinner mySpinner3;

    ArrayAdapter myAgesSpinnerAdapter;
    Spinner myAgeSpinner;

    RadioGroup mySexRadioGroup;
    RadioButton manBtn;
    RadioButton womanBtn;
    RadioButton mySexRadioBtn;

    Button editBtn;
    Button saveBtn;
    Button logoutBtn;
    Button deleteBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mypage, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        myIdEdittext = view.findViewById(R.id.myId_edittext);
        myNameEdittext = view.findViewById(R.id.myName_edittext);

        mySpinner1 = view.findViewById(R.id.spinner_1);
        mySpinner2 = view.findViewById(R.id.spinner_2);
        mySpinner3 = view.findViewById(R.id.spinner_3);
        mySpinner1.setPrompt("시/도 선택");
        mySpinner2.setPrompt("시/군/구 선택");
        mySpinner3.setPrompt("읍/면/동 선택");

        mySexRadioGroup = view.findViewById(R.id.sex_radio);
        mySexRadioBtn = view.findViewById(mySexRadioGroup.getCheckedRadioButtonId());
        manBtn = view.findViewById(R.id.man_btn);
        womanBtn = view.findViewById(R.id.woman_btn);

        myAgeSpinner = view.findViewById(R.id.ages_spinner);

        editBtn = view.findViewById(R.id.edit_btn);
        saveBtn = view.findViewById(R.id.save_btn);
        logoutBtn = view.findViewById(R.id.logout_btn);
        deleteBtn = view.findViewById(R.id.delete_btn);

        myIdEdittext.setText(user.getEmail());
        DocumentReference docRef = db.collection("users").document(user.getEmail());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());

                        // 닉네임 불러오기
                        myNameEdittext.setText(document.getData().get("name").toString());

                        // 주소 불러오기
                        List<String> region = (List<String>) document.getData().get("region");
                        String region0 = region.toArray()[0].toString();
                        String region1 = region.toArray()[1].toString();
                        String region2 = region.toArray()[2].toString();

                        mySpinner1Adapter = ArrayAdapter.createFromResource((MainActivity) getActivity(), R.array.spinner_region, R.layout.item_spinner);
                        mySpinner1Adapter.setDropDownViewResource(R.layout.item_spinner_dropdown);
                        mySpinner1.setAdapter(mySpinner1Adapter);

                        mySpinner1Adapter = (ArrayAdapter) mySpinner1.getAdapter();
                        int mySpinner1Position = mySpinner1Adapter.getPosition(region0);
                        mySpinner1.setSelection(mySpinner1Position);

                        mySpinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                if (mySpinner1Adapter.getItem(i).equals("서울특별시")) {
                                    mySpinner2Adapter = ArrayAdapter.createFromResource((MainActivity) getActivity(), R.array.spinner_region_seoul, R.layout.item_spinner);
                                } else if (mySpinner1Adapter.getItem(i).equals("부산광역시")) {
                                    mySpinner2Adapter = ArrayAdapter.createFromResource((MainActivity) getActivity(), R.array.spinner_region_busan, R.layout.item_spinner);
                                } else if (mySpinner1Adapter.getItem(i).equals("대구광역시")) {
                                    mySpinner2Adapter = ArrayAdapter.createFromResource((MainActivity) getActivity(), R.array.spinner_region_daegu, R.layout.item_spinner);
                                } else if (mySpinner1Adapter.getItem(i).equals("인천광역시")) {
                                    mySpinner2Adapter = ArrayAdapter.createFromResource((MainActivity) getActivity(), R.array.spinner_region_incheon, R.layout.item_spinner);
                                } else if (mySpinner1Adapter.getItem(i).equals("광주광역시")) {
                                    mySpinner2Adapter = ArrayAdapter.createFromResource((MainActivity) getActivity(), R.array.spinner_region_gwangju, R.layout.item_spinner);
                                } else if (mySpinner1Adapter.getItem(i).equals("대전광역시")) {
                                    mySpinner2Adapter = ArrayAdapter.createFromResource((MainActivity) getActivity(), R.array.spinner_region_daejeon, R.layout.item_spinner);
                                } else if (mySpinner1Adapter.getItem(i).equals("울산광역시")) {
                                    mySpinner2Adapter = ArrayAdapter.createFromResource((MainActivity) getActivity(), R.array.spinner_region_ulsan, R.layout.item_spinner);
                                } else if (mySpinner1Adapter.getItem(i).equals("세종특별자치시")) {
                                    mySpinner2Adapter = ArrayAdapter.createFromResource((MainActivity) getActivity(), R.array.spinner_region_sejong, R.layout.item_spinner);
                                } else if (mySpinner1Adapter.getItem(i).equals("경기도")) {
                                    mySpinner2Adapter = ArrayAdapter.createFromResource((MainActivity) getActivity(), R.array.spinner_region_gyeonggi, R.layout.item_spinner);
                                } else if (mySpinner1Adapter.getItem(i).equals("강원도")) {
                                    mySpinner2Adapter = ArrayAdapter.createFromResource((MainActivity) getActivity(), R.array.spinner_region_gangwon, R.layout.item_spinner);
                                } else if (mySpinner1Adapter.getItem(i).equals("충청북도")) {
                                    mySpinner2Adapter = ArrayAdapter.createFromResource((MainActivity) getActivity(), R.array.spinner_region_chung_buk, R.layout.item_spinner);
                                } else if (mySpinner1Adapter.getItem(i).equals("충청남도")) {
                                    mySpinner2Adapter = ArrayAdapter.createFromResource((MainActivity) getActivity(), R.array.spinner_region_chung_nam, R.layout.item_spinner);
                                } else if (mySpinner1Adapter.getItem(i).equals("전라북도")) {
                                    mySpinner2Adapter = ArrayAdapter.createFromResource((MainActivity) getActivity(), R.array.spinner_region_jeon_buk, R.layout.item_spinner);
                                } else if (mySpinner1Adapter.getItem(i).equals("전라남도")) {
                                    mySpinner2Adapter = ArrayAdapter.createFromResource((MainActivity) getActivity(), R.array.spinner_region_jeon_nam, R.layout.item_spinner);
                                } else if (mySpinner1Adapter.getItem(i).equals("경상북도")) {
                                    mySpinner2Adapter = ArrayAdapter.createFromResource((MainActivity) getActivity(), R.array.spinner_region_gyeong_buk, R.layout.item_spinner);
                                } else if (mySpinner1Adapter.getItem(i).equals("경상남도")) {
                                    mySpinner2Adapter = ArrayAdapter.createFromResource((MainActivity) getActivity(), R.array.spinner_region_gyeong_nam, R.layout.item_spinner);
                                } else if (mySpinner1Adapter.getItem(i).equals("제주특별자치도")) {
                                    mySpinner2Adapter = ArrayAdapter.createFromResource((MainActivity) getActivity(), R.array.spinner_region_jeju, R.layout.item_spinner);
                                }
                                mySpinner2Adapter.setDropDownViewResource(R.layout.item_spinner_dropdown);
                                mySpinner2.setAdapter(mySpinner2Adapter);
                                mySpinner2Adapter = (ArrayAdapter) mySpinner2.getAdapter();
                                int mySpinner2Position = mySpinner2Adapter.getPosition(region1);
                                mySpinner2.setSelection(mySpinner2Position);
                                mySpinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        chooseRegion3(mySpinner1, mySpinner2, region2);
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });

                        mySpinner1.setEnabled(false);
                        mySpinner2.setEnabled(false);
                        mySpinner3.setEnabled(false);

                        // 성별 불러오기
                        if (document.getData().get("sex").toString().equals("남자")) {
                            manBtn.setChecked(true);
                        } else {
                            womanBtn.setChecked(true);
                        }
                        mySexRadioBtn = view.findViewById(mySexRadioGroup.getCheckedRadioButtonId());
                        mySexRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                                mySexRadioBtn = view.findViewById(mySexRadioGroup.getCheckedRadioButtonId());
                            }
                        });

                        // 나이 불러오기
                        String age = document.getData().get("age").toString();
                        myAgesSpinnerAdapter = ArrayAdapter.createFromResource((MainActivity) getActivity(), R.array.ages, R.layout.item_spinner);
                        myAgesSpinnerAdapter.setDropDownViewResource(R.layout.item_spinner_dropdown);
                        myAgeSpinner.setAdapter(myAgesSpinnerAdapter);
                        myAgesSpinnerAdapter = (ArrayAdapter) myAgeSpinner.getAdapter();
                        int ageSpinnerPosition = myAgesSpinnerAdapter.getPosition(age);
                        myAgeSpinner.setSelection(ageSpinnerPosition);
                        myAgeSpinner.setEnabled(false);
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

        // 수정버튼
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myNameEdittext.setEnabled(true);
                mySpinner1.setEnabled(true);
                mySpinner2.setEnabled(true);
                mySpinner3.setEnabled(true);
                manBtn.setEnabled(true);
                womanBtn.setEnabled(true);
                myAgeSpinner.setEnabled(true);
            }
        });

        // 저장버튼
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (myNameEdittext.isEnabled()) {
                    Map<String, Object> updateUser = new HashMap<>();
                    updateUser.put("name", myNameEdittext.getText().toString());
                    updateUser.put("region", Arrays.asList(spinnerToString(mySpinner1), spinnerToString(mySpinner2), spinnerToString(mySpinner3)));
                    Log.d(TAG, "mySexRadioBtn: " + mySexRadioBtn);
                    Log.d(TAG, "mySexRadioBtn: " + mySexRadioBtn.getText());
                    updateUser.put("sex", mySexRadioBtn.getText());
                    updateUser.put("age", spinnerToString(myAgeSpinner));

                    db.collection("users").document(user.getEmail())
                            .set(updateUser, SetOptions.merge())
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "정보 업데이트 완료");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(TAG, "Error writing document", e);
                                }
                            });

                    myNameEdittext.setEnabled(false);
                    mySpinner1.setEnabled(false);
                    mySpinner2.setEnabled(false);
                    mySpinner3.setEnabled(false);
                    manBtn.setEnabled(false);
                    womanBtn.setEnabled(false);
                    myAgeSpinner.setEnabled(false);
                    Toast.makeText((MainActivity) getActivity(), "저장되었습니다. ", Toast.LENGTH_LONG).show();
                } else {
                }
            }
        });

        // 로그아웃
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog logout_confirm = new AlertDialog.Builder((MainActivity) getActivity())
                        .setMessage("로그아웃 하시겠습니까?")
                        .setPositiveButton("취소", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText((MainActivity) getActivity(), "로그아웃을 취소하셨습니다.", Toast.LENGTH_LONG).show();
                            }
                        }).setNegativeButton("로그아웃", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ((MainActivity) getActivity()).signOut(user);
                                Toast.makeText((MainActivity) getActivity(), "로그아웃 성공!", Toast.LENGTH_LONG).show();
                            }
                        })
                        .show();

                TextView textView = (TextView) logout_confirm.findViewById(android.R.id.message);
                Typeface face = Typeface.createFromAsset(getActivity().getAssets(), "nanum_regular.ttf");
                textView.setTypeface(face);
            }
        });

        // 탈퇴
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog delete_confirm = new AlertDialog.Builder((MainActivity) getActivity())
                        .setMessage("정말 탈퇴하시겠습니까?")
                        .setPositiveButton("취소", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText((MainActivity) getActivity(), "탈퇴를 취소하셨습니다.", Toast.LENGTH_LONG).show();
                            }
                        }).setNegativeButton("탈퇴", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ((MainActivity) getActivity()).deleteUser(user);
                                Toast.makeText((MainActivity) getActivity(), "정상적으로 탈퇴처리 되었습니다.", Toast.LENGTH_LONG).show();
                            }
                        })
                        .show();

                TextView textView = (TextView) delete_confirm.findViewById(android.R.id.message);
                Typeface face = Typeface.createFromAsset(getActivity().getAssets(), "nanum_regular.ttf");
                textView.setTypeface(face);
            }
        });
    }



    // Edittext의 입력값을 String으로 리턴하는 함수
    private String edittextToString(EditText sentence) {
        return sentence.getText().toString();
    }

    // Spinner의 선택값을 String으로 리턴하는 함수
    private String spinnerToString(Spinner selected) {
        return selected.getSelectedItem().toString();
    }

    // RadioGroup의 선택값을 String으로 리턴하는 함수
    private String radiobtnToString(RadioButton selected) {
        return selected.getText().toString();
    }

    private void chooseRegion3(Spinner spinner1, Spinner spinner2, String region2) {
        if (spinnerToString(spinner1).equals("서울특별시")) {
            if (spinnerToString(spinner2).equals("강남구")) {
                mySpinner3Adapter = ArrayAdapter.createFromResource((MainActivity) getActivity(), R.array.spinner_region_seoul_gangnam, R.layout.item_spinner);
            } else if ("강동구".equals(spinnerToString(spinner2))) {
                mySpinner3Adapter = ArrayAdapter.createFromResource((MainActivity) getActivity(), R.array.spinner_region_seoul_gangdong, R.layout.item_spinner);
            } else if (spinnerToString(spinner2).equals("강북구")) {
                mySpinner3Adapter = ArrayAdapter.createFromResource((MainActivity) getActivity(), R.array.spinner_region_seoul_gangbuk, R.layout.item_spinner);
            } else if (spinnerToString(spinner2).equals("강서구")) {
                mySpinner3Adapter = ArrayAdapter.createFromResource((MainActivity) getActivity(), R.array.spinner_region_seoul_gangseo, R.layout.item_spinner);
            } else if (spinnerToString(spinner2).equals("관악구")) {
                mySpinner3Adapter = ArrayAdapter.createFromResource((MainActivity) getActivity(), R.array.spinner_region_seoul_gwanak, R.layout.item_spinner);
            } else if (spinnerToString(spinner2).equals("광진구")) {
                mySpinner3Adapter = ArrayAdapter.createFromResource((MainActivity) getActivity(), R.array.spinner_region_seoul_gwangjin, R.layout.item_spinner);
            } else if (spinnerToString(spinner2).equals("구로구")) {
                mySpinner3Adapter = ArrayAdapter.createFromResource((MainActivity) getActivity(), R.array.spinner_region_seoul_guro, R.layout.item_spinner);
            } else if (spinnerToString(spinner2).equals("금천구")) {
                mySpinner3Adapter = ArrayAdapter.createFromResource((MainActivity) getActivity(), R.array.spinner_region_seoul_geumcheon, R.layout.item_spinner);
            } else if (spinnerToString(spinner2).equals("노원구")) {
                mySpinner3Adapter = ArrayAdapter.createFromResource((MainActivity) getActivity(), R.array.spinner_region_seoul_nowon, R.layout.item_spinner);
            } else if (spinnerToString(spinner2).equals("도봉구")) {
                mySpinner3Adapter = ArrayAdapter.createFromResource((MainActivity) getActivity(), R.array.spinner_region_seoul_dobong, R.layout.item_spinner);
            } else if (spinnerToString(spinner2).equals("동대문구")) {
                mySpinner3Adapter = ArrayAdapter.createFromResource((MainActivity) getActivity(), R.array.spinner_region_seoul_dongdaemun, R.layout.item_spinner);
            } else if (spinnerToString(spinner2).equals("동작구")) {
                mySpinner3Adapter = ArrayAdapter.createFromResource((MainActivity) getActivity(), R.array.spinner_region_seoul_dongjag, R.layout.item_spinner);
            } else if (spinnerToString(spinner2).equals("마포구")) {
                mySpinner3Adapter = ArrayAdapter.createFromResource((MainActivity) getActivity(), R.array.spinner_region_seoul_mapo, R.layout.item_spinner);
            } else if (spinnerToString(spinner2).equals("서대문구")) {
                mySpinner3Adapter = ArrayAdapter.createFromResource((MainActivity) getActivity(), R.array.spinner_region_seoul_seodaemun, R.layout.item_spinner);
            } else if (spinnerToString(spinner2).equals("서초구")) {
                mySpinner3Adapter = ArrayAdapter.createFromResource((MainActivity) getActivity(), R.array.spinner_region_seoul_seocho, R.layout.item_spinner);
            } else if (spinnerToString(spinner2).equals("성동구")) {
                mySpinner3Adapter = ArrayAdapter.createFromResource((MainActivity) getActivity(), R.array.spinner_region_seoul_seongdong, R.layout.item_spinner);
            } else if (spinnerToString(spinner2).equals("성북구")) {
                mySpinner3Adapter = ArrayAdapter.createFromResource((MainActivity) getActivity(), R.array.spinner_region_seoul_seongbuk, R.layout.item_spinner);
            } else if (spinnerToString(spinner2).equals("송파구")) {
                mySpinner3Adapter = ArrayAdapter.createFromResource((MainActivity) getActivity(), R.array.spinner_region_seoul_songpa, R.layout.item_spinner);
            } else if (spinnerToString(spinner2).equals("양천구")) {
                mySpinner3Adapter = ArrayAdapter.createFromResource((MainActivity) getActivity(), R.array.spinner_region_seoul_yangcheon, R.layout.item_spinner);
            } else if (spinnerToString(spinner2).equals("영등포구")) {
                mySpinner3Adapter = ArrayAdapter.createFromResource((MainActivity) getActivity(), R.array.spinner_region_seoul_yeongdeungpo, R.layout.item_spinner);
            } else if (spinnerToString(spinner2).equals("용산구")) {
                mySpinner3Adapter = ArrayAdapter.createFromResource((MainActivity) getActivity(), R.array.spinner_region_seoul_yongsan, R.layout.item_spinner);
            } else if (spinnerToString(spinner2).equals("은평구")) {
                mySpinner3Adapter = ArrayAdapter.createFromResource((MainActivity) getActivity(), R.array.spinner_region_seoul_eunpyeong, R.layout.item_spinner);
            } else if (spinnerToString(spinner2).equals("종로구")) {
                mySpinner3Adapter = ArrayAdapter.createFromResource((MainActivity) getActivity(), R.array.spinner_region_seoul_jongno, R.layout.item_spinner);
            } else if (spinnerToString(spinner2).equals("중구")) {
                mySpinner3Adapter = ArrayAdapter.createFromResource((MainActivity) getActivity(), R.array.spinner_region_seoul_jung, R.layout.item_spinner);
            } else if (spinnerToString(spinner2).equals("중랑구")) {
                mySpinner3Adapter = ArrayAdapter.createFromResource((MainActivity) getActivity(), R.array.spinner_region_seoul_jungnanggu, R.layout.item_spinner);
            }
        } else {
            mySpinner3Adapter = ArrayAdapter.createFromResource((MainActivity) getActivity(), R.array.spinner_region_empty, R.layout.item_spinner);
        }
        mySpinner3Adapter.setDropDownViewResource(R.layout.item_spinner_dropdown);
        mySpinner3.setAdapter(mySpinner3Adapter);
        mySpinner3Adapter = (ArrayAdapter) mySpinner3.getAdapter();
        int mySpinner3Position = mySpinner3Adapter.getPosition(region2);
        mySpinner3.setSelection(mySpinner3Position);
    }
}