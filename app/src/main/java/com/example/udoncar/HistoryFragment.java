package com.example.udoncar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.udoncar.model.Chat;
import com.example.udoncar.model.History;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoryFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HistoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment history.
     */
    // TODO: Rename and change types and number of parameters
    public static HistoryFragment newInstance(String param1, String param2) {
        HistoryFragment fragment = new HistoryFragment();
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

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private RecyclerView historyRecyclerView;
    private RecyclerView.Adapter historyAdapter;
    private RecyclerView.LayoutManager historyLayoutManager;
    private List<History> historyList;
    private History history;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_history, container, false);

        //뷰
        historyRecyclerView = v.findViewById(R.id.history_rv);
        historyList = new ArrayList<>();

        //DB에서 불러오기
        historyList.add(new History("title", "dest", "time"));

        db.collection("history")
                .whereEqualTo("user_id", user.getEmail())
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        for (QueryDocumentSnapshot doc : value) {
                            db.collection("post").document(doc.getData().get("post_id").toString())
                                            .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            history.sethistId(doc.getData().get("hist_id").toString());
                                            history.setpostId(doc.getData().get("post_id").toString());
                                            history.setuserId(user.getEmail());
                                            historyList.add(history);
                                        }
                                    });
                        }
                    }
                });

//        historyRecyclerView.setHasFixedSize(true);
        historyAdapter = new HistoryAdapter(historyList, getContext());
        historyRecyclerView.setAdapter(historyAdapter);
        historyLayoutManager = new LinearLayoutManager(getActivity());
        historyRecyclerView.setLayoutManager(historyLayoutManager);

        return v;
    }
}