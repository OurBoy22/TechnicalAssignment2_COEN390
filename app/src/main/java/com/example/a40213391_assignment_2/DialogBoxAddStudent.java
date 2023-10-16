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

    EditText editTextSurname;
    EditText editTextName;
    EditText editTextID;
    EditText editTextGPA;
    Button buttonCancel;
    Button buttonSave;
    DataBaseHelper db;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_box_add_student, null);
        editTextSurname = view.findViewById(R.id.editTextSurname);
        editTextGPA = view.findViewById(R.id.editTextGPA);
        editTextName = view.findViewById(R.id.editTextName);
        editTextID = view.findViewById(R.id.editTextID);
        buttonCancel = view.findViewById(R.id.buttonCancel);
        buttonSave = view.findViewById(R.id.buttonSave);
        db = new DataBaseHelper(getContext(), "database.db", null, 1);


        builder.setView(view).setTitle("Create Student");

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String errorMessage = "";

                // write code similar to the one above but checks that GPA input is between 0 and 4.3, and only numbers are used
                if (validateGPA(editTextGPA.getText().toString()) && validateID(editTextID.getText().toString()) && validateName(editTextName.getText().toString()) && validateName(editTextSurname.getText().toString())) {
                    Toast.makeText(getContext(), "Student added", Toast.LENGTH_SHORT).show();
                    int year = Calendar.getInstance().get(Calendar.YEAR);
                    int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
                    int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
                    db.addProfile(new ProfileModel(Integer.parseInt(editTextID.getText().toString()), editTextName.getText().toString(), editTextSurname.getText().toString(), Float.parseFloat(editTextGPA.getText().toString()), day, month, year));
                    ((MainActivity) getActivity()).updateCounts();
                    dismiss();
                } else {
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
                    Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
                }
            }
        });
        return builder.create();
    }

    boolean validateGPA(String GPAIn){
        boolean isGPANum = false;
        boolean isGPAValidRange = false;
        if (testStringFloat(GPAIn)){
            isGPANum = true;
            float GPANum = Float.parseFloat(GPAIn);
            if (GPANum >= 0 && GPANum <= 4.3){
                isGPAValidRange = true;
            }
        }
        if (isGPANum && isGPAValidRange){
            return true;
        }
        else{
            return false;
        }
    }
    boolean validateID(String IDIn){

        boolean isIDNum = false;
        boolean isIDValidRange = false;
        boolean isIDAvailable = false;
        if (testStringInt(IDIn)){
            isIDNum = true;
            Log.d("ID", "ID is a number");
            int IDNum = Integer.parseInt(IDIn);
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