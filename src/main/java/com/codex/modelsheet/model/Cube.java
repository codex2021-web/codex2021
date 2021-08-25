package com.codex.modelsheet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Cube {
    private String name;
    private String sql;
    private Joins joins;
    private Measures measures;
    private Dimensions dimensions;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSQL() { return sql; }
    public void setSQL(String value) { this.sql = value; }

    public Joins getJoins() { return joins; }
    public void setJoins(Joins value) { this.joins = value; }

    public Measures getMeasures() { return measures; }
    public void setMeasures(Measures value) { this.measures = value; }

    public Dimensions getDimensions() { return dimensions; }
    public void setDimensions(Dimensions value) { this.dimensions = value; }

    @Override
    public String toString() {
        return "Cube{" +
                "name='" + name + '\'' +
                ", sql='" + sql + '\'' +
                ", joins=" + joins +
                ", measures=" + measures +
                ", dimensions=" + dimensions +
                '}';
    }
}