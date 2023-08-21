package com.example.openiums2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdvisorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdvisorFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AdvisorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AdvisorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AdvisorFragment newInstance(String param1, String param2) {
        AdvisorFragment fragment = new AdvisorFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_advisor, container, false);


        Spinner courseDropdown = rootView.findViewById(R.id.advisor_course_dropdown);
        EditText opinionEditText = rootView.findViewById(R.id.advisor_opinion_edit_text);
        Button submitButton = rootView.findViewById(R.id.advisor_submit_button);

        DatabaseReference coursesRef = FirebaseDatabase.getInstance().getReference("courses")
                .child(HelperClass.stringToPass); // Use the stored username to get data for that user


        DatabaseReference advisorsRef = FirebaseDatabase.getInstance().getReference("advisors").child(HelperClass.stringToPass);

        advisorsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                LinearLayout dataDisplayLayout = rootView.findViewById(R.id.data_display_layout);

                for (DataSnapshot courseSnapshot : dataSnapshot.getChildren()) {
                    String courseNumber = courseSnapshot.getKey(); // The course number is the key
                    String opinion = courseSnapshot.getValue(String.class); // The opinion is the value

                    // Create TextViews to display course number and opinion
                    TextView courseNumberTextView = new TextView(requireContext());
                    courseNumberTextView.setText("\n\nCourse Number: " + courseNumber);

                    TextView opinionTextView = new TextView(requireContext());
                    opinionTextView.setText("Message: " + opinion+"\n\n");

                    // Add the TextViews to the data display layout
                    dataDisplayLayout.addView(courseNumberTextView);
                    dataDisplayLayout.addView(opinionTextView);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the case where fetching data failed
                // You can leave this method empty if you don't need to handle cancellations
            }
        });


        coursesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> courseList = new ArrayList<>();

                for (DataSnapshot courseSnapshot : dataSnapshot.getChildren()) {
                    String courseNumber = courseSnapshot.child("courseNumber").getValue(String.class);

                    // Debug log to check course number and key
                    if (courseNumber != null) {
                        courseList.add(courseNumber);
                    } else {
                        System.out.println("courseNumber is null for document: " + courseSnapshot.getKey());
                    }
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, courseList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                courseDropdown.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the case where fetching data failed
            }
        });

        submitButton.setOnClickListener(view -> {
            String selectedCourse = courseDropdown.getSelectedItem().toString();
            String opinion = opinionEditText.getText().toString();

            if (!selectedCourse.isEmpty() && !opinion.isEmpty()) {
                saveEvaluation(selectedCourse, opinion);

                // Clear input fields and show a toast message
                opinionEditText.getText().clear();
                Toast.makeText(getActivity(), "Your message for " + selectedCourse + " is submitted", Toast.LENGTH_SHORT).show();


                // Refresh the fragment (You can reload the current fragment)
                FragmentTransaction ft = getParentFragmentManager().beginTransaction();
                ft.detach(this).attach(this).commit();
            } else {
                // Handle the case where input is empty
                Toast.makeText(getActivity(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }

    private void saveEvaluation(String selectedCourse, String opinion) {
        // Get the current user's ID (replace "user1" with the actual user's ID)
        String userId = HelperClass.stringToPass;

        // Get a reference to the "teachers" node and the specific course number node
        DatabaseReference teachersRef = FirebaseDatabase.getInstance().getReference("advisors");
        DatabaseReference courseRef = teachersRef.child(userId).child(selectedCourse);

        // Save the evaluation opinion under the course number node
        courseRef.setValue(opinion)
                .addOnSuccessListener(aVoid -> {
                    // Successful write to Firebase
                    // Here you might want to show a success message to the user
                })
                .addOnFailureListener(e -> {
                    // Failed to write to Firebase
                    // Here you might want to show an error message to the user
                });
    }
}