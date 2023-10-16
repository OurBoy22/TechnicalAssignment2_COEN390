package com.example.a40213391_assignment_2;

public class AccessModel {
    //declare consts for the following strings: created, opened, closed, deleted
    public static final String CREATED = "created";
    public static final String OPENED = "opened";
    public static final String CLOSED = "closed";
    public static final String DELETED = "deleted";

    public int accessID;
    public int profileID;
    public String accessType;
    public int day;
    public int month;
    public int year;
    public int hour;
    public int minute;
    public int second;

    //constructor for this class:
    public AccessModel(int accessID, int profileID, String accessType, int day, int month, int year, int hour, int minute, int second) {
        this.accessID = accessID;
        this.profileID = profileID;
        this.accessType = accessType;
        this.day = day;
        this.month = month;
        this.year = year;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }
    //constructor that takes in a date string and converts it to the right format
    public AccessModel(int accessID, int profileID, String accessType, String timestamp) {
        this.accessID = accessID;
        this.profileID = profileID;
        this.accessType = accessType;
        this.year = Integer.parseInt(timestamp.substring(0,4));
        this.month = Integer.parseInt(timestamp.substring(5,7));
        this.day = Integer.parseInt(timestamp.substring(8,10));
        this.hour = Integer.parseInt(timestamp.substring(11,13));
        this.minute = Integer.parseInt(timestamp.substring(14,16));
        this.second = Integer.parseInt(timestamp.substring(17,19));
    }
    public AccessModel(int profileID, String accessType) {
        this.accessID = -1;
        this.profileID = profileID;
        this.accessType = accessType;
        this.year = -1;
        this.month = -1;
        this.day = -1;
        this.hour = -1;
        this.minute = -1;
        this.second = -1;
    }

    //method that returns all the attributes into a string for printing
    public String toString() {
        return "Access ID: " + Integer.toString(this.accessID) + "\nProfile ID: " + Integer.toString(this.profileID) + "\nAccess Type: " + this.accessType;
    }
}
