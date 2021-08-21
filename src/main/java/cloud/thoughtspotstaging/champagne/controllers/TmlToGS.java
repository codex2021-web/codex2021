package cloud.thoughtspotstaging.champagne.controllers;

import com.codex.modelsheet.controller.ExcelPojoToExcel;
import com.codex.modelsheet.controller.ExcelToExcelPojo;
import com.codex.modelsheet.controller.TmlPojoToMsPojo;
import com.codex.modelsheet.controller.TmlToTmlPojo;
import com.codex.modelsheet.model.EDoc;
import com.codex.modelsheet.model.ModelSheet;
import com.codex.modelsheet.util.ConversionUtil;

public class TmlToGS {
    public static void main(String[] args) {

        try {
            //System.out.println(UUID.fromString("59481331-ee53-42be-a548-bd87be6ddd4b"));
            String filePathWorksheet = "/Users/shaik.ansari/Downloads/Test-Worksheet.worksheet/Test-Worksheet.worksheet.tml";
            //String tablePath = "/Users/shaik.ansari/Downloads/Test-Worksheet.worksheet/ORDERS.table.tml";
            String tablePath = "/Users/shaik.ansari/Downloads/Test-Worksheet.worksheet/CUSTOMER.table.tml";
            TmlToTmlPojo tmlToTmlPojo = new TmlToTmlPojo();
            EDoc.ObjectEDocProto.Builder builder = tmlToTmlPojo.createTMLPojo(tablePath);
            TmlPojoToMsPojo tmlPojoToMsPojo = new TmlPojoToMsPojo();
            ModelSheet modelSheet = tmlPojoToMsPojo.convertToGSPOJO(builder);
            ExcelPojoToExcel excelPojoToExcel = new ExcelPojoToExcel();
            excelPojoToExcel.dataWritingInToExcel(modelSheet);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
