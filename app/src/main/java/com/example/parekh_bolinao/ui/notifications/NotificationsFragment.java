package com.example.parekh_bolinao.ui.notifications;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.parekh_bolinao.MainActivity;
import com.example.parekh_bolinao.R;
import com.example.parekh_bolinao.Record;
import com.example.parekh_bolinao.Summary;
import com.example.parekh_bolinao.SummaryAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static android.content.ContentValues.TAG;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        root = inflater.inflate(R.layout.fragment_summaries, container, false);
        final TextView textView = root.findViewById(R.id.text_notifications);
        notificationsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();

        Log.d("==========", "Fuck and shit");

        DatabaseReference mDatabase = ((MainActivity)getActivity()).getmDatabase();

        ListView lv = root.findViewById(R.id.summary_list);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Summary> summaries = new ArrayList<>(0);
                if (dataSnapshot.exists()) {
                    for(DataSnapshot ds : dataSnapshot.getChildren()) {
                        String name = ds.getKey().toString();
                        double sys = 0, dia = 0;
                        for(DataSnapshot rec : ds.getChildren()) {
                            Record r = rec.getValue(Record.class);
                            sys += r.systolic_reading;
                            dia += r.diastolic_reading;
                        }
                        summaries.add(new Summary(name, sys, dia));
                        Log.d(TAG, "For "+name+ "dia="+dia+"sys="+sys);
                    }

                    SummaryAdapter adapter = new SummaryAdapter(Objects.requireNonNull(getActivity()), summaries);
                    lv.setAdapter(adapter);
                }
                else {
                    Log.d(TAG, "Dick");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private String getMonth(int mon) {
        String s = "";
        switch (mon) {
            case 1:
                s = "January";
            case 2:
                s = "February";
            case 3:
                s = "March";
            case 4:
                s = "April";
            case 5:
                s = "May";
            case 6:
                s = "June";
            case 7:
                s = "July";
            case 8:
                s = "August";
            case 9:
                s = "September";
            case 10:
                s = "October";
            case 11:
                s = "November";
            case 12:
                s = "December";

        }
        return s;
    }

    private void getDataForMonth(int mon) {
        DatabaseReference mDatabase = ((MainActivity)getActivity()).getmDatabase();
        Query query = mDatabase.child("Records");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // dataSnapshot is the "issue" node with all children with id 0
                    for (DataSnapshot issue : dataSnapshot.getChildren()) {
                        // do something with the individual "issues"
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}