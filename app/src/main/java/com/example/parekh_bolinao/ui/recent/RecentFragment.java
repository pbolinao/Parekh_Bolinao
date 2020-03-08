package com.example.parekh_bolinao.ui.recent;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class RecentFragment extends Fragment {

    private RecentViewModel recentViewModel;
    private Activity main;
    private View root;
    ListView lv;
    ArrayList<Record> recordList;
    DatabaseReference mDatabase;
    ValueEventListener dataChangeListener;
    RecordAdapter adapter;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        recentViewModel =
                ViewModelProviders.of(this).get(RecentViewModel.class);
        root = inflater.inflate(R.layout.fragment_recent, container, false);
        lv = root.findViewById(R.id.recent_entries_list);

        mDatabase = ((MainActivity)getActivity()).getDb();
        if (savedInstanceState != null) {
            recordList = (ArrayList<Record>) savedInstanceState.getSerializable("records");
        } else {
            recordList = ((MainActivity)getActivity()).getRecords();
        }

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

    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putSerializable("records", recordList);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onStart() {
        super.onStart();
        recordList.sort((r1, r2) -> r2.getCalendar().compareTo(r1.getCalendar()));
        adapter = new RecordAdapter(getActivity(), recordList);
        lv.setAdapter(adapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void updateRecord(String name, int syst, int dias, String id, String parentId, Record r) {
        DatabaseReference nameRef = mDatabase.child(parentId).child(id).child("name");
        DatabaseReference systRef = mDatabase.child(parentId).child(id).child("systolic_reading");
        DatabaseReference diasRef = mDatabase.child(parentId).child(id).child("diastolic_reading");

        recordList.remove(r);
        r.setName(name);
        r.setSystolic_reading(syst);
        r.setDiastolic_reading(dias);
        recordList.add(r);
        recordList.sort((r1, r2) -> r2.getCalendar().compareTo(r1.getCalendar()));

        Task setValueTask1 = nameRef.setValue(name);
        Task setValueTask2 = systRef.setValue(syst);
        Task setValueTask3 = diasRef.setValue(dias);

        setValueTask1.addOnSuccessListener(o -> Toast.makeText(getActivity(),
                "Record Updated.",Toast.LENGTH_LONG).show());
        setValueTask1.addOnFailureListener(e -> Toast.makeText(getActivity(),
                "Something went wrong.\n" + e.toString(),
                Toast.LENGTH_SHORT).show());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
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

        Record[] r = recordList.stream().filter(a->a.getID().equals(id)).toArray(Record[]::new);

        btnUpdate.setOnClickListener(v -> {
            String nameEdited = name;
            int systEdited = syst;
            int diasEdited = dias;
            String tempName = etName.getText().toString();
            if (!tempName.trim().isEmpty()) {
                nameEdited = tempName;
            }
            String tempSyst = etSyst.getText().toString();
            if (!tempSyst.trim().isEmpty()) {
                systEdited = Integer.parseInt(tempSyst);
            }
            String tempDias = etDias.getText().toString();
            if (!tempDias.trim().isEmpty()) {
                diasEdited = Integer.parseInt(tempDias);
            }

            if (TextUtils.isEmpty(name)) {
                etName.setError("This ");
            }

            updateRecord(nameEdited, systEdited, diasEdited, id, parentId, r[0]);
            alertDialog.dismiss();
        });

        btnDelete.setOnClickListener(v -> {
            mDatabase = ((MainActivity)getActivity()).getDb();
            DatabaseReference dbRef = mDatabase.child(parentId).child(id);
            Task setRemoveTask = dbRef.removeValue();
            setRemoveTask.addOnSuccessListener(o -> Toast.makeText(getActivity(), "Student Deleted.",Toast.LENGTH_LONG).show());
            setRemoveTask.addOnFailureListener(e -> Toast.makeText(getActivity(),
                    "Something went wrong.\n" + e.toString(),
                    Toast.LENGTH_SHORT).show());
            alertDialog.dismiss();

            recordList.remove(r[0]);
            adapter.notifyDataSetChanged();
        });
    }
}
