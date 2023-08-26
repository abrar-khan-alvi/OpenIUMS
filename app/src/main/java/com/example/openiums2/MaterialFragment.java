package com.example.openiums2;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

public class MaterialFragment extends Fragment implements MatAdapter.OnItemClickListener {

    private List<String> courseNumbers;
    private List<String> course_pdf;
    private Context context;
    public MaterialFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_mat, container, false);
        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView2);

        // Initialize courseNumbers and coursepdf using string arrays
        courseNumbers = Arrays.asList(getResources().getStringArray(R.array.course_code));
        course_pdf = Arrays.asList(getResources().getStringArray(R.array.course_pdf));

        MatAdapter adapter = new MatAdapter(courseNumbers, course_pdf, this);
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return rootView;
    }

    public void downloadFile(String downloadUrl, String fileName) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(downloadUrl));
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DOWNLOADS,
                fileName);

        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        downloadManager.enqueue(request);
    }

    @Override
    public void onItemClick(int position) {
        String selectedCourseNumber = courseNumbers.get(position);
        String selectedCourseTitle = course_pdf.get(position);


        // Create a new instance of the CourseDetailFragment and set the arguments
        Bundle args = new Bundle();
        args.putString("courseNumber", selectedCourseNumber);//https://aust.edu/lab_manuals/CSE/CSE1102-Lab%20Manual.pdf
        args.putString("courseTitle", selectedCourseTitle);//https://aust.edu/lab_manuals/CSE/CSE1108-Lab%20Manual.pdf


        CourseDetailFragment courseDetailFragment = new CourseDetailFragment();
        courseDetailFragment.setArguments(args);
        String str1 = "https://aust.edu/lab_manuals/CSE/";
        String str3 = "-Lab%20Manual.pdf";
        String str2 = selectedCourseTitle;
        String modifiedString = str2.substring(0, str2.length() - 4);
        str2 = modifiedString;

        String link = str1+str2+str3;
        System.out.println(link);


        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
        startActivity(intent);

    }
}