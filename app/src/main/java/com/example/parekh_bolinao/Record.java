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
    int day;
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
        this.day = calendar.get(Calendar.DATE);
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
    return month;
    }

    public String getMonthStr() {
        String s = "";
        switch (month) {
            case 1:
                s = "January";
            case 2:
                s = "February";
            case 3:
                s = "March";
            case 4:
                s = "April";
            case 5:
                s = "May";
            case 6:
                s = "June";
            case 7:
                s = "July";
            case 8:
                s = "August";
            case 9:
                s = "September";
            case 10:
                s = "October";
            case 11:
                s = "November";
            case 12:
                s = "December";
            }
        return s;
    }
    public String getTime () {
        return this.time;
    }
    public void setTime (String time){
        this.time = time;
    }
    public String getID () {
        return this.id;
    }
    public int getDay () {
        return day;
    }
}
