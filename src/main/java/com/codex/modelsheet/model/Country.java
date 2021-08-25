package com.codex.modelsheet.model;

public class Country {
    private String type;
    private String sql;

    public String getType() { return type; }
    public void setType(String value) { this.type = value; }

    public String getSQL() { return sql; }
    public void setSQL(String value) { this.sql = value; }

    @Override
    public String toString() {
        return "Country{" +
                "type='" + type + '\'' +
                ", sql='" + sql + '\'' +
                '}';
    }
}