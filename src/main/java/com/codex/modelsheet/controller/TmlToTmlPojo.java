package com.codex.modelsheet.controller;

import com.codex.modelsheet.model.EDoc;
import com.codex.modelsheet.util.ProtoUtils;
import com.codex.modelsheet.util.Util;

import java.io.IOException;
import java.util.Map;

public class TmlToTmlPojo {

    public EDoc.ObjectEDocProto.Builder createTMLPojo(String tmlFile) throws IOException {

        //String tmlContent = Util.readFile(tmlFile);
        Map<String, String> fileNameToContentMap = Util.readZip(tmlFile);
        EDoc.ObjectEDocProto.Builder tableBuilder = EDoc.ObjectEDocProto.newBuilder();
        //TML to Object
        for( String tmlContent : fileNameToContentMap.values()) {
            ProtoUtils.yamlToProto(tmlContent, tableBuilder);
            System.out.println("TML to Object: " + tableBuilder.toString());
        }
        return tableBuilder;
    }
}
