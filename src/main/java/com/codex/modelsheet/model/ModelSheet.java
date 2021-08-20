package com.codex.modelsheet.model;

import java.util.ArrayList;
import java.util.List;

public class ModelSheet {
    private List<WorkSheet> workSheets;
    private List<Tables> tables;
    private List<Attribute> attributes;

    public ModelSheet() {
        this.workSheets = new ArrayList<>();
        this.tables = new ArrayList<>();
        this.attributes = new ArrayList<>();
    }

    public List<WorkSheet> getWorkSheets() {
        return workSheets;
    }

    public void setWorkSheets(List<WorkSheet> workSheets) {
        this.workSheets = workSheets;
    }

    public List<Tables> getTables() {
        return tables;
    }

    public void setTables(List<Tables> tables) {
        this.tables = tables;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String toString() {
        return "ModelSheet{" +
                "workSheets=" + workSheets +
                ", tables=" + tables +
                ", attributes=" + attributes +
                '}';
    }

}
