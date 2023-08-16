package com.example.openiums2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {
    private List<String> courseNumbers;
    private List<String> courseTitles;
    private List<String> hoursPerWeekList;
    private List<String> creditsList;
    private List<String> prerequisitesList;

    public CourseAdapter(List<String> courseNumbers, List<String> courseTitles,
                         List<String> hoursPerWeekList, List<String> creditsList,
                         List<String> prerequisitesList) {
        this.courseNumbers = courseNumbers;
        this.courseTitles = courseTitles;
        this.hoursPerWeekList = hoursPerWeekList;
        this.creditsList = creditsList;
        this.prerequisitesList = prerequisitesList;
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_course, parent, false);
        return new CourseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        if (position == 0) {
            // Display header content in bold and larger size
            holder.courseNumberTextView.setTextAppearance(R.style.HeaderTextStyle);
            holder.courseNumberTextView.setText("Course no.");

            holder.courseTitleTextView.setTextAppearance(R.style.HeaderTextStyle);
            holder.courseTitleTextView.setText("Course Title");

            holder.hoursPerWeekTextView.setTextAppearance(R.style.HeaderTextStyle);
            holder.hoursPerWeekTextView.setText("Hours/Week");

            holder.creditsTextView.setTextAppearance(R.style.HeaderTextStyle);
            holder.creditsTextView.setText("Credits");

            holder.prerequisiteTextView.setTextAppearance(R.style.HeaderTextStyle);
            holder.prerequisiteTextView.setText("Prerequisite");
        } else {
            // Display course data
            String courseNumber = courseNumbers.get(position - 1);
            String courseTitle = courseTitles.get(position - 1);
            String hoursPerWeek = hoursPerWeekList.get(position - 1);
            String credits = creditsList.get(position - 1);
            String prerequisite = prerequisitesList.get(position - 1);

            holder.courseNumberTextView.setTextAppearance(R.style.DataTextStyle);
            holder.courseNumberTextView.setText(courseNumber);

            holder.courseTitleTextView.setTextAppearance(R.style.DataTextStyle);
            holder.courseTitleTextView.setText(courseTitle);

            holder.hoursPerWeekTextView.setTextAppearance(R.style.DataTextStyle);
            holder.hoursPerWeekTextView.setText(hoursPerWeek);

            holder.creditsTextView.setTextAppearance(R.style.DataTextStyle);
            holder.creditsTextView.setText(credits);

            holder.prerequisiteTextView.setTextAppearance(R.style.DataTextStyle);
            holder.prerequisiteTextView.setText(prerequisite);
        }
    }

    @Override
    public int getItemCount() {
        // +1 to account for the header row
        return courseNumbers.size() + 1;
    }

    public static class CourseViewHolder extends RecyclerView.ViewHolder {
        TextView courseNumberTextView;
        TextView courseTitleTextView;
        TextView hoursPerWeekTextView;
        TextView creditsTextView;
        TextView prerequisiteTextView;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            courseNumberTextView = itemView.findViewById(R.id.courseNumberTextView);
            courseTitleTextView = itemView.findViewById(R.id.courseTitleTextView);
            hoursPerWeekTextView = itemView.findViewById(R.id.hoursPerWeekTextView);
            creditsTextView = itemView.findViewById(R.id.creditsTextView);
            prerequisiteTextView = itemView.findViewById(R.id.prerequisiteTextView);
        }
    }
}


