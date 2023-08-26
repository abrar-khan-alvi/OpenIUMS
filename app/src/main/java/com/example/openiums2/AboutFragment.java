package com.example.openiums2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AboutFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public AboutFragment() {
        // Required empty public constructor
    }

    public static AboutFragment newInstance(String param1, String param2) {
        AboutFragment fragment = new AboutFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_about, container, false);

        Spinner resultDropdown = rootView.findViewById(R.id.about_course_dropdown);
        TextView selectedValueTextView = rootView.findViewById(R.id.selected_value_text_view);

        DatabaseReference coursesRef = FirebaseDatabase.getInstance().getReference("results")
                .child(HelperClass.stringToPass);

        coursesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> semesterList = new ArrayList<>();

                for (DataSnapshot semesterSnapshot : dataSnapshot.getChildren()) {
                    String semesterYear = semesterSnapshot.getKey();
                    semesterList.add(semesterYear);
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, semesterList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                resultDropdown.setAdapter(adapter);

                resultDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                        String selectedSemester = adapterView.getItemAtPosition(position).toString();
                        Double gpaValue = dataSnapshot.child(selectedSemester).getValue(Double.class);

                        if (gpaValue != null) {
                            selectedValueTextView.setText(selectedSemester+" GPA is:  " + gpaValue);
                        } else {
                            selectedValueTextView.setText("Selected Value: N/A");
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                        selectedValueTextView.setText("Selected Value: N/A");
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the case where fetching data failed
            }
        });

        resultDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String selectedCourse = adapterView.getItemAtPosition(position).toString();
                selectedValueTextView.setText("Selected Value: " + selectedCourse);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Handle the case where nothing is selected
            }
        });

        return rootView;
    }
}
