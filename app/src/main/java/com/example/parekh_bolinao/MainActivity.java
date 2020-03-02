package com.example.parekh_bolinao;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
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

        mDatabase = FirebaseDatabase.getInstance().getReference();

//        User user = new User("afas", "sfsafd@fds.asf");

        Log.d("---------------", "-----------------");

//        Task setValueTask = mDatabase.child("users").child("34eqwsda").setValue(user);
//        mDatabase.child("Hello").child("android").setValue("World");

//        setValueTask.addOnSuccessListener(new OnSuccessListener() {
//            @Override
//            public void onSuccess(Object o) {
//                Toast.makeText(MainActivity.this,"Student added.",Toast.LENGTH_LONG).show();
//                Log.d("---------------", "s-----------------");
//            }
//        });
//
//        setValueTask.addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(MainActivity.this,
//                        "something went wrong.\n" + e.toString(),
//                        Toast.LENGTH_SHORT).show();
//                Log.d("---------------", "f-----------------");
//            }
//        });
//
//        Log.d("---------------", "End-----------------");

    }

    public String checkHealth(int syst, int dias) {
        if (syst < 120 && dias < 80) {
            return "normal";
        } else if (syst <= 129 && syst >= 120 && dias < 80) {
            return "elevated";
        } else if ((syst <= 139 && syst >= 130) || (dias >= 80 && dias <= 89)) {
            return "hbp1";
        } else if ((syst <= 179 && syst >= 140) || (dias >= 90 && dias <= 120)) {
            return "hbp2";
        } else {
            return "Hypertensive Crisis";
        }
    }

    public void onCheckClick(View v) {
        // Store in database, display condition
        EditText nameEdit = findViewById(R.id.name_edit);
        EditText systEdit = findViewById(R.id.systolic_edit);
        EditText diasEdit = findViewById(R.id.diastolic_edit);

        String user_name = nameEdit.getText().toString();
        int systolicRead = Integer.valueOf(systEdit.getText().toString());
        int diastolicRead = Integer.valueOf(diasEdit.getText().toString());

        Record record = new Record(user_name, systolicRead, diastolicRead);
        String id = mDatabase.push().getKey();
        mDatabase.child("Records").child(id).setValue(record);

        String alert_message = checkHealth(systolicRead, diastolicRead);
        builder.setMessage(alert_message);
        builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        alert = builder.create();
        alert.show();
    }

}
