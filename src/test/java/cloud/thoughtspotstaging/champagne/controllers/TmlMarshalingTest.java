/*
 * TSLib
 *
 * This file was automatically generated by APIMATIC v2.0 ( https://apimatic.io ).
 */

package cloud.thoughtspotstaging.champagne.controllers;

import static org.junit.Assert.assertEquals;

import cloud.thoughtspotstaging.champagne.TSClient;
import cloud.thoughtspotstaging.champagne.exceptions.ApiException;
import com.codex.modelsheet.model.EDoc;
import com.codex.modelsheet.util.ProtoUtils;
import org.junit.*;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import sun.misc.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TmlMarshalingTest extends BaseControllerTest {

    /**
     * Client instance.
     */
    private static TSClient client;
    
    /**
     * Controller instance (for all tests).
     */
    private static TMLController controller;
    private static SessionController sessionControl;
    private String authToken = "";
    private String userId = "";
    final String password = "Test@1234";
    String content = "";
    /**
     * Setup test class.
     */
    @BeforeClass
    public static void setUpClass() {
        client = createConfiguration();
        controller = client.getTMLController();
        sessionControl = client.getSessionController();
    }

    @Before
     public void getAuthToken() {

        String contentType = "application/x-www-form-urlencoded";
        String accept = "text/plain";
        String xRequestedBy = "ThoughtSpot";
        String username = "tsadmin";//"srikanth.jandhyala@thoughtspot.com";
        String password = "admin";//"Test@1986";

        String serviceKey = "3e59ccca-cb3e-40df-838f-c8210320a2ce";
        String access_level = "FULL";

        // Set callback and perform API call
        try {
            sessionControl.getAuthToken(password, username, access_level, contentType, accept, xRequestedBy);
            authToken = new String(IOUtils.readFully(httpResponse.getResponse().getRawBody(), -1, true));
            System.out.println(authToken);
            //controller.login(username, password, rememberme, contentType, accept, xRequestedBy);
        } catch (ApiException | IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Tear down test class.
     */
    @AfterClass
    public static void tearDownClass() {
        controller = null;
    }

    /**
     * Test case for user/list.
     * @throws Throwable exception if occurs.
     */
    @Test
    public void exportWorksheet() throws Exception {
        String accept = "text/plain";
        String xRequestedBy = "ThoughtSpot";
        String username = "tsadmin";//"srikanth.jandhyala@thoughtspot.com";
        // Set callback and perform API call
        try {
            controller.export(username, authToken, accept, xRequestedBy);
        } catch (ApiException e) {
            // Empty block
        }
        // Test response code
        assertEquals("Status is not 200",
                200, httpResponse.getResponse().getStatusCode());

        content = new BufferedReader(
                new InputStreamReader(httpResponse.getResponse().getRawBody(), StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.joining("\n"));
        System.out.println("Export Worksheet Response: "+content);

        EDoc.ObjectEDocProto.Builder tableBuilder = EDoc.ObjectEDocProto.newBuilder();
        //TML to Object
        ProtoUtils.yamlToProto(content, tableBuilder);
        System.out.println("TML to Object: "+tableBuilder.toString());

        //Object to TML
        EDoc.ObjectEDocProto objectEDocProto = tableBuilder.build();
        String tml =  ProtoUtils.protoToYaml(objectEDocProto);
        System.out.println("Object to TML: "+tml);
    }
}
