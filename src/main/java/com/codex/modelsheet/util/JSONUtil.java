/*
 * Copyright: ThoughtSpot Inc. 2012
 */
package com.codex.modelsheet.controller;

import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.math.DoubleMath;

import com.google.gson.JsonParseException;
import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.Message;
import com.googlecode.protobuf.format.JsonFormat;
import com.googlecode.protobuf.format.JsonFormat.ParseException;
import org.yaml.snakeyaml.error.YAMLException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

/**
 * Utility methods related to JSON processing.
 *
 * @author Vijay Ganesan
 */
public final class JSONUtil {

    /**
     * Double value representing tolerance in number comparison.
     */
    private static final double TOLERANCE = 0.1;


    /**
     * Hide constructor.
     */
    private JSONUtil() {

    }
    /**
     * @param proto to conver to JSON
     * @return JSON form of given proto
     */
    public static String protoToJSON(final Message proto) {
        return JsonFormat.printToString(proto);
    }


    /**
     * @param json string to generate proto from
     * @param builder to build proto from given json
     */
    public static void jsonToProto(final String json, final Message.Builder builder) {
        try {
            JsonFormat.merge(json, builder);
        } catch (ParseException e) {
            throw new JsonParseException(e);
        }
    }

    /**
     * @param json json string to convert to yaml.
     * @return Yaml representation of given json string.
     */
    public static String jsonToYaml(String json) {
        try {
            JsonNode jsonNode = new ObjectMapper().readTree(json);
            return getYamlMapper().writeValueAsString(jsonNode);
        } catch (IOException e) {
            throw new JsonParseException(e);
        }
    }

    /**
     * @param yaml Yaml string to convert to json.
     * @return Json representation for given yaml string.
     */
    public static String yamlToJson(String yaml) {
        try {
            YAMLMapper yamlMapper = getYamlMapper();
            ObjectMapper yamlReader = new ObjectMapper(yamlMapper.getFactory());
            Object obj = yamlReader.readValue(yaml, Object.class);
            ObjectMapper jsonWriter = new ObjectMapper();
            return jsonWriter.writeValueAsString(obj);
        } catch (IOException e) {
            throw new YAMLException(e);
        }
    }

    /**
     * YAML mapper with specific properties set.
     *  - Reduce number of quotes
     *  - Allow comments in the YAML file
     *  - Do not split lines when writing a long string to increase readability.
     *
     * @return YAML mapper object with specific configuration.
     */
    private static YAMLMapper getYamlMapper() {
        YAMLMapper yamlMapper = new YAMLMapper();
        yamlMapper.configure(Feature.ALLOW_COMMENTS, true);
        yamlMapper.configure(YAMLGenerator.Feature.MINIMIZE_QUOTES, true);
        yamlMapper.configure(YAMLGenerator.Feature.SPLIT_LINES, false);
        yamlMapper.configure(YAMLGenerator.Feature.WRITE_DOC_START_MARKER, false);
        yamlMapper.configure(YAMLGenerator.Feature.ALWAYS_QUOTE_NUMBERS_AS_STRINGS, true);
        return yamlMapper;
    }
}
