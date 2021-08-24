package com.codex.modelsheet.controller;

import com.codex.modelsheet.model.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TmlPojoToMsPojo {

    public ModelSheet convertToGSPOJO(EDoc.ObjectEDocProto.Builder worksheetBuilder, List<EDoc.ObjectEDocProto.Builder> tableslist){
        ModelSheet modelSheet = new ModelSheet();

        if(worksheetBuilder.hasWorksheet()){
            List<WorkSheet> workSheetList = new ArrayList<>();
            for(EDoc.Identity table : worksheetBuilder.getWorksheet().getTablesList()){
                WorkSheet workSheet = new WorkSheet();
                workSheet.setGuid(worksheetBuilder.getGuid());
                workSheet.setWorksheetName(worksheetBuilder.getWorksheet().getName());
                workSheet.setTables(table.getName());
                workSheet.setByPassRls(String.valueOf(worksheetBuilder.getWorksheet().getProperties().getIsBypassRls()));
                workSheet.setProgressiveJoin(String.valueOf(worksheetBuilder.getWorksheet().getProperties().getJoinProgressive()));
                workSheetList.add(workSheet);
            }
            modelSheet.setWorkSheets(workSheetList);

        }

        for (EDoc.ObjectEDocProto.Builder tableBuilder : tableslist) {
            if (tableBuilder.hasTable()) {
                List<Tables> tables = new ArrayList<>();
                List<String> tableNames = new ArrayList<>();

                if (tableBuilder.getTable().getJoinsWithCount() > 0) {
                    for (EDoc.RelationEDocProto joinsWith : tableBuilder.getTable().getJoinsWithList()) {
                        Tables table = new Tables();
                        table.setConnection(tableBuilder.getTable().getConnection().getName());
                        table.setDatabase(tableBuilder.getTable().getDb());
                        table.setSchema(tableBuilder.getTable().getSchema());
                        table.setDbTable(tableBuilder.getTable().getDbTable());
                        table.setTable(tableBuilder.getTable().getName());
                        table.setJoinName(joinsWith.getName());
                        table.setJoinsWith(joinsWith.getDestination().getName());
                        table.setJoinCondition(joinsWith.getOn());

                        if (worksheetBuilder.hasWorksheet()) {//TODO we need to fetch from worksheet
                            worksheetBuilder.getWorksheet().getJoinsList()
                                    .parallelStream().filter(join -> join.getName().equals(joinsWith.getName()))
                                    .forEach(join -> {
                                        if (join.getIsOneToOne()) {
                                            table.setJoinCardinality("One to One");
                                        }else {
                                            table.setJoinCardinality("Many to One");
                                        }
                                        table.setJoinType(join.getType());
                                    });
                                    //.findFirst().get().getIsOneToOne();
                            /*if (isOneToOne) {
                                table.setJoinCardinality("One to One");
                            }else {
                                table.setJoinCardinality("Many to One");
                            }*/
                        }else {
                            table.setJoinType(joinsWith.getType());
                            table.setJoinCardinality("One to One");
                        }
                        tables.add(table);
                        tableNames.add(tableBuilder.getTable().getName());
                    }
                }else{
                    Tables table = new Tables();
                    table.setConnection(tableBuilder.getTable().getConnection().getName());
                    table.setDatabase(tableBuilder.getTable().getDb());
                    table.setSchema(tableBuilder.getTable().getSchema());
                    table.setDbTable(tableBuilder.getTable().getDbTable());
                    table.setTable(tableBuilder.getTable().getName());
                    tables.add(table);
                }
                modelSheet.getTables().addAll(tables);

                String tableId = "";
                List<String> worksheetColumns = new ArrayList<>();
                Map<String, EDoc.WorksheetEDocProto.WorksheetColumn> worksheetColumnHashMap = new HashMap<>();
                if (worksheetBuilder.hasWorksheet()) {
                    for (EDoc.TablePath tablePathProto : worksheetBuilder.getWorksheet().getTablePathsList()){
                        if (tableBuilder.getTable().getName().equals(tablePathProto.getTable())){
                            tableId = tablePathProto.getId();
                        }
                    }
                    for (EDoc.WorksheetEDocProto.WorksheetColumn worksheetColumn : worksheetBuilder.getWorksheet().getWorksheetColumnsList()) {
                            if (worksheetColumn.getColumnId().contains(tableId + "::")) {
                                worksheetColumns.add(worksheetColumn.getColumnId().replace(tableId + "::", ""));
                                worksheetColumnHashMap.put(worksheetColumn.getColumnId().replace(tableId + "::", ""),worksheetColumn);
                            }

                    }
                }

                /*List<String> tableIds = new ArrayList<>();
                List<String> worksheetColumns = new ArrayList<>();
                Map<String, EDoc.WorksheetEDocProto.WorksheetColumn> worksheetColumnHashMap = new HashMap<>();
                if (worksheetBuilder.hasWorksheet()) {
                    worksheetBuilder.getWorksheet().getTablePathsList().parallelStream()
                            .filter(tp -> tableNames.contains(tp.getTable()))
                            .filter(tp -> tableIds.add(tp.getId()));
                    for (EDoc.WorksheetEDocProto.WorksheetColumn worksheetColumn : worksheetBuilder.getWorksheet().getWorksheetColumnsList()) {
                        for (String id : tableIds) {
                            if (worksheetColumn.getColumnId().contains(id + "::")) {
                                worksheetColumns.add(worksheetColumn.getColumnId().replace(id + "::", ""));
                                worksheetColumnHashMap.put(worksheetColumn.getColumnId().replace(id + "::", ""),worksheetColumn);
                            }
                        }
                    }
                }*/

                List<Attribute> attributes = new ArrayList<>();
                for (EDoc.LogicalTableEDocProto.LogicalColumnEDocProto attributeProto : tableBuilder.getTable().getColumnsList()) {
                    Attribute attribute = new Attribute();
                    attribute.setTable(tableBuilder.getTable().getName());
                    attribute.setColumn(attributeProto.getName());
                    attribute.setDataType(attributeProto.getDbColumnProperties().getDataType());
                    if (worksheetColumns.contains(attributeProto.getDbColumnName())) {
                        attribute.setWorksheetColumnFlag("Y");
                        EDoc.WorksheetEDocProto.WorksheetColumn worksheetColumn = worksheetColumnHashMap.get(attributeProto.getDbColumnName());

                        //TODO column name datatype should be  from table's attribute as we have dependency on table paths
                        //attribute.setColumn(worksheetColumn.getName());
                        //attribute.setDataType(worksheetColumn.getDbColumnProperties().getDataType());
                        attribute.setWorkSheetColumn(worksheetColumn.getName());
                        attribute.setDescription(worksheetColumn.getDescription());
                        attribute.setColumnType(worksheetColumn.getProperties().getColumnType());
                        attribute.setAdditive(String.valueOf(worksheetColumn.getProperties().getIsAdditive()));
                        attribute.setAggregation(worksheetColumn.getProperties().getAggregation());
                        attribute.setHiddenAttribute(String.valueOf(worksheetColumn.getProperties().getIsHidden()));
                        if (worksheetColumn.getProperties().getSynonymsCount() > 0) {
                            attribute.setSynonyms(worksheetColumn.getProperties().getSynonymsList().toString());
                        }
                        //attribute.setSuggestedValue(attributeProto.getProperties().get); //TODO need find exact field form ts wrapper
                        //attribute.setGeoConfig(attributeProto.getProperties().getGeoConfig().get); TODO GeoConfig is a bean
                        attribute.setIndexType(worksheetColumn.getProperties().getIndexType());
                        attribute.setIndexPriority(String.valueOf(worksheetColumn.getProperties().getIndexPriority()));
                        attribute.setFormatPattern(worksheetColumn.getProperties().getFormatPattern());
                        attribute.setCurrencyType(String.valueOf(worksheetColumn.getProperties().getCurrencyType()));
                        attribute.setAttributeDimension(String.valueOf(worksheetColumn.getProperties().getIsAttributionDimension()));
                        attribute.setSpotIqPreference(worksheetColumn.getProperties().getSpotiqPreference());
                        attribute.setCalenderType(worksheetColumn.getProperties().getCalendar());
                    }else {
                        attribute.setWorkSheetColumn(attributeProto.getDbColumnName());
                        attribute.setColumn(attributeProto.getDbColumnName());
                        attribute.setDescription(attributeProto.getDescription());
                        attribute.setDataType(attributeProto.getDbColumnProperties().getDataType());
                        attribute.setColumnType(attributeProto.getProperties().getColumnType());
                        attribute.setAdditive(String.valueOf(attributeProto.getProperties().getIsAdditive()));
                        attribute.setAggregation(attributeProto.getProperties().getAggregation());
                        attribute.setHiddenAttribute(String.valueOf(attributeProto.getProperties().getIsHidden()));
                        if (attributeProto.getProperties().getSynonymsCount() > 0) {
                            attribute.setSynonyms(attributeProto.getProperties().getSynonymsList().toString());
                        }
                        //attribute.setSuggestedValue(attributeProto.getProperties().get); //TODO need find exact field form ts wrapper
                        //attribute.setGeoConfig(attributeProto.getProperties().getGeoConfig().get); TODO GeoConfig is a bean
                        attribute.setIndexType(attributeProto.getProperties().getIndexType());
                        attribute.setIndexPriority(String.valueOf(attributeProto.getProperties().getIndexPriority()));
                        attribute.setFormatPattern(attributeProto.getProperties().getFormatPattern());
                        attribute.setCurrencyType(String.valueOf(attributeProto.getProperties().getCurrencyType()));
                        attribute.setAttributeDimension(String.valueOf(attributeProto.getProperties().getIsAttributionDimension()));
                        attribute.setSpotIqPreference(attributeProto.getProperties().getSpotiqPreference());
                        attribute.setCalenderType(attributeProto.getProperties().getCalendar());
                    }
                    attributes.add(attribute);
                }
                modelSheet.getAttributes().addAll(attributes);

            }
        }
        return modelSheet;
    }

}
