package com.example.udoncar;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.udoncar.model.Post;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainDialogActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private Spinner dest1Spn, dest2Spn, dest3Spn;
    private RadioGroup posRg, isreRg;
    private RadioButton posRb;
    private RadioButton isreRb;
    private Button cancelBtn, submitBtn;
    private CheckBox sexCb1, sexCb2, ageCb1, ageCb2, ageCb3, ageCb4, ageCb5, ageCb6;
    private List<String> destList;

    private ArrayAdapter<CharSequence> destspnAdpater1, destspnAdapter2, destspnAdapter3;

    private Post post;
    private ArrayList<Post> postListD;
    private Toast toast;
    private Intent intent;

    protected void onCreate(Bundle savedInstanceState){
        //다이얼로그 배경을 투명으로
        //왜인지 없으면 안 돌아감
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_main);
        intent = getIntent();

        dest1Spn = findViewById(R.id.dialogdest_spn1);
        dest2Spn = findViewById(R.id.dialogdest_spn2);
        dest3Spn = findViewById(R.id.dialogdest_spn3);
        sexCb1 = findViewById(R.id.dialogsex_cb1);
        sexCb2 = findViewById(R.id.dialogsex_cb2);
        ageCb1 = findViewById(R.id.dialogage_cb1);
        ageCb2 = findViewById(R.id.dialogage_cb2);
        ageCb3 = findViewById(R.id.dialogage_cb3);
        ageCb4 = findViewById(R.id.dialogage_cb4);
        ageCb5 = findViewById(R.id.dialogage_cb5);
        ageCb6 = findViewById(R.id.dialogage_cb6);
        cancelBtn = findViewById(R.id.dialogcancel_btn);
        submitBtn = findViewById(R.id.dialogsubmit_btn);
        posRg = (RadioGroup) findViewById(R.id.dialogpos_rg);
        isreRg = (RadioGroup) findViewById(R.id.dialogisre_rg);

        destspnAdpater1 = ArrayAdapter.createFromResource(this, R.array.spinner_region, R.layout.item_spinner);
        destspnAdpater1.setDropDownViewResource(R.layout.item_spinner_dropdown);
        dest1Spn.setAdapter(destspnAdpater1);

        dest1Spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (destspnAdpater1.getItem(i).equals("서울특별시")) {
                    destspnAdapter2 = ArrayAdapter.createFromResource(MainDialogActivity.this, R.array.spinner_region_seoul, R.layout.item_spinner);
                } else if (destspnAdpater1.getItem(i).equals("부산광역시")) {
                    destspnAdapter2 = ArrayAdapter.createFromResource(MainDialogActivity.this, R.array.spinner_region_busan, R.layout.item_spinner);
                }
                else if (destspnAdpater1.getItem(i).equals("대구광역시")) {
                    destspnAdapter2 = ArrayAdapter.createFromResource(MainDialogActivity.this, R.array.spinner_region_daegu, R.layout.item_spinner);
                } else if (destspnAdpater1.getItem(i).equals("인천광역시")) {
                    destspnAdapter2 = ArrayAdapter.createFromResource(MainDialogActivity.this, R.array.spinner_region_incheon, R.layout.item_spinner);
                } else if (destspnAdpater1.getItem(i).equals("광주광역시")) {
                    destspnAdapter2 = ArrayAdapter.createFromResource(MainDialogActivity.this, R.array.spinner_region_gwangju, R.layout.item_spinner);
                } else if (destspnAdpater1.getItem(i).equals("대전광역시")) {
                    destspnAdapter2 = ArrayAdapter.createFromResource(MainDialogActivity.this, R.array.spinner_region_daejeon, R.layout.item_spinner);
                } else if (destspnAdpater1.getItem(i).equals("울산광역시")) {
                    destspnAdapter2 = ArrayAdapter.createFromResource(MainDialogActivity.this, R.array.spinner_region_ulsan, R.layout.item_spinner);
                } else if (destspnAdpater1.getItem(i).equals("세종특별자치시")) {
                    destspnAdapter2 = ArrayAdapter.createFromResource(MainDialogActivity.this, R.array.spinner_region_sejong, R.layout.item_spinner);
                } else if (destspnAdpater1.getItem(i).equals("경기도")) {
                    destspnAdapter2 = ArrayAdapter.createFromResource(MainDialogActivity.this, R.array.spinner_region_gyeonggi, R.layout.item_spinner);
                } else if (destspnAdpater1.getItem(i).equals("강원도")) {
                    destspnAdapter2 = ArrayAdapter.createFromResource(MainDialogActivity.this, R.array.spinner_region_gangwon, R.layout.item_spinner);
                } else if (destspnAdpater1.getItem(i).equals("충청북도")) {
                    destspnAdapter2 = ArrayAdapter.createFromResource(MainDialogActivity.this, R.array.spinner_region_chung_buk, R.layout.item_spinner);
                } else if (destspnAdpater1.getItem(i).equals("충청남도")) {
                    destspnAdapter2 = ArrayAdapter.createFromResource(MainDialogActivity.this, R.array.spinner_region_chung_nam, R.layout.item_spinner);
                } else if (destspnAdpater1.getItem(i).equals("전라북도")) {
                    destspnAdapter2 = ArrayAdapter.createFromResource(MainDialogActivity.this, R.array.spinner_region_jeon_buk, R.layout.item_spinner);
                } else if (destspnAdpater1.getItem(i).equals("전라남도")) {
                    destspnAdapter2 = ArrayAdapter.createFromResource(MainDialogActivity.this, R.array.spinner_region_jeon_nam, R.layout.item_spinner);
                } else if (destspnAdpater1.getItem(i).equals("경상북도")) {
                    destspnAdapter2 = ArrayAdapter.createFromResource(MainDialogActivity.this, R.array.spinner_region_gyeong_buk, R.layout.item_spinner);
                } else if (destspnAdpater1.getItem(i).equals("경상남도")) {
                    destspnAdapter2 = ArrayAdapter.createFromResource(MainDialogActivity.this, R.array.spinner_region_gyeong_nam, R.layout.item_spinner);
                } else if (destspnAdpater1.getItem(i).equals("제주특별자치도")) {
                    destspnAdapter2 = ArrayAdapter.createFromResource(MainDialogActivity.this, R.array.spinner_region_jeju, R.layout.item_spinner);
                }
                destspnAdapter2.setDropDownViewResource(R.layout.item_spinner_dropdown);
                dest2Spn.setAdapter(destspnAdapter2);
                dest2Spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        choose3(dest1Spn, dest2Spn);
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

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                posRb = (RadioButton) findViewById(posRg.getCheckedRadioButtonId());
                posRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        posRb = findViewById(posRg.getCheckedRadioButtonId());
                    }
                });
                isreRb = (RadioButton) findViewById(isreRg.getCheckedRadioButtonId());
                isreRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        isreRb = findViewById(isreRg.getCheckedRadioButtonId());
                    }
                });

                destList = Arrays.asList(spinnerToString(dest1Spn), spinnerToString(dest2Spn), spinnerToString(dest3Spn));

                //필터링
                postListD = new ArrayList<>();

                Query query = db.collection("post")
                        .whereArrayContains("destspn", spinnerToString(dest3Spn));
                if (posRb.getText() != null){
                    query.whereEqualTo("position", posRb.getText().toString());
                }
                if (isreRb.getText() != null){
                    query.whereEqualTo("isrepeat", isreRb.getText().toString());
                }
                if (sexCb(sexCb1, sexCb2) != null){
                    query.whereEqualTo("optsex", sexCb(sexCb1, sexCb2));
                }
                if (ageCb(ageCb1,ageCb2,ageCb3,ageCb4,ageCb5,ageCb6) != null){
                    query.whereEqualTo("optage", ageCb(ageCb1,ageCb2,ageCb3,ageCb4,ageCb5,ageCb6));
                }
                query.addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        for (QueryDocumentSnapshot doc : value) {
                            post = doc.toObject(Post.class);
                            postListD.add(post);

                            intent.putExtra("postListD", postListD);
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    }
                });

                toast.makeText(getApplicationContext(), "필터링 성공!", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private String spinnerToString(Spinner selected) {
        return selected.getSelectedItem().toString();
    }

    private String radiobtnToString(RadioButton selected) {
        return selected.getText().toString();
    }

    private String checkboxToString(CheckBox selected) {
        return selected.getText().toString();
    }

    public String sexCb(CheckBox cb1, CheckBox cb2) {
        if (cb1.isChecked())
            return checkboxToString(cb1);
        else if (cb2.isChecked())
            return checkboxToString(cb2);
        else
            return null;
    }

/*
    public ArrayList<String> sexCb(CheckBox cb1, CheckBox cb2) {
        ArrayList<String> optsex = new ArrayList<>();
        if (cb1.isChecked())
            optsex.add("남자");
        else if (cb2.isChecked())
            optsex.add("여자");
        return optsex;
    }

 */

    public String ageCb(CheckBox cb1, CheckBox cb2, CheckBox cb3, CheckBox cb4, CheckBox cb5, CheckBox cb6){
        if (cb1.isChecked())
            return checkboxToString(cb1);
        else if (cb2.isChecked())
            return  checkboxToString(cb2);
        else if (cb3.isChecked())
            return  checkboxToString(cb3);
        else if (cb4.isChecked())
            return  checkboxToString(cb4);
        else if (cb5.isChecked())
            return  checkboxToString(cb5);
        else if (cb6.isChecked())
            return  checkboxToString(cb6);
        else
            return null;
    }

    public void choose3(Spinner spinner1, Spinner spinner2) {
        if (spinnerToString(spinner1).equals("서울특별시")) {
            if (spinnerToString(spinner2).equals("강남구")) {
                destspnAdapter3 = ArrayAdapter.createFromResource(this, R.array.spinner_region_seoul_gangnam, R.layout.item_spinner);
//                regionSpinner3Adapter.setDropDownViewResource(R.layout.item_spinner_dropdown);
//                regionSpinner3.setAdapter(regionSpinner3Adapter);
            } else if ("강동구".equals(spinnerToString(spinner2))) {
                destspnAdapter3 = ArrayAdapter.createFromResource(this, R.array.spinner_region_seoul_gangdong, R.layout.item_spinner);
//                regionSpinner3Adapter.setDropDownViewResource(R.layout.item_spinner_dropdown);
//                regionSpinner3.setAdapter(regionSpinner3Adapter);
            }
            else if (spinnerToString(spinner2).equals("강북구")) {
                destspnAdapter3 = ArrayAdapter.createFromResource(this, R.array.spinner_region_seoul_gangbuk, R.layout.item_spinner);
//                regionSpinner3Adapter.setDropDownViewResource(R.layout.item_spinner_dropdown);
//                regionSpinner3.setAdapter(regionSpinner3Adapter);
            } else if (spinnerToString(spinner2).equals("강서구")) {
                destspnAdapter3 = ArrayAdapter.createFromResource(this, R.array.spinner_region_seoul_gangseo, R.layout.item_spinner);
//                regionSpinner3Adapter.setDropDownViewResource(R.layout.item_spinner_dropdown);
//                regionSpinner3.setAdapter(regionSpinner3Adapter);
            } else if (spinnerToString(spinner2).equals("관악구")) {
                destspnAdapter3 = ArrayAdapter.createFromResource(this, R.array.spinner_region_seoul_gwanak, R.layout.item_spinner);
//                regionSpinner3Adapter.setDropDownViewResource(R.layout.item_spinner_dropdown);
//                regionSpinner3.setAdapter(regionSpinner3Adapter);
            } else if (spinnerToString(spinner2).equals("광진구")) {
                destspnAdapter3 = ArrayAdapter.createFromResource(this, R.array.spinner_region_seoul_gwangjin, R.layout.item_spinner);
//                regionSpinner3Adapter.setDropDownViewResource(R.layout.item_spinner_dropdown);
//                regionSpinner3.setAdapter(regionSpinner3Adapter);
            } else if (spinnerToString(spinner2).equals("구로구")) {
                destspnAdapter3 = ArrayAdapter.createFromResource(this, R.array.spinner_region_seoul_guro, R.layout.item_spinner);
//                regionSpinner3Adapter.setDropDownViewResource(R.layout.item_spinner_dropdown);
//                regionSpinner3.setAdapter(regionSpinner3Adapter);
            } else if (spinnerToString(spinner2).equals("금천구")) {
                destspnAdapter3 = ArrayAdapter.createFromResource(this, R.array.spinner_region_seoul_geumcheon, R.layout.item_spinner);
//                regionSpinner3Adapter.setDropDownViewResource(R.layout.item_spinner_dropdown);
//                regionSpinner3.setAdapter(regionSpinner3Adapter);
            } else if (spinnerToString(spinner2).equals("노원구")) {
                destspnAdapter3 = ArrayAdapter.createFromResource(this, R.array.spinner_region_seoul_nowon, R.layout.item_spinner);
//                regionSpinner3Adapter.setDropDownViewResource(R.layout.item_spinner_dropdown);
//                regionSpinner3.setAdapter(regionSpinner3Adapter);
            } else if (spinnerToString(spinner2).equals("도봉구")) {
                destspnAdapter3 = ArrayAdapter.createFromResource(this, R.array.spinner_region_seoul_dobong, R.layout.item_spinner);
//                regionSpinner3Adapter.setDropDownViewResource(R.layout.item_spinner_dropdown);
//                regionSpinner3.setAdapter(regionSpinner3Adapter);
            } else if (spinnerToString(spinner2).equals("동대문구")) {
                destspnAdapter3 = ArrayAdapter.createFromResource(this, R.array.spinner_region_seoul_dongdaemun, R.layout.item_spinner);
//                regionSpinner3Adapter.setDropDownViewResource(R.layout.item_spinner_dropdown);
//                regionSpinner3.setAdapter(regionSpinner3Adapter);
            } else if (spinnerToString(spinner2).equals("동작구")) {
                destspnAdapter3 = ArrayAdapter.createFromResource(this, R.array.spinner_region_seoul_dongjag, R.layout.item_spinner);
//                regionSpinner3Adapter.setDropDownViewResource(R.layout.item_spinner_dropdown);
//                regionSpinner3.setAdapter(regionSpinner3Adapter);
            } else if (spinnerToString(spinner2).equals("마포구")) {
                destspnAdapter3 = ArrayAdapter.createFromResource(this, R.array.spinner_region_seoul_mapo, R.layout.item_spinner);
//                regionSpinner3Adapter.setDropDownViewResource(R.layout.item_spinner_dropdown);
//                regionSpinner3.setAdapter(regionSpinner3Adapter);
            } else if (spinnerToString(spinner2).equals("서대문구")) {
                destspnAdapter3 = ArrayAdapter.createFromResource(this, R.array.spinner_region_seoul_seodaemun, R.layout.item_spinner);
//                regionSpinner3Adapter.setDropDownViewResource(R.layout.item_spinner_dropdown);
//                regionSpinner3.setAdapter(regionSpinner3Adapter);
            } else if (spinnerToString(spinner2).equals("서초구")) {
                destspnAdapter3 = ArrayAdapter.createFromResource(this, R.array.spinner_region_seoul_seocho, R.layout.item_spinner);
//                regionSpinner3Adapter.setDropDownViewResource(R.layout.item_spinner_dropdown);
//                regionSpinner3.setAdapter(regionSpinner3Adapter);
            } else if (spinnerToString(spinner2).equals("성동구")) {
                destspnAdapter3 = ArrayAdapter.createFromResource(this, R.array.spinner_region_seoul_seongdong, R.layout.item_spinner);
//                regionSpinner3Adapter.setDropDownViewResource(R.layout.item_spinner_dropdown);
//                regionSpinner3.setAdapter(regionSpinner3Adapter);
            } else if (spinnerToString(spinner2).equals("성북구")) {
                destspnAdapter3 = ArrayAdapter.createFromResource(this, R.array.spinner_region_seoul_seongbuk, R.layout.item_spinner);
//                regionSpinner3Adapter.setDropDownViewResource(R.layout.item_spinner_dropdown);
//                regionSpinner3.setAdapter(regionSpinner3Adapter);
            } else if (spinnerToString(spinner2).equals("송파구")) {
                destspnAdapter3 = ArrayAdapter.createFromResource(this, R.array.spinner_region_seoul_songpa, R.layout.item_spinner);
//                regionSpinner3Adapter.setDropDownViewResource(R.layout.item_spinner_dropdown);
//                regionSpinner3.setAdapter(regionSpinner3Adapter);
            } else if (spinnerToString(spinner2).equals("양천구")) {
                destspnAdapter3 = ArrayAdapter.createFromResource(this, R.array.spinner_region_seoul_yangcheon, R.layout.item_spinner);
//                regionSpinner3Adapter.setDropDownViewResource(R.layout.item_spinner_dropdown);
//                regionSpinner3.setAdapter(regionSpinner3Adapter);
            } else if (spinnerToString(spinner2).equals("영등포구")) {
                destspnAdapter3 = ArrayAdapter.createFromResource(this, R.array.spinner_region_seoul_yeongdeungpo, R.layout.item_spinner);
//                regionSpinner3Adapter.setDropDownViewResource(R.layout.item_spinner_dropdown);
//                regionSpinner3.setAdapter(regionSpinner3Adapter);
            } else if (spinnerToString(spinner2).equals("용산구")) {
                destspnAdapter3 = ArrayAdapter.createFromResource(this, R.array.spinner_region_seoul_yongsan, R.layout.item_spinner);
//                regionSpinner3Adapter.setDropDownViewResource(R.layout.item_spinner_dropdown);
//                regionSpinner3.setAdapter(regionSpinner3Adapter);
            } else if (spinnerToString(spinner2).equals("은평구")) {
                destspnAdapter3 = ArrayAdapter.createFromResource(this, R.array.spinner_region_seoul_eunpyeong, R.layout.item_spinner);
//                regionSpinner3Adapter.setDropDownViewResource(R.layout.item_spinner_dropdown);
//                regionSpinner3.setAdapter(regionSpinner3Adapter);
            } else if (spinnerToString(spinner2).equals("종로구")) {
                destspnAdapter3 = ArrayAdapter.createFromResource(this, R.array.spinner_region_seoul_jongno, R.layout.item_spinner);
//                regionSpinner3Adapter.setDropDownViewResource(R.layout.item_spinner_dropdown);
//                regionSpinner3.setAdapter(regionSpinner3Adapter);
            } else if (spinnerToString(spinner2).equals("중구")) {
                destspnAdapter3 = ArrayAdapter.createFromResource(this, R.array.spinner_region_seoul_jung, R.layout.item_spinner);
//                regionSpinner3Adapter.setDropDownViewResource(R.layout.item_spinner_dropdown);
//                regionSpinner3.setAdapter(regionSpinner3Adapter);
            } else if (spinnerToString(spinner2).equals("중랑구")) {
                destspnAdapter3 = ArrayAdapter.createFromResource(this, R.array.spinner_region_seoul_jungnanggu, R.layout.item_spinner);
//                regionSpinner3Adapter.setDropDownViewResource(R.layout.item_spinner_dropdown);
//                regionSpinner3.setAdapter(regionSpinner3Adapter);
            }
        } else {
            destspnAdapter3 = ArrayAdapter.createFromResource(this, R.array.spinner_region_empty, R.layout.item_spinner);
//            regionSpinner3Adapter.setDropDownViewResource(R.layout.item_spinner_dropdown);
//            regionSpinner3.setAdapter(regionSpinner3Adapter);
        }
        destspnAdapter3.setDropDownViewResource(R.layout.item_spinner_dropdown);
        dest3Spn.setAdapter(destspnAdapter3);
    }
}