package com.example.parekh_bolinao;

import android.os.Parcelable;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.stream.Stream;

@IgnoreExtraProperties
public class Record implements Serializable {

    private String parent_id;
    private String name;
    public int systolic_reading;
    public int diastolic_reading;
    private int year;
    private int month;
    private int day;
    private String time;
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
    public int getMonth() { return month; }
    public void setName(String name) {this.name = name;}
    public void setSystolic_reading(int systolic_reading) {this.systolic_reading = systolic_reading;}
    public void setDiastolic_reading(int diastolic_reading) {this.diastolic_reading = diastolic_reading;}

    public String getMonthStr() {
        String s = "";
        switch (month) {
            case 0:
                s = "January";
                break;
            case 1:
                s = "February";
                break;
            case 2:
                s = "March";
                break;
            case 3:
                s = "April";
                break;
            case 4:
                s = "May";
                break;
            case 5:
                s = "June";
                break;
            case 6:
                s = "July";
                break;
            case 7:
                s = "August";
                break;
            case 8:
                s = "September";
                break;
            case 9:
                s = "October";
                break;
            case 10:
                s = "November";
                break;
            case 11:
                s = "December";
                break;
            }
        return s;
    }
    public String getTime () { return this.time; }
    public void setTime (String time){ this.time = time; }
    public String getID () { return this.id; }
    public int getDay () { return day; }

    public Date getCalendar() {
        int hour = Integer.parseInt(time.substring(0, time.indexOf(':')));
        int min = Integer.parseInt(time.substring(time.indexOf(':')+1, time.lastIndexOf(':')));
        return new Date(year, month, day, hour, min);
    }
}
