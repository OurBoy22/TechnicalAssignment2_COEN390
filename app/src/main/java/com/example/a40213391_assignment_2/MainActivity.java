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

        //make back arrow clickable and visible (???)
//        settingsActionBar.setDisplayHomeAsUpEnabled(true);
        settingsActionBar.setDisplayShowHomeEnabled(true);

        db = new DataBaseHelper(this, "database.db", null, 1);
        textViewTotalCounts = findViewById(R.id.textViewTotalCounts);
        eventListStudents = findViewById(R.id.eventListStudents);
        floatingActionButtonAddStudent = findViewById(R.id.floatingActionButtonAddStudent);
        floatingActionButtonAddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogBoxAddStudent exampleDialog = new DialogBoxAddStudent();
                exampleDialog.show(getSupportFragmentManager(), "example dialog");
                updateCounts();
            }
        });
        eventListStudents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("Element clicked ID", Long.toString(arrayAdapter.getItemId(i)));
                Intent profileActivity = new Intent(view.getContext(), ProfileActivity.class);
                profileActivity.putExtra("ProfileModelID", currentOrder.get((int) arrayAdapter.getItemId(i)).profileID);
                startActivity(profileActivity);
            }
        });
    }

    void updateCounts(){
        if (sortByName){
            //update the counts of students and accesses
            int studentCount = db.getProfilesCount();
            currentOrder = db.getAllProfilesNameAsc();
            String[] names = new String[currentOrder.size()];
            int numPrefix = 1;
            for (int i = 0; i < currentOrder.size(); i++){
                Log.d("Database", currentOrder.get(i).name.toString());
                names[i] = numPrefix + ". " +  currentOrder.get(i).surname.toString() + ", " + currentOrder.get(i).name.toString();
                numPrefix++;
            }

            arrayAdapter = new ArrayAdapter(this, android.R.layout. simple_list_item_1, names);
            eventListStudents.setAdapter(arrayAdapter);
            textViewTotalCounts.setText(Integer.toString(studentCount) + " Profiles, sorted by name");
        }
        else{
            //update the counts of students and accesses
            int studentCount = db.getProfilesCount();
            currentOrder = db.getAllProfilesIDAsc();
            String[] names = new String[currentOrder.size()];
            int numPrefix = 1;
            for (int i = 0; i < currentOrder.size(); i++){
                Log.d("Database", currentOrder.get(i).name.toString());
                names[i] = numPrefix + ". " +  currentOrder.get(i).profileID;
                numPrefix++;
            }

            arrayAdapter = new ArrayAdapter(this, android.R.layout. simple_list_item_1, names);
            eventListStudents.setAdapter(arrayAdapter);
            textViewTotalCounts.setText(Integer.toString(studentCount) + " Profiles, sorted by ID");
        }

        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.data, menu);
        return true;
    }
    //To handle the inflatable menu options for the "..." on the ActionBar
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == R.id.toggleEvents){ // if toggle events buttons is clicked
            if (sortByName){
                sortByName = false;
            }
            else{
                sortByName = true;
            }
            updateCounts();
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
