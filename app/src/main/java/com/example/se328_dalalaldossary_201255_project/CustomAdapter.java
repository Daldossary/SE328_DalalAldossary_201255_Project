package com.example.se328_dalalaldossary_201255_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    Context context;
    ArrayList<Student> list;

    public CustomAdapter(Context context, ArrayList<Student> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Student std = list.get(position);

        holder.id.setText(std.getStudentID());
        holder.name.setText(std.getName());
        holder.surname.setText(std.getSurname());
        holder.fathersName.setText(std.getFathersName());
        holder.nationalID.setText(std.getNationalID());
        holder.dob.setText(std.getDoB());
        holder.gender.setText(std.getGender());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView id, name, surname, fathersName, nationalID, dob, gender;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.studentID);
            name = itemView.findViewById(R.id.studentName);
            surname = itemView.findViewById(R.id.studentSurname);
            fathersName = itemView.findViewById(R.id.fathersName);
            nationalID = itemView.findViewById(R.id.nationalID);
            dob = itemView.findViewById(R.id.dob);
            gender = itemView.findViewById(R.id.gender);

        }
    }

}
