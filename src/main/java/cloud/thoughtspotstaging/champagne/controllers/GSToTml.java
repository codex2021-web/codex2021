package cloud.thoughtspotstaging.champagne.controllers;

import com.codex.modelsheet.controller.ExcelToExcelPojo;
import com.codex.modelsheet.controller.MsPojoToTmlPojo;
import com.codex.modelsheet.controller.TmlPojoToTml;
import com.codex.modelsheet.helper.ConfigInfo;
import com.codex.modelsheet.model.EDoc;
import com.codex.modelsheet.model.ModelSheet;

import java.util.List;

public class GSToTml {

    public static void main(String[] args) {
        try {
            ConfigInfo.loadConfigurations();
            String spreadsheetId = "1ga0z6pqMNg2Ta9V6WwY1W9_SJWrvVLGZxBU809-PCsI";
            ExcelToExcelPojo excelToExcelPojo = new ExcelToExcelPojo();
            ModelSheet modelSheet = excelToExcelPojo.getModelSheet(spreadsheetId);
            MsPojoToTmlPojo msPojoToTmlPojo = new MsPojoToTmlPojo();
            List<EDoc.ObjectEDocProto.Builder> builder = msPojoToTmlPojo.convertToTMLPOJO(modelSheet);
            TmlPojoToTml tmlPojoToTml = new TmlPojoToTml();
            tmlPojoToTml.createTml(builder, "/Users/shaik.ansari/Downloads/ansari_Supplier_Worksheet3.zip");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
