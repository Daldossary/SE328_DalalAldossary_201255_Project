package com.example.se328_dalalaldossary_201255_project;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseCommonRegistrar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class Firebase extends AppCompatActivity {
    private FirebaseDatabase fireDB;
    private DatabaseReference dbRef;
    SharedPreferences shared;
    SharedPreferences.Editor edit;

    public Firebase() {
        FirebaseApp.initializeApp(this);
        fireDB = FirebaseDatabase.getInstance();
        dbRef = fireDB.getReference();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        shared = PreferenceManager.getDefaultSharedPreferences(Firebase.this);
        edit = shared.edit();
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value = null;
                for(DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Map<String, Object> map = (Map<String, Object>) snapshot.getValue();
                    Log.d("Dalal", "Value:  "+ map);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("Dalal", "Failed: " + error);
            }
        });
    }

    public void createStudent (String studentID, String name, String surname,
                                String fathersName, String nationalID, String DoB,
                                String gender) {
        Student student = new Student(studentID, name, surname,
                fathersName, nationalID, DoB, gender);
        dbRef.child("Students").child(studentID).setValue(student);
    }

    public void createWithSuccess(String studentID, String name, String surname,
                                 String fathersName, String nationalID, String DoB,
                                 String gender) {
        Student student = new Student(studentID, name, surname,
                fathersName, nationalID, DoB, gender);
        dbRef.child("Students").child(studentID).setValue(student).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d("Dalal", "Created Student Successfully");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("Dalal", "Error creating student: " + e);
            }
        });
    }

    public void updateName(String userID, String name) {
        try {
            dbRef = fireDB.getReference("Students");
            dbRef.child(userID).child("name").setValue(name);
            Log.d("Dalal", "Student Updated Successfully");
            dbRef = fireDB.getReference();
        } catch (Exception e) {
            Log.d("Dalal", "Error creating student: " + e);
        }
    }

    public void updateSurname(String userID, String surname) {
        try {
            dbRef = fireDB.getReference("Students");
            dbRef.child(userID).child("surname").setValue(surname);
            Log.d("Dalal", "Student Created Successfully");
            dbRef = fireDB.getReference();
        } catch (Exception e) {
            Log.d("Dalal", "Error creating student: " + e);
        }
    }

    public void updateFathersName(String userID, String fathersName) {
        try {
            dbRef = fireDB.getReference("Students");
            dbRef.child(userID).child("fathersName").setValue(fathersName);
            Log.d("Dalal", "Student Created Successfully");
            dbRef = fireDB.getReference();
        } catch (Exception e) {
            Log.d("Dalal", "Error creating student: " + e);
        }
    }

    public void deleteStudent(String userID) {
        dbRef = fireDB.getReference("Students").child(userID);
        dbRef.child("name").removeValue();
        dbRef.child("surname").removeValue();
        dbRef.child("fathersName").removeValue();
        dbRef.child("nationalID").removeValue();
        dbRef.child("doB").removeValue();
        dbRef.child("gender").removeValue();
        dbRef.child("studentID").removeValue();
        dbRef = fireDB.getReference("Students");
    }

    public void getAllStudentInfo() {
        dbRef = fireDB.getReference("Students");
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()) {
                    String id = ds.child("studentID").getValue(String.class);
                    String name = ds.child("name").getValue(String.class);
                    String surname = ds.child("surname").getValue(String.class);
                    String fathersName = ds.child("fathersName").getValue(String.class);
                    String nationalID = ds.child("nationalID").getValue(String.class);
                    String dob = ds.child("doB").getValue(String.class);
                    String gender = ds.child("gender").getValue(String.class);
                    String studentInfo = "---------------------\n" + "Student ID: " + id  +
                            "\nStudent Name: " + name + "\nStudent Father's Name:  " + fathersName +
                            "\nStudent Surname: " + surname + "\nNational ID: " + nationalID +
                            "\nDate of birth: " + dob + "\nGender: " + gender +
                            "\n---------------------\n";

                    Log.d("Dalal", studentInfo);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
