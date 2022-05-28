package com.example.se328_dalalaldossary_201255_project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.R.layout;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveAction;

public class FirebaseRecords extends AppCompatActivity {

    RecyclerView recycle;
    DatabaseReference ref;
    CustomAdapter cAdapter;
    ArrayList<Student> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_records);

        recycle = (findViewById(R.id.recycle));
        ref = FirebaseDatabase.getInstance().getReference("Students");
        recycle.setHasFixedSize(true);
        recycle.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        cAdapter = new CustomAdapter(this, list);
        recycle.setAdapter(cAdapter);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Student std = dataSnapshot.getValue(Student.class);
                    list.add(std);
                }
                cAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}