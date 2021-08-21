package com.codex.modelsheet.controller;

import com.codex.modelsheet.model.Attribute;
import com.codex.modelsheet.model.ModelSheet;
import com.codex.modelsheet.model.Tables;
import com.codex.modelsheet.model.WorkSheet;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

public class ExcelToExcelPojo {
    public ModelSheet getModelSheet(String spreadsheetId) throws GeneralSecurityException, IOException {
        SheetsQuickstart sheetsQuickstart = new SheetsQuickstart();
       // String spreadsheetId = "1iztXkbN3v1nbI3GoXoIeGvznjvKlOMGzY4xG9ZM0RXE";
        Sheets service = sheetsQuickstart.getService();
        String range = "TABLE";
        ValueRange tableSheet = sheetsQuickstart.getSingleSheet(service,spreadsheetId,range);
        range = "ATTRIBUTES";
        ValueRange attributeSheet = sheetsQuickstart.getSingleSheet(service,spreadsheetId,range);
        range = "WORKSHEET";
        ValueRange workSheet = sheetsQuickstart.getSingleSheet(service,spreadsheetId,range);
       // System.out.println("worksheet::"+workSheet);
        List<Tables> tablesList = new ArrayList<>();
        WorkSheet workSheet1 = new WorkSheet();
        List<WorkSheet> workSheetList = new ArrayList<>();
        Tables tables = new Tables();
        Attribute attribute = new Attribute();
        List<Attribute> attributes = new ArrayList<>();
        ModelSheet modelSheet = new ModelSheet();
        int count =1;
        for(List tableValue: tableSheet.getValues()){
            Object newobj = tableValue;
            tables = new Tables();
            if(count !=1){
                if(tableValue.size() >=1){
                    tables.setConnection(tableValue.get(0).toString());
                }
                if(tableValue.size() >=2){
                    tables.setDatabase(tableValue.get(1).toString());
                }
                if(tableValue.size() >=3){
                    tables.setSchema(tableValue.get(2).toString());
                }
                if(tableValue.size() >=4){
                    tables.setDbTable(tableValue.get(3).toString());
                }
                if(tableValue.size() >=5){
                    tables.setTable(tableValue.get(4).toString());
                }
                if(tableValue.size() >=6){
                    tables.setJoinName(tableValue.get(5).toString());
                }if(tableValue.size() >=7){
                    tables.setJoinsWith(tableValue.get(6).toString());
                }if(tableValue.size() >=8){
                    tables.setJoinType(tableValue.get(7).toString());
                }if(tableValue.size() >=9){
                    tables.setJoinCardinality(tableValue.get(8).toString());
                }if(tableValue.size() >=10){
                    tables.setJoinCondition(tableValue.get(9).toString());
                }
            }
            if(count !=1) {
                tablesList.add(tables);
            }
            ++count;
        }
        count =1;
        for(List attributeValue: attributeSheet.getValues()){
            attribute = new Attribute();
            if(count !=1){
                if(attributeValue.size() >=1){
                    attribute.setTable(attributeValue.get(0).toString());
                }if(attributeValue.size() >=2){
                    attribute.setColumn(attributeValue.get(1).toString());
                }
                if(attributeValue.size() >=3){
                    attribute.setDescription(attributeValue.get(2).toString());
                }
                if(attributeValue.size() >=4){
                    attribute.setDataType(attributeValue.get(3).toString());
                }
                if(attributeValue.size() >=5){
                    attribute.setColumnType(attributeValue.get(4).toString());
                }
                if(attributeValue.size() >=6){
                    attribute.setAdditive(attributeValue.get(5).toString());
                }
                if(attributeValue.size() >=7){
                    attribute.setAggregation(attributeValue.get(6).toString());
                }
                if(attributeValue.size() >=8){
                    attribute.setHiddenAttribute(attributeValue.get(7).toString());
                }
                if(attributeValue.size() >=9){
                    attribute.setSynonyms(attributeValue.get(8).toString());
                }if(attributeValue.size() >=10){
                    attribute.setSuggestedValue(attributeValue.get(9).toString());
                }if(attributeValue.size() >=11){
                    attribute.setGeoConfig(attributeValue.get(10).toString());
                }if(attributeValue.size() >=12){
                    attribute.setIndexType(attributeValue.get(11).toString());
                }if(attributeValue.size() >=13){
                    attribute.setIndexPriority(attributeValue.get(12).toString());
                }if(attributeValue.size() >=14){
                    attribute.setFormatPattern(attributeValue.get(13).toString());
                }if(attributeValue.size() >=15){
                    attribute.setCurrencyType(attributeValue.get(14).toString());
                }if(attributeValue.size() >=16){
                    attribute.setAttributeDimension(attributeValue.get(15).toString());
                }if(attributeValue.size() >=17){
                    attribute.setSpotIqPreference(attributeValue.get(16).toString());
                }if(attributeValue.size() >=18){
                    attribute.setCalenderType(attributeValue.get(17).toString());
                }if(attributeValue.size() >=19){
                    attribute.setWorksheetColumn(attributeValue.get(18).toString());
                }


            } if(count !=1) {
                attributes.add(attribute);
            }
            ++count;
        }
        count =1;
        for(List worksheetValue: workSheet.getValues()){
            workSheet1 = new WorkSheet();
            if(count !=1){
                if(worksheetValue.size() >=1){
                    workSheet1.setWorksheetName(worksheetValue.get(0).toString());
                } if(worksheetValue.size() >=2){
                    workSheet1.setTables(worksheetValue.get(1).toString());
                } if(worksheetValue.size() >=3){
                    workSheet1.setByPassRls(worksheetValue.get(2).toString());
                } if(worksheetValue.size() >=4){
                    workSheet1.setProgressiveJoin(worksheetValue.get(3).toString());
                }

            }
            if(count != 1){
                workSheetList.add(workSheet1);
            }
            ++count;
        }

        modelSheet.setTables(tablesList);
        modelSheet.setAttributes(attributes);
        modelSheet.setWorkSheets(workSheetList);
        System.out.println("worksheet model:: "+modelSheet);
        return modelSheet;
    }
}
