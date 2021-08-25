package com.codex.modelsheet.controller;

import com.codex.modelsheet.model.*;

import java.util.ArrayList;
import java.util.List;

public class CubePojoToMsPojo {

    public ModelSheet convertToGSPOJO(Cube cube) {

        ModelSheet modelSheet = new ModelSheet();

        List<WorkSheet> workSheetList = new ArrayList<>();

        WorkSheet CubeNameWorkSheet = new WorkSheet();
        CubeNameWorkSheet.setWorksheetName("CUBE "+cube.getName());
        CubeNameWorkSheet.setTables(cube.getName());
        CubeNameWorkSheet.setByPassRls("FALSE");
        CubeNameWorkSheet.setProgressiveJoin("FALSE");
        workSheetList.add(CubeNameWorkSheet);
        WorkSheet workSheet = new WorkSheet();
        workSheet.setWorksheetName("CUBE "+cube.getName());
        workSheet.setTables("ORGANIZATIONS");
        workSheet.setByPassRls("FALSE");
        workSheet.setProgressiveJoin("FALSE");
        workSheetList.add(workSheet);

        modelSheet.setWorkSheets(workSheetList);

        List<Tables> tables = new ArrayList<>();

        Tables table = new Tables();
        //table.setConnection(tableBuilder.getTable().getConnection().getName());
        //table.setDatabase(tableBuilder.getTable().getDb());
        //table.setSchema(tableBuilder.getTable().getSchema());
        table.setDbTable(cube.getName());
        table.setTable(cube.getName());
        table.setJoinName(cube.getJoins().getOrganizations().getRelationship());
        table.setJoinsWith("ORGANIZATIONS");
        table.setJoinCondition(cube.getJoins().getOrganizations().getSQL());
        table.setJoinType("INNER");
        table.setJoinCardinality("One to One");
        tables.add(table);
        Tables OrganizationsTable = new Tables();
        //OrganizationsTable.setConnection(tableBuilder.getTable().getConnection().getName());
        //OrganizationsTable.setDatabase(tableBuilder.getTable().getDb());
        //OrganizationsTable.setSchema(tableBuilder.getTable().getSchema());
        OrganizationsTable.setDbTable("ORGANIZATIONS");
        OrganizationsTable.setTable("ORGANIZATIONS");
        tables.add(OrganizationsTable);

        modelSheet.getTables().addAll(tables);

        List<Attribute> attributes = new ArrayList<>();

        Attribute createdAtAttribute = new Attribute();
        createdAtAttribute.setTable(cube.getName());
        createdAtAttribute.setColumnType("ATTRIBUTE");
        createdAtAttribute.setIndexType("DONT_INDEX");
        createdAtAttribute.setAttributeDimension("FALSE");
        createdAtAttribute.setColumn("CREATEDAT");
        createdAtAttribute.setWorkSheetColumn("CREATEDAT");
        String datatype = cube.getDimensions().getCreatedAt().getType();
        if(datatype.equalsIgnoreCase("time")) {
            createdAtAttribute.setDataType("VARCHAR");
        }
        createdAtAttribute.setDescription(cube.getDimensions().getCreatedAt().getSQL());
        createdAtAttribute.setWorksheetColumnFlag("Y");
        attributes.add(createdAtAttribute);
        Attribute countryAttribute = new Attribute();
        countryAttribute.setTable("ORGANIZATIONS");
        countryAttribute.setColumnType("ATTRIBUTE");
        countryAttribute.setIndexType("DONT_INDEX");
        countryAttribute.setAttributeDimension("FALSE");
        countryAttribute.setColumn("COUNTRY");
        countryAttribute.setWorkSheetColumn("COUNTRY");
        String cdatatype = cube.getDimensions().getCountry().getType();
        if(cdatatype.equalsIgnoreCase("string")) {
            countryAttribute.setDataType("VARCHAR");
        }
        countryAttribute.setDescription(cube.getDimensions().getCountry().getSQL());
        countryAttribute.setWorksheetColumnFlag("Y");
        attributes.add(countryAttribute);
        modelSheet.getAttributes().addAll(attributes);

        return modelSheet;
    }
}
