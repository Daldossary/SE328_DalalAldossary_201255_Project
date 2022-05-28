package com.example.se328_dalalaldossary_201255_project;

public class Student {
    private String studentID, name, surname, fathersName, nationalID, DoB, gender;


    Student(){}

    Student(String studentID, String name, String surname,
            String fathersName, String nationalID, String DoB,
            String gender) {
        this.studentID = studentID;
        this.name = name;
        this.surname = surname;
        this.fathersName = fathersName;
        this.nationalID = nationalID;
        this.DoB = DoB;
        this.gender = gender;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFathersName() {
        return fathersName;
    }

    public void setFathersName(String fathersName) {
        this.fathersName = fathersName;
    }

    public String getNationalID() {
        return nationalID;
    }

    public void setNationalID(String nationalID) {
        this.nationalID = nationalID;
    }

    public String getDoB() {
        return DoB;
    }

    public void setDoB(String doB) {
        DoB = doB;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
