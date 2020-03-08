package com.example.parekh_bolinao;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference db;
    public ArrayList<Record> records;
    public ArrayList<Summary> summaries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_recent, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        // Get a reference of the database
        db = FirebaseDatabase.getInstance().getReference();

        records = new ArrayList<>(0);
        summaries = new ArrayList<>(0);
    }

    @Override
    protected void onStart() {
        super.onStart();

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                records = new ArrayList<>(0);
                summaries = new ArrayList<>(0);
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    for(DataSnapshot recordSnapshot : ds.getChildren()) {
                        Record record = recordSnapshot.getValue(Record.class);
                        records.add(record);
                        addToSummaries(record);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Unable to add record!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void addToSummaries(Record record) {
        boolean found = false;
        String name = record.getName();
        double syst = record.getSystolic_reading();
        double dia = record.getDiastolic_reading();

        for(Summary s: summaries) {
            if (s.getName().equalsIgnoreCase(name)) {
                found = true;
                int count = s.getRecordCount();
                syst = s.getSyst();
                double syst_new = (syst * count + record.getSystolic_reading()) / (count + 1);

                dia = s.getDia();
                double dia_new = (dia * count + record.getDiastolic_reading()) / (count + 1);

                s.setRecordCount(++count);
                s.setDia(dia_new);
                s.setSyst(syst_new);
            }
        }
        if (!found) {
            summaries.add(new Summary(name, syst, dia, 1));
        }
    }

    public DatabaseReference getDb() {
        return db;
    }
}
