package com.codex.modelsheet.controller;

import com.codex.modelsheet.model.EDoc;
import com.codex.modelsheet.util.ProtoUtils;
import com.codex.modelsheet.util.Util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TmlToTmlPojo {

    public List<EDoc.ObjectEDocProto.Builder> createTMLPojo(String tmlFile) throws IOException {

        List<EDoc.ObjectEDocProto.Builder> tmlObjs = new ArrayList<>();
        //String tmlContent = Util.readFile(tmlFile);
        Map<String, String> fileNameToContentMap = Util.readZip(tmlFile);
        //TML to Object
        for( String tmlContent : fileNameToContentMap.values()) {
            EDoc.ObjectEDocProto.Builder tableBuilder = EDoc.ObjectEDocProto.newBuilder();
            ProtoUtils.yamlToProto(tmlContent, tableBuilder);
            tmlObjs.add(tableBuilder);
            //System.out.println("TML to Object: " + tableBuilder.toString());
        }
        return tmlObjs;
    }
    public List<EDoc.ObjectEDocProto.Builder> createTMLPojo(Map<String, String> fileNameToContentMap) throws IOException {

        List<EDoc.ObjectEDocProto.Builder> tmlObjs = new ArrayList<>();
        //TML to Object
        for( String tmlContent : fileNameToContentMap.values()) {
            EDoc.ObjectEDocProto.Builder tableBuilder = EDoc.ObjectEDocProto.newBuilder();
            ProtoUtils.yamlToProto(tmlContent, tableBuilder);
            tmlObjs.add(tableBuilder);
            //System.out.println("TML to Object: " + tableBuilder.toString());
        }
        return tmlObjs;
    }
}
