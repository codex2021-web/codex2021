package cloud.thoughtspotstaging.champagne.controllers;

import com.codex.modelsheet.controller.ExcelPojoToExcel;
import com.codex.modelsheet.controller.ExcelToExcelPojo;
import com.codex.modelsheet.helper.ConfigInfo;
import com.codex.modelsheet.model.ModelSheet;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class ExcelTests {
    public static void main(String... args) throws GeneralSecurityException, IOException {
        ConfigInfo.loadConfigurations();
        ExcelPojoToExcel excelToExcelPojo = new ExcelPojoToExcel();
        String sheetName ="Code//test";
        String spreadsheetId = "1iztXkbN3v1nbI3GoXoIeGvznjvKlOMGzY4xG9ZM0RXE";
        ExcelToExcelPojo excelPojo = new ExcelToExcelPojo();
        ModelSheet modelSheet = excelPojo.getModelSheet(spreadsheetId);
        excelToExcelPojo.dataWritingInToExcel(modelSheet,sheetName);
    }
}
