package com.example.a40213391_assignment_2;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ProfileActivity extends MainActivity {
    //instantiate all elements in activity_main.xml
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

        //Declare toolbar at the top and name it accordingly
        setSupportActionBar(findViewById(R.id.myToolbar));
        ActionBar settingsActionBar = getSupportActionBar();
        settingsActionBar.setTitle("Profile Activity"); // set the title of the ActionBar
        settingsActionBar.setDisplayHomeAsUpEnabled(true); // set back arrow

        //declare all of the elements that are used
        textViewProfileHeader = findViewById(R.id.textViewProfileHeader);
        textViewProfileInfo = findViewById(R.id.textViewProfileInfo);
        textViewAccessHistory = findViewById(R.id.textViewAccessHistory);
        eventListAccessHistory = findViewById(R.id.eventListAccessHistory);
        floatingActionButtonDelete = findViewById(R.id.floatingActionButtonDelete);

        //retrieve information from the Intent that started this activity
        profileID = getIntent().getIntExtra("ProfileModelID", 0);

        //instantiate database and retrieve the profile and access information
        db = new DataBaseHelper(this, "database.db", null, 1);
        db.addAccess(new AccessModel(profileID, AccessModel.OPENED));
        profile = db.getProfile(profileID);
        access = db.getAccess(profileID);
        currentOrder = db.getAllAccesses(profileID); // retrieve all accesses of this profileID

        //set textview to display the profile information
        textViewProfileInfo.setText("Surname: " + profile.surname + "\nName: " + profile.name + "\nID: " + profile.profileID + "\nGPA: " + profile.gpa + "\nProfile Created: " + db.createDate(profile.creationYear, profile.creationMonth, profile.creationDay) + " @ " + db.createTime(access.hour, access.minute, access.second));
        updateCounts(); // call function to update the list of accesses

        floatingActionButtonDelete.setOnClickListener(new View.OnClickListener() { // Listener of delete button
            @Override
            public void onClick(View view) {
                db.deleteProfile(profileID); // call delete based on specific profile
                db.addAccess(new AccessModel(profileID, AccessModel.DELETED)); // add new access to the database with access type deleted
                finish(); // call the end of the activity, returns to main activity
            }
        });
    }

    void updateCounts(){
        //update the counts of students and accesses
        currentOrder = db.getAllAccesses(profileID); // retrieve all accesses of this profileID
        String[] info = new String[currentOrder.size()]; // declare array of strings for all the listView items

        for (int i = 0; i < currentOrder.size(); i++){ //add timestamp of each access of currentOrder array to the info array
            info[i] = (db.createDate(currentOrder.get(i).year, currentOrder.get(i).month, currentOrder.get(i).day) + " @ " + db.createTime(currentOrder.get(i).hour, currentOrder.get(i).minute, currentOrder.get(i).second) + " " + currentOrder.get(i).accessType);
        }
        //set the adapter of the listView to the info array
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

    //CHANGE THIS
    @Override
    protected void onPause(){ // whenever the activity is paused, add a new access to the database with access type closed
        super.onPause();
        db.addAccess(new AccessModel(profileID, AccessModel.CLOSED));
    }

    @Override // must be overriden since this class inherits main activity which has the "..." menu button and for this activity we don't need it
    public boolean onCreateOptionsMenu(Menu menu){
        return true;
    }


}