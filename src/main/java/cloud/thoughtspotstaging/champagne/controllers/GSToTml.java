package cloud.thoughtspotstaging.champagne.controllers;

import com.codex.modelsheet.controller.ExcelToExcelPojo;
import com.codex.modelsheet.controller.MsPojoToTmlPojo;
import com.codex.modelsheet.controller.TmlPojoToTml;
import com.codex.modelsheet.model.EDoc;
import com.codex.modelsheet.model.ModelSheet;

public class GSToTml {

    public static void main(String[] args) {
        try {
            String spreadsheetId = "1mICKYeEkEvs4FfXBvEe9im2LCI876Xh5IkozqnKZXiw";
            ExcelToExcelPojo excelToExcelPojo = new ExcelToExcelPojo();
            ModelSheet modelSheet = excelToExcelPojo.getModelSheet(spreadsheetId);
            MsPojoToTmlPojo msPojoToTmlPojo = new MsPojoToTmlPojo();
            EDoc.ObjectEDocProto.Builder builder = msPojoToTmlPojo.convertToTMLPOJO(modelSheet);
            TmlPojoToTml tmlPojoToTml = new TmlPojoToTml();
            tmlPojoToTml.createTml(builder);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
