package com.codex.modelsheet.controller;

import com.codex.modelsheet.model.ModelSheet;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class ExcelPojoToExcel {
    public static void main(String args[]) throws GeneralSecurityException, IOException {
        ExcelToExcelPojo excelToExcelPojo = new ExcelToExcelPojo();
        String spreadsheetId = "1yfcgRdGqc540HLdwO26Zar83aLXjatkBeCUhCyDBFqY";
        ModelSheet modelSheet = excelToExcelPojo.getModelSheet(spreadsheetId);
        System.out.println("model sheet::"+modelSheet);

    }
}
