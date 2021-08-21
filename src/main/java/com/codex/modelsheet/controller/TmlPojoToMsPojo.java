package com.codex.modelsheet.controller;

import com.codex.modelsheet.model.*;
import java.util.ArrayList;
import java.util.List;

public class TmlPojoToMsPojo {

    public ModelSheet convertToGSPOJO(EDoc.ObjectEDocProto.Builder tableBuilder){
        ModelSheet modelSheet = new ModelSheet();

        if(tableBuilder.hasWorksheet()){
            List<WorkSheet> workSheetList = new ArrayList<WorkSheet>();
            for(EDoc.Identity table : tableBuilder.getWorksheet().getTablesList()){
                WorkSheet workSheet = new WorkSheet();
                workSheet.setWorksheetName(tableBuilder.getWorksheet().getName());
                workSheet.setTables(table.getName());
                workSheet.setByPassRls(String.valueOf(tableBuilder.getWorksheet().getProperties().getIsBypassRls()));
                workSheet.setProgressiveJoin(String.valueOf(tableBuilder.getWorksheet().getProperties().getJoinProgressive()));
                workSheetList.add(workSheet);
            }
            modelSheet.setWorkSheets(workSheetList);

        }
        if(tableBuilder.hasTable()){
            List<Tables> tables = new ArrayList<Tables>();
            List<String> tableNames = new ArrayList<>();

            if(tableBuilder.getTable().getJoinsWithCount() > 0){
                for(EDoc.RelationEDocProto joinsWith : tableBuilder.getTable().getJoinsWithList()){
                    Tables table = new Tables();
                    table.setConnection(tableBuilder.getTable().getConnection().getName());
                    table.setDatabase(tableBuilder.getTable().getDb());
                    table.setSchema(tableBuilder.getTable().getSchema());
                    table.setDbTable(tableBuilder.getTable().getDbTable());
                    table.setTable(tableBuilder.getTable().getName());
                    table.setJoinName(joinsWith.getName());
                    table.setJoinsWith(joinsWith.getDestination().getName());
                    table.setJoinType(joinsWith.getType());
                    if(tableBuilder.getWorksheet() != null){//TODO we need to fetch from worksheet
                        table.setJoinCardinality(tableBuilder.getWorksheet().getJoinsList()
                                .parallelStream().filter(join -> join.getName().equals(joinsWith.getName()))
                                .findFirst().get().getName());
                    }

                    table.setJoinCondition(joinsWith.getOn());
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
                tableNames.add(tableBuilder.getTable().getName());
            }
            modelSheet.setTables(tables);

            List<String> tableIds = new ArrayList<>();
            List<String> worksheetColumns = new ArrayList<>();
            if(tableBuilder.hasWorksheet()){
                tableBuilder.getWorksheet().getTablePathsList().parallelStream()
                        .filter(tp -> tableNames.contains(tp.getTable()))
                        .filter(tp -> tableIds.add(tp.getId()));
                for(EDoc.WorksheetEDocProto.WorksheetColumn worksheetColumn : tableBuilder.getWorksheet().getWorksheetColumnsList()){
                    for(String id : tableIds){
                        if(worksheetColumn.getColumnId().contains(id+"::")){
                            worksheetColumns.add(worksheetColumn.getColumnId().replace(id+"::",""));
                            //worksheetColumns.add(worksheetColumn.getColumnId().substring(worksheetColumn.getColumnId().indexOf(id+"::")));
                        }
                    }
                }
            }

            List<Attribute> attributes = new ArrayList<Attribute>();
            for(EDoc.LogicalTableEDocProto.LogicalColumnEDocProto attributeProto : tableBuilder.getTable().getColumnsList()){
                Attribute attribute = new Attribute();
                //BeanUtils.copyProperties(attribute, attributeProto);
                attribute.setTable(tableBuilder.getTable().getName());
                attribute.setColumn(attributeProto.getName());//TODO db_column name or column name
                attribute.setDescription(attributeProto.getDescription());
                attribute.setDataType(attributeProto.getDbColumnProperties().getDataType());
                attribute.setColumnType(attributeProto.getProperties().getColumnType());
                attribute.setAdditive(String.valueOf(attributeProto.getProperties().getIsAdditive()));
                attribute.setAggregation(attributeProto.getProperties().getAggregation());
                attribute.setHiddenAttribute(String.valueOf(attributeProto.getProperties().getIsHidden()));
                attribute.setSynonyms(attributeProto.getProperties().getSynonymsList().toString());//TODO it's a list
                //attribute.setSuggestedValue(attributeProto.getProperties().get); //TODO need find exact field form ts wrapper
                //attribute.setGeoConfig(attributeProto.getProperties().getGeoConfig().get); TODO GeoConfig is a bean
                attribute.setIndexType(attributeProto.getProperties().getIndexType());
                attribute.setIndexPriority(String.valueOf(attributeProto.getProperties().getIndexPriority()));
                attribute.setFormatPattern(attributeProto.getProperties().getFormatPattern());
                attribute.setCurrencyType(String.valueOf(attributeProto.getProperties().getCurrencyType()));
                attribute.setAttributeDimension(String.valueOf(attributeProto.getProperties().getIsAttributionDimension()));
                attribute.setSpotIqPreference(attributeProto.getProperties().getSpotiqPreference());
                attribute.setCalenderType(attributeProto.getProperties().getCalendar());
                if(worksheetColumns.contains(attributeProto.getDbColumnName())){
                    attribute.setWorksheetColumn("Y");
                }
                attributes.add(attribute);
            }
            modelSheet.setAttributes(attributes);

        }
        return modelSheet;
    }

}
