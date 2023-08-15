package com.example.openiums2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment {

    LinearLayout personalinfo, experience, review;
    TextView personalinfobtn, experiencebtn, reviewbtn;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    TextView userbirth, username,usercgpa,usermail,usergender,userreli,userblood, userdept,userdegree,usersec,usersemester;
    TextView usergroup,session,userfaname,userfaocp,usermaname,usermaocp,userid,usercoursecmp,usercontact;
    public SettingsFragment() {
        // Required empty public constructor
    }

    private void showUserData() {
        String userUsername = HelperClass.stringToPass; // receive a string from Login class

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkUserDatabase = reference.orderByChild("username").equalTo(userUsername);
        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    DataSnapshot userSnapshot = snapshot.child(userUsername);

                    username.setText(userSnapshot.child("name").getValue(String.class));
                    userbirth.setText(userSnapshot.child("birth").getValue(String.class));
                    Double cgpaValue = userSnapshot.child("cgpa").getValue(Double.class);
                    if (cgpaValue != null) {
                        usercgpa.setText(String.valueOf(cgpaValue));
                    } else {
                        usercgpa.setText("N/A"); // Set a default value if CGPA is not available
                    }
                    userdept.setText(userSnapshot.child("department").getValue(String.class));
                    usermail.setText(userSnapshot.child("email").getValue(String.class));
                    usergender.setText(userSnapshot.child("gender").getValue(String.class));
                    userreli.setText(userSnapshot.child("religion").getValue(String.class));
                    userblood.setText(userSnapshot.child("blood").getValue(String.class));
                    userdegree.setText(userSnapshot.child("degree").getValue(String.class));
                    usersec.setText(userSnapshot.child("sec").getValue(String.class));
                    usergroup.setText(userSnapshot.child("group").getValue(String.class));
                    session.setText(userSnapshot.child("session").getValue(String.class));
                    userfaname.setText(userSnapshot.child("fathername").getValue(String.class));
                    usermaname.setText(userSnapshot.child("mothername").getValue(String.class));
                    userfaocp.setText(userSnapshot.child("fatherocp").getValue(String.class));
                    usermaocp.setText(userSnapshot.child("motherocp").getValue(String.class));
                    usersemester.setText(userSnapshot.child("semester").getValue(String.class));
                    usercoursecmp.setText(userSnapshot.child("coursecmp").getValue(String.class));
                    Long idFromDBLong = snapshot.child(userUsername).child("id").getValue(Long.class);
                    String idFromDB = String.valueOf(idFromDBLong);
                    userid.setText(idFromDB);
                    Long contactFromDBLong = snapshot.child(userUsername).child("contact").getValue(Long.class);
                    String contactFromDB = String.valueOf(contactFromDBLong);
                    usercontact.setText(contactFromDB);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle onCancelled as needed
            }
        });
    }


    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);


        userbirth=rootView.findViewById(R.id.birth);
        username=rootView.findViewById(R.id.profname);
        usercgpa=rootView.findViewById(R.id.cgpa);
        userdept=rootView.findViewById(R.id.department);
        usermail=rootView.findViewById(R.id.usermail);
        usergender=rootView.findViewById(R.id.gender);
        userreli=rootView.findViewById(R.id.religion);
        userblood=rootView.findViewById(R.id.blood);
        userdegree=rootView.findViewById(R.id.degree);
        usersec=rootView.findViewById(R.id.sec);
        usergroup=rootView.findViewById(R.id.group);
        session=rootView.findViewById(R.id.session);
        userfaname=rootView.findViewById(R.id.fathername);
        usermaname=rootView.findViewById(R.id.mothername);
        userfaocp=rootView.findViewById(R.id.fatherocp);
        usermaocp=rootView.findViewById(R.id.motherocp);
        usersemester=rootView.findViewById(R.id.semester);
        userid=rootView.findViewById(R.id.studentid);
        usercoursecmp=rootView.findViewById(R.id.coursecmp);
        usercontact=rootView.findViewById(R.id.contact);
        showUserData();
        personalinfo = rootView.findViewById(R.id.personalinfo);
        experience = rootView.findViewById(R.id.experience);
        review = rootView.findViewById(R.id.review);
        personalinfobtn = rootView.findViewById(R.id.personalinfobtn);
        experiencebtn = rootView.findViewById(R.id.experiencebtn);
        reviewbtn = rootView.findViewById(R.id.reviewbtn);

        /*making personal info visible*/
        personalinfo.setVisibility(View.VISIBLE);
        experience.setVisibility(View.GONE);
        review.setVisibility(View.GONE);

        personalinfobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                personalinfo.setVisibility(View.VISIBLE);
                experience.setVisibility(View.GONE);
                review.setVisibility(View.GONE);
                personalinfobtn.setTextColor(getResources().getColor(R.color.blue));
                experiencebtn.setTextColor(getResources().getColor(R.color.grey));
                reviewbtn.setTextColor(getResources().getColor(R.color.grey));
            }
        });

        experiencebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                personalinfo.setVisibility(View.GONE);
                experience.setVisibility(View.VISIBLE);
                review.setVisibility(View.GONE);
                personalinfobtn.setTextColor(getResources().getColor(R.color.grey));
                experiencebtn.setTextColor(getResources().getColor(R.color.blue));
                reviewbtn.setTextColor(getResources().getColor(R.color.grey));
            }
        });

        reviewbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                personalinfo.setVisibility(View.GONE);
                experience.setVisibility(View.GONE);
                review.setVisibility(View.VISIBLE);
                personalinfobtn.setTextColor(getResources().getColor(R.color.grey));
                experiencebtn.setTextColor(getResources().getColor(R.color.grey));
                reviewbtn.setTextColor(getResources().getColor(R.color.blue));
            }
        });

        return rootView;
    }
}