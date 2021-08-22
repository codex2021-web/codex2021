package com.codex.modelsheet.controller;

import com.codex.modelsheet.helper.ConfigInfo;
import com.codex.modelsheet.model.EDoc;
import com.codex.modelsheet.util.JSONUtil;
import com.codex.modelsheet.util.ProtoUtils;
import com.codex.modelsheet.util.Util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TmlPojoToTml {
    public void createTml(List<EDoc.ObjectEDocProto.Builder> tableBuilders, String outFile) throws IOException {
        Map<String, String> tmls = new HashMap<>();
        for( EDoc.ObjectEDocProto.Builder tableBuilder : tableBuilders ) {
            EDoc.ObjectEDocProto objectEDocProto = tableBuilder.build();
            String tml = ProtoUtils.protoToYaml(objectEDocProto);
            tmls.put(tableBuilder.hasTable()? tableBuilder.getTable().getName() : tableBuilder.getWorksheet().getName(), tml);
        }
        byte[] zipBytes = Util.createZip(tmls);
        Util.writeFile(outFile, zipBytes);

        System.out.println("Object to TML: "+tmls);
    }

    public String createTml(List<EDoc.ObjectEDocProto.Builder> tableBuilders) throws IOException {
        List<String> tmls = new ArrayList<>();
        for( EDoc.ObjectEDocProto.Builder tableBuilder : tableBuilders ) {
            EDoc.ObjectEDocProto objectEDocProto = tableBuilder.build();
            String tml = ProtoUtils.protoToYaml(objectEDocProto);
            tmls.add(tml);
        }
        String json = JSONUtil.getGsonUI().toJson(tmls);
        System.out.println("Object to json: "+json);
        return json;

    }
}
