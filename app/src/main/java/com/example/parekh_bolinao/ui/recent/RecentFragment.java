package com.example.parekh_bolinao.ui.recent;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.parekh_bolinao.MainActivity;
import com.example.parekh_bolinao.R;
import com.example.parekh_bolinao.Record;
import com.example.parekh_bolinao.RecordAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RecentFragment extends Fragment {

    private RecentViewModel recentViewModel;
    private Activity main;
    private View root;
    ListView lv;
    List<Record> recordList;
    DatabaseReference mDatabase;
    ValueEventListener dataChangeListener;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        recentViewModel =
                ViewModelProviders.of(this).get(RecentViewModel.class);
        root = inflater.inflate(R.layout.fragment_recent, container, false);
        lv = root.findViewById(R.id.recent_entries_list);
        recordList = new ArrayList<>();
        mDatabase = ((MainActivity)getActivity()).getmDatabase();

        lv.setOnItemLongClickListener((parent, view, position, id) -> {
            Record record = recordList.get(position);

            showUpdateDialog(record.getName(),
                    record.getSystolic_reading(),
                    record.getDiastolic_reading(),
                    record.getID(),
                    record.getParent_id());

            return false;
        });

        return root;
    }

    public void onStart() {
        super.onStart();
        dataChangeListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                recordList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    for(DataSnapshot recordSnapshot : ds.getChildren()) {
                        Record record = recordSnapshot.getValue(Record.class);
                        recordList.add(record);
                        Log.d("User", record.getName());
                    }
                }
                RecordAdapter adapter = new RecordAdapter(getActivity(), recordList);
                lv.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        };
        mDatabase.addValueEventListener(dataChangeListener);
    }

    public void onStop() {
        super.onStop();
        mDatabase.removeEventListener(dataChangeListener);
    }

    private void updateRecord(String name, int syst, int dias, String id, String parentId) {
        DatabaseReference nameRef = mDatabase.child(parentId).child(id).child("name");
        DatabaseReference systRef = mDatabase.child(parentId).child(id).child("systolic_reading");
        DatabaseReference diasRef = mDatabase.child(parentId).child(id).child("diastolic_reading");

        Task setValueTask1 = nameRef.setValue(name);
        Task setValueTask2 = systRef.setValue(syst);
        Task setValueTask3 = diasRef.setValue(dias);

        setValueTask1.addOnSuccessListener(o -> Toast.makeText(getActivity(),
                "Record Updated.",Toast.LENGTH_LONG).show());
        setValueTask1.addOnFailureListener(e -> Toast.makeText(getActivity(),
                "Something went wrong.\n" + e.toString(),
                Toast.LENGTH_SHORT).show());
    }

    private void showUpdateDialog(String name, int syst, int dias, String id, String parentId) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.update_record, null);
        dialogBuilder.setView(dialogView);

        EditText etName = dialogView.findViewById(R.id.update_name);
        EditText etSyst = dialogView.findViewById(R.id.update_systolic);
        EditText etDias = dialogView.findViewById(R.id.update_diastolic);
        Button btnUpdate = dialogView.findViewById(R.id.update_button);
        Button btnDelete = dialogView.findViewById(R.id.delete_button);

        dialogBuilder.setTitle("Update " + name + "\'s record");
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameEdited = name;
                int systEdited = syst;
                int diasEdited = dias;
                String tempName = etName.getText().toString();
                if (tempName != null) {
                    nameEdited = tempName;
                }
                String tempSyst = etSyst.getText().toString();
                if (tempSyst != null) {
                    systEdited = Integer.parseInt(tempSyst);
                }
                String tempDias = etDias.getText().toString();
                if (tempDias != null) {
                    diasEdited = Integer.parseInt(tempDias);
                }

                if (TextUtils.isEmpty(name)) {
                    etName.setError("This ");
                }

                updateRecord(nameEdited, systEdited, diasEdited, id, parentId);
                alertDialog.dismiss();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference dbRef = mDatabase.child(parentId).child(id);
                Task setRemoveTask = dbRef.removeValue();
                setRemoveTask.addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
                        Toast.makeText(getActivity(), "Student Deleted.",Toast.LENGTH_LONG).show();
                    }
                });
                setRemoveTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(),
                                "Something went wrong.\n" + e.toString(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
                alertDialog.dismiss();
            }
        });
    }
}
