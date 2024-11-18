package org.mf.model;

public class Ingredient {

    private double amount;
    private String name;
    private String measurement;


    public double getAmount() {
        return amount;
    }

    public String getName() {
        return name;
    }

    public String getMeasurement() {
        return measurement;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }
}
