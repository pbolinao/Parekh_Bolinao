package com.example.parekh_bolinao;

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

    public SummaryAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        _context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Activity activity = (Activity) _context;

        // Get the data item for this position
        Summary article = getItem(position);

//         Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.summary_row, parent, false);
        }

        // Lookup view for data population
//        TextView field1 = convertView.findViewById(R.id.f1);
//
//        // Populate the data into the template view using the data object
//        field1.setText(String.format("%s", article.getTitle()));
//
//        ImageView imgOnePhoto = convertView.findViewById(R.id.thumbImage);
//        if (article.getUrlToImage() != null) {
//            new ImageDownloaderTask(imgOnePhoto).execute(article.getUrlToImage());  // execute() for async tasks
//        }

        // Return the completed view to render on screen
        return convertView;
    }
}

