package com.codex.modelsheet.controller;

import com.codex.modelsheet.model.Attribute;
import com.codex.modelsheet.model.ModelSheet;
import com.codex.modelsheet.model.Tables;
import com.codex.modelsheet.model.WorkSheet;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ExcelPojoToExcel {
    public static void main(String... args) throws GeneralSecurityException, IOException {
        ExcelPojoToExcel excelToExcelPojo = new ExcelPojoToExcel();
        String sheetName ="Code//test";
        String spreadsheetId = "1iztXkbN3v1nbI3GoXoIeGvznjvKlOMGzY4xG9ZM0RXE";
        ExcelToExcelPojo excelPojo = new ExcelToExcelPojo();
        ModelSheet modelSheet = excelPojo.getModelSheet(spreadsheetId);
        excelToExcelPojo.dataWritingInToExcel(modelSheet,sheetName);
    }
    public void dataWritingInToExcel(ModelSheet modelSheet, String sheetName) throws GeneralSecurityException, IOException {
        ExcelToExcelPojo excelToExcelPojo = new ExcelToExcelPojo();
        SheetsQuickstart sheetsQuickstart = new SheetsQuickstart();
       // String spreadsheetId = "1iztXkbN3v1nbI3GoXoIeGvznjvKlOMGzY4xG9ZM0RXE";
       // ModelSheet modelSheet = excelToExcelPojo.getModelSheet(spreadsheetId);

        List<Sheet> sheetsNew = new ArrayList<>();
        Sheet tableSheet = new Sheet();
        SheetProperties properties = new SheetProperties();
        properties.setSheetId(1);
        properties.setTitle("TABLE");
        tableSheet.setProperties(properties);
        Sheet attributeSheet = new Sheet();
        properties = new SheetProperties();
        properties.setSheetId(2);
        properties.setTitle("ATTRIBUTES");
        attributeSheet.setProperties(properties);
        Sheet workSheetnew = new Sheet();
        properties = new SheetProperties();
        properties.setSheetId(3);
        properties.setTitle("WORKSHEET");
        workSheetnew.setProperties(properties);
        sheetsNew.add(tableSheet);
        sheetsNew.add(attributeSheet);
        sheetsNew.add(workSheetnew);
        Sheets service = sheetsQuickstart.getService();
        Spreadsheet spreadsheet = new Spreadsheet()
                .setProperties(new SpreadsheetProperties()
                        .setTitle(sheetName)).setSheets(sheetsNew);

        spreadsheet = service.spreadsheets().create(spreadsheet)
                .setFields("spreadsheetId")
                .execute();
        System.out.println("Spreadsheet ID: " + spreadsheet.getSpreadsheetId());

        List<List<Object>> workSheetObjects = new ArrayList<>();
        List<Object> newObject = new ArrayList<>();
        newObject =
                Arrays.asList("Worksheet Name", "Tables", "Bypass RLS?", "Progressive Join?");
        workSheetObjects.addAll(Collections.singleton(newObject));
        for(WorkSheet workSheet :modelSheet.getWorkSheets()){
            newObject =
                    Arrays.asList(workSheet.getWorksheetName(),workSheet.getTables(),workSheet.getByPassRls(),workSheet.getProgressiveJoin());
            workSheetObjects.addAll(Collections.singleton(newObject));
        }
        String range = "WORKSHEET";
        updateSpreadSheet(service,workSheetObjects,spreadsheet.getSpreadsheetId(),range);
        range = "TABLE";
        workSheetObjects = new ArrayList<>();
        newObject = new ArrayList<>();
        newObject =
                Arrays.asList("Connection","Database","Schema","DB Table","Table","Join name"
                        ,"Joins with","Join Type","Join Cardinality","Join condition");
        workSheetObjects.addAll(Collections.singleton(newObject));
        for(Tables table :modelSheet.getTables()){
            newObject =
                    Arrays.asList(table.getConnection(),table.getDatabase(),table.getSchema(),table.getDbTable(),table.getTable(),table.getJoinName()
                            ,table.getJoinsWith(),table.getJoinType(),table.getJoinCardinality(),table.getJoinCondition());
            workSheetObjects.addAll(Collections.singleton(newObject));
        }
        updateSpreadSheet(service,workSheetObjects,spreadsheet.getSpreadsheetId(),range);
        range = "ATTRIBUTES";
        workSheetObjects = new ArrayList<>();
        newObject = new ArrayList<>();
        newObject =
                Arrays.asList("Table","Column","Description","Data Type","Column Type","Additive","Aggregation",
                        "Hidden Attribute","Synonyms","Suggested Values in Search","GEO Config","Index Type","Index Priority"
                        ,"Format Pattern","Currency Type","Attribution Dimension","SpotIQ Preference","Calendar Type","Worksheet Column(Y/N)?");
        workSheetObjects.addAll(Collections.singleton(newObject));
        for(Attribute attribute :modelSheet.getAttributes()){
            newObject =
                    Arrays.asList(attribute.getTable(),attribute.getColumn(),attribute.getDescription(),attribute.getDataType(),attribute.getColumnType()
                            ,attribute.getAdditive(),attribute.getAggregation(),attribute.getHiddenAttribute(),attribute.getSynonyms(),attribute.getSuggestedValue()
                            ,attribute.getGeoConfig(),attribute.getIndexType(),attribute.getIndexPriority(),attribute.getFormatPattern(),attribute.getCurrencyType()
                            ,attribute.getAttributeDimension(),attribute.getSpotIqPreference(),attribute.getCalenderType(),attribute.getWorksheetColumn()
                    );
            workSheetObjects.addAll(Collections.singleton(newObject));
        }
        updateSpreadSheet(service,workSheetObjects,spreadsheet.getSpreadsheetId(),range);
    }

    private static void updateSpreadSheet(Sheets service, List<List<Object>> workSheetObjects, String spreadsheetId, String range) throws IOException {
        ValueRange body = new ValueRange()
                .setValues(workSheetObjects);
        UpdateValuesResponse result =
                service.spreadsheets().values().update(spreadsheetId, range, body)
                        .setValueInputOption("USER_ENTERED")
                        .execute();
    }
}
