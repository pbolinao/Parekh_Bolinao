package com.example.parekh_bolinao.ui.recent;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.parekh_bolinao.MainActivity;
import com.example.parekh_bolinao.R;
import com.example.parekh_bolinao.Record;

public class RecentFragment extends Fragment {

    private RecentViewModel recentViewModel;
    private Activity main;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        recentViewModel =
                ViewModelProviders.of(this).get(RecentViewModel.class);
        View root = inflater.inflate(R.layout.fragment_recent, container, false);
//        ((MainActivity)getActivity());
        // CREATE FUNCTION TO RETURN RECENT ENTRIES AS AN ARRAY, THEN PASS THAT ARRAY AS THE 3RD ARGUMENT
//        ArrayAdapter<Record> recordArrayAdapter = new ArrayAdapter<>(this, android.R.layout.recent_entry_row_layout, );
//        ListView lv = root.findViewById(R.id.recent_entries_list);
//        lv.setAdapter(recordArrayAdapter);
        return root;
    }
}