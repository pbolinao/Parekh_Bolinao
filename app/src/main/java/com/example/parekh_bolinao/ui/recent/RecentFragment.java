package com.example.parekh_bolinao.ui.recent;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.parekh_bolinao.MainActivity;
import com.example.parekh_bolinao.R;
import com.example.parekh_bolinao.Record;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;

public class RecentFragment extends Fragment {

    private RecentViewModel recentViewModel;
    private Activity main;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        recentViewModel =
                ViewModelProviders.of(this).get(RecentViewModel.class);
        View root = inflater.inflate(R.layout.fragment_recent, container, false);
        DatabaseReference mDatabase = ((MainActivity)getActivity()).getmDatabase();
        ArrayAdapter<Record> recordArrayAdapter = new FirebaseListAdapter<Record>(this, R.layout.recent_entry_row_layout,mDatabase){
            @Override
            protected void populateView(View view, Record rec, int position) {
                //Set the value for the views
                ((TextView)view.findViewById(R.id.user_name)).setText(rec.getName());
                //...
            }
        };
        };
        ListView lv = root.findViewById(R.id.recent_entries_list);
        lv.setAdapter(recordArrayAdapter);
        return root;
    }
}