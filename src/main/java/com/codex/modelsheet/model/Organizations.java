package com.codex.modelsheet.model;

public class Organizations {
    private String relationship;
    private String sql;

    public String getRelationship() { return relationship; }
    public void setRelationship(String value) { this.relationship = value; }

    public String getSQL() { return sql; }
    public void setSQL(String value) { this.sql = value; }

    @Override
    public String toString() {
        return "Organizations{" +
                "relationship='" + relationship + '\'' +
                ", sql='" + sql + '\'' +
                '}';
    }
}