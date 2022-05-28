package com.example.se328_dalalaldossary_201255_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import es.dmoral.toasty.Toasty;

public class FirebaseActivity extends AppCompatActivity {

    EditText studentIDEdit, nameEdit, fathersNameEdit,
            surnameEdit, nationalIDEdit, genderEdit;

    Spinner daySpinner, monthSpinner, yearSpinner;

    Button addBttn, updateBttn, deleteBttn,
            oneRecordBttn, viewAllBttn, toSqlBttn;

    static TextView tempFirebase, humidFirebase, conditionFirebase;

    SharedPreferences shared;

    Firebase fb;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase);

        db = new DatabaseHelper(FirebaseActivity.this);
        fb = new Firebase();
        shared = PreferenceManager.getDefaultSharedPreferences(FirebaseActivity.this);

        studentIDEdit = (EditText) findViewById(R.id.editStudentID);
        nameEdit = (EditText) findViewById(R.id.editName);
        fathersNameEdit = (EditText) findViewById(R.id.editFathersName);
        surnameEdit = (EditText) findViewById(R.id.editSurname);
        nationalIDEdit = (EditText) findViewById(R.id.editNationalID);
        genderEdit = (EditText) findViewById(R.id.editGender);

        daySpinner = (Spinner) findViewById(R.id.daysSelect);
        monthSpinner = (Spinner) findViewById(R.id.monthsSelect);
        yearSpinner = (Spinner) findViewById(R.id.yearsSelect);

        addBttn = (Button) findViewById(R.id.addFirebaseBttn);
        updateBttn = (Button) findViewById(R.id.updateFirebaseBttn);
        deleteBttn = (Button) findViewById(R.id.deleteFirebaseBttn);
        oneRecordBttn = (Button) findViewById(R.id.oneRecordBttn);
        viewAllBttn = (Button) findViewById(R.id.viewAllBttn);
        toSqlBttn = (Button) findViewById(R.id.toSqlBttn);

        tempFirebase = (TextView) findViewById(R.id.tempTxtFirebase);
        humidFirebase = (TextView) findViewById(R.id.humidTextFirebase);
        conditionFirebase = (TextView) findViewById(R.id.conditionTextFirebase);

        tempFirebase.setText(shared.getString("temp", ""));
        humidFirebase.setText(shared.getString("humid", ""));
        conditionFirebase.setText(shared.getString("condition", ""));

        viewAllBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FirebaseActivity.this, FirebaseRecords.class));
            }
        });

        addBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = studentIDEdit.getText().toString();
                String name = nameEdit.getText().toString();
                String fathersName = fathersNameEdit.getText().toString();
                String surname = surnameEdit.getText().toString();
                String nationalID = nationalIDEdit.getText().toString();
                String gender = genderEdit.getText().toString();

                if(id.isEmpty() || name.isEmpty() || fathersName.isEmpty() || surname.isEmpty()
                 || nationalID.isEmpty() || gender.isEmpty()) {
                    Toasty.warning(getBaseContext(), "Please fill all fields and try again!",
                            Toast.LENGTH_SHORT, true).show();
                } else {
                    try {
                        String dob = daySpinner.getSelectedItem().toString() + " - " +
                                monthSpinner.getSelectedItem().toString() + " - " +
                                yearSpinner.getSelectedItem().toString();
                        fb.createWithSuccess(id, name, surname, fathersName, nationalID, dob, gender);
                        Toasty.success(getBaseContext(), "Successfully added student!",
                                Toast.LENGTH_SHORT, true).show();
                    } catch (Exception e) {
                        Toasty.error(getBaseContext(), "Error occurred while adding...",
                                Toast.LENGTH_SHORT, true).show();
                        Log.e("Dalal", "Error adding to firebase: " + e);
                    }
                }
            }
        });

        deleteBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = studentIDEdit.getText().toString();

                if(id.isEmpty()) {
                    Toasty.warning(getBaseContext(), "Please enter an id!",
                            Toast.LENGTH_SHORT, true).show();
                } else {
                    try {
                        fb.deleteStudent(id);
                        Toasty.success(getBaseContext(), "Record Deleted Successfully!",
                                Toast.LENGTH_SHORT, true).show();
                    } catch (Exception e) {
                        Toasty.error(getBaseContext(), "Error deleting id...",
                                Toast.LENGTH_SHORT, true).show();
                        Log.e("Dalal", "Error deleting from firebase: " + e);
                    }
                }
            }
        });

        updateBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = studentIDEdit.getText().toString();
                String name = nameEdit.getText().toString();
                String fathersName = fathersNameEdit.getText().toString();
                String surname = surnameEdit.getText().toString();

                if(id.isEmpty()) {
                    Toasty.warning(getBaseContext(), "Please enter the id!",
                            Toast.LENGTH_SHORT, true).show();
                } else {
                    if(!name.isEmpty()) {
                        try {
                            fb.updateName(id, name);
                            Toasty.success(getBaseContext(), "Successfully updated name!",
                                    Toast.LENGTH_SHORT, true).show();
                        } catch (Exception e) {
                            Toasty.error(getBaseContext(), "Error occurred while updating name...",
                                    Toast.LENGTH_SHORT, true).show();
                            Log.e("Dalal", "Error updating name in firebase: " + e);
                        }
                    }

                    if(!fathersName.isEmpty()) {
                        try {
                            fb.updateFathersName(id, fathersName);
                            Toasty.success(getBaseContext(), "Successfully updated father's name!",
                                    Toast.LENGTH_SHORT, true).show();
                        } catch (Exception e) {
                            Toasty.error(getBaseContext(), "Error occurred while updating father's name...",
                                    Toast.LENGTH_SHORT, true).show();
                            Log.e("Dalal", "Error updating father's name in firebase: " + e);
                        }
                    }

                    if(!surname.isEmpty()) {
                        try {
                            fb.updateSurname(id, surname);
                            Toasty.success(getBaseContext(), "Successfully updated surname!",
                                    Toast.LENGTH_SHORT, true).show();
                        } catch (Exception e) {
                            Toasty.error(getBaseContext(), "Error occurred while updating surname...",
                                    Toast.LENGTH_SHORT, true).show();
                            Log.e("Dalal", "Error updating surname in firebase: " + e);
                        }
                    }

                }
            }
        });

        oneRecordBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = studentIDEdit.getText().toString();
                if(!id.isEmpty()) {
                    try {
                        DatabaseReference Ref = FirebaseDatabase.getInstance().getReference("Students").child(id);
                        Ref.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String id = snapshot.child("studentID").getValue(String.class);
                                String name = snapshot.child("name").getValue(String.class);
                                String surname = snapshot.child("surname").getValue(String.class);
                                String fathersName = snapshot.child("fathersName").getValue(String.class);
                                String nationalID = snapshot.child("nationalID").getValue(String.class);
                                String DoB = snapshot.child("doB").getValue(String.class);
                                String gender = snapshot.child("gender").getValue(String.class);

                                String studentInfo = "Student ID: " + id + "\nStudent Name: " + name
                                        + "\nStudent Father's Name: " + fathersName + "\nStudent Surname: " + surname
                                        + "\nNational ID: " + nationalID + "\nDate of Birth: " + DoB +
                                        "\nGender: " + gender;

                                AlertDialog.Builder alert = new
                                        AlertDialog.Builder(FirebaseActivity.this);
                                alert.setTitle("Student Record");
                                alert.setMessage(studentInfo);
                                alert.setCancelable(true);
                                alert.show();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Log.e("Dalal", "Retrieve error: " + error);
                            }
                        });

                        Toasty.success(getBaseContext(), "Successfuly retrieved record!",
                                Toast.LENGTH_SHORT, true).show();
                    } catch (Exception e) {
                        Toasty.error(getBaseContext(), "Error retrieving record...",
                                Toast.LENGTH_SHORT, true).show();
                        Log.e("Dalal", "Error fetching firebase record: " +e);
                    }
                } else {
                    Toasty.warning(getBaseContext(), "Please enter the id!",
                            Toast.LENGTH_SHORT, true).show();
                }
            }
        });

        toSqlBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = studentIDEdit.getText().toString();
                if(!id.isEmpty()) {
                    try {
                        DatabaseReference Ref = FirebaseDatabase.getInstance().getReference("Students").child(id);
                        Ref.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String id = snapshot.child("studentID").getValue(String.class);
                                String name = snapshot.child("name").getValue(String.class);
                                String surname = snapshot.child("surname").getValue(String.class);
                                String fathersName = snapshot.child("fathersName").getValue(String.class);
                                String nationalID = snapshot.child("nationalID").getValue(String.class);
                                String DoB = snapshot.child("doB").getValue(String.class);
                                String gender = snapshot.child("gender").getValue(String.class);

                                db.addStudent(id, name, surname, fathersName, nationalID, DoB, gender);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Log.e("Dalal", "Retrieve error: " + error);
                            }
                        });
                        Toasty.success(getBaseContext(), "Successfully added student " +
                                "to SQL Database!", Toast.LENGTH_SHORT, true).show();
                    } catch (Exception e) {
                        Toasty.error(getBaseContext(), "Error occurred while " +
                                "adding record...", Toast.LENGTH_SHORT, true).show();
                    }
                } else {
                    Toasty.warning(getBaseContext(), "Please enter the id!",
                            Toast.LENGTH_SHORT, true).show();
                }
            }
        });

    }

}