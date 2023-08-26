package com.example.openiums2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RoutineFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RoutineFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private int totalCredits = 0;

    public RoutineFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RoutineFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RoutineFragment newInstance(String param1, String param2) {
        RoutineFragment fragment = new RoutineFragment();
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
        View view = inflater.inflate(R.layout.fragment_routine, container, false);

        DatabaseReference userCoursesRef = FirebaseDatabase.getInstance().getReference("courses")
                .child(HelperClass.stringToPass);

        userCoursesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Clear the existing courseTextView content
                TextView courseTextView = view.findViewById(R.id.courseTextView);
                courseTextView.setText("");
                totalCredits = 0;
                System.out.println(dataSnapshot.toString());
                for (DataSnapshot courseSnapshot : dataSnapshot.getChildren()) {
                    Course course = courseSnapshot.getValue(Course.class);

                    if (course != null) {
                        String courseInfo = "Course Number: " + course.getCourseNumber() + "\n"
                               // + "Course Title: " + course.getCourseTitle() + "\n"
                              //  + "Hours Per Week: " + course.getHoursPerWeek() + "\n"
                               + "Credits: " + course.getCredits() + "\n\n";
                               // + "Prerequisites: " + course.getPrerequisites() + "\n\n";

                        courseTextView.append(courseInfo);
                        try {
                            int courseCredits = Integer.parseInt(course.getCredits());
                            totalCredits += courseCredits;
                            int paymentAmount = totalCredits * 5000;

                            // Display the payment amount
                            //TextView paymentTextView = view.findViewById(R.id.paymentTextView);
                            //paymentTextView.setText("Payment Amount: BDT " + paymentAmount);

                            DatabaseReference paymentsRef = FirebaseDatabase.getInstance().getReference("payments")
                                    .child(HelperClass.stringToPass);

                            Map<String, Object> paymentData = new HashMap<>();
                            paymentData.put("duePayments", paymentAmount);
                            paymentData.put("totalCredits", totalCredits);

                            paymentsRef.setValue(paymentData);
                        } catch (NumberFormatException e) {
                            // Handle the case where credit value is not a valid integer
                            // You might want to log or display an error message
                        }
                       // System.out.println("Course Number: " + course.getCourseNumber());
                        //System.out.println("Course Title: " + course.getCourseTitle());
                        //System.out.println("Hours Per Week: " + course.getHoursPerWeek());
                        //System.out.println("Credits: " + course.getCredits());
                       // System.out.println("Prerequisites: " + course.getPrerequisites());
                    }
                }
                TextView totalCreditsTextView = view.findViewById(R.id.totalCreditsTextView);
                totalCreditsTextView.setText("Total Credits: " + totalCredits);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the error
                Toast.makeText(getActivity(), "Failed to retrieve courses", Toast.LENGTH_SHORT).show();

                // Update the total credits TextView on error
                TextView totalCreditsTextView = view.findViewById(R.id.totalCreditsTextView);
                totalCreditsTextView.setText("Total Credits: 0");
            }
        });

        return view;
    }

}