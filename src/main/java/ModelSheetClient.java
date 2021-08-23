import com.codex.modelsheet.controller.*;
import com.codex.modelsheet.helper.ConfigInfo;
import com.codex.modelsheet.model.EDoc;
import com.codex.modelsheet.model.ModelSheet;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ModelSheetClient {

    public static void main(String[] args) throws Exception {
        ConfigInfo.loadConfigurations();

        if (args.length == 0) {
            throw new Exception("No arguments passed.");
        }
        String command = args[0];
        switch (command) {
            /**
             * ts2gsheet -w TestWorksheet -g ( worksheet to googlesheet)
             */
            case "ts2gsheet":
                if (args.length != 5) throw new Exception("Invalid number of arguments.");
                ts2gsheet(args);
                break;
            /**
             * gsheet2ts -g sheetname( from googlesheet to TS Cloud)
              */
            case "gsheet2ts":
                if (args.length < 3 && args.length > 4) throw new Exception("Invalid number of arguments.");
                if(args.length == 4 && args[3].equalsIgnoreCase("includedependents")) {
                    gsheet2ts(args, true);
                }else {
                    gsheet2ts(args, false);
                }

                break;
            /**
             * xport worksheet will create zip file with worksheet/table name
             */
            case "exportworksheet":
                if (args.length != 4) throw new Exception("Invalid number of arguments.");
                ExportDBWorkSheet exportDBWorkSheet = new ExportDBWorkSheet();
                exportDBWorkSheet.exportWorkSheet(args[2], args[1].equals("-w") ? true : false, args[3]);
                break;
            /**
             * Input TML will be zip file again TML zip file will create with specified filename in config
             */
            case "tml2tml":
                if (args.length != 3) throw new Exception("Invalid number of arguments.");
                TmlToTmlPojo tmlToTmlPojo = new TmlToTmlPojo();
                List<EDoc.ObjectEDocProto.Builder> tableBuilder = tmlToTmlPojo.createTMLPojo(args[1]);

                TmlPojoToTml tmlPojoToTml = new TmlPojoToTml();
                tmlPojoToTml.createTml(tableBuilder, args[2]);
                break;

            case "ms2tml":
                if (args.length != 3) throw new Exception("Invalid number of arguments.");
                ExcelToExcelPojo ee = new ExcelToExcelPojo();
                ModelSheet modelSheet = ee.getModelSheet(args[1]);
                MsPojoToTmlPojo mp = new MsPojoToTmlPojo();
                List<EDoc.ObjectEDocProto.Builder> builder = mp.convertToTMLPOJO(modelSheet);
                TmlPojoToTml pt = new TmlPojoToTml();
                pt.createTml(builder, args[2]);
                break;

            case "tml2ms":
                if (args.length != 3) throw new Exception("Invalid number of arguments.");
                TmlToTmlPojo tt = new TmlToTmlPojo();
                EDoc.ObjectEDocProto.Builder finalbuilder = EDoc.ObjectEDocProto.newBuilder();
                List<EDoc.ObjectEDocProto.Builder> builders = tt.createTMLPojo(args[1]);
                TmlPojoToMsPojo tmlPojoToMsPojo = new TmlPojoToMsPojo();
                List<EDoc.ObjectEDocProto.Builder> tableslist  = new ArrayList<>();
                for (EDoc.ObjectEDocProto.Builder edoc : builders) {
                    if (edoc.hasWorksheet()){
                        finalbuilder = edoc;
                    }else if(edoc.hasTable()){
                        tableslist.add(edoc);
                    }
                }
                ModelSheet mm = tmlPojoToMsPojo.convertToGSPOJO(finalbuilder,tableslist);
                ExcelPojoToExcel excelPojoToExcel = new ExcelPojoToExcel();
                excelPojoToExcel.dataWritingInToExcel(mm,args[2]);
                break;

            case "tml2ts":
                if (args.length < 2 && args.length > 3) throw new Exception("Invalid number of arguments.");
                TmlToTmlPojo tp = new TmlToTmlPojo();
                List<EDoc.ObjectEDocProto.Builder> tbs = tp.createTMLPojo(args[1]);
                /**
                 * Convert tml object to tml.
                 */
                TmlPojoToTml TmlPojoToTml = new TmlPojoToTml();
                String jsonTml = "";
                if(args.length == 3 && args[2].equalsIgnoreCase("includedependents")) {
                    jsonTml = TmlPojoToTml.createTml(tbs, true);
                }else {
                    jsonTml = TmlPojoToTml.createTml(tbs, false);
                }
                /**
                 * Get list of tml data
                 */
                ImportDMWorkSheet importTml = new ImportDMWorkSheet();
                importTml.importWorkSheet(jsonTml);
                break;

            case "ms2ms":
                if (args.length != 3) throw new Exception("Invalid number of arguments.");
                ExcelToExcelPojo ep = new ExcelToExcelPojo();
                ModelSheet sheet = ep.getModelSheet(args[1]);

                ExcelPojoToExcel pe = new ExcelPojoToExcel();
                pe.dataWritingInToExcel(sheet, args[2]);
                break;

            case "modelsheet2pojo":
                if (args.length != 2) throw new Exception("Invalid number of arguments.");
                ExcelToExcelPojo excelToExcelPojo = new ExcelToExcelPojo();
                excelToExcelPojo.getModelSheet(args[1]);
                break;

            case "pojo2modelsheet"://TODO Not Req
                excelPojoToExcel = new ExcelPojoToExcel();
                excelPojoToExcel.dataWritingInToExcel(new ModelSheet(), "");
                break;

            case "tmlpojo2modelpojo"://TODO Not Req
                tmlPojoToMsPojo = new TmlPojoToMsPojo();
                tmlPojoToMsPojo.convertToGSPOJO(EDoc.ObjectEDocProto.newBuilder(), new ArrayList<>());
                break;

            case "modelpojo2tmlpojo"://TODO Not Req
                MsPojoToTmlPojo msPojoToTmlPojo = new MsPojoToTmlPojo();
                msPojoToTmlPojo.convertToTMLPOJO(new ModelSheet());
                break;

            case "importworksheet":
                if (args.length != 2) throw new Exception("Invalid number of arguments.");
                ImportDMWorkSheet importDMWorkSheet = new ImportDMWorkSheet();
                importDMWorkSheet.importWorkSheet(args[1]);
                break;
        }
    }

    private static void gsheet2ts(String[] args, boolean includeDependents) throws Exception {
        /**
         * Modelsheet to Modelsheet object
         */
        ExcelToExcelPojo excelToExcelPojo = new ExcelToExcelPojo();
        ModelSheet ms = excelToExcelPojo.getModelSheet(args[2]);

        /**
         * Converting modelsheet to tml object
         */
        MsPojoToTmlPojo msPojoToTmlPojo = new MsPojoToTmlPojo();
        List<EDoc.ObjectEDocProto.Builder> builderList = msPojoToTmlPojo.convertToTMLPOJO(ms);

        /**
         * Convert tml object to tml.
         */
        TmlPojoToTml TmlPojoToTml = new TmlPojoToTml();
        String jsonTml = TmlPojoToTml.createTml(builderList, includeDependents);

        /**
         * Get list of tml data
         */
        ImportDMWorkSheet importTml = new ImportDMWorkSheet();
        importTml.importWorkSheet(jsonTml);
    }

    private static void ts2gsheet(String... args) throws Exception {
        /**
         * Get list of tml data
         */
        ExportDBWorkSheet export = new ExportDBWorkSheet();
        Map<String, String> tmls = export.exportWorkSheet(args[2], args[1].equals("-w") ? true : false);

        /**
         * Convert tml data to tml object.
         */
        TmlToTmlPojo tmlToTmlPojo = new TmlToTmlPojo();
        List<EDoc.ObjectEDocProto.Builder> tmlObjects = tmlToTmlPojo.createTMLPojo(tmls);

        /**
         * Converting tml objects to Worksheet Object
         */
        EDoc.ObjectEDocProto.Builder workSheetBuilder = null;
        List<EDoc.ObjectEDocProto.Builder> tableBuilders = new ArrayList<>();
                    for(
        EDoc.ObjectEDocProto.Builder builder :tmlObjects)

        {
            if (builder.hasWorksheet()) {
                workSheetBuilder = builder;
            } else if (builder.hasTable()) {
                tableBuilders.add(builder);
            } else {
                //TODO Manifest
            }
        }

        TmlPojoToMsPojo tmlPojoToMsPojo = new TmlPojoToMsPojo();
        ModelSheet modelSheet = tmlPojoToMsPojo.convertToGSPOJO(workSheetBuilder, tableBuilders);

        /**
         * Modelsheet object to modelsheet
         */
        ExcelPojoToExcel excelPojoToExcel = new ExcelPojoToExcel();
        excelPojoToExcel.dataWritingInToExcel(modelSheet,args[4]);
}
}
