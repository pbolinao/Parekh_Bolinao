package com.example.parekh_bolinao.ui.home;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.parekh_bolinao.MainActivity;
import com.example.parekh_bolinao.R;
import com.example.parekh_bolinao.Record;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);

        root = inflater.inflate(R.layout.fragment_home, container, false);
        return root;
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

    public void onStart() {
        super.onStart();
        View btn = root.findViewById(R.id.btn_check);

        btn.setOnClickListener((v) -> {
            // Store in database, display condition
            EditText nameEdit = root.findViewById(R.id.name_edit);
            EditText systEdit = root.findViewById(R.id.systolic_edit);
            EditText diasEdit = root.findViewById(R.id.diastolic_edit);

            String user_name = nameEdit.getText().toString();
            int systolicRead = Integer.valueOf(systEdit.getText().toString());
            int diastolicRead = Integer.valueOf(diasEdit.getText().toString());

            Record record = new Record(user_name, systolicRead, diastolicRead);

            DatabaseReference mDatabase = ((MainActivity)getActivity()).getmDatabase();
            // Get an ID for the entry from firebase
            String id = mDatabase.push().getKey();
            assert id != null;
            Task insert = mDatabase.child("Records").child(id).setValue(record);

            insert.addOnSuccessListener((o) -> {
                Toast.makeText(getActivity(),"Record added.",Toast.LENGTH_LONG).show();
                nameEdit.setText("");
                systEdit.setText("");
                diasEdit.setText("");
            });

            insert.addOnFailureListener((o) -> Toast.makeText(getActivity(),
                    "Something went wrong! Please check your connection and try again later.",
                    Toast.LENGTH_LONG).show());

            if (checkHealth(systolicRead, diastolicRead)) {
                String alert_message = "WARNING! Hypertensive Crisis. Consult your doctor IMMEDIATELY.";
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage(alert_message);
                builder.setNegativeButton("Close", (dialog, id1) -> dialog.cancel());
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }
}