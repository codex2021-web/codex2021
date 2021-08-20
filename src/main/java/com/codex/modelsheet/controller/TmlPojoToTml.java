package com.codex.modelsheet.controller;

import com.codex.modelsheet.model.EDoc;
import com.codex.modelsheet.util.ProtoUtils;

public class TmlPojoToTml {
    public void createTml(EDoc.ObjectEDocProto.Builder tableBuilder){
        EDoc.ObjectEDocProto objectEDocProto = tableBuilder.build();
        String tml =  ProtoUtils.protoToYaml(objectEDocProto);
        System.out.println("Object to TML: "+tml);
    }
}
