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

public class RecordUsersAdapter extends ArrayAdapter<Record> {

    Context _context;

    public RecordUsersAdapter(@NonNull Activity context, List<Record> recordList) {
        super(context, 0, recordList);
        _context = context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Record record = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.existing_users_row_layout, parent, false);
        }

        TextView tvName = convertView.findViewById(R.id.user_name_row);
        TextView tvID = convertView.findViewById(R.id.user_id_row);

        tvName.setText(record.getName());
        tvID.setText(record.getParent_id());
        return convertView;
    }

}
