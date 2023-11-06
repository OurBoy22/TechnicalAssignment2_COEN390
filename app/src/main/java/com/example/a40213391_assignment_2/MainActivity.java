package com.example.a40213391_assignment_2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //instantiate all elements in activity_main.xml
    TextView textViewTotalCounts;
    FloatingActionButton floatingActionButtonAddStudent;
    DataBaseHelper db;
    ListView eventListStudents;
    ArrayAdapter arrayAdapter;
    List<ProfileModel> currentOrder;
    boolean sortByName = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get and setup the action bar
        setSupportActionBar(findViewById(R.id.myToolbar));
        ActionBar settingsActionBar = getSupportActionBar();
        settingsActionBar.setTitle("Main Activity"); // set the title of the ActionBar
        settingsActionBar.setDisplayShowHomeEnabled(true);

        // instantiace Database Helper
        db = new DataBaseHelper(this, "database.db", null, 1);

        //declare all of the elements that are used
        textViewTotalCounts = findViewById(R.id.textViewTotalCounts);
        eventListStudents = findViewById(R.id.eventListStudents);
        floatingActionButtonAddStudent = findViewById(R.id.floatingActionButtonAddStudent);

        //Add student button handler
        floatingActionButtonAddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogBoxAddStudent dialogBox = new DialogBoxAddStudent(); // instantiate dialog box from DialogBoxAddStudent.java
                dialogBox.show(getSupportFragmentManager(), "example dialog"); // show the dialog box
                updateCounts(); // when the dialog box is closed, call updateCounts() to update the listView
            }
        });
        eventListStudents.setOnItemClickListener(new AdapterView.OnItemClickListener() { //listener for each element in listView
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("Element clicked ID", Long.toString(arrayAdapter.getItemId(i))); // log ID of element clicked
                Intent profileActivity = new Intent(view.getContext(), ProfileActivity.class); // create intent to start ProfileActivity
                profileActivity.putExtra("ProfileModelID", currentOrder.get((int) arrayAdapter.getItemId(i)).profileID); // pass the profile ID of the element clicked based on index
                startActivity(profileActivity); // start intent to start ProfileActivity
            }
        });
    }

    void updateCounts(){
        if (sortByName){ // if the display mode is to sort by names
            //update the counts of students and accesses
            int studentCount = db.getProfilesCount(); // get current count of profiles
            currentOrder = db.getAllProfilesNameAsc(); // get all profiles sorted by name and store into array
            String[] names = new String[currentOrder.size()]; // create names [] array to store things to display in listView
            int numPrefix = 1; // variable to keep track of the 1. 2. 3. etc. prefix

            for (int i = 0; i < currentOrder.size(); i++){//iterate through each profile
                Log.d("Database", currentOrder.get(i).name.toString());
                names[i] = numPrefix + ". " +  currentOrder.get(i).surname.toString() + ", " + currentOrder.get(i).name.toString(); // add to names[] the surname and name of the profile as well as the prefix
                numPrefix++; // increment prefix
            }

            //set the adapter to the listView
            arrayAdapter = new ArrayAdapter(this, android.R.layout. simple_list_item_1, names);
            eventListStudents.setAdapter(arrayAdapter);
            //set the textview to display the number of profiles
            textViewTotalCounts.setText(Integer.toString(studentCount) + " Profiles, sorted by name");
        }
        else{ // if the display mode is to sort by ID
            //update the counts of students and accesses
            int studentCount = db.getProfilesCount(); // get current count of profiles
            currentOrder = db.getAllProfilesIDAsc(); // get all profiles sorted by ID and store into array
            String[] names = new String[currentOrder.size()]; // create names [] array to store things to display in listView
            int numPrefix = 1; // variable to keep track of the 1. 2. 3. etc. prefix
            for (int i = 0; i < currentOrder.size(); i++){ //iterate through each profile
                Log.d("Database", currentOrder.get(i).name.toString());
                names[i] = numPrefix + ". " +  currentOrder.get(i).profileID; // add to names[] the ID of the profile as well as the prefix
                numPrefix++; // increment prefix
            }
            //set the adapter to the listView
            arrayAdapter = new ArrayAdapter(this, android.R.layout. simple_list_item_1, names);
            eventListStudents.setAdapter(arrayAdapter);
            //set the textview to display the number of profiles
            textViewTotalCounts.setText(Integer.toString(studentCount) + " Profiles, sorted by ID");
        }

        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){ // to inflate the menu options for the "..." on the ActionBar
        getMenuInflater().inflate(R.menu.data, menu); // import menu from data.xml
        return true;
    }

    //To handle the inflatable menu options for the "..." on the ActionBar
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == R.id.toggleEvents){ // if toggle events buttons is clicked
            if (sortByName){
                sortByName = false; // set the display mode to sort by ID
            }
            else{
                sortByName = true; // set the display mode to sort by name
            }
            updateCounts(); // call updateCounts() to update the listView
        }
        else if (item.getItemId() == android.R.id.home){ // if back button is pressed
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume(){

        super.onResume();
        updateCounts();
    }

}
