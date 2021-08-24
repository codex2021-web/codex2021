/*
 * TSLib
 *
 * This file was automatically generated by APIMATIC v2.0 ( https://apimatic.io ).
 */

package cloud.thoughtspotstaging.champagne.controllers;

import cloud.thoughtspotstaging.champagne.ApiHelper;
import cloud.thoughtspotstaging.champagne.AuthManager;
import cloud.thoughtspotstaging.champagne.Configuration;
import cloud.thoughtspotstaging.champagne.exceptions.ApiException;
import cloud.thoughtspotstaging.champagne.http.Headers;
import cloud.thoughtspotstaging.champagne.http.client.HttpCallback;
import cloud.thoughtspotstaging.champagne.http.client.HttpClient;
import cloud.thoughtspotstaging.champagne.http.client.HttpContext;
import cloud.thoughtspotstaging.champagne.http.request.HttpRequest;
import cloud.thoughtspotstaging.champagne.http.response.HttpResponse;
import cloud.thoughtspotstaging.champagne.models.DynamicResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * This class lists all the endpoints of the groups.
 */
public final class TMLController extends BaseController {

    /**
     * Initializes the controller.
     * @param config    Configurations added in client.
     * @param httpClient    Send HTTP requests and read the responses.
     * @param authManagers    Apply authorization to requests.
     */
    public TMLController(Configuration config, HttpClient httpClient,
                         Map<String, AuthManager> authManagers) {
        super(config, httpClient, authManagers);
    }

    /**
     * Initializes the controller with HTTPCallback.
     * @param config    Configurations added in client.
     * @param httpClient    Send HTTP requests and read the responses.
     * @param authManagers    Apply authorization to requests.
     * @param httpCallback    Callback to be called before and after the HTTP call.
     */
    public TMLController(Configuration config, HttpClient httpClient,
                         Map<String, AuthManager> authManagers, HttpCallback httpCallback) {
        super(config, httpClient, authManagers, httpCallback);
    }

    /**
     * Api to get all users, groups and their inter-dependencies.
     * @return    Returns the DynamicResponse response from the API call
     * @throws    ApiException    Represents error response from the server.
     * @throws    IOException    Signals that an I/O exception of some sort has occurred.
     */
    public DynamicResponse importWorksheet(
            final String userName,
            final String content,
            final String authToken,
            final String accept
    ) throws ApiException, IOException {
        HttpRequest request = buildImportWorksheetRequest(userName, content, authToken, accept);
        HttpResponse response = getClientInstance().execute(request, false);

        HttpContext context = new HttpContext(request, response);

        return handleUserListResponse(context);
    }
    /**
     * Api to get all users, groups and their inter-dependencies.
     * @param  accept  Required parameter: Example: application/json
     * @param  xRequestedBy  Required parameter: Example: ThoughtSpot
     * @return    Returns the DynamicResponse response from the API call
     * @throws    ApiException    Represents error response from the server.
     * @throws    IOException    Signals that an I/O exception of some sort has occurred.
     */
    public DynamicResponse export(//username, authToken, accept, xRequestedBy
                                    final String username,
                                    final String authToken,
                                    final String accept,
                                    final String xRequestedBy,
                                    final String workSheetId,
                                    final boolean isWorkSheet
    ) throws ApiException, IOException {
        HttpRequest request = buildExportRequest(username, authToken, accept, xRequestedBy, workSheetId, isWorkSheet);

        HttpResponse response = getClientInstance().execute(request, false);

        HttpContext context = new HttpContext(request, response);

        return handleUserListResponse(context);
    }

    /**
     * Api to get all users, groups and their inter-dependencies.
     * @return    Returns the DynamicResponse response from the API call
     * @throws    ApiException    Represents error response from the server.
     * @throws    IOException    Signals that an I/O exception of some sort has occurred.
     */
    public DynamicResponse userCreate(
            final String name,
            final String password,
            final String displayname,
            final String properties,
            final String groups,
            final String usertype,
            final String tenantid,
            final String visibility,
            final String authToken) throws ApiException, IOException {
        HttpRequest request = buildUserCreateRequest(name,
        password,
        displayname,
        properties,
        groups,
        usertype,
        tenantid,
        visibility,
                authToken);

        //WebUt.getPathWithinApplication(WebUtils.toHttp(request));

        HttpResponse response = getClientInstance().execute(request, false);

        HttpContext context = new HttpContext(request, response);

        return handleUserListResponse(context);
    }

    /**
     * Api to get all users, groups and their inter-dependencies.
     * @param  accept  Required parameter: Example: application/json
     * @param  xRequestedBy  Required parameter: Example: ThoughtSpot
     * @return    Returns the DynamicResponse response from the API call
     */



    /**
     * Builds the HttpRequest object for userList.
     */
    private HttpRequest buildUserListRequest(
            final String userName,
            final String authToken,
            final String accept,
            final String xRequestedBy) {
        //the base uri for api requests
        String baseUri = config.getBaseUri();
        //prepare query string for API call
        final StringBuilder queryBuilder = new StringBuilder(baseUri
                + "/tspublic/v1/user/list");
        //System.out.println(queryBuilder.toString());
        //load all headers for the outgoing API request
        Headers headers = new Headers();
        headers.add("user-agent", BaseController.userAgent);
        headers.add("Accept", accept);
        headers.add("X-Requested-By", xRequestedBy);
        headers.add("UserName", userName);
        headers.add("Authorization", "Bearer "+authToken);

        HttpRequest request = getClientInstance().get(queryBuilder, headers, null, null);

        return request;
    }

    /**
     * Builds the HttpRequest object for userList.
     */
    private HttpRequest buildExportRequest(
            final String userName,
            final String authToken,
            final String accept,
            final String xRequestedBy,
            final String workSheetId,
            final boolean isWorkSheet) {
        //the base uri for api requests
        String baseUri = config.getBaseUri();
        //prepare query string for API call
        final StringBuilder queryBuilder = new StringBuilder(baseUri).append("/tspublic/v1/metadata/tml/exportmodelsheet");
                //+ "/metadata/edoc/").append("WORKSHEET").append("/").append(workSheetId);
        //System.out.println(queryBuilder.toString());
        //load all headers for the outgoing API request
        Headers headers = new Headers();
        headers.add("user-agent", BaseController.userAgent);
        headers.add("Accept", accept);
        headers.add("X-Requested-By", xRequestedBy);
        headers.add("UserName", userName);
        headers.add("Authorization", "Bearer "+authToken);

        Map<String, Object> formParameters = new HashMap<>();
        formParameters.put("export_names", workSheetId);
        formParameters.put("isworksheet", isWorkSheet);

        HttpRequest request = getClientInstance().post(queryBuilder, headers, null, ApiHelper.prepareFormFields(formParameters));

        return request;
    }

    /**
     * Builds the HttpRequest object for userList.
     */
    private HttpRequest buildUserCreateRequest(
            final String name,
            final String password,
            final String displayname,
            final String properties,
            final String groups,
            final String usertype,
            final String tenantid,
            final String visibility,
            final String authToken) {
        //the base uri for api requests
        String baseUri = config.getBaseUri();
        //prepare query string for API call
        final StringBuilder queryBuilder = new StringBuilder(baseUri
                + "/tspublic/v1/user");
        //System.out.println(queryBuilder.toString());
        //load all headers for the outgoing API request
        Headers headers = new Headers();
        headers.add("user-agent", BaseController.userAgent);
        headers.add("Accept", "application/json");
        headers.add("X-Requested-By", "ThoughtSpot");
        headers.add("UserName", "tsadmin");
        headers.add("Authorization", "Bearer "+authToken);

        Map<String, Object> formParameters = new HashMap<>();
        formParameters.put("name", name);
        formParameters.put("password", password);
        formParameters.put("displayname", displayname);
        formParameters.put("properties", properties);
        formParameters.put("groups", groups);
        formParameters.put("usertype", usertype);
        formParameters.put("tenantid", tenantid);
        formParameters.put("visibility", visibility);

        HttpRequest request = getClientInstance().post(queryBuilder, headers, null, ApiHelper.prepareFormFields(formParameters));

        return request;
    }

    /**
     * Builds the HttpRequest object for userList.
     */
    private HttpRequest buildImportWorksheetRequest(
            final String userName,
            final String content,
            final String authToken,
            final String accept) {
        //the base uri for api requests
        String baseUri = config.getBaseUri();
        //prepare query string for API call
        final StringBuilder queryBuilder = new StringBuilder(baseUri
                + "/metadata/edoc/importEPack");
        //System.out.println(queryBuilder.toString());
        //load all headers for the outgoing API request
        Headers headers = new Headers();
        headers.add("user-agent", BaseController.userAgent);
        headers.add("Accept", accept);
        headers.add("X-Requested-By", "ThoughtSpot");
        headers.add("UserName", userName);
        headers.add("Authorization", "Bearer "+authToken);

        Map<String, Object> formParameters = new HashMap<>();
        formParameters.put("request", content);

        HttpRequest request = getClientInstance().post(queryBuilder, headers, null, ApiHelper.prepareFormFields(formParameters));

        return request;
    }

    /**
     * Builds the HttpRequest object for userList.
     */
    private HttpRequest buildUserDeleteRequest(
            final String userid,
            final String authToken) {
        //the base uri for api requests
        String baseUri = config.getBaseUri();
        //prepare query string for API call
        final StringBuilder queryBuilder = new StringBuilder(baseUri
                + "/tspublic/v1/user/").append(userid);
        //System.out.println(queryBuilder.toString());
        //load all headers for the outgoing API request
        Headers headers = new Headers();
        headers.add("user-agent", BaseController.userAgent);
        headers.add("Accept", "application/json");
        headers.add("X-Requested-By", "ThoughtSpot");
        headers.add("UserName", "tsadmin");
        headers.add("Authorization", "Bearer "+authToken);

        Map<String, Object> formParameters = new HashMap<>();
        formParameters.put("userid", userid);

        HttpRequest request = getClientInstance().delete(queryBuilder, headers, null, ApiHelper.prepareFormFields(formParameters));

        return request;
    }

    /**
     * Processes the response for userList.
     * @return An object of type DynamicResponse
     */
    private DynamicResponse handleUserListResponse(
            HttpContext context) throws ApiException, IOException {
        HttpResponse response = context.getResponse();

        //invoke the callback after response if its not null
        if (getHttpCallback() != null) {
            getHttpCallback().onAfterResponse(context);
        }

        //handle errors defined at the API level
        validateResponse(response, context);

        //extract result from the http response
        DynamicResponse result = new DynamicResponse(response);

        return result;
    }

}