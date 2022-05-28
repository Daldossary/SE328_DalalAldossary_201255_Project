package com.example.se328_dalalaldossary_201255_project;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.SimpleCursorAdapter;

/**
 * Created by Mitch on 2016-05-13.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "project.db";
    public static final String TABLE_NAME = "Students";
    public static final String COL1 = "STUDENT_ID";
    public static final String COL2 = "NAME";
    public static final String COL3 = "SURNAME";
    public static final String COL4 = "FATHERS_NAME";
    public static final String COL5 = "NATIONAL_ID";
    public static final String COL6 = "DATE_OF_BIRTH";
    public static final String COL7 = "GENDER";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME +
                " (STUDENT_NUM INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " STUDENT_ID TEXT, NAME TEXT, SURNAME TEXT, FATHERS_NAME TEXT, " +
                " NATIONAL_ID INTEGER, DATE_OF_BIRTH TEXT, GENDER TEXT)";
        db.execSQL(createTable);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addStudent(String studentID, String name, String surname,
                           String fathersName, String nationalID, String dob,
                           String gender) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, studentID);
        contentValues.put(COL2, name);
        contentValues.put(COL3, surname);
        contentValues.put(COL4, fathersName);
        contentValues.put(COL5, nationalID);
        contentValues.put(COL6, dob);
        contentValues.put(COL7, gender);
        long result = db.insert(TABLE_NAME, null, contentValues);

        if(result == -1) {return false;} else {return true;}
    }

    public Cursor structuredQuery(int ID) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{COL1,
                        COL2, COL3, COL4, COL5, COL6, COL7}, COL1 + "=?",
                new String[]{String.valueOf(ID)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        return cursor;
    }

    public Cursor getSpecificStudent(String ID){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME+" WHERE STUDENT_ID="+ID,null);
        return data;
    }

    public Cursor getSpecificStudentUsingPosition(int position){
        SQLiteDatabase db = this.getWritableDatabase();
        String num = "" + position;
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME+" WHERE STUDENT_NUM="+num,null);
        return data;
    }

    public Cursor getListContents() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return data;
    }

    public void deleteStudent(String sID) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "STUDENT_ID=?", new String[] {sID});
    }

    public void updateStudent(String sID, ContentValues cv) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.update(TABLE_NAME, cv, "STUDENT_ID = ?", new String[]{sID});
            Log.d("Dalal", "Successfully Updated SQL Record");
        } catch (Exception e) {
            Log.e("Dalal", "Error updating SQL Record: " + e);
        }
    }
}