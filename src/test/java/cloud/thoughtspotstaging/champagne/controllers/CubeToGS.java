package cloud.thoughtspotstaging.champagne.controllers;

import com.codex.modelsheet.controller.CubePojoToMsPojo;
import com.codex.modelsheet.controller.ExcelPojoToExcel;
import com.codex.modelsheet.helper.ConfigInfo;
import com.codex.modelsheet.model.Cube;
import com.codex.modelsheet.model.ModelSheet;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CubeToGS {

    public static void main(String[] args) {
        try {
            ConfigInfo.loadConfigurations();
            String cubeFilePath = "/Users/shaik.ansari/Downloads/Cube.js";
            String cubeData = new String(Files.readAllBytes(Paths.get(cubeFilePath)), StandardCharsets.US_ASCII);
            String cubelessContent =  cubeData.replace("cube(","").replace(");","").replace("`","\"");

            String cubename = "";
            Pattern p = Pattern.compile("\"([^\"]*)\"");
            Matcher m = p.matcher(cubelessContent);
            while (m.find()) {
                cubename= m.group(1);
                break;
            }
            System.out.println("Cubename :: "+cubename);

            String dataContent = cubelessContent.replace("\""+cubename+"\",","");
            System.out.println("DataContent :: " +dataContent);

            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            Cube cube = mapper.readValue(dataContent, Cube.class);
            cube.setName(cubename.toUpperCase());
            System.out.println(cube);
            CubePojoToMsPojo cubePojoToMsPojo = new CubePojoToMsPojo();
            ModelSheet modelSheet = cubePojoToMsPojo.convertToGSPOJO(cube);
            ExcelPojoToExcel excelPojoToExcel = new ExcelPojoToExcel();
            excelPojoToExcel.dataWritingInToExcel(modelSheet,"Cube "+cubename);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
