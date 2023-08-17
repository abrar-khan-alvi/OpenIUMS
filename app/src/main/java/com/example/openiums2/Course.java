package com.example.openiums2;

public class Course {
    private String courseNumber;
    private String courseTitle;
    private String hoursPerWeek;
    private String credits;
    private String prerequisites;

    public Course() {
        // Default constructor required for Firebase's DataSnapshot.getValue()
    }

    public Course(String courseNumber, String courseTitle, String hoursPerWeek, String credits, String prerequisites) {
        this.courseNumber = courseNumber;
        this.courseTitle = courseTitle;
        this.hoursPerWeek = hoursPerWeek;
        this.credits = credits;
        this.prerequisites = prerequisites;
    }

    public String getCourseNumber() {
        return courseNumber;
    }

    public String getCourseTitle() {
        return courseTitle;
    }
    public String getCredits() {
        return courseNumber;
    }

    public String getPrerequisites() {
        return courseTitle;
    }
    public String getHoursPerWeek() {
        return courseNumber;
    }

}
