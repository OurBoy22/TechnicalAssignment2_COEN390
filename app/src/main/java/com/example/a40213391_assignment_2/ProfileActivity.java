package com.example.a40213391_assignment_2;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ProfileActivity extends MainActivity {
    TextView textViewProfileHeader;
    TextView textViewProfileInfo;
    TextView textViewAccessHistory;
    ListView eventListAccessHistory;

    DataBaseHelper db;
    ProfileModel profile;
    AccessModel access;
    List<AccessModel> currentOrder;
    ArrayAdapter arrayAdapter;
    FloatingActionButton floatingActionButtonDelete;
    int profileID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        setSupportActionBar(findViewById(R.id.myToolbar));
        ActionBar settingsActionBar = getSupportActionBar();
        settingsActionBar.setTitle("Profile Activity"); // set the title of the ActionBar

        //make back arrow clickable and visible (???)
        settingsActionBar.setDisplayHomeAsUpEnabled(true);
        settingsActionBar.setDisplayShowHomeEnabled(true);

        textViewProfileHeader = findViewById(R.id.textViewProfileHeader);
        textViewProfileInfo = findViewById(R.id.textViewProfileInfo);
        textViewAccessHistory = findViewById(R.id.textViewAccessHistory);
        eventListAccessHistory = findViewById(R.id.eventListAccessHistory);
        floatingActionButtonDelete = findViewById(R.id.floatingActionButtonDelete);


        profileID = getIntent().getIntExtra("ProfileModelID", 0);


        db = new DataBaseHelper(this, "database.db", null, 1);
        db.addAccess(new AccessModel(profileID, AccessModel.OPENED));
        profile = db.getProfile(profileID);
        access = db.getAccess(profileID);
        currentOrder = db.getAllAccesses(profileID);

        textViewProfileInfo.setText("Surname: " + profile.surname + "\nName: " + profile.name + "\nID: " + profile.profileID + "\nGPA: " + profile.gpa + "\nProfile Created: " + db.createDate(profile.creationYear, profile.creationMonth, profile.creationDay) + " @ " + db.createTime(access.hour, access.minute, access.second));
        updateCounts();

        floatingActionButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.deleteProfile(profileID);
                db.addAccess(new AccessModel(profileID, AccessModel.DELETED));
                finish();
            }
        });


    }

    void updateCounts(){
        //update the counts of students and accesses
        int studentCount = db.getAccessCount(profileID);
        currentOrder = db.getAllAccesses(profileID);
        String[] info = new String[currentOrder.size()];

        for (int i = 0; i < currentOrder.size(); i++){
            info[i] = (db.createDate(currentOrder.get(i).year, currentOrder.get(i).month, currentOrder.get(i).day) + " @ " + db.createTime(currentOrder.get(i).hour, currentOrder.get(i).minute, currentOrder.get(i).second) + " " + currentOrder.get(i).accessType);
        }

        arrayAdapter = new ArrayAdapter(this, android.R.layout. simple_list_item_1, info);
        eventListAccessHistory.setAdapter(arrayAdapter);
    }

    @Override
    protected void onResume(){
        super.onResume();
        db = new DataBaseHelper(this, "database.db", null, 1);
        profile = db.getProfile(profileID);
        access = db.getAccess(profileID);
    }

    @Override
    protected void onPause(){
        super.onPause();
        db.addAccess(new AccessModel(profileID, AccessModel.CLOSED));
    }


}