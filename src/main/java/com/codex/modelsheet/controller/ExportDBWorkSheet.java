package com.codex.modelsheet.controller;

import cloud.thoughtspotstaging.champagne.TSClient;
import cloud.thoughtspotstaging.champagne.controllers.SessionController;
import cloud.thoughtspotstaging.champagne.controllers.TMLController;
import cloud.thoughtspotstaging.champagne.exceptions.ApiException;
import com.codex.modelsheet.helper.ConfigInfo;
import com.codex.modelsheet.util.JSONUtil;
import com.codex.modelsheet.util.Util;
import sun.misc.IOUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class ExportDBWorkSheet extends  BaseController{

    TSClient client = null;
    public ExportDBWorkSheet() throws Exception {
        super();
    }

    public void exportWorkSheet(String workSheetId, boolean isWorkSheet ) throws IOException, ApiException {

        client = BaseController.createConfiguration();
        String authToken = getAuthToken();
        TMLController controller = client.getTMLController();

        String accept = "text/plain";
        String xRequestedBy = "ThoughtSpot";
        String username = ConfigInfo.getConfigs().getProperty("username");

        controller.export(username, authToken, accept, xRequestedBy, workSheetId);

        String response = new BufferedReader(
                new InputStreamReader(httpResponse.getResponse().getRawBody(), StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.joining("\n"));
        //System.out.println("Export Worksheet Response: "+response);
        Map<String, String> tmls = JSONUtil.getGsonUI().fromJson(response, Map.class);
        byte[] zipBytes = Util.createZip(tmls);
        Util.writeFile(ConfigInfo.getConfigs().getProperty("filename"), zipBytes);
    }
    public String getAuthToken() throws IOException, ApiException {

        String contentType = "application/x-www-form-urlencoded";
        String accept = "text/plain";
        String xRequestedBy = "ThoughtSpot";
        String username = ConfigInfo.getConfigs().getProperty("username");//"srikanth.jandhyala@thoughtspot.com";
        String password = ConfigInfo.getConfigs().getProperty("password");//"Test@1986";
        String access_level = "FULL";
        // Set callback and perform API call
        SessionController sessionControl = client.getSessionController();
        sessionControl.getAuthToken(password, username, access_level, contentType, accept, xRequestedBy);
        String authToken = new String(IOUtils.readFully(httpResponse.getResponse().getRawBody(), -1, true));
        System.out.println(authToken);
            //controller.login(username, password, rememberme, contentType, accept, xRequestedBy);
        return  authToken;
    }
}
