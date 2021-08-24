package com.codex.modelsheet.util;

import com.codex.modelsheet.model.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ConversionUtil {

    public static ModelSheet convertToGSPOJO(EDoc.ObjectEDocProto.Builder tableBuilder){
        ModelSheet modelSheet = new ModelSheet();

        if(tableBuilder.getWorksheet() != null){
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
        if(tableBuilder.getTable() != null){

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
            if(tableBuilder.getWorksheet() != null){
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
                    attribute.setWorksheetColumnFlag("Y");
                }
                attributes.add(attribute);
            }
            modelSheet.setAttributes(attributes);

            }
        return modelSheet;
    }

    public static EDoc.ObjectEDocProto.Builder convertToTMLPOJO(ModelSheet modelSheet) {
        EDoc.ObjectEDocProto.Builder builder = EDoc.ObjectEDocProto.newBuilder();


        EDoc.WorksheetEDocProto.Builder worksheetBuilder = EDoc.WorksheetEDocProto.newBuilder();

        worksheetBuilder.setName(modelSheet.getWorkSheets().get(0).getWorksheetName());

        for (WorkSheet workSheet : modelSheet.getWorkSheets()) {
            EDoc.Identity.Builder tableBuilder = EDoc.Identity.newBuilder();
            tableBuilder.setName(workSheet.getTables());
            worksheetBuilder.getTablesList().add(tableBuilder.build());
        }

        for(Tables t : modelSheet.getTables()){
            EDoc.TablePath.JoinPath.Builder tablePathJoinPathBuilder = null;
            if(!t.getJoinsWith().isEmpty()){
                EDoc.Join.Builder joinBuilder =  EDoc.Join.newBuilder();
                joinBuilder.setName(t.getJoinName());
                joinBuilder.setSource(t.getTable());
                joinBuilder.setDestination(t.getJoinsWith());
                joinBuilder.setType(t.getJoinType());
                joinBuilder.setIsOneToOne(Boolean.parseBoolean(t.getJoinCardinality()));
                worksheetBuilder.getJoinsList().add(joinBuilder.build());

                tablePathJoinPathBuilder = EDoc.TablePath.JoinPath.newBuilder();
                tablePathJoinPathBuilder.getJoinList().add(t.getJoinName());
            }

            EDoc.TablePath.Builder tablePathBuilder = EDoc.TablePath.newBuilder();
            tablePathBuilder.setId(t.getTable());
            tablePathBuilder.setTable(t.getTable());
            if (tablePathJoinPathBuilder != null ) {
                tablePathBuilder.getJoinPathList().add(tablePathJoinPathBuilder.build());
            }
            worksheetBuilder.getTablePathsList().add(tablePathBuilder.build());
        }

        List<Attribute> worksheetAttributes = modelSheet.getAttributes().parallelStream().filter(attr -> "Y".equalsIgnoreCase(attr.getWorksheetColumnFlag())).collect(Collectors.toList());
        for(Attribute attribute : worksheetAttributes){
            EDoc.WorksheetEDocProto.WorksheetColumn.Builder worksheetColumnProto = EDoc.WorksheetEDocProto.WorksheetColumn.newBuilder();
            worksheetColumnProto.setName(attribute.getColumn());
            worksheetColumnProto.setColumnId(attribute.getTable()+"::"+attribute.getColumn());
            worksheetColumnProto.setDescription(attribute.getDescription());

            EDoc.ColumnProperties.Builder columnPropertiesBuilder = EDoc.ColumnProperties.newBuilder();
            columnPropertiesBuilder.setColumnType(attribute.getColumnType());
            columnPropertiesBuilder.setIsAdditive(Boolean.parseBoolean(attribute.getAdditive()));
            columnPropertiesBuilder.setAggregation(attribute.getAggregation());
            columnPropertiesBuilder.setIsHidden(Boolean.parseBoolean(attribute.getHiddenAttribute()));
            columnPropertiesBuilder.getSynonymsList().add(attribute.getSynonyms());//TODO it's a list
            //attribute.setSuggestedValue(attributeProto.getProperties().get); //TODO need find exact field form ts wrapper
            //attribute.setGeoConfig(attributeProto.getProperties().getGeoConfig().get); TODO GeoConfig is a bean
            columnPropertiesBuilder.setIndexType(attribute.getIndexType());
            columnPropertiesBuilder.setIndexPriority(Double.parseDouble(attribute.getIndexPriority()));
            columnPropertiesBuilder.setFormatPattern(attribute.getFormatPattern());
            if(attribute.getCurrencyType() != null && attribute.getCurrencyType().isEmpty()){
                EDoc.ColumnProperties.CurrencyFormat.Builder currencyFormat = EDoc.ColumnProperties.CurrencyFormat.newBuilder();
                currencyFormat.setColumn(attribute.getCurrencyType());
                columnPropertiesBuilder.setCurrencyType(currencyFormat);
            }
            columnPropertiesBuilder.setIsAttributionDimension(Boolean.parseBoolean(attribute.getAttributeDimension()));
            columnPropertiesBuilder.setSpotiqPreference(attribute.getSpotIqPreference());
            columnPropertiesBuilder.setCalendar(attribute.getCalenderType());

            worksheetColumnProto.setProperties(columnPropertiesBuilder);
            worksheetBuilder.getWorksheetColumnsList().add(worksheetColumnProto.build());
        }

        EDoc.WorksheetEDocProto.QueryProperties.Builder worksheetProperties = EDoc.WorksheetEDocProto.QueryProperties.newBuilder();
        worksheetProperties.setIsBypassRls(Boolean.parseBoolean(modelSheet.getWorkSheets().get(0).getByPassRls()));
        worksheetProperties.setJoinProgressive(Boolean.parseBoolean(modelSheet.getWorkSheets().get(0).getProgressiveJoin()));
        worksheetBuilder.setProperties(worksheetProperties.build());

        builder.setWorksheet(worksheetBuilder);



        for(Tables table : modelSheet.getTables()){
            EDoc.LogicalTableEDocProto.Builder tableEDocProto = EDoc.LogicalTableEDocProto.newBuilder();
            tableEDocProto.setName(table.getTable());
            tableEDocProto.setDb(table.getDatabase());
            tableEDocProto.setSchema(table.getSchema());
            tableEDocProto.setDbTable(table.getDbTable());

            EDoc.Identity.Builder connectionBuilder = EDoc.Identity.newBuilder();
            connectionBuilder.setName(table.getConnection());
            tableEDocProto.setConnection(connectionBuilder);

            List<Attribute> tableAttributes = modelSheet.getAttributes().parallelStream().filter(a -> a.getTable().equals(table.getTable())).collect(Collectors.toList());
            for (Attribute attribute : tableAttributes) {
                EDoc.LogicalTableEDocProto.LogicalColumnEDocProto.Builder columnEDocProto = EDoc.LogicalTableEDocProto.LogicalColumnEDocProto.newBuilder();
                columnEDocProto.setName(attribute.getColumn());
                columnEDocProto.setDbColumnName(attribute.getColumn());
                columnEDocProto.setDescription(attribute.getDescription());

                EDoc.ColumnProperties.Builder propertyProto = EDoc.ColumnProperties.newBuilder();
                propertyProto.setColumnType(attribute.getColumnType());
                propertyProto.setIsAdditive(Boolean.parseBoolean(attribute.getAdditive()));
                propertyProto.setAggregation(attribute.getAggregation());
                propertyProto.setIsHidden(Boolean.parseBoolean(attribute.getHiddenAttribute()));
                propertyProto.getSynonymsList().add(attribute.getSynonyms());//TODO it's a list
                //attribute.setSuggestedValue(attributeProto.getProperties().get); //TODO need find exact field form ts wrapper
                //attribute.setGeoConfig(attributeProto.getProperties().getGeoConfig().get); TODO GeoConfig is a bean
                propertyProto.setIndexType(attribute.getIndexType());
                propertyProto.setIndexPriority(Double.parseDouble(attribute.getIndexPriority()));
                propertyProto.setFormatPattern(attribute.getFormatPattern());
                if(attribute.getCurrencyType() != null && attribute.getCurrencyType().isEmpty()){
                    EDoc.ColumnProperties.CurrencyFormat.Builder currencyFormat = EDoc.ColumnProperties.CurrencyFormat.newBuilder();
                    currencyFormat.setColumn(attribute.getCurrencyType());
                    propertyProto.setCurrencyType(currencyFormat);
                }
                propertyProto.setIsAttributionDimension(Boolean.parseBoolean(attribute.getAttributeDimension()));
                propertyProto.setSpotiqPreference(attribute.getSpotIqPreference());
                propertyProto.setCalendar(attribute.getCalenderType());
                columnEDocProto.setProperties(propertyProto);

                EDoc.LogicalTableEDocProto.DbColumnProperties.Builder dbPropertyProto = EDoc.LogicalTableEDocProto.DbColumnProperties.newBuilder();
                dbPropertyProto.setDataType(attribute.getDataType());
                columnEDocProto.setDbColumnProperties(dbPropertyProto);

                tableEDocProto.getColumnsList().add(columnEDocProto.build());
            }
            if(!table.getJoinsWith().isEmpty()) {
                EDoc.RelationEDocProto.Builder joinsWithProto = EDoc.RelationEDocProto.newBuilder();
                joinsWithProto.setName(table.getJoinName());
                EDoc.Identity.Builder destinationProto = EDoc.Identity.newBuilder();
                destinationProto.setName(table.getJoinsWith());
                joinsWithProto.setDestination(destinationProto);
                joinsWithProto.setOn(table.getConnection());
                joinsWithProto.setType(table.getJoinType());
                tableEDocProto.getJoinsWithList().add(joinsWithProto.build());
            }
            builder.setTable(tableEDocProto.build());
        }
        return builder;
    }
}
