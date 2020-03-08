package com.example.parekh_bolinao.ui.summary;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

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
import java.util.Calendar;
import java.util.Locale;

import static android.content.ContentValues.TAG;

public class SummaryFragment extends Fragment {
    private View root;
    SummaryAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SummaryViewModel summaryViewModel = ViewModelProviders.of(this).get(SummaryViewModel.class);
        root = inflater.inflate(R.layout.fragment_summaries, container, false);
        getActivity().setRequestedOrientation(
                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();

        ListView lv = root.findViewById(R.id.summary_list);

        Calendar c = Calendar.getInstance();
        TextView field0 = root.findViewById(R.id.summary_title);
        String month = c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
        field0.setText(String.format("Month-to-date average readings for %s, %d", month, c.get(Calendar.YEAR)));

        ArrayList<Summary> summaries = ((MainActivity)getActivity()).summaries;
        adapter = new SummaryAdapter(getActivity(), summaries);
        lv.setAdapter(adapter);
    }

    public SummaryAdapter getAdapter() { return this.adapter; }

    public void onStop() {
        super.onStop();
    }
}