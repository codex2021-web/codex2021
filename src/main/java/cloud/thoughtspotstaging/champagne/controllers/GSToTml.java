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
            String spreadsheetId = "1z3CF9dI1PNDlme_NGwbA7STgalIh9oxc_eE-cmxOQ-4";
            ExcelToExcelPojo excelToExcelPojo = new ExcelToExcelPojo();
            ModelSheet modelSheet = excelToExcelPojo.getModelSheet(spreadsheetId);
            MsPojoToTmlPojo msPojoToTmlPojo = new MsPojoToTmlPojo();
            List<EDoc.ObjectEDocProto.Builder> builder = msPojoToTmlPojo.convertToTMLPOJO(modelSheet);
            TmlPojoToTml tmlPojoToTml = new TmlPojoToTml();
            tmlPojoToTml.createTml(builder, "/Users/shaik.ansari/Downloads/ansari_Untitled_guid1.zip");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
