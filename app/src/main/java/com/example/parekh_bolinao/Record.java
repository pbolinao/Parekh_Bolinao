package com.example.parekh_bolinao;

import com.google.firebase.database.IgnoreExtraProperties;
import java.util.Calendar;

@IgnoreExtraProperties
public class Record {

    public String name;
    public int systolic_reading;
    public int diastolic_reading;
    public int year;
    public int month;

    public Record() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Record(String username, int systolic, int diastolic) {
        Calendar calendar = Calendar.getInstance();
        this.name = username;
        this.systolic_reading = systolic;
        this.diastolic_reading = diastolic;
        this.year = calendar.get(Calendar.YEAR);
        this.month = calendar.get(Calendar.MONTH);
    }

}
