package com.codex.modelsheet.model;

public class WorkSheet {
    private String worksheetName;
    private String tables;
    private String byPassRls;
    private String progressiveJoin;

    public WorkSheet() {
        this.worksheetName = "";
        this.tables = "";
        this.byPassRls = "";
        this.progressiveJoin = "";
    }

    public String getWorksheetName() {
        return worksheetName;
    }

    public void setWorksheetName(String worksheetName) {
        this.worksheetName = worksheetName;
    }

    public String getTables() {
        return tables;
    }

    public void setTables(String tables) {
        this.tables = tables;
    }

    public String getByPassRls() {
        return byPassRls;
    }

    public void setByPassRls(String byPassRls) {
        this.byPassRls = byPassRls;
    }

    public String getProgressiveJoin() {
        return progressiveJoin;
    }

    public void setProgressiveJoin(String progressiveJoin) {
        this.progressiveJoin = progressiveJoin;
    }

    @Override
    public String toString() {
        return "WorkSheet{" +
                "worksheetName='" + worksheetName + '\'' +
                ", tables='" + tables + '\'' +
                ", byPassRls='" + byPassRls + '\'' +
                ", progressiveJoin='" + progressiveJoin + '\'' +
                '}';
    }
}
