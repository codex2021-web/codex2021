package com.codex.modelsheet.controller;

import com.codex.modelsheet.model.EDoc;
import com.codex.modelsheet.util.ProtoUtils;
import com.codex.modelsheet.util.Util;

import java.io.IOException;

public class TmlToTmlPojo {

    public EDoc.ObjectEDocProto.Builder createTMLPojo(String tmlFile) throws IOException {

        String tmlContent = Util.readFile(tmlFile);
        EDoc.ObjectEDocProto.Builder tableBuilder = EDoc.ObjectEDocProto.newBuilder();
        //TML to Object
        ProtoUtils.yamlToProto(tmlContent, tableBuilder);
        System.out.println("TML to Object: "+tableBuilder.toString());
        return tableBuilder;
    }
}
