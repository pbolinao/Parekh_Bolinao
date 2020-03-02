package com.example.parekh_bolinao;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Record {

    public String name;
    public int systolic_reading;
    public int diastolic_reading;

    public Record() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Record(String username, int systolic, int diastolic) {
        this.name = username;
        this.systolic_reading = systolic;
        this.diastolic_reading = diastolic;
    }

}
