package com.codex.modelsheet.controller;

import com.codex.modelsheet.model.ModelSheet;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class ExcelPojoToExcel {
    public static void main(String args[]) throws GeneralSecurityException, IOException {
        ExcelToExcelPojo excelToExcelPojo = new ExcelToExcelPojo();
        String spreadsheetId = "1iztXkbN3v1nbI3GoXoIeGvznjvKlOMGzY4xG9ZM0RXE";
        ModelSheet modelSheet = excelToExcelPojo.getModelSheet(spreadsheetId);
        System.out.println("model sheet::"+modelSheet);

    }
}
