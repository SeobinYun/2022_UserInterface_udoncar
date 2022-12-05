package com.example.udoncar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.udoncar.model.Chat;
import com.example.udoncar.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.TimeZone;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WriteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WriteFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public WriteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment write.
     */
    // TODO: Rename and change types and number of parameters
    public static WriteFragment newInstance(String param1, String param2) {
        WriteFragment fragment = new WriteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_write, container, false);
    }

    private Button writeBtn;
    private EditText titleEt;
    private EditText destEt;

    private DatePicker datePicker;
    private TimePicker timePicker;

    private EditText contentEt;
    private RadioGroup positionRg;
    private RadioButton positionRb;
    private RadioGroup isrepeatRg;
    private RadioButton isrepeatRb;
    private RadioGroup optsexRg;
    private RadioButton optsexRb;
    private Spinner optageSpn;
    private ArrayAdapter optageSpnAdapter;
    private Spinner destspn1;
    private Spinner destspn2;
    private Spinner destspn3;
    private ArrayAdapter<CharSequence> destspnAdpater1, destspnAdapter2, destspnAdapter3;
    private CheckBox sexCb1;
    private CheckBox sexCb2;
    private CheckBox ageCb1;
    private CheckBox ageCb2;
    private CheckBox ageCb3;
    private CheckBox ageCb4;
    private CheckBox ageCb5;
    private CheckBox ageCb6;

    private FirebaseAuth mAuth;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private TextView startTv;
    private List<String> startList;
    private String startS;
    private String start1;
    private String start2;
    private String start3;
    private Date meetDate;
    private String selectedDate;
    private String selectedTime;


//    private List<Arrays> optSex;
//    private List<Arrays> optAge;


    private List<Boolean> optSex;
    private List<Boolean> optAge;

//    private boolean[] optSex;
//    private boolean[] optAge;


    private String position;
    private String postIdS;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAuth = FirebaseAuth.getInstance();

        writeBtn = view.findViewById(R.id.write_btn);
        titleEt = view.findViewById(R.id.writetitle_et);
        destEt = view.findViewById(R.id.writedest_et);



        // build.gradle - minSDK 21-> 26
        datePicker = (DatePicker) view.findViewById(R.id.date);
        datePicker.init(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                Log.d("디버깅", "날짜: " + i + "년 " + (i1 + 1) + "월 " + i2 + "일");
                selectedDate = i + "/" + (i1 + 1) + "/" + i2;
                Log.d("디버깅", "selectedDate: " + selectedDate);

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                try {
                    Date date = dateFormat.parse(selectedDate); // 기존 string을 date 클래스로 변환
                    selectedDate = dateFormat.format(date); // 변환한 값의 format 변경
                    Log.d("디버깅", "포맷 후 selectedDate: " + selectedDate);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
//        datePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
//            @Override
//            public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
//                Log.d("디버깅", "날짜: " + i + "년 " + (i1 + 1) + "월 " + i2 + "일");
//                selectedDate = i + "/" + (i1 + 1) + "/" + i2;
//                Log.d("디버깅", "selectedDate: " + selectedDate);
//
//                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
//                try {
//                    Date date = dateFormat.parse(selectedDate); // 기존 string을 date 클래스로 변환
//                    selectedDate = dateFormat.format(date); // 변환한 값의 format 변경
//                    Log.d("디버깅", "포맷 후 selectedDate: " + selectedDate);
//                }
//                catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });


        timePicker = (TimePicker) view.findViewById(R.id.time);
        timePicker.setIs24HourView(false);
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int hour, int minute) {
                Log.d("디버깅", "시간: " + hour + "시 " + minute + "분");
                selectedTime = " " + hour + ":" + minute;
                Log.d("디버깅", "selectTime: " + selectedTime);

                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
                try {
                    Date date = timeFormat.parse(selectedTime); // 기존 string을 date 클래스로 변환
                    selectedTime = timeFormat.format(date); // 변환한 값의 format 변경
                    Log.d("디버깅", "포맷 후 selectedTime: " + selectedTime);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });


        contentEt = view.findViewById(R.id.writecont_et);
        destspn1 = (Spinner) view.findViewById(R.id.writedest_spn1);
        destspn1.setPrompt("시/도 선택");
        destspn2 = (Spinner) view.findViewById(R.id.writedest_spn2);
        destspn2.setPrompt("시/군/구 선택");
        destspn3 = (Spinner) view.findViewById(R.id.writedest_spn3);
        destspn3.setPrompt("읍/면/동 선택");

        positionRg = (RadioGroup) view.findViewById(R.id.writepos_rg);
        positionRb = (RadioButton) view.findViewById(positionRg.getCheckedRadioButtonId());
        positionRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                positionRb = view.findViewById(positionRg.getCheckedRadioButtonId());
                Log.d("디버깅", "선택: " + positionRb.getText().toString());
            }
        });
        isrepeatRg = (RadioGroup) view.findViewById(R.id.writeisre_rg);
        isrepeatRb = (RadioButton) view.findViewById(isrepeatRg.getCheckedRadioButtonId());
        isrepeatRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                isrepeatRb = view.findViewById(isrepeatRg.getCheckedRadioButtonId());
                Log.d("디버깅", "선택: " + isrepeatRb.getText().toString());
            }
        });


        optSex = new ArrayList<Boolean>(Arrays.asList(new Boolean[2]));
        Collections.fill(optSex,Boolean.FALSE);
        Log.w("디버깅", "optSex: " + optSex.toArray().toString());


        sexCb1 = (CheckBox) view.findViewById(R.id.writesex1_Cb);
        sexCb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                //optSex.toArray()[0] = true;
                optSex.set(0, true);

                Log.w("디버그", "sexCb1 checked");
            }
        });
        sexCb2 = (CheckBox) view.findViewById(R.id.writesex2_Cb);
        sexCb2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                optSex.toArray()[1] = true;
                optSex.set(1, true);
                Log.w("디버그", "sexCb2 checked");
            }
        });



        optAge = new ArrayList<Boolean>(Arrays.asList(new Boolean[6]));
        Collections.fill(optAge,Boolean.FALSE);
        Log.w("디버깅", "optAge: " + optAge.toArray().toString());
        ageCb1 = (CheckBox) view.findViewById(R.id.writeage1_Cb);
        ageCb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                optAge.toArray()[0] = true;
                optAge.set(0, true);
                Log.w("디버그", "ageCb1 checked");
            }
        });
        ageCb2 = (CheckBox) view.findViewById(R.id.writeage2_Cb);
        ageCb2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                optAge.toArray()[1] = true;
                optAge.set(1, true);
                Log.w("디버그", "ageCb2 checked");
            }
        });
        ageCb3 = (CheckBox) view.findViewById(R.id.writeage3_Cb);
        ageCb3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                optAge.toArray()[2] = true;
                optAge.set(2, true);
                Log.w("디버그", "ageCb3 checked");
            }
        });
        ageCb4 = (CheckBox) view.findViewById(R.id.writeage4_Cb);
        ageCb4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                optAge.toArray()[3] = true;
                optAge.set(3, true);
                Log.w("디버그", "ageCb4 checked");
            }
        });
        ageCb5 = (CheckBox) view.findViewById(R.id.writeage5_Cb);
        ageCb5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                optAge.toArray()[4] = true;
                optAge.set(4, true);
                Log.w("디버그", "ageCb5 checked");
            }
        });
        ageCb6 = (CheckBox) view.findViewById(R.id.writeage6_Cb);
        ageCb6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                optAge.toArray()[5] = true;
                optAge.set(5, true);
                Log.w("디버그", "ageCb6 checked");
            }
        });


        DocumentReference currentuserRef = db.collection("users").document(user.getEmail());
        currentuserRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document = task.getResult();
                startList = (List<String>) document.getData().get("region");
                startTv = view.findViewById(R.id.writestart_tv);
                startS = startList.toString();
                startTv.setText(startS);

            }
        });

        writeBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {

//                positionRb = (RadioButton) view.findViewById(positionRg.getCheckedRadioButtonId());
//                isrepeatRb = (RadioButton) view.findViewById(isrepeatRg.getCheckedRadioButtonId());

                //System.out.println("포지션 : " + positionRg.getCheckedRadioButtonId());
                //position = positionRb.getText().toString();

                start1 = startList.toArray()[0].toString();
                start2 = startList.toArray()[1].toString();
                start3 = startList.toArray()[2].toString();


                selectedDate = selectedDate + " " + selectedTime;
                Log.w("디버깅", selectedDate);
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
                // String을 Date로 변환
                try {
                    meetDate = dateFormat.parse(selectedDate);
                    Log.w("디버깅", "meetDate parse: " + meetDate);
                    Log.w("디버깅", "meetDate parse: " + meetDate.toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Log.w("디버깅", optAge.toArray().toString());


                //post만드는 함수
                if(edittextToString(titleEt).equals("") || edittextToString(destEt).equals("")) {
                    Toast.makeText((MainActivity)getActivity(), "필수 정보를 입력해주세요. ", Toast.LENGTH_LONG).show();
                }
                else{
                    createpost(edittextToString(titleEt), edittextToString(destEt),
                            start1, start2, start3,
                            spinnerToString(destspn1), spinnerToString(destspn2), spinnerToString(destspn3),
                            positionRb.getText().toString(), isrepeatRb.getText().toString(),
                            optSex, optAge,
                            meetDate, edittextToString(contentEt));
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    startActivity(intent);
                }
            }
        });

        //startspn1Adapter.notifyDataSetChanged();
        destspnAdpater1 = ArrayAdapter.createFromResource(getActivity(), R.array.spinner_region, R.layout.item_spinner);
        destspnAdpater1.setDropDownViewResource(R.layout.item_spinner_dropdown);
        destspn1.setAdapter(destspnAdpater1);

        destspn1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (destspnAdpater1.getItem(i).equals("서울특별시")) {
                    destspnAdapter2 = ArrayAdapter.createFromResource(getActivity(), R.array.spinner_region_seoul, R.layout.item_spinner);
                } else if (destspnAdpater1.getItem(i).equals("부산광역시")) {
                    destspnAdapter2 = ArrayAdapter.createFromResource(getActivity(), R.array.spinner_region_busan, R.layout.item_spinner);
                } else if (destspnAdpater1.getItem(i).equals("대구광역시")) {
                    destspnAdapter2 = ArrayAdapter.createFromResource(getActivity(), R.array.spinner_region_daegu, R.layout.item_spinner);
                } else if (destspnAdpater1.getItem(i).equals("인천광역시")) {
                    destspnAdapter2 = ArrayAdapter.createFromResource(getActivity(), R.array.spinner_region_incheon, R.layout.item_spinner);
                } else if (destspnAdpater1.getItem(i).equals("광주광역시")) {
                    destspnAdapter2 = ArrayAdapter.createFromResource(getActivity(), R.array.spinner_region_gwangju, R.layout.item_spinner);
                } else if (destspnAdpater1.getItem(i).equals("대전광역시")) {
                    destspnAdapter2 = ArrayAdapter.createFromResource(getActivity(), R.array.spinner_region_daejeon, R.layout.item_spinner);
                } else if (destspnAdpater1.getItem(i).equals("울산광역시")) {
                    destspnAdapter2 = ArrayAdapter.createFromResource(getActivity(), R.array.spinner_region_ulsan, R.layout.item_spinner);
                } else if (destspnAdpater1.getItem(i).equals("세종특별자치시")) {
                    destspnAdapter2 = ArrayAdapter.createFromResource(getActivity(), R.array.spinner_region_sejong, R.layout.item_spinner);
                } else if (destspnAdpater1.getItem(i).equals("경기도")) {
                    destspnAdapter2 = ArrayAdapter.createFromResource(getActivity(), R.array.spinner_region_gyeonggi, R.layout.item_spinner);
                } else if (destspnAdpater1.getItem(i).equals("강원도")) {
                    destspnAdapter2 = ArrayAdapter.createFromResource(getActivity(), R.array.spinner_region_gangwon, R.layout.item_spinner);
                } else if (destspnAdpater1.getItem(i).equals("충청북도")) {
                    destspnAdapter2 = ArrayAdapter.createFromResource(getActivity(), R.array.spinner_region_chung_buk, R.layout.item_spinner);
                } else if (destspnAdpater1.getItem(i).equals("충청남도")) {
                    destspnAdapter2 = ArrayAdapter.createFromResource(getActivity(), R.array.spinner_region_chung_nam, R.layout.item_spinner);
                } else if (destspnAdpater1.getItem(i).equals("전라북도")) {
                    destspnAdapter2 = ArrayAdapter.createFromResource(getActivity(), R.array.spinner_region_jeon_buk, R.layout.item_spinner);
                } else if (destspnAdpater1.getItem(i).equals("전라남도")) {
                    destspnAdapter2 = ArrayAdapter.createFromResource(getActivity(), R.array.spinner_region_jeon_nam, R.layout.item_spinner);
                } else if (destspnAdpater1.getItem(i).equals("경상북도")) {
                    destspnAdapter2 = ArrayAdapter.createFromResource(getActivity(), R.array.spinner_region_gyeong_buk, R.layout.item_spinner);
                } else if (destspnAdpater1.getItem(i).equals("경상남도")) {
                    destspnAdapter2 = ArrayAdapter.createFromResource(getActivity(), R.array.spinner_region_gyeong_nam, R.layout.item_spinner);
                } else if (destspnAdpater1.getItem(i).equals("제주특별자치도")) {
                    destspnAdapter2 = ArrayAdapter.createFromResource(getActivity(), R.array.spinner_region_jeju, R.layout.item_spinner);
                }
                destspnAdapter2.setDropDownViewResource(R.layout.item_spinner_dropdown);
                destspn2.setAdapter(destspnAdapter2);
                destspn2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        choose3(destspn1, destspn2);
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

    }


    public String sexCb(CheckBox cb1, CheckBox cb2) {
        if (cb1.isChecked())
            return checkboxToString(cb1);
        else if (cb2.isChecked())
            return checkboxToString(cb2);
        else
            return null;
    }

    public String ageCb(CheckBox cb1, CheckBox cb2, CheckBox cb3, CheckBox cb4, CheckBox cb5, CheckBox cb6) {
        if (cb1.isChecked())
            return checkboxToString(cb1);
        else if (cb2.isChecked())
            return checkboxToString(cb2);
        else if (cb3.isChecked())
            return checkboxToString(cb3);
        else if (cb4.isChecked())
            return checkboxToString(cb4);
        else if (cb5.isChecked())
            return checkboxToString(cb5);
        else if (cb6.isChecked())
            return checkboxToString(cb6);
        else
            return null;
    }

    public void choose3(Spinner spinner1, Spinner spinner2) {
        if (spinnerToString(spinner1).equals("서울특별시")) {
            if (spinnerToString(spinner2).equals("강남구")) {
                destspnAdapter3 = ArrayAdapter.createFromResource(getActivity(), R.array.spinner_region_seoul_gangnam, R.layout.item_spinner);
            } else if ("강동구".equals(spinnerToString(spinner2))) {
                destspnAdapter3 = ArrayAdapter.createFromResource(getActivity(), R.array.spinner_region_seoul_gangdong, R.layout.item_spinner);
            } else if (spinnerToString(spinner2).equals("강북구")) {
                destspnAdapter3 = ArrayAdapter.createFromResource(getActivity(), R.array.spinner_region_seoul_gangbuk, R.layout.item_spinner);
            } else if (spinnerToString(spinner2).equals("강서구")) {
                destspnAdapter3 = ArrayAdapter.createFromResource(getActivity(), R.array.spinner_region_seoul_gangseo, R.layout.item_spinner);
            } else if (spinnerToString(spinner2).equals("관악구")) {
                destspnAdapter3 = ArrayAdapter.createFromResource(getActivity(), R.array.spinner_region_seoul_gwanak, R.layout.item_spinner);
            } else if (spinnerToString(spinner2).equals("광진구")) {
                destspnAdapter3 = ArrayAdapter.createFromResource(getActivity(), R.array.spinner_region_seoul_gwangjin, R.layout.item_spinner);
            } else if (spinnerToString(spinner2).equals("구로구")) {
                destspnAdapter3 = ArrayAdapter.createFromResource(getActivity(), R.array.spinner_region_seoul_guro, R.layout.item_spinner);
            } else if (spinnerToString(spinner2).equals("금천구")) {
                destspnAdapter3 = ArrayAdapter.createFromResource(getActivity(), R.array.spinner_region_seoul_geumcheon, R.layout.item_spinner);
            } else if (spinnerToString(spinner2).equals("노원구")) {
                destspnAdapter3 = ArrayAdapter.createFromResource(getActivity(), R.array.spinner_region_seoul_nowon, R.layout.item_spinner);
            } else if (spinnerToString(spinner2).equals("도봉구")) {
                destspnAdapter3 = ArrayAdapter.createFromResource(getActivity(), R.array.spinner_region_seoul_dobong, R.layout.item_spinner);
            } else if (spinnerToString(spinner2).equals("동대문구")) {
                destspnAdapter3 = ArrayAdapter.createFromResource(getActivity(), R.array.spinner_region_seoul_dongdaemun, R.layout.item_spinner);
            } else if (spinnerToString(spinner2).equals("동작구")) {
                destspnAdapter3 = ArrayAdapter.createFromResource(getActivity(), R.array.spinner_region_seoul_dongjag, R.layout.item_spinner);
            } else if (spinnerToString(spinner2).equals("마포구")) {
                destspnAdapter3 = ArrayAdapter.createFromResource(getActivity(), R.array.spinner_region_seoul_mapo, R.layout.item_spinner);
            } else if (spinnerToString(spinner2).equals("서대문구")) {
                destspnAdapter3 = ArrayAdapter.createFromResource(getActivity(), R.array.spinner_region_seoul_seodaemun, R.layout.item_spinner);
            } else if (spinnerToString(spinner2).equals("서초구")) {
                destspnAdapter3 = ArrayAdapter.createFromResource(getActivity(), R.array.spinner_region_seoul_seocho, R.layout.item_spinner);
            } else if (spinnerToString(spinner2).equals("성동구")) {
                destspnAdapter3 = ArrayAdapter.createFromResource(getActivity(), R.array.spinner_region_seoul_seongdong, R.layout.item_spinner);
            } else if (spinnerToString(spinner2).equals("성북구")) {
                destspnAdapter3 = ArrayAdapter.createFromResource(getActivity(), R.array.spinner_region_seoul_seongbuk, R.layout.item_spinner);
            } else if (spinnerToString(spinner2).equals("송파구")) {
                destspnAdapter3 = ArrayAdapter.createFromResource(getActivity(), R.array.spinner_region_seoul_songpa, R.layout.item_spinner);
            } else if (spinnerToString(spinner2).equals("양천구")) {
                destspnAdapter3 = ArrayAdapter.createFromResource(getActivity(), R.array.spinner_region_seoul_yangcheon, R.layout.item_spinner);
            } else if (spinnerToString(spinner2).equals("영등포구")) {
                destspnAdapter3 = ArrayAdapter.createFromResource(getActivity(), R.array.spinner_region_seoul_yeongdeungpo, R.layout.item_spinner);
            } else if (spinnerToString(spinner2).equals("용산구")) {
                destspnAdapter3 = ArrayAdapter.createFromResource(getActivity(), R.array.spinner_region_seoul_yongsan, R.layout.item_spinner);
            } else if (spinnerToString(spinner2).equals("은평구")) {
                destspnAdapter3 = ArrayAdapter.createFromResource(getActivity(), R.array.spinner_region_seoul_eunpyeong, R.layout.item_spinner);
            } else if (spinnerToString(spinner2).equals("종로구")) {
                destspnAdapter3 = ArrayAdapter.createFromResource(getActivity(), R.array.spinner_region_seoul_jongno, R.layout.item_spinner);
            } else if (spinnerToString(spinner2).equals("중구")) {
                destspnAdapter3 = ArrayAdapter.createFromResource(getActivity(), R.array.spinner_region_seoul_jung, R.layout.item_spinner);
            } else if (spinnerToString(spinner2).equals("중랑구")) {
                destspnAdapter3 = ArrayAdapter.createFromResource(getActivity(), R.array.spinner_region_seoul_jungnanggu, R.layout.item_spinner);
            }
        } else {
            destspnAdapter3 = ArrayAdapter.createFromResource(getActivity(), R.array.spinner_region_empty, R.layout.item_spinner);
        }
        destspnAdapter3.setDropDownViewResource(R.layout.item_spinner_dropdown);
        destspn3.setAdapter(destspnAdapter3);
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

    private String checkboxToString(CheckBox selected) {
        return selected.getText().toString();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public String randomString() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();
        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return generatedString;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void createpost(String title, String dest,
                            String startspn1, String startspn2, String startspn3,
                            String destspn1, String destspn2, String destspn3,
                            String position, String isrepeat, List<Boolean> optsex, List<Boolean> optage,
                            Date meetAt, String content) {

        //postIdS = Integer.toString((int) Math.random()*100000000);

        List<String> startList = Arrays.asList(startspn1, startspn2, startspn3);
        List<String> destList = Arrays.asList(destspn1, destspn2, destspn3);

        Map<String, Object> docData = new HashMap<>();
        docData.put("postId", randomString());
        docData.put("userId", user.getEmail());
        docData.put("startspn", startList);
        docData.put("destspn", destList);
        docData.put("title", title);
        docData.put("dest", dest);
        docData.put("content", content);
        docData.put("position", position);
        docData.put("isrepeat", isrepeat);
        docData.put("optsex", optsex);
        docData.put("optage", optage);
        docData.put("creatAt", new Date());
        docData.put("meetAt", meetAt);

        db.collection("post").document().set(docData);
        Toast.makeText((MainActivity)getActivity(), "작성 완료!", Toast.LENGTH_LONG).show();
    }

}