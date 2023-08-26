package com.example.openiums2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    TextView username, userid, userdept, useryearsem, useradmit;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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

    public void showuserdata() {
        String userUsername = HelperClass.stringToPass; // receive a string from Login class

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkUserDatabase = reference.orderByChild("username").equalTo(userUsername);
        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    String fullnameFromDB = snapshot.child(userUsername).child("name").getValue(String.class);
                    Long idFromDBLong = snapshot.child(userUsername).child("id").getValue(Long.class);
                    String idFromDB = String.valueOf(idFromDBLong);

                    String deptFromDB = snapshot.child(userUsername).child("dept").getValue(String.class);
                    String yearsemFromDB = snapshot.child(userUsername).child("yearsem").getValue(String.class);
                    String admitFromDB = snapshot.child(userUsername).child("admit").getValue(String.class);

                    username.setText(fullnameFromDB);
                    //username.setText(HelperClass.stringToPass);
                    userid.setText(idFromDB);
                    userdept.setText(deptFromDB);
                    useryearsem.setText(yearsemFromDB);
                    useradmit.setText(admitFromDB);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        username = view.findViewById(R.id.username);
        userid = view.findViewById(R.id.studentid);
        userdept = view.findViewById(R.id.dept);
        useryearsem = view.findViewById(R.id.yearsem);
        useradmit = view.findViewById(R.id.admit);

        showuserdata();

        return view;
    }
}