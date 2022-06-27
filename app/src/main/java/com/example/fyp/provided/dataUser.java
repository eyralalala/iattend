package com.example.fyp.provided;

public class dataUser {
    private String id;
    private String Coursecode;
    private String Coursename;
    private String Examtype;
    private long Examdate;

    public dataUser() {
    }

    public dataUser(String id, String coursecode, String coursename, String examtype, long examdate) {
        this.id = id;
        Coursecode = coursecode;
        Coursename = coursename;
        Examtype = examtype;
        Examdate = examdate;
    }

    public dataUser(String coursecode, String toString, String coursename, long time) {
    }

    public String getId() {
        return id;
    }

    public String getCoursecode() {
        return Coursecode;
    }

    public String getCoursename() {
        return Coursename;
    }

    public String getExamtype() {
        return Examtype;
    }

    public long getExamdate() {
        return Examdate;
    }
}
