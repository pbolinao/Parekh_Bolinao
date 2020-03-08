package com.example.parekh_bolinao;

import java.io.Serializable;

public class Summary implements Serializable {
    public String name;
    public double syst;
    public double dia;
    public String avgCond;
    public String parent_id;
    int recordCount;

    public Summary(String name, double syst, double dia, int recordCount, String parent_id) {
        this.name = name;
        this.syst = syst;
        this.dia = dia;
        this.recordCount = recordCount;
        this.avgCond = calculateCondition(syst, dia);
        this.parent_id = parent_id;
    }

    public String getName() {
        return name;
    }

    public double getSyst() {
        return syst;
    }

    public double getDia() {
        return dia;
    }

    public String getAvgCond() {
        return avgCond;
    }

    public int getRecordCount() { return recordCount; }

    public String getParent_id() { return parent_id; }

    public void setRecordCount(int count) { this.recordCount = count; }

    public void setSyst(double syst) { this.syst = syst; }

    public void setDia(double dia) {this.dia = dia; }

    private static String calculateCondition(double syst, double dias) {
        if (syst < 120 && dias < 80)
            return "Normal";
        else if (syst <= 129 && dias < 80)
            return "Elevated";
        else if (syst <= 139 || dias <= 89)
            return "High Blood Pressure (Stage 1)";
        else if (syst <= 179 || dias <= 120)
            return "High Blood Pressure (Stage 2)";
        else
            return "Hypertensive Crisis";
    }
}
