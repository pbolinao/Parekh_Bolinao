package com.example.parekh_bolinao.ui.recent;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.parekh_bolinao.MainActivity;
import com.example.parekh_bolinao.R;
import com.example.parekh_bolinao.Record;
import com.example.parekh_bolinao.RecordAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RecentFragment extends Fragment {

    private RecentViewModel recentViewModel;
    private Activity main;
    private View root;
    ListView lv;
    List<Record> recordList;
    DatabaseReference mDatabase;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        recentViewModel =
                ViewModelProviders.of(this).get(RecentViewModel.class);
        root = inflater.inflate(R.layout.fragment_recent, container, false);
        lv = root.findViewById(R.id.recent_entries_list);
        recordList = new ArrayList<>();
        return root;
    }

    public void onStart() {
        super.onStart();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                recordList.clear();
                for(DataSnapshot recordSnapshot : dataSnapshot.getChildren()) {
                    Record record = recordSnapshot.getValue(Record.class);
                    recordList.add(record);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
        RecordAdapter adapter = new RecordAdapter(getActivity(), recordList);
        lv.setAdapter(adapter);
    }
}
