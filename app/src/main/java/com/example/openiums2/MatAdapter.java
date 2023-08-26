package com.example.openiums2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MatAdapter extends RecyclerView.Adapter<MatAdapter.MatViewHolder> {
    private List<String> courseNumbers;
    private List<String> course_pdf;

    private OnItemClickListener itemClickListener;

    public MatAdapter(List<String> courseNumbers, List<String> course_pdf,
                      OnItemClickListener listener) {
        this.courseNumbers = courseNumbers;
        this.course_pdf = course_pdf;
        this.itemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    @NonNull
    @Override
    public MatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_mat, parent, false);
        return new MatViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MatViewHolder holder, int position) {
        final int dataIndex = position; // No need to adjust index for data lists

        // Display course data
        String courseNumber = courseNumbers.get(dataIndex);
        String coursePdf = course_pdf.get(dataIndex);



        holder.courseNumberTextView.setTextAppearance(R.style.DataTextStyle);
        holder.courseNumberTextView.setText(courseNumber);



        holder.coursepdfTextView.setTextAppearance(R.style.DataTextStyle);
        holder.coursepdfTextView.setText(coursePdf);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(dataIndex);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return courseNumbers.size();
    }

    public static class MatViewHolder extends RecyclerView.ViewHolder {
        TextView courseNumberTextView;
        TextView coursepdfTextView;

        public MatViewHolder(@NonNull View itemView) {
            super(itemView);
            courseNumberTextView = itemView.findViewById(R.id.courseNumberTextView);
            coursepdfTextView = itemView.findViewById(R.id.courseTitleTextView);
        }
    }
}