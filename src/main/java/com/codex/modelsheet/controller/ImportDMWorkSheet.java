package com.codex.modelsheet.controller;

import cloud.thoughtspotstaging.champagne.TSClient;
import cloud.thoughtspotstaging.champagne.controllers.SessionController;
import cloud.thoughtspotstaging.champagne.controllers.TMLController;
import cloud.thoughtspotstaging.champagne.exceptions.ApiException;
import com.codex.modelsheet.helper.ConfigInfo;
import sun.misc.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class ImportDMWorkSheet extends BaseController{
    TSClient client = null;
    public ImportDMWorkSheet() throws Exception {
        super();
    }

    public void importWorkSheet(String jsonArrayTmls) throws IOException, ApiException {
        //Input of import worksheet looks below
        client = BaseController.createConfiguration();
        String authToken = getAuthToken();
        TMLController controller = client.getTMLController();

        String accept = "text/plain";
        String username = ConfigInfo.getConfigs().getProperty("username");

        controller.importWorksheet(username, jsonArrayTmls, authToken, accept);

        String response = new BufferedReader(
                new InputStreamReader(httpResponse.getResponse().getRawBody(), StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.joining("\n"));
        //System.out.println("Export Worksheet Response: "+response);
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
        //System.out.println(authToken);
        //controller.login(username, password, rememberme, contentType, accept, xRequestedBy);
        return  authToken;
    }
}
