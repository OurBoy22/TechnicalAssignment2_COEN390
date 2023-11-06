package com.example.a40213391_assignment_2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialogFragment;

import java.util.Calendar;

public class DialogBoxAddStudent extends AppCompatDialogFragment {
    //declare all of the elements that are used
    EditText editTextSurname;
    EditText editTextName;
    EditText editTextID;
    EditText editTextGPA;
    Button buttonCancel;
    Button buttonSave;
    DataBaseHelper db;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // instantiate the dialog box
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_box_add_student, null);

        //declare all of the elements that are used
        editTextSurname = view.findViewById(R.id.editTextSurname);
        editTextGPA = view.findViewById(R.id.editTextGPA);
        editTextName = view.findViewById(R.id.editTextName);
        editTextID = view.findViewById(R.id.editTextID);
        buttonCancel = view.findViewById(R.id.buttonCancel);
        buttonSave = view.findViewById(R.id.buttonSave);

        // instantiate Database Helper
        db = new DataBaseHelper(getContext(), "database.db", null, 1);

        // set the title of the dialog box
        builder.setView(view).setTitle("Create Student");

        buttonCancel.setOnClickListener(new View.OnClickListener() { // cancel button handler
            @Override
            public void onClick(View view) {
                dismiss();
            } // calls dismiss(), which closes the dialog box
        });

        buttonSave.setOnClickListener(new View.OnClickListener() { // save button handler
            @Override
            public void onClick(View view) {

                String errorMessage = ""; // variable to store the error messages

                // write code similar to the one above but checks that GPA input is between 0 and 4.3, and only numbers are used
                if (validateGPA(editTextGPA.getText().toString()) && validateID(editTextID.getText().toString()) && validateName(editTextName.getText().toString()) && validateName(editTextSurname.getText().toString())) { // if all of the validations pass
                    Toast.makeText(getContext(), "Student added", Toast.LENGTH_SHORT).show(); // display toast message to show student was addded
                    //get current year, month and day
                    int year = Calendar.getInstance().get(Calendar.YEAR);
                    int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
                    int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

                    // add the profile to the database by using all the parameters in the textEdit
                    db.addProfile(new ProfileModel(Integer.parseInt(editTextID.getText().toString()), editTextName.getText().toString(), editTextSurname.getText().toString(), Float.parseFloat(editTextGPA.getText().toString()), day, month, year));
                    ((MainActivity) getActivity()).updateCounts(); // call updateCounts() from the main activity
                    dismiss(); // close the dialog box
                } else { // if one of the validations fail, show the error message
                    if (!validateGPA(editTextGPA.getText().toString())) {
                        errorMessage = errorMessage + "GPA must be between 0 and 4.3\n";
                    }
                    if (!validateID(editTextID.getText().toString())) {
                        errorMessage = errorMessage + "ID must be between 10000000 and 99999999, not already exist and contain only numbers\n";
                    }
                    if (!validateName(editTextName.getText().toString())) {
                        errorMessage = errorMessage + "Name must only contain letters and whitespace\n";
                    }
                    if (!validateName(editTextSurname.getText().toString())) {
                        errorMessage = errorMessage + "Surname must only contain letters and whitespace\n";
                    }
                    Toast.makeText(getContext(), errorMessage, Toast.LENGTH_LONG).show(); // create toast with error message
                }
            }
        });
        return builder.create();
    }

    boolean validateGPA(String GPAIn){ // function to validate a GPA input
        boolean isGPANum = false;
        boolean isGPAValidRange = false;
        if (testStringFloat(GPAIn)){ // test to see if the is a float number
            isGPANum = true;
            float GPANum = Float.parseFloat(GPAIn);
            if (GPANum >= 0 && GPANum <= 4.3){ // test to see if the number is between 0 and 4.3
                isGPAValidRange = true;
            }
        }
        if (isGPANum && isGPAValidRange){ // if both tests pass, return true
            return true;
        }
        else{
            return false;
        }
    }
    boolean validateID(String IDIn){ // function to validate an ID input

        boolean isIDNum = false;
        boolean isIDValidRange = false;
        boolean isIDAvailable = false;
        if (testStringInt(IDIn)){ // test to see if the input is a number
            isIDNum = true;
            Log.d("ID", "ID is a number");
            int IDNum;
            try{
                 IDNum= Integer.parseInt(IDIn);
            }
            catch (Exception e){
                return false;
            }

            if (IDNum >= 10000000 && IDNum <= 99999999){
                Log.d("ID", "ID is in range");
                isIDValidRange = true;
            }
            if (!db.checkExistingIDProfile(IDNum)){
                Log.d("ID", "ID is available");
                isIDAvailable = true;
            }
        }
        if (isIDNum && isIDValidRange && isIDAvailable){
            return true;
        }
        else{
            return false;
        }
    }

    boolean validateName(String nameIn){
        boolean isNameValid = false;
        if (testString(nameIn)){
            isNameValid = true;
        }
        return isNameValid;
    }

    boolean testString(String string_in){ // checks if string only contains letters and whitespace
        for (int i = 0; i < string_in.length(); i ++){
            char character = string_in.charAt(i);
            if (!(Character.isLetter(character) || Character.isWhitespace(character))){
                return false;
            }
        }
        return true;
    }
    boolean testStringInt(String string_in){ // checks if string only contains numbers
        for (int i = 0; i < string_in.length(); i ++){
            char character = string_in.charAt(i);
            if (!(Character.isDigit(character))){
                return false;
            }
        }
        return true;
    }
    boolean testStringFloat(String string_in){ // checks if string only contains numbers
        try{
            //convert string_in to  float
            float num = Float.parseFloat(string_in);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }
}