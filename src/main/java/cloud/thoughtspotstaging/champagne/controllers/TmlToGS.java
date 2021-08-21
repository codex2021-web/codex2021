package cloud.thoughtspotstaging.champagne.controllers;

import com.codex.modelsheet.controller.ExcelPojoToExcel;
import com.codex.modelsheet.controller.ExcelToExcelPojo;
import com.codex.modelsheet.controller.TmlPojoToMsPojo;
import com.codex.modelsheet.controller.TmlToTmlPojo;
import com.codex.modelsheet.model.EDoc;
import com.codex.modelsheet.model.ModelSheet;
import com.codex.modelsheet.util.ConversionUtil;

import java.util.ArrayList;
import java.util.List;

public class TmlToGS {
    public static void main(String[] args) {

        try {
            String filePath = "/Users/shaik.ansari/Downloads/Test-Worksheet.worksheet.zip";
            TmlToTmlPojo tmlToTmlPojo = new TmlToTmlPojo();
            EDoc.ObjectEDocProto.Builder finalbuilder = EDoc.ObjectEDocProto.newBuilder();
            List<EDoc.ObjectEDocProto.Builder> builders = tmlToTmlPojo.createTMLPojo(filePath);
            TmlPojoToMsPojo tmlPojoToMsPojo = new TmlPojoToMsPojo();
            List<EDoc.LogicalTableEDocProto> tableslist  = new ArrayList<>();
            for (EDoc.ObjectEDocProto.Builder builder : builders) {
                    if (builder.hasWorksheet()){
                        finalbuilder =builder;
                    }else{
                        tableslist.add(builder.getTable());
                    }
            }
            ModelSheet modelSheet = tmlPojoToMsPojo.convertToGSPOJO(finalbuilder,tableslist);
            ExcelPojoToExcel excelPojoToExcel = new ExcelPojoToExcel();
            excelPojoToExcel.dataWritingInToExcel(modelSheet,"Worksheet_codex");

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
