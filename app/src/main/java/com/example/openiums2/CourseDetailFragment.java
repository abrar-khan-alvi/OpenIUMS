package com.example.openiums2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CourseDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CourseDetailFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CourseDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CourseDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CourseDetailFragment newInstance(String param1, String param2) {
        CourseDetailFragment fragment = new CourseDetailFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_course_detail, container, false);

        Bundle args = getArguments();
        if (args != null) {
            String courseNumber = args.getString("courseNumber");
            String courseTitle = args.getString("courseTitle");
            String hoursPerWeek = args.getString("hoursPerWeek");
            String credits = args.getString("credits");
            String prerequisites = args.getString("prerequisites");

            // Display the passed data in your layout's TextViews
            TextView courseNumberTextView = rootView.findViewById(R.id.courseNumberTextView);
            TextView courseTitleTextView = rootView.findViewById(R.id.courseTitleTextView);
            TextView hoursPerWeekTextView = rootView.findViewById(R.id.hoursPerWeekTextView);
            TextView creditsTextView = rootView.findViewById(R.id.creditsTextView);
            TextView prerequisitesTextView = rootView.findViewById(R.id.prerequisitesTextView);

            courseNumberTextView.setText(courseNumber);
            courseTitleTextView.setText(courseTitle);
            hoursPerWeekTextView.setText(hoursPerWeek);
            creditsTextView.setText(credits);
            prerequisitesTextView.setText(prerequisites);

            Button addToFirebaseButton = rootView.findViewById(R.id.addToFirebaseButton);
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("courses");
            addToFirebaseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Course course = new Course(courseNumber,courseTitle,hoursPerWeek,credits,prerequisites); // You might want to store the course title only

                    // Push the course data to Firebase Database under the user's node
                    databaseReference.child(HelperClass.stringToPass).child(courseNumber).setValue(course)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        // Show a success message
                                        Toast.makeText(getActivity(), "Course added successfully", Toast.LENGTH_SHORT).show();
                                        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                                        fragmentManager.popBackStack();
                                    } else {
                                        // Show an error message
                                        Toast.makeText(getActivity(), "Failed to add course", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            });
        }

        return rootView;
    }
}