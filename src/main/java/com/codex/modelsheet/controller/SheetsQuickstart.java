package com.codex.modelsheet.controller;

import com.codex.modelsheet.util.Util;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import java.io.*;
import java.security.GeneralSecurityException;
public class SheetsQuickstart {

    /**
     * Prints the names and majors of students in a sample spreadsheet:
     * https://docs.google.com/spreadsheets/d/1BxiMVs0XRA5nFMdKvBdBZjgmUUqptlbs74OgvE2upms/edit
     */
    public Sheets getService() throws IOException, GeneralSecurityException {
        // Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        //final String spreadsheetId = "1yfcgRdGqc540HLdwO26Zar83aLXjatkBeCUhCyDBFqY";
        //final String range = "TABLE!A1:F";
        Sheets service = new Sheets.Builder(HTTP_TRANSPORT, Util.JSON_FACTORY, Util.getCredentials(HTTP_TRANSPORT))
                .setApplicationName(Util.APPLICATION_NAME)
                .build();
        return service;
    }
    public ValueRange getSingleSheet(Sheets service, String spreadsheetId, String range) throws IOException {
        ValueRange response = service.spreadsheets().values()
                .get(spreadsheetId, range)
                .execute();
        return response;
    }

}