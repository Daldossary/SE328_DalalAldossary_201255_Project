package com.example.se328_dalalaldossary_201255_project;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

public class SqlActivity extends AppCompatActivity {

    EditText studentIDEdit, nameEdit, fathersNameEdit,
            surnameEdit, nationalIDEdit, genderEdit;

    Spinner daySpinner, monthSpinner, yearSpinner;

    Button addBttn, updateBttn, deleteBttn,
            oneRecordBttn, viewAllBttn;

    static TextView tempSQL, humidSQL, conditionSQL;

    DatabaseHelper db;
    SharedPreferences shared;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sql);

        db = new DatabaseHelper(SqlActivity.this);
        shared = PreferenceManager.getDefaultSharedPreferences(SqlActivity.this);

        studentIDEdit = (EditText) findViewById(R.id.editStudentID2);
        nameEdit = (EditText) findViewById(R.id.editName2);
        fathersNameEdit = (EditText) findViewById(R.id.editFathersName2);
        surnameEdit = (EditText) findViewById(R.id.editSurname2);
        nationalIDEdit = (EditText) findViewById(R.id.editNationalD2);
        genderEdit = (EditText) findViewById(R.id.editGender2);

        daySpinner = (Spinner) findViewById(R.id.daysSelect2);
        monthSpinner = (Spinner) findViewById(R.id.monthsSelect2);
        yearSpinner = (Spinner) findViewById(R.id.yearsSelect2);

        addBttn = (Button) findViewById(R.id.addSQLBttn);
        updateBttn = (Button) findViewById(R.id.updateSQLBttn);
        deleteBttn = (Button) findViewById(R.id.deleteSQLBttn);
        oneRecordBttn = (Button) findViewById(R.id.oneRecordSQLBttn);
        viewAllBttn = (Button) findViewById(R.id.viewAllSQLBttn);

        tempSQL = (TextView) findViewById(R.id.tempTextSQL);
        humidSQL = (TextView) findViewById(R.id.humidTextSQL);
        conditionSQL = (TextView) findViewById(R.id.conditionTextSQL);

        tempSQL.setText(shared.getString("temp", ""));
        humidSQL.setText(shared.getString("humid", ""));
        conditionSQL.setText(shared.getString("condition", ""));

        viewAllBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SqlActivity.this, SqlRecords.class));
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
                String dob = daySpinner.getSelectedItem().toString() + " - " +
                        monthSpinner.getSelectedItem().toString() + " - " +
                        yearSpinner.getSelectedItem().toString();

                if(id.isEmpty() || name.isEmpty() || fathersName.isEmpty() ||
                surname.isEmpty() || nationalID.isEmpty() || gender.isEmpty()) {
                    Toasty.warning(getBaseContext(), "Please fill all fields and try again!",
                            Toast.LENGTH_SHORT, true).show();
                } else {
                    try {
                        db.addStudent(id, name, surname, fathersName,
                                nationalID, dob, gender);
                        Toasty.success(getBaseContext(), "Student added successfully!",
                                Toast.LENGTH_SHORT, true).show();
                        Log.d("Dalal", "Student added to SQL database successfully!");
                    } catch(Exception e) {
                        Log.e("Dalal", "Error occured while adding student to SQL database: " + e);
                        Toasty.error(getBaseContext(), "Error occured while adding student...",
                                Toast.LENGTH_SHORT, true).show();
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
                String nationalID = nationalIDEdit.getText().toString();
                String gender = genderEdit.getText().toString();

                if(id.isEmpty()) {
                    Toasty.warning(getBaseContext(), "Please enter id!",
                            Toast.LENGTH_SHORT, true).show();
                } else {
                    ContentValues cv = new ContentValues();
                    if(!name.isEmpty()) {
                        cv.put("NAME", name);
                    }
                    if(!fathersName.isEmpty()) {
                        cv.put("FATHERS_NAME", fathersName);
                    }
                    if(!surname.isEmpty()) {
                        cv.put("SURNAME", surname);
                    }
                    if(!nationalID.isEmpty()) {
                        cv.put("NATIONAL_ID", nationalID);
                    }
                    if(!gender.isEmpty()) {
                        cv.put("GENDER", gender);
                    }

                    try {
                        db.updateStudent(id, cv);
                        Toasty.success(getBaseContext(), "Successfully updated student info!",
                                Toast.LENGTH_SHORT, true).show();
                    } catch(Exception e) {
                        Toasty.error(getBaseContext(), "Error updating student info...",
                                Toast.LENGTH_SHORT, true).show();
                        Log.e("Dalal", "Error updating student info");
                    }
                }
            }
        });

        deleteBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = studentIDEdit.getText().toString();
                if(id.isEmpty()) {
                    Toasty.warning(getBaseContext(), "Please fill in the id!",
                            Toast.LENGTH_SHORT, true).show();
                } else {
                    try {
                        db.deleteStudent(id);
                        Toasty.success(getBaseContext(), "Deleted student successfully!",
                                Toast.LENGTH_SHORT, true).show();
                        Log.d("Dalal", "Deleted student from SQL database successfully");
                    } catch(Exception e) {
                        Log.e("Dalal", "Error deleting student from SQL database...");
                    }

                }
            }
        });

        oneRecordBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = studentIDEdit.getText().toString();
                if(id.isEmpty()) {
                    Toasty.warning(getBaseContext(), "Please fill in the id!",
                            Toast.LENGTH_SHORT, true).show();
                } else {
                    try {
                        Cursor c = db.getSpecificStudent(id);
                        StringBuffer buff = new StringBuffer();

                        while(c.moveToNext()) {
                            buff.append("Student #" + c.getString(0) + "\n");
                            buff.append("Student ID: " + c.getString(1) + "\n");
                            buff.append("Student Name: " + c.getString(2) + "\n");
                            buff.append("Student Surname: " + c.getString(3) + "\n");
                            buff.append("Father's Name: " + c.getString(4) + "\n");
                            buff.append("National ID: " + c.getString(5) + "\n");
                            buff.append("Date of Birth: " + c.getString(6) + "\n");
                            buff.append("Gender: " + c.getString(7) + "\n");
                        }

                        AlertDialog.Builder alert = new AlertDialog.Builder(SqlActivity.this);
                        alert.setTitle("Student Record");
                        alert.setMessage(buff.toString());
                        alert.setCancelable(true);
                        alert.show();
                        Toasty.success(getBaseContext(), "Successfully retrieved record!",
                                Toast.LENGTH_SHORT, true).show();
                        Log.d("Dalal", "SQL Student record retrieved successfully!");
                    } catch(Exception e) {
                        Log.e("Dalal", "Error retrieving student record...");
                    }
                }
            }
        });

    }
}