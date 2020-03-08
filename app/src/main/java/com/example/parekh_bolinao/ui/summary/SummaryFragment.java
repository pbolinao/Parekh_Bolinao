package com.example.parekh_bolinao.ui.summary;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
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
import com.example.parekh_bolinao.Summary;
import com.example.parekh_bolinao.SummaryAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class SummaryFragment extends Fragment {
    private View root;
    ArrayList<Summary> summaries;
    SummaryAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SummaryViewModel summaryViewModel = ViewModelProviders.of(this).get(SummaryViewModel.class);
        root = inflater.inflate(R.layout.fragment_summaries, container, false);

        if (savedInstanceState != null) {
            summaries = (ArrayList<Summary>) savedInstanceState.getSerializable("summaries");
        } else {
            summaries = ((MainActivity)getActivity()).getSummaries();
        }

        return root;
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putSerializable("summaries", summaries);
    }

    @Override
    public void onStart() {
        super.onStart();

        ListView lv = root.findViewById(R.id.summary_list);
        adapter = new SummaryAdapter(getActivity(), summaries);
        lv.setAdapter(adapter);
    }

    public SummaryAdapter getAdapter() { return this.adapter; }

    public void onStop() {
        super.onStop();
    }
}