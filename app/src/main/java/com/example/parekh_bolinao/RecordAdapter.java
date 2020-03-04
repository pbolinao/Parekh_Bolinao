package com.example.parekh_bolinao;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class RecordAdapter extends ArrayAdapter<Record> {

    public Activity context;
    private List<Record> recordList;

    public RecordAdapter(Activity context, List<Record> recordList) {
        super(context, R.layout.recent_entry_row_layout, recordList);
        this.context = context;
        this.recordList = recordList;
    }

    public RecordAdapter(Context context, int resource,
                         List<Record> objects, Activity context1, List<Record> recordList) {
        super(context, resource, objects);
        this.context = context1;
        this.recordList = recordList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.recent_entry_row_layout, null, true);

        TextView tvName = listViewItem.findViewById(R.id.username_data);
        TextView tvTime = listViewItem.findViewById(R.id.entry_datetime);
        TextView tvSystolic = listViewItem.findViewById(R.id.systolic_reading);
        TextView tvDiastolic = listViewItem.findViewById(R.id.diastolic_reading);

        Record record = recordList.get(position);
        tvName.setText(record.getName());
        String time = record.getYear() + ", " + record.getMonth()
                + " " + record.getTime();
        tvTime.setText(time);
        tvSystolic.setText(String.valueOf(record.getSystolic_reading()));
        tvDiastolic.setText(String.valueOf(record.getDiastolic_reading()));
        return listViewItem;
    }

}
