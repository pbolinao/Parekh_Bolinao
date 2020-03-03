package com.example.parekh_bolinao;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    AlertDialog.Builder builder;
    AlertDialog alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);

        builder = new AlertDialog.Builder(MainActivity.this);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        // Get a reference of the database
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    /**
     * Checks the users health based on their systolic and
     * diastolic readings.
     * @param syst int
     * @param dias int
     * @return an integer from 1-5 representing normal,
     *          elevated, high blood pressure (stage 1 and 2),
     *          and hypertensive crisis respectively
     */
    public boolean checkHealth(int syst, int dias) {
        return  (syst >= 180 || dias <= 120);
    }

    protected void onStart() {
        super.onStart();
        View btn = findViewById(R.id.btn_check);

        btn.setOnClickListener((v) -> {
            // Store in database, display condition
            EditText nameEdit = findViewById(R.id.name_edit);
            EditText systEdit = findViewById(R.id.systolic_edit);
            EditText diasEdit = findViewById(R.id.diastolic_edit);

            String user_name = nameEdit.getText().toString();
            int systolicRead = Integer.valueOf(systEdit.getText().toString());
            int diastolicRead = Integer.valueOf(diasEdit.getText().toString());

            Record record = new Record(user_name, systolicRead, diastolicRead);
            // Get an ID for the entry from firebase
            String id = mDatabase.push().getKey();
            assert id != null;
            Task insert = mDatabase.child("Records").child(id).setValue(record);

            insert.addOnSuccessListener((o) -> {
                Toast.makeText(MainActivity.this,"Record added.",Toast.LENGTH_LONG).show();
                nameEdit.setText("");
                systEdit.setText("");
                diasEdit.setText("");
            });

            insert.addOnFailureListener((o) -> Toast.makeText(MainActivity.this,
                    "Something went wrong! Please check your connection and try again later.",
                    Toast.LENGTH_LONG).show());

            if (checkHealth(systolicRead, diastolicRead)) {
                String alert_message = "WARNING! Hypertensive Crisis. Consult your doctor IMMEDIATELY.";
                builder.setMessage(alert_message);
                builder.setNegativeButton("Close", (dialog, id1) -> dialog.cancel());
                alert = builder.create();
                alert.show();
            }
        });
    }
}
