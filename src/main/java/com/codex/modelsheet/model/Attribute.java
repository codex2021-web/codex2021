package com.codex.modelsheet.model;

public class Attribute {
    private String table;
    private String column;
    private String tableColumn;
    private String description;
    private String dataType;
    private String columnType;
    private String additive;
    private String aggregation;
    private String hiddenAttribute;
    private String synonyms;
    private String suggestedValue;
    private String geoConfig;
    private String indexType;
    private String indexPriority;
    private String formatPattern;
    private String currencyType;
    private String attributeDimension;
    private String spotIqPreference;
    private String calenderType;
    private String worksheetColumn;

    public Attribute(){
        this.table = "";
        this.column = "";
        this.tableColumn = "";
        this.description = "";
        this.dataType = "";
        this.columnType = "";
        this.additive = "";
        this.aggregation = "";
        this.hiddenAttribute = "";
        this.synonyms = "";
        this.suggestedValue = "";
        this.geoConfig = "";
        this.indexType = "";
        this.indexPriority = "";
        this.formatPattern = "";
        this.currencyType = "";
        this.attributeDimension = "";
        this.spotIqPreference = "";
        this.calenderType = "";
        this.worksheetColumn = "";
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getColumn() {
        return column;
    }

    public String getTableColumn() {
        return tableColumn;
    }

    public void setTableColumn(String tableColumn) {
        this.tableColumn = tableColumn;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public String getAdditive() {
        return additive;
    }

    public void setAdditive(String additive) {
        this.additive = additive;
    }

    public String getAggregation() {
        return aggregation;
    }

    public void setAggregation(String aggregation) {
        this.aggregation = aggregation;
    }

    public String getHiddenAttribute() {
        return hiddenAttribute;
    }

    public void setHiddenAttribute(String hiddenAttribute) {
        this.hiddenAttribute = hiddenAttribute;
    }

    public String getSynonyms() {
        return synonyms;
    }

    public void setSynonyms(String synonyms) {
        this.synonyms = synonyms;
    }

    public String getSuggestedValue() {
        return suggestedValue;
    }

    public void setSuggestedValue(String suggestedValue) {
        this.suggestedValue = suggestedValue;
    }

    public String getGeoConfig() {
        return geoConfig;
    }

    public void setGeoConfig(String geoConfig) {
        this.geoConfig = geoConfig;
    }

    public String getIndexType() {
        return indexType;
    }

    public void setIndexType(String indexType) {
        this.indexType = indexType;
    }

    public String getIndexPriority() {
        return indexPriority;
    }

    public void setIndexPriority(String indexPriority) {
        this.indexPriority = indexPriority;
    }

    public String getFormatPattern() {
        return formatPattern;
    }

    public void setFormatPattern(String formatPattern) {
        this.formatPattern = formatPattern;
    }

    public String getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(String currencyType) {
        this.currencyType = currencyType;
    }

    public String getAttributeDimension() {
        return attributeDimension;
    }

    public void setAttributeDimension(String attributeDimension) {
        this.attributeDimension = attributeDimension;
    }

    public String getSpotIqPreference() {
        return spotIqPreference;
    }

    public void setSpotIqPreference(String spotIqPreference) {
        this.spotIqPreference = spotIqPreference;
    }

    public String getCalenderType() {
        return calenderType;
    }

    public void setCalenderType(String calenderType) {
        this.calenderType = calenderType;
    }

    public String getWorksheetColumn() {
        return worksheetColumn;
    }

    public void setWorksheetColumn(String worksheetColumn) {
        this.worksheetColumn = worksheetColumn;
    }
}
