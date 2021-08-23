package com.codex.modelsheet.controller;

import com.codex.modelsheet.model.*;

import java.util.*;
import java.util.stream.Collectors;

public class MsPojoToTmlPojo {
    private java.lang.Object Object;

    public List<EDoc.ObjectEDocProto.Builder> convertToTMLPOJO(ModelSheet modelSheet) {
        List<EDoc.ObjectEDocProto.Builder> builderList = new ArrayList<>();
        EDoc.ObjectEDocProto.Builder worksheetbuilder = EDoc.ObjectEDocProto.newBuilder();


        if (modelSheet.getWorkSheets() != null && !modelSheet.getWorkSheets().isEmpty()) {
            EDoc.WorksheetEDocProto.Builder worksheetBuilder = EDoc.WorksheetEDocProto.newBuilder();
            worksheetBuilder.setName(modelSheet.getWorkSheets().get(0).getWorksheetName());
            for (WorkSheet workSheet : modelSheet.getWorkSheets()) {
                EDoc.Identity.Builder tableBuilder = EDoc.Identity.newBuilder();
                if (contentCheck(workSheet.getTables())) {
                    tableBuilder.setName(workSheet.getTables());
                }
                worksheetBuilder.addTables(tableBuilder);
            }

            //table joins logic
            for (Tables t : modelSheet.getTables()) {
                if (contentCheck(t.getJoinsWith())) {
                    EDoc.Join.Builder joinBuilder = EDoc.Join.newBuilder();
                    if (contentCheck(t.getJoinName())) {
                        joinBuilder.setName(t.getJoinName());
                    }
                    if (contentCheck(t.getTable())) {
                        joinBuilder.setSource(t.getTable());
                    }
                    if (contentCheck(t.getJoinsWith())) {
                        joinBuilder.setDestination(t.getJoinsWith());
                    }
                    if (contentCheck(t.getJoinType())) {
                        joinBuilder.setType(t.getJoinType());
                    }
                    if (contentCheck(t.getJoinCardinality())) {
                        if ("One to One".equals(t.getJoinCardinality())){
                            joinBuilder.setIsOneToOne(true);
                        }else{
                            joinBuilder.setIsOneToOne(false);
                        }
                    }
                    worksheetBuilder.addJoins(joinBuilder);
                }
            }


            //table paths logic
            Set<String> duplicateCheck = new HashSet<>();
            for (Tables t : modelSheet.getTables()) {
                if (duplicateCheck.add(t.getTable())) {
                    List<String> joinNames = new ArrayList<>();
                    for (Tables tb : modelSheet.getTables()) {
                        if (t.getTable().equals(tb.getJoinsWith())) {
                            joinNames.add(tb.getJoinName());
                        }
                    }
                    EDoc.TablePath.JoinPath.Builder tablePathJoinPathBuilder = EDoc.TablePath.JoinPath.newBuilder();
                    if (!joinNames.isEmpty()) {
                        for (String jn : joinNames) {
                            tablePathJoinPathBuilder.addJoin(jn);
                        }
                    }
                    EDoc.TablePath.Builder tablePathBuilder = EDoc.TablePath.newBuilder();
                    if (contentCheck(t.getTable())) {
                        tablePathBuilder.setId(t.getTable());
                        tablePathBuilder.setTable(t.getTable());
                    }
                    tablePathBuilder.addJoinPath(tablePathJoinPathBuilder);
                    worksheetBuilder.addTablePaths(tablePathBuilder);
                }
            }

            List<Attribute> worksheetAttributes = modelSheet.getAttributes().parallelStream().filter(attr -> "Y".equalsIgnoreCase(attr.getWorksheetColumn())).collect(Collectors.toList());
            for (Attribute attribute : worksheetAttributes) {
                EDoc.WorksheetEDocProto.WorksheetColumn.Builder worksheetColumnProto = EDoc.WorksheetEDocProto.WorksheetColumn.newBuilder();
                if (contentCheck(attribute.getColumn())) {
                    worksheetColumnProto.setName(attribute.getColumn());
                }
                if (contentCheck(attribute.getTable()) && contentCheck(attribute.getColumn())) {
                    worksheetColumnProto.setColumnId(attribute.getTable() + "::" + attribute.getColumn());
                }
                if (contentCheck(attribute.getDescription())) {
                    worksheetColumnProto.setDescription(attribute.getDescription());
                }

                EDoc.ColumnProperties.Builder columnPropertiesBuilder = EDoc.ColumnProperties.newBuilder();
                if (contentCheck(attribute.getColumnType())) {
                    columnPropertiesBuilder.setColumnType(attribute.getColumnType());
                }
                if (contentCheck(attribute.getAdditive())) {
                    columnPropertiesBuilder.setIsAdditive(Boolean.parseBoolean(attribute.getAdditive()));
                }
                if (contentCheck(attribute.getAggregation())) {
                    columnPropertiesBuilder.setAggregation(attribute.getAggregation());
                }
                if (contentCheck(attribute.getHiddenAttribute())) {
                    columnPropertiesBuilder.setIsHidden(Boolean.parseBoolean(attribute.getHiddenAttribute()));
                }
                if (contentCheck(attribute.getSynonyms())) {
                    if (attribute.getSynonyms().contains(",")){
                        for (String synonnym : attribute.getSynonyms().split(",")){
                            columnPropertiesBuilder.addSynonyms(synonnym);
                        }
                    }else{
                        columnPropertiesBuilder.addSynonyms(attribute.getSynonyms());
                    }
                }
                //attribute.setSuggestedValue(attributeProto.getProperties().get); //TODO need find exact field form ts wrapper
                //attribute.setGeoConfig(attributeProto.getProperties().getGeoConfig().get); TODO GeoConfig is a bean
                if (contentCheck(attribute.getIndexType())) {
                    columnPropertiesBuilder.setIndexType(attribute.getIndexType());
                }
                if (contentCheck(attribute.getIndexPriority())) {
                    columnPropertiesBuilder.setIndexPriority(Double.parseDouble(attribute.getIndexPriority()));
                }
                if (contentCheck(attribute.getFormatPattern())) {
                    columnPropertiesBuilder.setFormatPattern(attribute.getFormatPattern());
                }
                if (contentCheck(attribute.getCurrencyType())) {
                    EDoc.ColumnProperties.CurrencyFormat.Builder currencyFormat = EDoc.ColumnProperties.CurrencyFormat.newBuilder();
                    currencyFormat.setColumn(attribute.getCurrencyType());
                    columnPropertiesBuilder.setCurrencyType(currencyFormat);
                }
                if (contentCheck(attribute.getAttributeDimension())) {
                    columnPropertiesBuilder.setIsAttributionDimension(Boolean.parseBoolean(attribute.getAttributeDimension()));
                }
                if (contentCheck(attribute.getSpotIqPreference())) {
                    columnPropertiesBuilder.setSpotiqPreference(attribute.getSpotIqPreference());
                }
                if (contentCheck(attribute.getCalenderType())) {
                    columnPropertiesBuilder.setCalendar(attribute.getCalenderType());
                }

                worksheetColumnProto.setProperties(columnPropertiesBuilder);
                worksheetBuilder.addWorksheetColumns(worksheetColumnProto);
            }

            EDoc.WorksheetEDocProto.QueryProperties.Builder worksheetProperties = EDoc.WorksheetEDocProto.QueryProperties.newBuilder();
            worksheetProperties.setIsBypassRls(Boolean.parseBoolean(modelSheet.getWorkSheets().get(0).getByPassRls()));
            worksheetProperties.setJoinProgressive(Boolean.parseBoolean(modelSheet.getWorkSheets().get(0).getProgressiveJoin()));
            worksheetBuilder.setProperties(worksheetProperties.build());

            worksheetbuilder.setWorksheet(worksheetBuilder);
            if (modelSheet.getWorkSheets().get(0) != null && contentCheck(modelSheet.getWorkSheets().get(0).getGuid())){
                worksheetbuilder.setGuid(modelSheet.getWorkSheets().get(0).getGuid());
            }else{
                worksheetbuilder.setGuid(UUID.randomUUID().toString());
            }

            builderList.add(worksheetbuilder);
        }

        for(Tables table : modelSheet.getTables()){
            EDoc.ObjectEDocProto.Builder tablebuilder = EDoc.ObjectEDocProto.newBuilder();
            EDoc.LogicalTableEDocProto.Builder tableEDocProto = EDoc.LogicalTableEDocProto.newBuilder();
            if (contentCheck(table.getTable())) {
                tableEDocProto.setName(table.getTable());
            }
            if (contentCheck(table.getDatabase())) {
                tableEDocProto.setDb(table.getDatabase());
            }
            if (contentCheck(table.getSchema())) {
                tableEDocProto.setSchema(table.getSchema());
            }
            if (contentCheck(table.getDbTable())) {
                tableEDocProto.setDbTable(table.getDbTable());
            }

            EDoc.Identity.Builder connectionBuilder = EDoc.Identity.newBuilder();
            if (contentCheck(table.getConnection())) {
                connectionBuilder.setName(table.getConnection());
            }

            tableEDocProto.setConnection(connectionBuilder);


            List<Attribute> tableAttributes = modelSheet.getAttributes().parallelStream().filter(a -> a.getTable().equals(table.getTable())).collect(Collectors.toList());

            for (Attribute attribute : tableAttributes) {
                EDoc.LogicalTableEDocProto.LogicalColumnEDocProto.Builder columnEDocProto = EDoc.LogicalTableEDocProto.LogicalColumnEDocProto.newBuilder();
                if (contentCheck(attribute.getColumn())) {
                    columnEDocProto.setName(attribute.getColumn());
                    columnEDocProto.setDbColumnName(attribute.getColumn());
                }
                if (contentCheck(attribute.getDescription())) {
                    columnEDocProto.setDescription(attribute.getDescription());
                }
                EDoc.ColumnProperties.Builder propertyProto = EDoc.ColumnProperties.newBuilder();
                if (contentCheck(attribute.getColumnType())) {
                    propertyProto.setColumnType(attribute.getColumnType());
                }
                if (contentCheck(attribute.getAdditive())) {
                    propertyProto.setIsAdditive(Boolean.parseBoolean(attribute.getAdditive()));
                }
                if (contentCheck(attribute.getAggregation())) {
                    propertyProto.setAggregation(attribute.getAggregation());
                }
                if (contentCheck(attribute.getHiddenAttribute())) {
                    propertyProto.setIsHidden(Boolean.parseBoolean(attribute.getHiddenAttribute()));
                }
                if (contentCheck(attribute.getSynonyms())) {
                    if (attribute.getSynonyms().contains(",")){
                        for (String synonym : attribute.getSynonyms().split(",")){
                            propertyProto.addSynonyms(synonym);
                        }
                    }else{
                        propertyProto.addSynonyms(attribute.getSynonyms());
                    }
                }
                //attribute.setSuggestedValue(attributeProto.getProperties().get); //TODO need find exact field form ts wrapper
                //attribute.setGeoConfig(attributeProto.getProperties().getGeoConfig().get); TODO GeoConfig is a bean
                if (contentCheck(attribute.getIndexType())) {
                    propertyProto.setIndexType(attribute.getIndexType());
                }
                if (contentCheck(attribute.getIndexPriority())) {
                    propertyProto.setIndexPriority(Double.parseDouble(attribute.getIndexPriority()));
                }
                if (contentCheck(attribute.getFormatPattern())) {
                    propertyProto.setFormatPattern(attribute.getFormatPattern());
                }
                if(contentCheck(attribute.getCurrencyType())){
                    EDoc.ColumnProperties.CurrencyFormat.Builder currencyFormat = EDoc.ColumnProperties.CurrencyFormat.newBuilder();
                    currencyFormat.setColumn(attribute.getCurrencyType());
                    propertyProto.setCurrencyType(currencyFormat);
                }
                if(contentCheck(attribute.getAttributeDimension())) {
                    propertyProto.setIsAttributionDimension(Boolean.parseBoolean(attribute.getAttributeDimension()));
                }
                if(contentCheck(attribute.getSpotIqPreference())) {
                    propertyProto.setSpotiqPreference(attribute.getSpotIqPreference());
                }
                if(contentCheck(attribute.getCalenderType())) {
                    propertyProto.setCalendar(attribute.getCalenderType());
                }
                columnEDocProto.setProperties(propertyProto);

                EDoc.LogicalTableEDocProto.DbColumnProperties.Builder dbPropertyProto = EDoc.LogicalTableEDocProto.DbColumnProperties.newBuilder();
                if(contentCheck(attribute.getDataType())) {
                    dbPropertyProto.setDataType(attribute.getDataType());
                }
                columnEDocProto.setDbColumnProperties(dbPropertyProto);

                tableEDocProto.addColumns(columnEDocProto);
            }

            if(!table.getJoinsWith().isEmpty()) {
                EDoc.RelationEDocProto.Builder joinsWithProto = EDoc.RelationEDocProto.newBuilder();
                if(contentCheck(table.getJoinName())) {
                    joinsWithProto.setName(table.getJoinName());
                }
                EDoc.Identity.Builder destinationProto = EDoc.Identity.newBuilder();
                if(contentCheck(table.getJoinsWith())) {
                    destinationProto.setName(table.getJoinsWith());
                }

                joinsWithProto.setDestination(destinationProto);

                if(contentCheck(table.getJoinCondition())) {
                    joinsWithProto.setOn(table.getJoinCondition());
                }
                if(contentCheck(table.getJoinType())) {
                    joinsWithProto.setType(table.getJoinType());
                }
                tableEDocProto.addJoinsWith(joinsWithProto);

            }
            tablebuilder.setTable(tableEDocProto.build());
            tablebuilder.setGuid(UUID.randomUUID().toString());
            builderList.add(tablebuilder);
        }
        return builderList;
    }

    public static boolean contentCheck(String value){
        boolean flag = false;
        if (value != null && !value.isEmpty()){
            flag = true;
        }
        return flag;
    }
}
