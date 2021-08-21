import com.codex.modelsheet.controller.*;
import com.codex.modelsheet.helper.ConfigInfo;
import com.codex.modelsheet.model.EDoc;
import com.codex.modelsheet.model.ModelSheet;

import java.util.List;

public class ModelSheetClient {

    public static void main(String[] args) throws Exception {
        ConfigInfo.loadConfigurations();

        if(args.length == 0){
            throw new Exception("No arguments passed.");
        }
        String command = args[0];
        switch (command){
            /**
             * xport worksheet will create zip file with worksheet/table name
             */
            case "exportworksheet":
                if(args.length != 3) throw new Exception("Invalid number of arguments.");
                ExportDBWorkSheet exportDBWorkSheet = new ExportDBWorkSheet();
                exportDBWorkSheet.exportWorkSheet( args[2], args[1].equals("-w")? true : false);
                break;
            /**
             * Input TML will be zip file again TML zip file will create with specified filename in config
             */
            case "tml2tml":
                if(args.length != 2) throw new Exception("Invalid number of arguments.");
                TmlToTmlPojo tmlToTmlPojo = new TmlToTmlPojo();
                List<EDoc.ObjectEDocProto.Builder> tableBuilder = tmlToTmlPojo.createTMLPojo(args[1]);

                TmlPojoToTml tmlPojoToTml = new TmlPojoToTml();
                tmlPojoToTml.createTml(tableBuilder);
                break;

            case "modelsheet2pojo":
                if(args.length != 2) throw new Exception("Invalid number of arguments.");
                ExcelToExcelPojo excelToExcelPojo = new ExcelToExcelPojo();
                excelToExcelPojo.getModelSheet(args[1]);
                break;

            case "pojo2modelsheet"://TODO
                ExcelPojoToExcel excelPojoToExcel = new ExcelPojoToExcel();
                excelPojoToExcel.dataWritingInToExcel(new ModelSheet(),"");
                break;

            case "tmlpojo2modelpojo"://TODO
                TmlPojoToMsPojo tmlPojoToMsPojo = new TmlPojoToMsPojo();
                tmlPojoToMsPojo.convertToGSPOJO(EDoc.ObjectEDocProto.newBuilder());
                break;

            case "modelpojo2tmlpojo"://TODO
                MsPojoToTmlPojo msPojoToTmlPojo = new MsPojoToTmlPojo();
                msPojoToTmlPojo.convertToTMLPOJO(new ModelSheet());
                break;

            case "importWorksheet"://TODO
                //ImportDMWorkSheet importDMWorkSheet = new ImportDMWorkSheet();
                break;
        }
    }
}
