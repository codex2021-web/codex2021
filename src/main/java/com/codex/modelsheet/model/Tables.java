package com.codex.modelsheet.model;

public class Tables {
    private String connection;
    private String database;
    private String schema;
    private String dbTable;
    private String table;
    private String joinName;
    private String joinsWith;
    private String joinType;
    private String joinCardinality;
    private String joinCondition;

    public Tables(){
        this.connection = "";
        this.database = "";
        this.schema = "";
        this.dbTable = "";
        this.table = "";
        this.joinName = "";
        this.joinsWith = "";
        this.joinType = "";
        this.joinCardinality = "";
        this.joinCondition = "";
    }

    public String getConnection() {
        return connection;
    }

    public void setConnection(String connection) {
        this.connection = connection;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getDbTable() {
        return dbTable;
    }

    public void setDbTable(String dbTable) {
        this.dbTable = dbTable;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getJoinName() {
        return joinName;
    }

    public void setJoinName(String joinName) {
        this.joinName = joinName;
    }

    public String getJoinsWith() {
        return joinsWith;
    }

    public void setJoinsWith(String joinsWith) {
        this.joinsWith = joinsWith;
    }

    public String getJoinType() {
        return joinType;
    }

    public void setJoinType(String joinType) {
        this.joinType = joinType;
    }

    public String getJoinCardinality() {
        return joinCardinality;
    }

    public void setJoinCardinality(String joinCardinality) {
        this.joinCardinality = joinCardinality;
    }

    public String getJoinCondition() {
        return joinCondition;
    }

    public void setJoinCondition(String joinCondition) {
        this.joinCondition = joinCondition;
    }

    @Override
    public String toString() {
        return "Tables{" +
                "connection='" + connection + '\'' +
                ", database='" + database + '\'' +
                ", schema='" + schema + '\'' +
                ", dbTable='" + dbTable + '\'' +
                ", table='" + table + '\'' +
                ", joinName='" + joinName + '\'' +
                ", joinsWith='" + joinsWith + '\'' +
                ", joinType='" + joinType + '\'' +
                ", joinCardinality='" + joinCardinality + '\'' +
                ", joinCondition='" + joinCondition + '\'' +
                '}';
    }
}
