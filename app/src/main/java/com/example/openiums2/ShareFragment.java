package com.example.openiums2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShareFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShareFragment extends Fragment implements CourseAdapter.OnItemClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private List<String> courseNumbers ;
    private List<String> courseTitles;
    private List<String> hoursPerWeek;
    private List<String> credits;
    private List<String> prerequisites;

    public ShareFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShareFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShareFragment newInstance(String param1, String param2) {
        ShareFragment fragment = new ShareFragment();
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
    public void onItemClick(int position) {
        // Get the selected course data
        String selectedCourseNumber = courseNumbers.get(position);
        String selectedCourseTitle = courseTitles.get(position);
        String selectedHoursPerWeek = hoursPerWeek.get(position);
        String selectedCredits = credits.get(position);
        String selectedPrerequisites = prerequisites.get(position);

        // Create a new instance of the CourseDetailFragment and set the arguments
        Bundle args = new Bundle();
        args.putString("courseNumber", selectedCourseNumber);
        args.putString("courseTitle", selectedCourseTitle);
        args.putString("hoursPerWeek", selectedHoursPerWeek);
        args.putString("credits", selectedCredits);
        args.putString("prerequisites", selectedPrerequisites);

        CourseDetailFragment courseDetailFragment = new CourseDetailFragment();
        courseDetailFragment.setArguments(args);

        // Perform the fragment transaction to replace the current fragment with the CourseDetailFragment
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, courseDetailFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_share, container, false);
        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView);

        courseNumbers = Arrays.asList(getResources().getStringArray(R.array.course_numbers));
        courseTitles = Arrays.asList(getResources().getStringArray(R.array.course_titles));
        hoursPerWeek = Arrays.asList(getResources().getStringArray(R.array.hours_per_week));
        credits = Arrays.asList(getResources().getStringArray(R.array.credits));
        prerequisites = Arrays.asList(getResources().getStringArray(R.array.prerequisites));

        CourseAdapter adapter = new CourseAdapter(courseNumbers, courseTitles, hoursPerWeek, credits, prerequisites, this);

        recyclerView.setAdapter(adapter);

// Set layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return rootView;

    }
}