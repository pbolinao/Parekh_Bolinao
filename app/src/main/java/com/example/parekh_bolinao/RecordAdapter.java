package com.example.parekh_bolinao;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
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

        int syst = record.getSystolic_reading();
        int dias = record.getDiastolic_reading();

        switch (healthCheck(syst, dias)) {
            case 1:
                convertView.setBackgroundColor(this._context.getColor(R.color.normal));
                break;
            case 2:
                convertView.setBackgroundColor(this._context.getColor(R.color.elevated));
                break;
            case 3:
                convertView.setBackgroundColor(this._context.getColor(R.color.hbp1));
                break;
            case 4:
                convertView.setBackgroundColor(this._context.getColor(R.color.hbp2));
                break;
            case 5:
                convertView.setBackgroundColor(this._context.getColor(R.color.hypertensive));
                break;
            case 6:
                break;
        }

        tvName.setText(record.getName());
        String time = "Date: " + record.getMonthStr() + " " + record.getDay() + " " + record.getYear() + "\n" + "Time: " + record.getTime();
        tvTime.setText(time);
        tvSystolic.setText(String.valueOf(syst));
        tvDiastolic.setText(String.valueOf(dias));
        return convertView;
    }

    public int healthCheck(int syst, int dias) {
        if (syst > 180 || dias > 120) {
            return 5;
        } else if(syst < 120 && dias < 80) {
            // Normal
            return 1;
        } else if ((syst <= 129 && syst >= 120) && dias < 80) {
            // Elevated
            return 2;
        } else if ((syst <= 139 && syst >= 130) || (dias <= 89 && dias >= 80)) {
            // High blood pressure stage 1
            return 3;
        } else if ((syst <= 180 && syst >= 140) || (dias <= 120 && dias >= 90)) {
            // High blood pressure stage 2
            return 4;
        } else {
            return 6;
        }
    }

}
