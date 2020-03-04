package com.example.parekh_bolinao;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.parekh_bolinao.ui.recent.RecentFragment;

import java.util.ArrayList;
import java.util.List;

public class RecordAdapter extends ArrayAdapter<Record> {

    Context _context;
//    private List<Record> recordList;

    public RecordAdapter(@NonNull Activity context, List<Record> recordList) {
        super(context, 0, recordList);
        _context = context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Record record = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.recent_entry_row_layout, parent, false);
        }

        TextView tvName = convertView.findViewById(R.id.username_data);
        TextView tvTime = convertView.findViewById(R.id.entry_datetime);
        TextView tvSystolic = convertView.findViewById(R.id.systolic_reading);
        TextView tvDiastolic = convertView.findViewById(R.id.diastolic_reading);

        tvName.setText(record.getName());
        String time = record.getYear() + ", " + record.getMonth()
                + " " + record.getTime();
        tvTime.setText(time);
        tvSystolic.setText(String.valueOf(record.getSystolic_reading()));
        tvDiastolic.setText(String.valueOf(record.getDiastolic_reading()));
        return convertView;
    }

}
