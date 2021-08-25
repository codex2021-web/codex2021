package com.codex.modelsheet.model;

public class Measures {
    private Country count;

    public Country getCount() { return count; }
    public void setCount(Country value) { this.count = value; }

    @Override
    public String toString() {
        return "Measures{" +
                "count=" + count +
                '}';
    }
}