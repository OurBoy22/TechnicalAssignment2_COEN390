package com.example.a40213391_assignment_2;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String PROFILE_TABLE = "profile_table";
    public static final String ACCESS_TABLE = "access_table";
    //create consts based on on column names:
    public static final String PROFILE_ID = "profile_id";
    public static final String NAME = "name";
    public static final String SURNAME = "surname";
    public static final String GPA = "GPA";
    public static final String CREATION_DATE = "creation_date";
    //create consts for access_table based on column names:
    public static final String ACCESS_TYPE = "access_type";
    public static final String TIME_STAMP = "time_stamp";
    public static final String ACCESS_ID = "access_id";

    public DataBaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
//        onCreate(this.getWritableDatabase());
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS profile_table(profile_id INTEGER PRIMARY KEY, name text, surname text, GPA real, creation_date date)");
        db.execSQL("CREATE TABLE IF NOT EXISTS access_table(access_id INTEGER PRIMARY KEY AUTOINCREMENT, profile_id int, access_type text, time_stamp text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public String createDate(int day, int month, int year){
        String out = "";
        out = out + Integer.toString(year)+ "-";
        //pad out month with 0 if needed
        if (month < 10){
            out = out + "0";
        }
        out = out + Integer.toString(month)+ "-";
        //pad out day with 0 if needed
        if (day < 10){
            out = out + "0";
        }
        out = out + Integer.toString(day);
        return out;
    }
    public String createTime(int hour, int minute, int second){
        String out = "";
        //pad out hour with 0 if needed
        if (hour < 10){
            out = out + "0";
        }
        out = out + Integer.toString(hour)+ ":";
        //pad out minute with 0 if needed
        if (minute < 10){
            out = out + "0";
        }
        out = out + Integer.toString(minute)+ ":";
        //pad out second with 0 if needed
        if (second < 10){
            out = out + "0";
        }
        out = out + Integer.toString(second);
        return out;
    }

    public String createTimestamp(int day, int month, int year, int hour, int minute, int second){
        String out = "";
        out = out + Integer.toString(year)+ "-";
        //pad out month with 0 if needed
        if (month < 10){
            out = out + "0";
        }
        out = out + Integer.toString(month)+ "-";
        //pad out day with 0 if needed
        if (day < 10){
            out = out + "0";
        }
        out = out + Integer.toString(day)+ " ";
        //pad out hour with 0 if needed
        if (hour < 10){
            out = out + "0";
        }
        out = out + Integer.toString(hour)+ ":";
        //pad out minute with 0 if needed
        if (minute < 10){
            out = out + "0";
        }
        out = out + Integer.toString(minute)+ ":";
        //pad out second with 0 if needed
        if (second < 10){
            out = out + "0";
        }
        out = out + Integer.toString(second);
        return out;
    }

    public int getProfilesCount(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM profile_table";
        Cursor cursor = db.rawQuery(query, null);
        int count = 0;
        while (cursor.moveToNext()){
            count++;
        }
        return count;
    }

    public int getAccessCount(int profileID){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM access_table WHERE profile_id = " + profileID;
        Cursor cursor = db.rawQuery(query, null);
        int count = 0;
        while (cursor.moveToNext()){
            count++;
        }
        return count;
    }


    public List<ProfileModel> getAllProfiles(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM profile_table";
        Cursor cursor = db.rawQuery(query, null);
        List<ProfileModel> arr = new ArrayList<ProfileModel>();
        while (cursor.moveToNext() != false){
            @SuppressLint("Range") int profileID = cursor.getInt(cursor.getColumnIndex(PROFILE_ID));
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(NAME));
            @SuppressLint("Range") String surname = cursor.getString(cursor.getColumnIndex(SURNAME));
            @SuppressLint("Range") float gpa = cursor.getFloat(cursor.getColumnIndex(GPA));
            @SuppressLint("Range") String creationDate = cursor.getString(cursor.getColumnIndex(CREATION_DATE));
            ProfileModel profile = new ProfileModel(profileID, name, surname, gpa, creationDate);
            arr.add(profile);
            Log.d("Printing from Method call", profile.toString());
        }
        return arr;
    }
    public List<ProfileModel> getAllProfilesNameAsc(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM profile_table ORDER BY surname ASC";
        Cursor cursor = db.rawQuery(query, null);
        List<ProfileModel> arr = new ArrayList<ProfileModel>();
        while (cursor.moveToNext() != false){
            @SuppressLint("Range") int profileID = cursor.getInt(cursor.getColumnIndex(PROFILE_ID));
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(NAME));
            @SuppressLint("Range") String surname = cursor.getString(cursor.getColumnIndex(SURNAME));
            @SuppressLint("Range") float gpa = cursor.getFloat(cursor.getColumnIndex(GPA));
            @SuppressLint("Range") String creationDate = cursor.getString(cursor.getColumnIndex(CREATION_DATE));
            ProfileModel profile = new ProfileModel(profileID, name, surname, gpa, creationDate);
            arr.add(profile);
            Log.d("Printing from Method call", profile.toString());
        }
        return arr;
    }
    public List<ProfileModel> getAllProfilesIDAsc(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM profile_table ORDER BY profile_id ASC";
        Cursor cursor = db.rawQuery(query, null);
        List<ProfileModel> arr = new ArrayList<ProfileModel>();
        while (cursor.moveToNext() != false){
            @SuppressLint("Range") int profileID = cursor.getInt(cursor.getColumnIndex(PROFILE_ID));
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(NAME));
            @SuppressLint("Range") String surname = cursor.getString(cursor.getColumnIndex(SURNAME));
            @SuppressLint("Range") float gpa = cursor.getFloat(cursor.getColumnIndex(GPA));
            @SuppressLint("Range") String creationDate = cursor.getString(cursor.getColumnIndex(CREATION_DATE));
            ProfileModel profile = new ProfileModel(profileID, name, surname, gpa, creationDate);
            arr.add(profile);
            Log.d("Printing from Method call", profile.toString());
        }
        return arr;
    }


    public ProfileModel getProfile(int profileID){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM profile_table WHERE profile_id = " + profileID;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst() != false){
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(NAME));
            @SuppressLint("Range") String surname = cursor.getString(cursor.getColumnIndex(SURNAME));
            @SuppressLint("Range") float gpa = cursor.getFloat(cursor.getColumnIndex(GPA));
            @SuppressLint("Range") String creationDate = cursor.getString(cursor.getColumnIndex(CREATION_DATE));
            return new ProfileModel(profileID, name, surname, gpa, creationDate);
        }
        else{
            return null;
        }
    }

    public AccessModel getAccess(int profileID){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM access_table WHERE profile_id = " + profileID;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst() != false){
            @SuppressLint("Range") int accessID = cursor.getInt(cursor.getColumnIndex(ACCESS_ID));
            @SuppressLint("Range") String accessType = cursor.getString(cursor.getColumnIndex(ACCESS_TYPE));
            @SuppressLint("Range") String timeStamp = cursor.getString(cursor.getColumnIndex(TIME_STAMP));
            return new AccessModel(accessID, profileID, accessType, timeStamp);
        }
        else{
            return null;
        }
    }
    public List<AccessModel> getAllAccesses(int profileID){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM access_table WHERE profile_id = " + profileID;
        Cursor cursor = db.rawQuery(query, null);
        List<AccessModel> arr = new ArrayList<AccessModel>();
        while (cursor.moveToNext() != false){
            @SuppressLint("Range") int accessID = cursor.getInt(cursor.getColumnIndex(ACCESS_ID));
            @SuppressLint("Range") String accessType = cursor.getString(cursor.getColumnIndex(ACCESS_TYPE));
            @SuppressLint("Range") String timeStamp = cursor.getString(cursor.getColumnIndex(TIME_STAMP));
            AccessModel access = new AccessModel(accessID, profileID, accessType, timeStamp);
            arr.add(access);
            Log.d("Printing from Method call", access.toString());
        }
        return arr;
    }

    public boolean checkExistingIDProfile(int profileID){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM profile_table WHERE profile_id = " + profileID;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst() != false){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean deleteProfile(int profileID){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM profile_table WHERE profile_id = " + profileID;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst() != false){
            return true;
        }
        else{
            return false;
        }
    }
    public boolean addProfile(ProfileModel profileModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("name", profileModel.name);
        cv.put("surname", profileModel.surname);
        cv.put("profile_id", profileModel.profileID);
        cv.put("GPA", profileModel.gpa);
        cv.put("creation_date", createDate(profileModel.creationDay, profileModel.creationMonth, profileModel.creationYear));



        if (db.insert(PROFILE_TABLE, null, cv) == -1){
            //create toast to say that it failed

            return false;
        }
        else{
            addAccess(new AccessModel(profileModel.profileID, AccessModel.CREATED));
            return true;
        }
    }

    public void dropDatabases(){
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("DROP TABLE IF EXISTS profile_table");
        db.execSQL("DROP TABLE IF EXISTS access_table");
    }
    public boolean addAccess(AccessModel accessModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int minute = Calendar.getInstance().get(Calendar.MINUTE);
        int second = Calendar.getInstance().get(Calendar.SECOND);
        String timestamp = createTimestamp(day, month, year, hour, minute, second);

        cv.put("profile_id", accessModel.profileID);
        cv.put("access_type", accessModel.accessType);
        cv.put("time_stamp", timestamp);

        if (db.insert(ACCESS_TABLE, null, cv) == -1){
            return false;
        }
        else{
            return true;
        }

    }
}
