package com.example.parekh_bolinao;

import com.google.firebase.database.IgnoreExtraProperties;
import java.util.Calendar;
import java.util.Date;

@IgnoreExtraProperties
public class Record {

    public String parent_id;
    public String name;
    public int systolic_reading;
    public int diastolic_reading;
    public int year;
    public int month;
    public String time;
    public String id;

    public Record() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Record(String username, int systolic, int diastolic, String id, String parent_id) {
        this.parent_id = parent_id;
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
        this.id = id;
    }

    public String getParent_id() { return this.parent_id; }
    public String getName() { return this.name; }
    public int getSystolic_reading() {
        return this.systolic_reading;
    }
    public int getDiastolic_reading() {
        return this.diastolic_reading;
    }
    public int getYear() {
        return this.year;
    }
    public int getMonth() {
        return this.month;
    }
    public String getTime() {
        return this.time;
    }
    public void setTime(String time) { this.time = time; }
    public String getID() { return this.id; }

}
