/*
 * Copyright: ThoughtSpot Inc. 2019
 */

package com.codex.modelsheet.util;

import com.google.protobuf.Message;

/**
 * Proto utility functions.
 *
 * @author Nipun Gupta (nipun.gupta@thoughtspot.com)
 */
public final class ProtoUtils {

    /**
     * Private constructor.
     */
    private ProtoUtils() { }

    /**
     * @param proto to convert to JSON.
     * @return JSON form of given proto.
     */
    public static String protoToJSON(final Message proto) {
        return JSONUtil.protoToJSON(proto);
    }

    /**
     * @param json string to generate proto from.
     * @param builder to build proto from given json.
     */
    public static void jsonToProto(final String json, final Message.Builder builder) {
        JSONUtil.jsonToProto(json, builder);
    }

    /**
     * @param proto proto object to convert to yaml.
     * @return Yaml representation of given proto.
     */
    public static String protoToYaml(final Message proto) {
        String json = protoToJSON(proto);
        return JSONUtil.jsonToYaml(json);
    }

    /**
     * @param yaml string to generate proto from.
     * @param builder to build proto from given yaml string.
     */
    public static void yamlToProto(final String yaml, final Message.Builder builder) {
        String json = JSONUtil.yamlToJson(yaml);
        jsonToProto(json, builder);
    }
}
