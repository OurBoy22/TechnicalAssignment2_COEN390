package com.example.a40213391_assignment_2;

public class ProfileModel {
  public int profileID;
  public String name;
  public String surname;
  public float gpa;
  public int creationDay;
  public int creationMonth;
  public int creationYear;

    //null cosntructor for this class
    public ProfileModel() {
        //set attributes to null
        this.profileID = -1;
        this.name = null;
        this.surname = null;
        this.gpa = -1;
        this.creationDay = -1;
        this.creationMonth = -1;
        this.creationYear = -1;
    }
    public ProfileModel(int profileID, String name, String surname, float gpa, int creationDay, int creationMonth, int creationYear) {
        this.profileID = profileID;
        this.name = name;
        this.surname = surname;
        this.gpa = gpa;
        this.creationDay = creationDay;
        this.creationMonth = creationMonth;
        this.creationYear = creationYear;
    }

    //constructor that takes in a date string and converts it to the right format
    public ProfileModel(int profileID, String name, String surname, float gpa, String date) {
        this.profileID = profileID;
        this.name = name;
        this.surname = surname;
        this.gpa = gpa;
        this.creationDay = Integer.parseInt(date.substring(8,10));
        this.creationMonth = Integer.parseInt(date.substring(5,7));
        this.creationYear = Integer.parseInt(date.substring(0,4));
    }

    //method that returns all the attributes into a string for printing
    public String toString(){
        return "Profile ID: " + Integer.toString(this.profileID) + "\nName: " + this.name + "\nSurname: " + this.surname + "\nGPA: " + Float.toString(this.gpa) + "\nCreation Date: " + Integer.toString(this.creationDay) + "/" + Integer.toString(this.creationMonth) + "/" + Integer.toString(this.creationYear);
    }




}
