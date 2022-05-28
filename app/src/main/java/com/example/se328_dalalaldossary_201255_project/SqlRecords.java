package com.example.se328_dalalaldossary_201255_project;

import android.app.ListActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class SqlRecords extends ListActivity {

    ArrayList<String> records = new ArrayList<String>();
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getAllData();
        setListAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, records));
        db = new DatabaseHelper(SqlRecords.this);
    }

    public void getAllData() {
        ArrayList<String > results  = new ArrayList<>();
        String YOUR_QUERY  = "SELECT * FROM TABLE_NAME";
        DatabaseHelper dbHelper = new DatabaseHelper(this.getApplicationContext());
        SQLiteDatabase newDB = dbHelper.getWritableDatabase();
        Cursor c = newDB.rawQuery("SELECT * FROM Students", null);
        if (c != null ) {
            if  (c.moveToFirst()) {
                do {
                    String num = "Student #" + c.getString(0) + "\n";
                    String id = "Student ID: " + c.getString(1) + "\n";
                    String name = "Student Name: " + c.getString(2) + "\n";
                    String surname = "Student Surname" + c.getString(3) + "\n";
                    String fathersName = "Father's Name: " + c.getString(4) + "\n";
                    String nationalID = "National ID: " + c.getString(5) + "\n";
                    String dob = "Date of Birth: " + c.getString(6) + "\n";
                    String gender = "Gender: " + c.getString(7) + "\n";

                    String info = num + id + name + surname + fathersName +
                            nationalID + dob + gender;
                    records.add(info);
                }while (c.moveToNext());
            }
        }
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Cursor c = db.getSpecificStudentUsingPosition(position+1);
        StringBuffer buff = new StringBuffer();
        while(c.moveToNext()) {
            buff.append(c.getString(2) + " ");
            buff.append(c.getString(3));
        }
        Toasty.info(getBaseContext(), buff.toString(),
                Toast.LENGTH_SHORT, true).show();
    }
}