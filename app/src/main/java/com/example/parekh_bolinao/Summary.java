package com.example.parekh_bolinao;

public class Summary {
    public String name;
    public double syst;
    public double dia;
    public String avgCond;

    public Summary(String name, double syst, double dia) {
        this.name = name;
        this.syst = syst;
        this.dia = dia;

        this.avgCond = "Fine OK";
    }
}
