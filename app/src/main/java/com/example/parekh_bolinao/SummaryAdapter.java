package com.example.parekh_bolinao;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class SummaryAdapter extends ArrayAdapter<Summary> {
    Context _context;

    public SummaryAdapter(@NonNull Context context, ArrayList<Summary> summaries) {
        super(context, 0, summaries);
        _context = context;
    }

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Activity activity = (Activity) _context;

        // Get the data item for this position
        Summary row = getItem(position);

//         Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.summary_row, parent, false);
        }

        // Lookup view for data population
        TextView field1 = convertView.findViewById(R.id.name_val);
        field1.setText(row.getName());

        // Lookup view for data population
        TextView field2 = convertView.findViewById(R.id.sys_val);
        field2.setText(String.format("%f", row.getSyst()));

        // Lookup view for data population
        TextView field3 = convertView.findViewById(R.id.dia_val);
        field3.setText("" + row.getDia());

        // Lookup view for data population
        TextView field4 = convertView.findViewById(R.id.cond_val);
        field4.setText(String.format("%s", row.getAvgCond()));

        // Return the completed view to render on screen
        return convertView;
    }
}

