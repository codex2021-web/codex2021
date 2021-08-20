/*
 * TSLib
 *
 * This file was automatically generated by APIMATIC v2.0 ( https://apimatic.io ).
 */

package com.codex.modelsheet.controller;

import cloud.thoughtspotstaging.champagne.Environment;
import cloud.thoughtspotstaging.champagne.TSClient;
import com.codex.modelsheet.helper.HttpCallbackCatcher;

/**
 * Base class for all test cases.
 */
public class BaseController {
    /**
     * Test configuration.
     */
    public static final int REQUEST_TIMEOUT = 30;

    public static final double ASSERT_PRECISION = 0.01;

    /**
     * Test fixtures,
     * Used to serve as HttpCallback and to capture request & response.
     */

    protected static HttpCallbackCatcher httpResponse;

    public BaseController() throws Exception {
        httpResponse = new HttpCallbackCatcher();
    }
    /**
     * Create test configuration from Environment variables.
     */
    protected static TSClient createConfigurationFromEnvironment() {
        TSClient.Builder builder = new TSClient.Builder();

        final String environment = System.getenv("TS_LIB_ENVIRONMENT");
        final String timeout = System.getenv("TS_LIB_TIMEOUT");

        if (environment != null) {
            builder.environment(Environment.fromString(environment));
        }
        if (timeout != null) {
            builder.httpClientConfig(configBuilder -> configBuilder.timeout(
                    Long.parseLong(timeout)));
        }
        return builder.build();
    }


    /**
     * Create test configuration.
     */
    protected static TSClient createConfiguration() {
        // Set Configuration parameters for test execution
        TSClient config = createConfigurationFromEnvironment();
        config = config.newBuilder()
                .environment(Environment.PRODUCTION)
                .httpCallback(httpResponse)
                .build();
        return config;
    }


}
