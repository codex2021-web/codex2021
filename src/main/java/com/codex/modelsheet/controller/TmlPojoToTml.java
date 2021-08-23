package com.codex.modelsheet.controller;

import com.codex.modelsheet.importmodel.EdocCommon;
import com.codex.modelsheet.importmodel.EdocService;
import com.codex.modelsheet.model.EDoc;
import com.codex.modelsheet.util.ProtoUtils;
import com.codex.modelsheet.util.Util;
import org.jetbrains.annotations.NotNull;

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

    public String createTml(@NotNull List<EDoc.ObjectEDocProto.Builder> tableBuilders, boolean includeDependents) throws IOException {
        List<String> tmls = new ArrayList<>();

        EdocService.ImportEPackRequest.Builder request =
                EdocService.ImportEPackRequest.newBuilder();
        request.setFormatType(EdocCommon.EDocFormatType.E.YAML);

        for( EDoc.ObjectEDocProto.Builder tableBuilder : tableBuilders ) {
            EDoc.ObjectEDocProto objectEDocProto = tableBuilder.build();
            if (!(objectEDocProto.hasWorksheet() || objectEDocProto.hasTable())) continue;
            if (!includeDependents && objectEDocProto.hasTable()) continue;
            String tml = ProtoUtils.protoToYaml(objectEDocProto);

            EdocCommon.EDocObjectType.E ObjectType = objectEDocProto.hasWorksheet()? EdocCommon.EDocObjectType.E.WORKSHEET : EdocCommon.EDocObjectType.E.TABLE;
            String name = objectEDocProto.hasWorksheet()? objectEDocProto.getWorksheet().getName() : objectEDocProto.getTable().getName();
            request.addObjectBuilder()
                    .setFilename(name + ".tml")
                    .setEdoc(tml)
                    .setType(ObjectType)
                    .setAction(EdocCommon.ImportAction.E.CREATE).build();
        }

        String json = ProtoUtils.protoToJSON(request.build());

        //String json = JSONUtil.getGsonUI().toJson(tmls);
        System.out.println("Object to json: "+json);
        return json;

    }
}
