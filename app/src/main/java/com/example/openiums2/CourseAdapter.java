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
    private OnItemClickListener itemClickListener;

    public CourseAdapter(List<String> courseNumbers, List<String> courseTitles,
                         List<String> hoursPerWeekList, List<String> creditsList,
                         List<String> prerequisitesList, OnItemClickListener listener) {
        this.courseNumbers = courseNumbers;
        this.courseTitles = courseTitles;
        this.hoursPerWeekList = hoursPerWeekList;
        this.creditsList = creditsList;
        this.prerequisitesList = prerequisitesList;
        this.itemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
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
            // Display header content (if needed)
        } else {
            final int dataIndex = position - 1; // Adjust index for data lists

            // Display course data
            String courseNumber = courseNumbers.get(dataIndex);
            String courseTitle = courseTitles.get(dataIndex);
            String hoursPerWeek = hoursPerWeekList.get(dataIndex);
            String credits = creditsList.get(dataIndex);
            String prerequisite = prerequisitesList.get(dataIndex);

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

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (itemClickListener != null) {
                        itemClickListener.onItemClick(dataIndex);
                    }
                }
            });
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


