package com.example.parekh_bolinao;

import com.google.firebase.database.IgnoreExtraProperties;
import java.util.Calendar;
import java.util.Date;

@IgnoreExtraProperties
public class Record {

    public String name;
    public int systolic_reading;
    public int diastolic_reading;
    public int year;
    public int month;
    public String time;

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
        String hour = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
        String min = String.valueOf(calendar.get(Calendar.MINUTE));
        String sec = String.valueOf(calendar.get(Calendar.SECOND));
        this.time = hour + ":" + min + ":" + sec;
    }

    public String getName() {
        return this.name;
    }

}
